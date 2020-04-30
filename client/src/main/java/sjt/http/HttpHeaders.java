package sjt.http;

public enum HttpHeaders {

    ACCEPT("Accept"),
    ACCEPT_CHARSET("Accept-Charset");

    private String fieldName;

    HttpHeaders(String fieldName) {
        this.fieldName = fieldName;
    }
}
