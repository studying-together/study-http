package sjt.http.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerExecutor {

    private static final int POOL_SIZE = 5;
    public static void main(String[] args) {
        int port = 8080;

        try {
            ServerSocket serverSocket = new ServerSocket(port);

            // serverSocket.setSoTimeout(20000);
            // 연결 요청이 올 때까지 block .......
            Socket socket = serverSocket.accept();

            ExecutorService service = Executors.newFixedThreadPool(POOL_SIZE);

            do {
                try {
                    service.execute(new SjtWebServer(socket));
                    socket = serverSocket.accept();
                } catch (SocketTimeoutException e) {
                    System.out.println("Socket Timeout !");
                }

            } while (socket != null);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
