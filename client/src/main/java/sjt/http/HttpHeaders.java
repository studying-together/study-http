package sjt.http;

import java.nio.charset.StandardCharsets;

public enum HttpHeaders {

    ACCEPT("Accept"),
    ACCEPT_CHARSET("Accept-Charset"),
    CONTENT_TYPE("Content-Type"),
    CONTENT_LENGTH("Content-Length"),
    TRANSFER_ENCODING("Transfer-Encoding"),
    ;

    private String fieldName;

    HttpHeaders(String fieldName) {
        this.fieldName = fieldName;
    }

    public static int getContentLength(String body) {
        // TODO : Entity Type에 따른 Length 설정 (file, form, text,,)

        return body.getBytes(StandardCharsets.UTF_8).length;
    }

}
