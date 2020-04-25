package sjt.http.server;

import sjt.http.core.log.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class WebServer {

    int DEFAULT_PORT = 8080;
    private ExecutorService executorService = Executors.newCachedThreadPool();

    public static void main(String[] args) throws IOException {
        WebServer server = new WebServer();
        server.start();
    }

    void start() {
        try {
            Logger.log(this, "server started");
            ServerSocket serverSocket = new ServerSocket(DEFAULT_PORT);
            executorService.execute(new Connector(serverSocket.accept()));
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

}
