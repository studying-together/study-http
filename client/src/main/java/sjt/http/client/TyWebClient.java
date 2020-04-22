package sjt.http.client;

import sjt.http.client.core.HttpClient;
import sjt.http.client.core.Response;

import static sjt.http.client.core.HttpMethod.*;

public class TyWebClient implements WebClient {

    HttpClient httpClient = new HttpClient();

    public <T> T get(String host, int port, String path, Class<T> clazz) {
        Response response = httpClient.execute(GET, host , port, path, null);
        return null;
    }

    public <T> T post(String host, int port, String path, String body, Class<T> clazz) {
        Response response = httpClient.execute(POST, host, port, path, body);
        return null;
    }

    public void put(String host, int port, String path, String body) {
        httpClient.execute(PUT, host, port, path, body);
    }

    public void delete(String host, int port, String path) {
        httpClient.execute(DELETE, host, port, path, null);
    }

}
