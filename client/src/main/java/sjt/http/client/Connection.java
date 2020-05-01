package sjt.http.client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * socket 연결을 위한 클래스입니다.
 */
public class Connection {

    private final String host;
    private final int port;
    private Socket socket;
    private InputStream inputStream;
    private OutputStream outputStream;

    public Connection(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void start() throws IOException {
        socket = new Socket(host, port);
        inputStream = socket.getInputStream();
        outputStream = socket.getOutputStream();
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public OutputStream getOutputStream() {
        return outputStream;
    }

    public void close() {
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
