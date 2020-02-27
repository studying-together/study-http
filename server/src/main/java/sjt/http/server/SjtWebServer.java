package sjt.http.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SjtWebServer {

    public static void main(String[] args) throws IOException {
        int port = 8081;
        ServerSocket serverSocket = new ServerSocket(port);

        // thread pool
        ExecutorService executor = Executors.newFixedThreadPool(4);

        while(true) {
            System.out.println("접속 대기중");
            Socket socket = serverSocket.accept();

            System.out.println("접속 : " + socket.getInetAddress());
            executor.submit(new SjtServer(socket));

        }

    }

}
