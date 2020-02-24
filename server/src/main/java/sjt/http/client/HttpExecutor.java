package sjt.http.client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class HttpExecutor {

    private final Socket socket;

    public HttpExecutor(Socket socket) {
        this.socket = socket;
    }

    public void execute() {
        try {
            doExecute();
        } catch (IOException e) {
            throw new RuntimeException("IO failed");
        }
    }

    public void doExecute() throws IOException {
        InputStream in = socket.getInputStream();
        OutputStream out = socket.getOutputStream();
        StringBuilder requestLine = new StringBuilder();

        // read request line
        while (true) {
            int c = in.read();
            if (c <= 0 || c == 10) {
                break;
            }
            requestLine.append((char) c);
        }

        System.out.println("request line : " + requestLine.toString());
        RequestLine rl = RequestLine.parse(requestLine.toString());
        System.out.println("Parsing requestLine : " + rl);

        out.write(requestLine.toString().getBytes());
        out.write(0);

        System.out.println("[SERVER] connection end");
    }
}
