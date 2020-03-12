package sjt.http.server.common;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by kohyusik on 2020/03/11.
 */
public class HttpHeaders {

    public static final String HOST = "Host";
    public static final String CONNECTION = "Connection";
    public static final String CACHE_CONTROL = "Cache-Control";
    public static final String CONTENT_LENGTH = "Content-Length";
    public static final String CONTENT_TYPE = "Content-Type";

    private final Map<String, String> headers;

    public HttpHeaders() {
        headers = new HashMap<>();
    }

    public void put(String key, String value) {
        headers.put(key, value);
    }

    public String get(String key) {
        return headers.get(key);
    }

    public int getContentLength() {
        return Integer.parseInt(headers.getOrDefault(CONTENT_LENGTH, "0"));
    }

    public String getContentType() {
        return headers.get(CONTENT_TYPE);
    }

    @Override
    public String toString() {
        return headers.toString();
    }
}
