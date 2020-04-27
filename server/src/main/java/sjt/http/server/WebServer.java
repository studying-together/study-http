package sjt.http.server;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class WebServer {
    private static int threadPoolSize = 10;
    private static ExecutorService executor = Executors.newFixedThreadPool(threadPoolSize);

    public static void main(String[] args) throws Exception {

        int port = 8080;
        ServerSocket serverSocket = new ServerSocket(port);

        // TODO : 테스트용 코드입니다. 구현 후 제거해주세요.
//        while (true) {
//            Socket socket = serverSocket.accept();
//            executor.submit(new TestServer(socket));
//        }

        while (true) {
            Socket socket = serverSocket.accept();
            executor.submit(new RequestHandler(socket));
        }

    }

}
