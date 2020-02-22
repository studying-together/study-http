package sjt.http.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class SjtWebServer {

    public static void main(String[] args) throws IOException {
        System.out.println("[SERVER] connection start");

        int port = 8081;
        ServerSocket serverSocket = new ServerSocket(port);

        Socket socket = serverSocket.accept();
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
