package sjt.http;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public enum HttpHeaders {

    ACCEPT("Accept"),
    ACCEPT_CHARSET("Accept-Charset");

    private String fieldName;

    HttpHeaders(String fieldName) {
        this.fieldName = fieldName;
    }

    public static String getContentLength(String body) {
        // TODO : Entity Type에 따른 Length 설정 (file, form, text,,)

        return Arrays.toString(body.getBytes(StandardCharsets.UTF_8));
    }

}
