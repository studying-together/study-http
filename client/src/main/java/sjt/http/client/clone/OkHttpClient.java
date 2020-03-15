package sjt.http.client.clone;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSocketFactory;
import java.net.*;
import java.util.*;

public class OkHttpClient {
    private static final List<String> DEFAULT_TRANSPORTS = Util.immutableList(Arrays.asList("spdy/3", "http/1.1"));

    private Proxy proxy;
    private List<String> transports;
    private final Set<Route> failedRoutes;
    private ProxySelector proxySelector;
    private CookieHandler cookieHandler;
    private ResponseCache responseCache;
    private SSLSocketFactory sslSocketFactory;
    private HostnameVerifier hostnameVerifier;
    private OkAuthenticator authenticator;
    private ConnectionPool connectionPool;
    private boolean followProtocolRedirects = true;


    public OkHttpClient() {
        this.failedRoutes = Collections.synchronizedSet(new LinkedHashSet<>());
    }

    private OkHttpClient(OkHttpClient copyForm) {
        this.failedRoutes = copyForm.failedRoutes;
    }

    public Proxy getProxy() {
        return proxy;
    }

    public HttpURLConnection open(URL url) {
        String protocol = url.getProtocol();
        OkHttpClient copy = copyWithDefaults();
        if (protocol.equals("http")) {
            return new HttpUrlConnectionImpl(url, copy, copy.okResponseCache(), copy.failedRoutes);
        } else if (protocol.equals("https")) {
            return new HttpsURLConnectionImpl(url, copy, copy.okResponseCache(), copy.failedRoutes);
        } else {
            throw new IllegalArgumentException("Unexpected protocol: " + protocol);
        }
    }

    private OkHttpClient copyWithDefaults() {
        OkHttpClient result = new OkHttpClient(this);
        result.proxy = proxy;
        result.proxySelector = proxySelector != null ? proxySelector : ProxySelector.getDefault();
        result.cookieHandler = cookieHandler != null ? cookieHandler : CookieHandler.getDefault();
        result.responseCache = responseCache != null ? responseCache : ResponseCache.getDefault();
        result.sslSocketFactory = sslSocketFactory != null
                ? sslSocketFactory
                : HttpsURLConnection.getDefaultSSLSocketFactory();
        result.hostnameVerifier = hostnameVerifier != null
                ? hostnameVerifier
                : new OkHostnameVerifier();
        result.authenticator = authenticator != null
                ? authenticator
                : HttpAuthenticator.SYSTEM_DEFAULT;
        result.connectionPool = connectionPool != null ? connectionPool : ConnectionPool.getDefault();
        result.followProtocolRedirects = followProtocolRedirects;
        result.transports = transports != null ? transports : DEFAULT_TRANSPORTS;
        return result;
    }

    private OkResponseCache okResponseCache() {
        if (responseCache instanceof HttpResponseCache) {
            return ((HttpResponseCache) responseCache).okResponseCache;
        } else if (responseCache != null) {
            return new OkResponseCacheAdapter(responseCache);
        } else  {
            return null;
        }
    }

    public ConnectionPool getConnectionPool() {
        return connectionPool;
    }

    public CookieHandler getCookieHandler() {
        return cookieHandler;
    }

    public Set<Route> getFailedRoutes() {
        return failedRoutes;
    }

    public ProxySelector getProxySelector() {
        return proxySelector;
    }

    public ResponseCache getResponseCache() {
        return responseCache;
    }

    public void setProxy(Proxy proxy) {
        this.proxy = proxy;
    }

    public void setProxySelector(ProxySelector proxySelector) {
        this.proxySelector = proxySelector;
    }

    public void setCookieHandler(CookieHandler cookieHandler) {
        this.cookieHandler = cookieHandler;
    }

    public void setResponseCache(ResponseCache responseCache) {
        this.responseCache = responseCache;
    }

    public SSLSocketFactory getSslSocketFactory() {
        return sslSocketFactory;
    }

    public void setSslSocketFactory(SSLSocketFactory sslSocketFactory) {
        this.sslSocketFactory = sslSocketFactory;
    }

    public HostnameVerifier getHostnameVerifier() {
        return hostnameVerifier;
    }

    public void setHostnameVerifier(HostnameVerifier hostnameVerifier) {
        this.hostnameVerifier = hostnameVerifier;
    }

    public OkAuthenticator getAuthenticator() {
        return authenticator;
    }

    public void setAuthenticator(OkAuthenticator authenticator) {
        this.authenticator = authenticator;
    }

    public void setConnectionPool(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    public boolean isFollowProtocolRedirects() {
        return followProtocolRedirects;
    }

    public void setFollowProtocolRedirects(boolean followProtocolRedirects) {
        this.followProtocolRedirects = followProtocolRedirects;
    }

    public List<String> getTransports() {
        return transports;
    }

    public void setTransports(List<String> transports) {
        this.transports = transports;
    }
}
