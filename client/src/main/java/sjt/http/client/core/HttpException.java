package sjt.http.client.core;

import java.io.IOException;

public class HttpException extends RuntimeException {
    public HttpException(IOException e) {
        super(e);
    }
}
