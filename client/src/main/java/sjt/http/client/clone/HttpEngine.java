package sjt.http.client.clone;

import com.squareup.okhttp.internal.Platform;
import com.squareup.okhttp.internal.Util;
import sjt.http.client.ResponseSource;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSocketFactory;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.*;
import java.util.*;
import java.util.zip.GZIPInputStream;

import static com.squareup.okhttp.internal.http.HttpEngine.getDefaultUserAgent;
import static sjt.http.client.clone.Util.EMPTY_BYTE_ARRAY;
import static sjt.http.client.clone.Util.getDefaultPort;

public class HttpEngine {
    private static final CacheResponse GATEWAY_TIMEOUT_RESPONSE = new CacheResponse() {
        @Override
        public Map<String, List<String>> getHeaders() throws IOException {
            Map<String, List<String>> result = new HashMap<>();
            result.put(null, Collections.singletonList("HTTP/1.1 504 Gateway Timeout"));
            return result;
        }

        @Override
        public InputStream getBody() throws IOException {
            return new ByteArrayInputStream(EMPTY_BYTE_ARRAY);
        }
    };

    protected final HttpUrlConnectionImpl policy;

    protected final String method;

    private ResponseSource responseSource;

    protected Connection connection;
    protected RouterSelector routerSelector;
    private OutputStream requestBodyOut;

    private Transport transport;

    private InputStream responseTransferIn;
    private InputStream responseBodyIn;

    private CacheResponse cacheResponse;
    private CacheRequest cacheRequest;

    private boolean transferGzip;

    private boolean transparentGzip;

    long sentRequestMillis = -1;

    final URI uri;

    final RequestHeaders requestHeaders;

    ResponseHeaders responseHeaders;

    private ResponseHeaders cachedResponseHeaders;

    private InputStream cachedResponseBody;

    private boolean automaticallyReleaseConnectionToPool;

    private boolean connectionReleased;

    public HttpEngine(HttpUrlConnectionImpl policy, String method, RawHeaders requestHeaders, Connection connection, RetryableOutputStream requestBodyOut) throws IOException {
        this.policy = policy;
        this.method = method;
        try {
            uri = Platform.get().toUriLenient(policy.getURL());
        } catch (URISyntaxException e) {
            throw new IOException(e.getMessage());
        }

        this.requestHeaders = new RequestHeaders(uri, new RawHeaders(requestHeaders));
    }

    public void sendRequest() throws IOException {
        if (responseSource != null) {
            return;
        }

        prepareRawRequestHeaders();
        initResponseSource();

        if (policy.responseCache != null) {
            policy.responseCache.trackResponse(responseSource);
        }

        if (requestHeaders.isOnlyIfCached() && responseSource.requiresConnection()) {
            if (responseSource == ResponseSource.CONDITIONAL_CACHE) {
                Util.closeQuietly(cachedResponseBody);
            }
            this.responseSource = ResponseSource.CACHE;
            this.cacheResponse = GATEWAY_TIMEOUT_RESPONSE;
            RawHeaders rawResponseHeaders = RawHeaders.fromMultimap(cacheResponse.getHeaders(), true);
            setResponse(new ResponseHeaders(uri, rawResponseHeaders), cacheResponse.getBody());
        }

        if (responseSource.requiresConnection()) {
            sendSocketRequest();
        } else if (connection != null) {
            policy.connectionPool.recycle(connection);
            policy.getFailedRoutes().remove(connection.getRoute());
            connection = null;
        }
    }

    private void setResponse(ResponseHeaders headers, InputStream body) throws IOException {
        if (this.responseBodyIn != null) {
            throw new IllegalStateException();
        }
        this.responseHeaders = headers;
        if (body != null) {
            initContentStream(body);
        }
    }

    private void initContentStream(InputStream transferStream) throws IOException {
        responseTransferIn = transferStream;
        if (transferGzip && responseHeaders.isContentEncodingGzip()) {
            responseHeaders.stripContentEncoding();
            responseHeaders.stripContentLength();
            responseBodyIn = new GZIPInputStream(transferStream);
        } else {
            responseBodyIn = transferStream;
        }
    }

    private void initResponseSource() throws IOException {
        responseSource = ResponseSource.NETWORK;
        if (!policy.getUseCaches() || policy.responseCache == null) {
            return;
        }

        CacheResponse candidate =
                policy.responseCache.get(uri, method, requestHeaders.getHeaders().toMultimap(false));

        if (candidate == null) {
            return;
        }

        Map<String, List<String>> responseHeadersMap = candidate.getHeaders();
        cachedResponseBody = candidate.getBody();
        if (!acceptCacheResponseType(candidate)
                || responseHeadersMap == null
                || cachedResponseBody == null) {
            Util.closeQuietly(cachedResponseBody);
            return;
        }

        RawHeaders rawResponseHeaders = RawHeaders.fromMultimap(responseHeadersMap, true);
        cachedResponseHeaders = new ResponseHeaders(uri, rawResponseHeaders);
        long now = System.currentTimeMillis();
        if (responseSource == ResponseSource.CACHE) {
            this.responseSource = cachedResponseHeaders.chooseResponseSource(now, requestHeaders);
            setResponse(cachedResponseHeaders, cachedResponseBody);
        } else if (responseSource == ResponseSource.CONDITIONAL_CACHE) {
            this.cacheResponse = candidate;
        } else if (responseSource == ResponseSource.NETWORK) {
            Util.closeQuietly(cachedResponseBody);
        } else {
            throw new AssertionError();
        }

    }

    private boolean acceptCacheResponseType(CacheResponse candidate) {
        return true;
    }

    private void sendSocketRequest() throws UnknownHostException {
        if (connection == null) {
            connect();
        }

        if (transport != null) {
            throw new IllegalStateException();
        }

        transport = (Transport) connection.newTranport(this);

        if (hasRequestBody() && requestBodyOut == null) {
            requestBodyOut = transport.createRequestBody();
        }
    }

    protected final void connect() throws UnknownHostException {
        if (connection != null) {
            return;
        }

        if (routerSelector == null) {
            String uriHost  = uri.getHost();
            if (uriHost == null) {
                throw new UnknownHostException(uri.toString());
            }
            SSLSocketFactory sslSocketFactory = null;
            HostnameVerifier hostnameVerifier = null;
            if (uri.getScheme().equalsIgnoreCase("https")) {
                sslSocketFactory = policy.sslSocketFactory;
                hostnameVerifier = policy.hostnameVerifier;
            }
        }
    }

    private void prepareRawRequestHeaders() throws IOException {
        requestHeaders.getHeaders().setRequestLine(getRequestLine());

        if (requestHeaders.getUserAgent() == null) {
            requestHeaders.setUserAgent(getDefaultUserAgent());
        }

        if (requestHeaders.getHosts() == null) {
            requestHeaders.setHost(getOriginAddress(policy.getURL()));
        }

        if ((connection == null || connection.getHttpMinorVersion() != 0)
                && requestHeaders.getConnection() == null) {
            requestHeaders.setConnection("Keep-Alive");
        }

        if (requestHeaders.getAcceptEncoding() == null) {
            transparentGzip = true;
            requestHeaders.setAcceptEncoding("gzip");
        }

        if (hasRequestBody() && requestHeaders.getContentType() == null) {
            requestHeaders.setContentType("application/x-www-form-urlencoded");
        }

        long ifModifiedSince = policy.getIfModifiedSince();
        if (ifModifiedSince != 0) {
            requestHeaders.setIfModifiedSince(new Date(ifModifiedSince));
        }

        CookieHandler cookieHandler = policy.cookieHandler;
        if (cookieHandler != null) {
            requestHeaders.addCookies(
                    cookieHandler.get(uri, requestHeaders.getHeaders().toMultimap(false)));
        }
    }

    boolean hasRequestBody() {
        return method.equals("POST") || method.equals("PUT");
    }

    public static String getOriginAddress(URL url) {
        int port = url.getPort();
        String result = url.getHost();
        if (port > 0 && port != getDefaultPort(url.getProtocol())) {
            result = result + ":" + port;
        }
        return result;
    }

    private String getRequestLine() {
        return null;
    }

    public boolean hasResponse() {
        return responseHeaders != null;
    }

    public final InputStream getResponseBody() {
        if (responseHeaders == null) {
            throw new IllegalStateException();
        }
        return responseBodyIn;
    }

    public final void release(boolean streamCancelled) {
        if (responseBodyIn == cachedResponseBody) {
            Util.closeQuietly(responseBodyIn);
        }

        if (!connectionReleased && connection != null) {
            connectionReleased = true;

            if (transport == null || !transport.makeReusable(streamCancelled, requestBodyOut, responseTransferIn)) {
                Util.closeQuietly(connection);
                connection = null;
            } else if (automaticallyReleaseConnectionToPool) {
                policy.connectionPool.recycle(connection);
                connection = null;
            }
        }
    }

    public void readResponse() throws IOException {
        if (hasResponse()) {
            responseHeaders.setResponseSource(responseSource);
            return;
        }

        if (responseSource == null) {
            throw new IllegalStateException("readResponse() without sendRequest()");
        }

        if (!responseSource.requiresConnection()) {
            return;
        }

        if (sentRequestMillis == -1) {
            if (requestBodyOut instanceof RetryableOutputStream) {
                int contentLength = ((RetryableOutputStream) requestBodyOut).contentLength();
                requestHeaders.setContentLength(contentLength);
            }
            transport.writeRequestHeaders();
        }

        if (requestBodyOut != null) {
            requestBodyOut.close();
            if (requestBodyOut instanceof RetryableOutputStream) {
                transport.writeRequestBody((RetryableOutputStream) requestBodyOut);
            }
        }

        transport.flushRequest();

        responseHeaders = transport.readResponseHeaders();
        responseHeaders.setLocalTimestamps(sentRequestMillis, System.currentTimeMillis());
        responseHeaders.setResponseSource(responseSource);

        if (responseSource == ResponseSource.CONDITIONAL_CACHE) {
            if (cachedResponseHeaders.validate(responseHeaders)) {
                release(false);
                ResponseHeaders combinedHeaders = cachedResponseHeaders.combine(responseHeaders);
                setResponse(combinedHeaders, cachedResponseBody);
                policy.responseCache.trackConditionalCacheHit();
                policy.responseCache.update(cacheResponse, policy.getHttpConnectionToCache());
                return;
            } else {
                Util.closeQuietly(cachedResponseBody);
            }
        }

        if (hasResponseBody()) {
            maybeCache();
        }

        initContentStream(transport.getTransferStream(cacheRequest));
    }

    private void maybeCache() {


    }

    private boolean hasResponseBody() {
        return false;
    }

    public final void automaticallyReleaseConnectionToPool() {
        automaticallyReleaseConnectionToPool = true;
        if (connection != null && connectionReleased) {
            policy.connectionPool.recycle(connection);
            connection = null;
        }
    }

    public final  OutputStream getRequestBody() {
        if (responseSource == null) {
            throw new IllegalStateException();
        }
        return requestBodyOut;
    }

    public int getResponseCode() {
        if (responseHeaders == null) {
            throw new IllegalStateException();
        }
        return responseHeaders.getHeaders().getResponseCode();
    }

    public Connection getConnection() {
        return connection;
    }
}
