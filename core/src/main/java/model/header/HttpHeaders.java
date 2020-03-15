package model.header;

import java.util.HashMap;
import java.util.Map;

public class HttpHeaders {
    public static final Map<String, HttpHeader> requestHeaders;
    public static final Map<String, HttpHeader> responseHeaders;

    static {
        requestHeaders = new HashMap<>();
        responseHeaders = new HashMap<>();

        requestHeaders.putAll(HttpGeneralHeader.httpGeneralHeaders);
        requestHeaders.putAll(HttpRequestHeader.httpRequestHeaders);
        requestHeaders.putAll(HttpEntityHeader.httpEntityHeaders);

        responseHeaders.putAll(HttpGeneralHeader.httpGeneralHeaders);
        responseHeaders.putAll(HttpResponseHeader.httpResponseHeaders);
        responseHeaders.putAll(HttpEntityHeader.httpEntityHeaders);
    }


}
