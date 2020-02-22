package sjt.http.client;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class SjtWebClient {

    public static void main(String[] args) throws IOException {

        Socket socket = new Socket("127.0.0.1", 8088);

        final OutputStream out = socket.getOutputStream();
        out.write("GET /index.html HTTP/1.1".getBytes());
        socket.close();
    }

}
