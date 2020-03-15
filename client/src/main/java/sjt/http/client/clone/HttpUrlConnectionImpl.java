package sjt.http.client.clone;

import com.squareup.okhttp.internal.Util;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSocketFactory;
import java.io.IOException;
import java.io.OutputStream;
import java.net.*;
import java.util.List;
import java.util.Set;

public class HttpUrlConnectionImpl extends HttpURLConnection {

    final Proxy requestedProxy;

    private final RawHeaders rawRequestHeaders = new RawHeaders();

    final ProxySelector proxySelector;
    final CookieHandler  cookieHandler;
    final OkResponseCache responseCache;
    final ConnectionPool connectionPool;

    SSLSocketFactory sslSocketFactory;
    HostnameVerifier hostnameVerifier;
    List<String> transports;
    OkAuthenticator authenticator;

    final Set<Route> failedRoutes;

    protected IOException httpEngineFailure;
    protected HttpEngine httpEngine;

    protected HttpUrlConnectionImpl(URL url, OkHttpClient client, OkResponseCache responseCache, Set<Route> failedRoutes) {
        super(url);
        this.failedRoutes = failedRoutes;
        this.connectionPool = client.getConnectionPool();
        this.requestedProxy = client.getProxy();
        this.proxySelector = client.getProxySelector();
        this.cookieHandler = client.getCookieHandler();
        this.sslSocketFactory = client.getSslSocketFactory();
        this.hostnameVerifier = client.getHostnameVerifier();
        this.transports = client.getTransports();
        this.authenticator = client.getAuthenticator();
        this.responseCache = responseCache;
    }

    @Override
    public void connect() throws IOException {
        initHttpEngine();
        boolean success;
        do {
            success = execute(false);
        } while (!success);
    }

    @Override
    public void disconnect() {
        if (httpEngine != null) {
            if (httpEngine.hasResponse()) {
                Util.closeQuietly(httpEngine.getResponseBody());
            }
            httpEngine.release(true);
        }
    }

    @Override
    public boolean usingProxy() {
        return (requestedProxy != null && requestedProxy.type() != Proxy.Type.DIRECT);
    }

    private boolean execute(boolean readResponse) throws IOException {
        try {
            httpEngine.sendRequest();
            if (readResponse) {
                httpEngine.readResponse();
            }
            return true;
        } catch (IOException e) {
            if (handleFailure(e)) {
                return false;
            } else {
                throw e;
            }
        }
    }

    private boolean handleFailure(IOException e) {
        return false;
    }

    private void initHttpEngine() throws IOException {
        if (httpEngineFailure != null) {
            throw httpEngineFailure;
        } else if (httpEngine != null) {
            return;
        }

        connected = true;
        try {
            if (doOutput) {
                if (method.equals("GET")) {
                    method = "POST";
                } else if (!method.equals("POST") && !method.equals("PUT")) {
                    throw new ProtocolException(method + " does not support writing");
                }
            }
            httpEngine = newHttpEngine(method, rawRequestHeaders, null, null);
        } catch (IOException e) {
            httpEngineFailure = e;
            throw e;
        }
    }

    protected HttpURLConnection getHttpConnectionToCache() {
        return this;
    }

    private HttpEngine newHttpEngine(String method, RawHeaders requestHeaders, Connection connection, RetryableOutputStream requestBody) throws IOException {
        if (url.getProtocol().equals("http")) {
            return new HttpEngine(this, method, requestHeaders, connection, requestBody);
        } else if (url.getProtocol().equals("https")) {
            return new HttpUrlConnectionImpl.HttpsEngine(this, method, requestHeaders, connection, requestBody);
        } else {
            throw new AssertionError();
        }
    }

    Set<Route> getFailedRoutes() {
        return failedRoutes;
    }

    public static final class HttpsEngine extends HttpEngine {
        public HttpsEngine(HttpUrlConnectionImpl customHttpUrlConnection, String method, RawHeaders requestHeaders, Connection connection, RetryableOutputStream requestBody) throws IOException {
            super(customHttpUrlConnection, method, requestHeaders, connection, requestBody);
        }
    }


    @Override
    public String getHeaderField(String fieldName) {
        try {
            RawHeaders rawHeaders = getResponse().getResponseHeaders().getHeaders();
            return fieldName == null ? rawHeaders.getStatusLine() : rawHeaders.get(fieldName);
        } catch (IOException e) {
            return null;
        }
    }

    private HttpEngine getResponse() throws IOException {
        initHttpEngine();

        if (httpEngine.hasResponse()) {
            return httpEngine;
        }

        while (true) {
            if (!execute(true)) {
                continue;
            }

            Retry retry = processResponseHeaders();
            if (retry == Retry.NONE) {
                httpEngine.automaticallyReleaseConnectionToPool();
                return httpEngine;
            }

            String retryMethod = method;
            OutputStream requestBody = httpEngine.getRequestBody();

            int responseCode = getResponseCode();
            if (responseCode == HTTP_MULT_CHOICE
            || responseCode == HTTP_MOVED_PERM
            || responseCode == HTTP_MOVED_TEMP
            || responseCode == HTTP_SEE_OTHER) {
                retryMethod = "GET";
                requestBody = null;
            }

            if (requestBody != null && !(requestBody instanceof RetryableOutputStream)) {
                throw new HttpRetryException("Cannot retry streamed HTTP body",
                        httpEngine.getResponseCode());
            }

            if (retry == Retry.DIFFERENT_CONNECTION) {
                httpEngine.automaticallyReleaseConnectionToPool();
            }

            httpEngine.release(false);

            httpEngine = newHttpEngine(retryMethod, rawRequestHeaders, httpEngine.getConnection(),
                    (RetryableOutputStream) requestBody);
        }
    }

    private Retry processResponseHeaders() {
        return null;
    }

    enum Retry {
        NONE,
        SAME_CONNECTION,
        DIFFERENT_CONNECTION
    }

}
