package sjt.http.server.model.header;

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

    public static HttpRequestHeader fromString(String headerStr) {
        for (HttpRequestHeader requestHeader : HttpRequestHeader.values()) {
            if (requestHeader.header.equals(headerStr)) {
                return requestHeader;
            }
        }
        return null;
    }
}
