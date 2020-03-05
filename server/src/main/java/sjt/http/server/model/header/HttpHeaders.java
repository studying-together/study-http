package sjt.http.server.model.header;


import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class HttpHeaders {
    public static final Set<HttpHeader> requestHeaders;
    public static final Set<HttpHeader> responseHeaders;

    static {
        requestHeaders = new HashSet<>();
        responseHeaders = new HashSet<>();

        requestHeaders.addAll(Arrays.asList(HttpRequestHeader.values()));
        requestHeaders.addAll(Arrays.asList(HttpGeneralHeader.values()));
        requestHeaders.addAll(Arrays.asList(HttpEntityHeader.values()));

        responseHeaders.addAll(Arrays.asList(HttpResponseHeader.values()));
        responseHeaders.addAll(Arrays.asList(HttpGeneralHeader.values()));
        responseHeaders.addAll(Arrays.asList(HttpEntityHeader.values()));
    }

    public static HttpHeader getHttpHeaderForRequestByString (String headerString) {

    }

}
