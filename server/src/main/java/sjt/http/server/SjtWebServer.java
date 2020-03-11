package sjt.http.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SjtWebServer {
    private int port;
    private static final Logger LOGGER = LoggerFactory.getLogger(SjtWebServer.class);
    private ExecutorService executor;

    public SjtWebServer(int port, int threadPoolSize) {
        this.port = port;
        this.executor = Executors.newFixedThreadPool(threadPoolSize);
    }

    public void start() {
        LOGGER.info("Server is listening on {} port number.", port);
        try (ServerSocket serverSocket = new ServerSocket(port)){
            while (true) {
                executor.submit(new HttpHandler(serverSocket.accept()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            stop();
        }
    }

    public void stop() {
        LOGGER.info("Server is stop on {} port number.", port);
    }

}
