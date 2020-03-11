package sjt.http.server.model.header;

public enum HttpEntityHeader implements HttpHeader {
    CONTENT_TYPE("Content-Type"),   //  엔티티 바디의 미디어 타입
    CONTENT_LENGTH("Content-Length"),   //  엔티티 바디의 사이즈 (바이트 단위)
    CONTENT_LANGUAGE("Content-Language"),
    CONTENT_ENCODING("Content-Encoding"), // 컨텐츠 압축 방식
    ;

    private String header;

    HttpEntityHeader(String header) {
        this.header = header;
    }

    public String getHeader() {
        return header;
    }

    public static HttpEntityHeader fromString(String headerStr) {
        for (HttpEntityHeader entityHeader : HttpEntityHeader.values()) {
            if (entityHeader.header.equals(headerStr)) {
                return entityHeader;
            }
        }
        return null;
    }
}
