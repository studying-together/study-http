package sjt.http.client;


public class TcWebClient implements WebClient {

    private TcHttpClient tcHttpClient = new TcHttpClient();

    public <T> T get(String host, int port, String path, Class<T> clazz) {
        return tcHttpClient.execute(HttpMethod.GET, host, port, path, null, clazz);
    }

    public <T> T post(String host, int port, String path, String body, Class<T> clazz) {
        return tcHttpClient.execute(HttpMethod.POST, host, port, path, body, clazz);
    }

    public void put(String host, int port, String path, String body) {

    }

    public void delete(String host, int port, String path) {

    }
}
