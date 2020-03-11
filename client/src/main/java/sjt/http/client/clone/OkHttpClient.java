package sjt.http.client.clone;

import java.net.*;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

public class OkHttpClient {

    private Proxy proxy;
    private final Set<Route> failedRoutes;
    private CookieHandler cookieHandler;
    private ResponseCache responseCache;
    private ConnectionPool connectionPool;

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
        OkHttpClient copy = new OkHttpClient();
        if (protocol.equals("http")) {
            return new HttpUrlConnectionImpl(url, copy, copy.okResponseCache(), copy.failedRoutes);
        } else if (protocol.equals("https")) {
            return new HttpsURLConnectionImpl(url, copy, copy.okResponseCache(), copy.failedRoutes);
        } else {
            throw new IllegalArgumentException("Unexpected protocol: " + protocol);
        }
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
}
