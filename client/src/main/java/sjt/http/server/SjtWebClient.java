package sjt.http.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class SjtWebClient {

    public static void main(String[] args) throws IOException {

        System.out.println("[CLIENT] start");
        Socket socket = new Socket("127.0.0.1", 8081);

        final InputStream in = socket.getInputStream();
        final OutputStream out = socket.getOutputStream();
        StringBuilder response = new StringBuilder();

        out.write("GET /index.html HTTP/1.1\r\n".getBytes());

        // write end
        out.write(0);

        while (true) {
            int c = in.read();
            if (c <= 0) {
                break;
            }
            response.append((char) c);
        }

        System.out.println("[CLIENT] response : " + response.toString());
        socket.close();
        System.out.println("[CLIENT] end");
    }

}
