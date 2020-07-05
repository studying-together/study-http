package sjt.http;

import java.util.HashMap;
import java.util.Map;

public class HttpHeaders {

    public static Map<String, String> httpRequestHeader = new HashMap<>();
    public static Map<String, String> httpResponseHeader = new HashMap<>();

    public static void setHttpRequestHeader(Map<String, String> httpRequestHeader) {
        HttpHeaders.httpRequestHeader.putAll(httpRequestHeader);
    }

    public static void setHttpReponseHeader(Map<String, String> httpResponseHeader) {
        HttpHeaders.httpResponseHeader.putAll(httpResponseHeader);
    }
}
