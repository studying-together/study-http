package sjt.http.client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SjtWebServer {

    public static void main(String[] args) throws IOException {
        System.out.println("[SERVER] connection start");

        int port = 8081;
        ServerSocket serverSocket = new ServerSocket(port);

        Socket socket = serverSocket.accept();
        InputStream in = socket.getInputStream();
        OutputStream out = socket.getOutputStream();
        StringBuilder sb = new StringBuilder();

        while (true) {
            int c = in.read();
            if (c <= 0) {
                break;
            }
            sb.append((char) c);
        }

        System.out.println("in : " + sb.toString());
        out.write(sb.toString().getBytes());
        out.write(0);

        System.out.println("[SERVER] connection end");
    }



}
