package sjt.http.client;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SjtWebServer {

    public static boolean RUNNING = true;

    public static void main(String[] args) throws IOException {
        System.out.println("[SERVER] connection start");
        int port = 8081;
        ServerSocket serverSocket = new ServerSocket(port);
        ExecutorService executor = Executors.newFixedThreadPool(200);

        while (RUNNING) {
            Socket socket = serverSocket.accept();
            executor.execute(() -> new HttpExecutor(socket).execute());
        }
    }
}
