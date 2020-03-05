package sjt.http.server.model.header;

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

    public static HttpGeneralHeader fromString(String headerStr) {
        for (HttpGeneralHeader generalHeader : HttpGeneralHeader.values()) {
            if (generalHeader.header.equals(headerStr)) {
                return generalHeader;
            }
        }
        return null;
    }
}
