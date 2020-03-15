package model.header;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum HttpRequestHeader implements HttpHeader{
    HOST("Host"),                   // 서버 도메인
    USER_AGENT("User-Agent"),       // 사용자의 클라이언트 정보
    ACCEPT("Accept"),               // 원하는 데이터 타입
    AUTHORIZATION("Authorization"), //  인증 토큰
    REFERER("Referer"),             //  이전 페이지 주소
    ;

    private String header;

    HttpRequestHeader(String header) {
        this.header = header;
    }

    public String getHeader() {
        return header;
    }

    public static HttpHeader fromString(String headerStr) {
       return httpRequestHeaders.get(headerStr);
    }

    public static final Map<String, HttpHeader> httpRequestHeaders = Arrays.stream(HttpRequestHeader.values())
            .collect(Collectors.toMap(HttpRequestHeader::getHeader, Function.identity()));
}
