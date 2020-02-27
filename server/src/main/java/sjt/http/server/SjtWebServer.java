package sjt.http.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SjtWebServer {
    // thread pool
    private static int threadPoolSize = 10;
    private static ExecutorService executor = Executors.newFixedThreadPool(threadPoolSize);

    public static void main(String[] args) throws IOException {
        int port = 8081;
        ServerSocket serverSocket = new ServerSocket(port);

        while (true) {
            System.out.println(">>> 접속 대기중");
            Socket socket = serverSocket.accept();

            System.out.println(">>> 접속 :: " + socket.getInetAddress());
            executor.submit(new SjtServer(socket));

        }

    }

}
