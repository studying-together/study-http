package sjt.http.server.exception;

public class HttpInvalidRequestException extends RuntimeException {

    public HttpInvalidRequestException(Throwable throwable) {
        super(throwable);
    }

    public HttpInvalidRequestException(String message) {
        super(message);
    }
}