package sjt.http.server;

public class Main {
    private static int threadPoolSize = 20;
    private static final int port = 8081;

    public static void main(String[] args) {
        SjtWebServer sjtWebServer = new SjtWebServer(port, threadPoolSize);
        sjtWebServer.start();
    }
}
