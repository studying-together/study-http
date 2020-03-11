package sjt.http.client.clone;

import java.io.InputStream;
import java.io.OutputStream;

public class HttpTransport {

    private final HttpEngine httpEngine;
    private final InputStream socketIn;
    private final OutputStream socketOut;

    public HttpTransport(HttpEngine httpEngine, OutputStream socketOut, InputStream socketIn) {
        this.httpEngine = httpEngine;
        this.socketOut = socketOut;
        this.socketIn = socketIn;
    }
}
