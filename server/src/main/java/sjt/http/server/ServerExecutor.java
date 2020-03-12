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
            Socket socket;

            ExecutorService executorService = Executors.newFixedThreadPool(POOL_SIZE);

            // TODO : 브라우저로 테스트 할 경우 한번 호출해도 두 개의 스레드가 생성됨..
            while((socket = serverSocket.accept()) != null) {
                executorService.execute(new SjtWebServer(socket));
            }
//            do {
//                try {
//                    service.execute(new SjtWebServer(socket));
//                    socket = serverSocket.accept();
//                } catch (SocketTimeoutException e) {
//                    System.out.println("Socket Timeout !");
//                }
//
//            } while (socket != null);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
