package sjt.http.server;

public class SjtWebServer {

    public static void main(String[] args) {
        HttpServer server = new HttpServer(8081, 200);
        server.start();
    }
}
