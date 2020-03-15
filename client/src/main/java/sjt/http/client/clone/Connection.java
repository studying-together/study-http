package sjt.http.client.clone;

import java.io.*;
import java.net.Proxy;
import java.net.Socket;

public final class Connection implements Closeable {

    private final Route route;

    private Socket socket;
    private InputStream in;
    private OutputStream out;
    private boolean connected = false;
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

    public boolean isConnected() {
        return connected;
    }

    public void connect(int connectTimeout, int readTimeout, TunnelRequest tunnelConfig) throws IOException {
        if (connected) {
            throw new IllegalStateException("already connected");
        }

        connected = true;
        socket = (route.proxy.type() != Proxy.Type.HTTP) ? new Socket(route.proxy) : new Socket();
        socket.connect(route.inetSocketAddress, connectTimeout);
        socket.setSoTimeout(readTimeout);
        in = socket.getInputStream();
        out = socket.getOutputStream();

        if (route.address.sslSocketFactory != null) {
            upgradeToTls(tunnelConfig);
        }
    }

    private void upgradeToTls(TunnelRequest tunnelRequest) throws IOException {
        Platform platform = Platform.get();

        if (requiresTunnel()) {
            makeTunnel(tunnelRequest);
        }
    }

    private void makeTunnel(TunnelRequest tunnelRequest) throws IOException {
        RawHeaders requestHeaders = tunnelRequest.getRequestHeaders();
        while (true) {
            out.write(requestHeaders.toBytes());
            RawHeaders responseHeaders = RawHeaders.fromBytes(in);
        }
    }

    private boolean requiresTunnel() {
        return route.address.sslSocketFactory != null && route.proxy.type() == Proxy.Type.HTTP;
    }
}
