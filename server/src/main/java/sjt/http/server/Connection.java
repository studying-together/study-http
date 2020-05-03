package sjt.http.server;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Connection implements Closeable {
    private Socket socket;
    private InputStream inputStream;
    private OutputStream outputStream;
    boolean closed = false;

    public Connection(Socket socket, InputStream inputStream, OutputStream outputStream) {
        this.socket = socket;
        this.inputStream = inputStream;
        this.outputStream = outputStream;
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public OutputStream getOutputStream() {
        return outputStream;
    }

    @Override
    public void close() {
        if (!this.closed) {
            this.closed = true;

            try {
                if (this.inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException ignore) {
            }
            try {
                if (this.outputStream != null) {
                    outputStream.close();
                }
            } catch (IOException ignore) {
            }
            try {
                if (this.socket != null) {
                    socket.close();
                }
            } catch (IOException ignore) {
            }
        }
    }
}
