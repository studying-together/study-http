package sjt.http.client;

import sjt.http.client.core.HttpClient;

import static sjt.http.client.core.HttpMethod.*;

public class TyWebClient implements WebClient {

    private HttpClient httpClient;

    public TyWebClient() {
        this.httpClient = new HttpClient(30_000);
    }

    public TyWebClient(int socketTimeout) {
        this.httpClient = new HttpClient(socketTimeout);
    }

    public <T> T get(String host, int port, String path, Class<T> clazz) {
        httpClient.execute(GET, host , port, path, null);
        return null;
    }

    public <T> T post(String host, int port, String path, String body, Class<T> clazz) {
        httpClient.execute(POST, host, port, path, body);
        return null;
    }

    public void put(String host, int port, String path, String body) {
        httpClient.execute(PUT, host, port, path, body);
    }

    public void delete(String host, int port, String path) {
        httpClient.execute(DELETE, host, port, path, null);
    }

}
