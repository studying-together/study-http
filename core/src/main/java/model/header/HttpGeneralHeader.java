package model.header;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum HttpGeneralHeader implements HttpHeader {
    DATE("Date"), // HTTP 메시지가 만들어진 시각
    CONNECTION("Connection"),
    CACHE_CONTROL("Cache-Control"),
    ;

    private String header;

    HttpGeneralHeader(String header) {
        this.header = header;
    }

    public String getHeader() {
        return header;
    }

    public static HttpHeader fromString(String headerStr) {
        return httpGeneralHeaders.get(headerStr);
    }

    public static final Map<String, HttpHeader> httpGeneralHeaders = Arrays.stream(HttpGeneralHeader.values())
            .collect(Collectors.toMap(HttpGeneralHeader::getHeader, Function.identity()));
}
