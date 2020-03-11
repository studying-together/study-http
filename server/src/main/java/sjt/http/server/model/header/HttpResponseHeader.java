package sjt.http.server.model.header;

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

    public static HttpResponseHeader fromString(String headerStr) {
        for (HttpResponseHeader responseHeader : HttpResponseHeader.values()) {
            if (responseHeader.header.equals(headerStr)) {
                return responseHeader;
            }
        }
        return null;
    }
}
