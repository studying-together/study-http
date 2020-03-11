package sjt.http.client.clone;

public class SpdyTransport {
    private final HttpEngine httpEngine;
    private final SpdyConnection spdyConnection;
    public SpdyTransport(HttpEngine httpEngine, SpdyConnection spdyConnection) {
        this.httpEngine = httpEngine;
        this.spdyConnection = spdyConnection;
    }
}
