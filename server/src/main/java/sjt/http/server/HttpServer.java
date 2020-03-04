package sjt.http.server;

import sjt.http.server.handler.Echo;
import sjt.http.server.handler.RequestHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by kohyusik on 2020/02/26.
 */
public class HttpServer {

    private final static int DEFAULT_PORT = 8081;
    private final static int DEFAULT_POOL_SIZE = 200;

    private final int port;
    private final int poolSize;

    public HttpServer() {
        this.port = DEFAULT_PORT;
        this.poolSize = DEFAULT_POOL_SIZE;
    }

    public HttpServer(int port, int poolSize) {
        this.port = port;
        this.poolSize = poolSize;

    }

    public void start() {

        ExecutorService service = Executors.newFixedThreadPool(this.poolSize);

        try (ServerSocket server = new ServerSocket(this.port)) {
            System.out.println("Server start with " + this.port);
            Socket connection;
            while ((connection = server.accept()) != null) {
//                service.execute(new Echo(connection));
                service.execute(new RequestHandler(connection));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private void echo(Socket finalConnection) {
    }
}
