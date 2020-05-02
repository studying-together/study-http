package sjt.http.server.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum HttpCode {
    HTTP_OK(200, "OK"),
    HTTP_CREATED(201, "Created"),
    HTTP_MOVED_PERMANENTLY(301, "Moved Permanently"),
    HTTP_BAD_REQUEST(400, "Bad request"),
    HTTP_NOT_FOUND(404, "Not Found");

    private int code;
    private String msg;

}
