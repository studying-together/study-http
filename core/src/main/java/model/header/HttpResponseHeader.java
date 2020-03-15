package model.header;


import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum HttpResponseHeader implements HttpHeader {
    SERVER("Server"),   // HTTP 서버 정보
    ACCEPT_RANGES("Accept-Ranges"), // 리소스의 지정 경과 시간
    ;

    private String header;

    HttpResponseHeader(String header) {
        this.header = header;
    }

    public String getHeader() {
        return header;
    }

    public static HttpHeader fromString(String headerStr) {
        return httpResponseHeaders.get(headerStr);
    }

    public static final Map<String, HttpHeader> httpResponseHeaders = Arrays.stream(HttpResponseHeader.values())
            .collect(Collectors.toMap(HttpResponseHeader::getHeader, Function.identity()));
}
