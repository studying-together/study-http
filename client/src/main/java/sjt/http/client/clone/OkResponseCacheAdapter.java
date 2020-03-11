package sjt.http.client.clone;

import sjt.http.client.ResponseSource;

import java.io.IOException;
import java.net.CacheResponse;
import java.net.HttpURLConnection;
import java.net.ResponseCache;
import java.net.URI;
import java.util.List;
import java.util.Map;

public final class OkResponseCacheAdapter implements OkResponseCache {

    private final ResponseCache responseCache;

    public OkResponseCacheAdapter(ResponseCache responseCache) {
        this.responseCache = responseCache;
    }

    @Override
    public CacheResponse get(URI uri, String requestMethod, Map<String, List<String>> requestHeaders) throws IOException {
        return null;
    }

    @Override
    public void trackResponse(ResponseSource source) {

    }

    @Override
    public void trackConditionalCacheHit() {

    }

    @Override
    public void update(CacheResponse conditionalCacheHit, HttpURLConnection connection) {

    }
}
