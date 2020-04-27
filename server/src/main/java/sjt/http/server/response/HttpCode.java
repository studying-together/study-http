package sjt.http.server.response;

public class HttpCode {
    public static final int HTTP_OK = 200;
    public static final int HTTP_NOT_FOUND = 404;

    public static String msg(int httpCode) {
        switch (httpCode) {
            case HTTP_OK:
                return " OK";
            case HTTP_NOT_FOUND:
                return " Not Found";
            default:
                return "";
        }
    }
}
