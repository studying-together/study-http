package sjt.http.module.header;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Header {
    private Map<String, List<String>> headers = new HashMap<>();

    public Header setHeader(HeaderType headerName, String headerValue) {
        List<String> headerValues = Collections.singletonList(headerValue);
        this.headers.put(headerName.getName(), headerValues);

        return this;
    }

    public Header setHeaders(HeaderType headerName, List<String> headerValue){
        this.headers.put(headerName.getName(), headerValue);

        return this;
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
