package sjt.http.header;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Header {
//    private GeneralHeader generalHeader;
//    private RequestHeader requestHeader;
//    private ResponseHeader responseHeader;
//    private EntityHeader entityHeader;
//    private CustomHeader customHeader;
    private Map<String, List<String>> headers = new HashMap<>();

    public void setHeader(String headerName, String headerValue) {
        List<String> headerValues = Collections.singletonList(headerValue);
        this.headers.put(headerName, headerValues);
    }

    public void setHeaders(String headerName, List<String> headerValue){
        this.headers.put(headerName, headerValue);
    }

    @Override
    public String toString() {
        return this.headers.keySet().stream()
                .map(key -> key + ":" + this.headers.get(key))
                .map(header -> header.replaceAll("\\[", ""))
                .map(header -> header.replaceAll("]", ""))
                .collect(Collectors.joining("\r\n"))
                + "\r\n";
    }

    public static Header getHeader(StringBuilder request) {
        List<String> headers = Arrays.asList(request.toString().split(" "));
        return new Header();
    }
}
