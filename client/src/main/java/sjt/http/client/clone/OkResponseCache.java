package sjt.http.client.clone;

import java.io.IOException;
import java.net.CacheResponse;
import java.net.HttpURLConnection;
import java.net.URI;
import java.util.List;
import java.util.Map;

public interface OkResponseCache {
    CacheResponse get(URI uri, String requestMethod, Map<String, List<String>> requestHeaders) throws IOException;

    void trackResponse(ResponseSource source);

    void trackConditionalCacheHit();

    void update(CacheResponse conditionalCacheHit, HttpURLConnection connection);
}
