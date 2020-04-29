package sjt.http.client;

import sjt.http.client.module.HttpMethod;

public class TgWebClient implements WebClient {

    private HttpEngine httpEngine = new HttpEngine();

    public <T> T get(String host, int port, String path, Class<T> clazz) {
        return httpEngine.execute(HttpMethod.GET, host, port, path, clazz, null);
    }

    public <T> T post(String host, int port, String path, String body, Class<T> clazz) {
        return httpEngine.execute(HttpMethod.POST, host, port, path, clazz, body);
    }

    @Override
    public <T> void put(String host, int port, String path, String body, Class<T> clazz) {
        httpEngine.execute(HttpMethod.PUT, host, port, path, clazz, body);
    }

    @Override
    public <T> void delete(String host, int port, String path, Class<T> clazz) {
        httpEngine.execute(HttpMethod.DELETE, host, port, path, clazz, null);
    }
}
