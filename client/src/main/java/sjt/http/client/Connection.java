package sjt.http.client;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Connection implements Closeable {
    private Socket socket;
    private InputStream in;
    private OutputStream out;
    private boolean connected = false;

    public void connect(String host, int port, int readTimeout) throws IOException {
        if (connected) {
            throw new IllegalStateException("already connected");
        }
        connected = true;
        socket = new Socket(host, port);
        socket.setSoTimeout(readTimeout);
        in = socket.getInputStream();
        out = socket.getOutputStream();
    }

    @Override
    public void close() throws IOException {
        socket.close();
    }

    public InputStream getIn() {
        return in;
    }

    public OutputStream getOut() {
        return out;
    }
}
