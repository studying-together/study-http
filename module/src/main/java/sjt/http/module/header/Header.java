package sjt.http.module.header;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    public Map<String, List<String>> getHeaders() {
        return headers;
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

    public Header() {
    }

    public Header (String header) {
        Stream.of(header.split("\r\n"))
                .filter(h -> h.contains(":"))
                .forEach(h -> headers.put(h.substring(0, h.indexOf(":"))
                        , parsingMultiHeaders(h.substring(h.indexOf(":")+1))));
    }

    private List<String> parsingMultiHeaders(String multiHeader) {
        return Stream.of(multiHeader.split(","))
                .collect(Collectors.toList());
    }

}
