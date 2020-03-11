package sjt.http.server.model.header;


import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public final class HttpHeaders {
    public static final Map<String, HttpHeader> requestHeaders;
    public static final Map<String, HttpHeader> responseHeaders;

    static {
        requestHeaders = new HashMap<>();
        responseHeaders = new HashMap<>();

//        requestHeaders.(Arrays.asList(HttpRequestHeader.values()));
//        requestHeaders.addAll(Arrays.asList(HttpGeneralHeader.values()));
//        requestHeaders.addAll(Arrays.asList(HttpEntityHeader.values()));
//
//        responseHeaders.addAll(Arrays.asList(HttpResponseHeader.values()));
//        responseHeaders.addAll(Arrays.asList(HttpGeneralHeader.values()));
//        responseHeaders.addAll(Arrays.asList(HttpEntityHeader.values()));
    }

//    public static HttpHeader getHttpHeaderForRequestByString (String headerString) {
////        if ()
//    }

}
