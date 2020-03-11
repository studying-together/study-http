package sjt.http.client.clone;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public final class Connection implements Closeable {

    private final Route route;

    private Socket socket;
    private InputStream in;
    private OutputStream out;
    private SpdyConnection spdyConnection;
    private int httpMonitorVersion = 1;
    private long idleStartTimeNs;

    public Connection(Route route) {
        this.route = route;
    }

    @Override
    public void close() throws IOException {

    }

    public Socket getSocket() {
        return socket;
    }

    public boolean isAlive() {
        return !socket.isClosed() && !socket.isInputShutdown() && socket.isOutputShutdown();
    }

    public void resetIdleStartTime() {
        if (spdyConnection != null) {
            throw new IllegalStateException("spdyConnection != null");
        }
        this.idleStartTimeNs = System.nanoTime();
    }

    public boolean isIdle() {
        return spdyConnection == null || spdyConnection.isIdle();
    }

    public boolean isExpired(long keepAliveDurationNs) {
        return isIdle() && System.nanoTime() - getIdleStartTimeNs() > keepAliveDurationNs;
    }

    public long getIdleStartTimeNs() {
        return spdyConnection == null ? idleStartTimeNs : spdyConnection.getIdleStartTimeNs();
    }

    public boolean isSpdy() {
        return spdyConnection != null;
    }


    public Route getRoute() {
        return route;
    }

    public int getHttpMinorVersion() {
        return httpMonitorVersion;
    }

    public Object newTranport(HttpEngine httpEngine) {
        return (spdyConnection != null) ?  new SpdyTransport(httpEngine,  spdyConnection)
                : new HttpTransport(httpEngine, out, in);
    }
}
