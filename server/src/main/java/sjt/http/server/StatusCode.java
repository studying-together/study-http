package sjt.http.server;

import lombok.Getter;

/**
 * @reference : https://tools.ietf.org/html/rfc2616#section-10
 */
@Getter
public enum StatusCode {
    OK(200, "OK"),
    BAD_REQUEST(400, "Bad Request");

    private int code;
    private String message;

    StatusCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
