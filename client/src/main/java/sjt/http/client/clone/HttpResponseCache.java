package sjt.http.client.clone;

import sjt.http.client.ResponseSource;

import java.io.IOException;
import java.net.*;
import java.util.List;
import java.util.Map;

public final class HttpResponseCache extends ResponseCache {

    final OkResponseCache okResponseCache = new OkResponseCache() {

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
    };

    @Override
    public CacheResponse get(URI uri, String rqstMethod, Map<String, List<String>> rqstHeaders) throws IOException {
        return null;
    }

    @Override
    public CacheRequest put(URI uri, URLConnection conn) throws IOException {
        return null;
    }
}
