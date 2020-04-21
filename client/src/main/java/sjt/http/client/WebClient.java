package sjt.http.client;

import java.util.Map;

public interface WebClient {

    <T> T get(String host, int port, String path, Class<T> clazz);

    void post(String host, int port, String path, String body);

    void put(String host, int port, String path, String body);

    void delete(String host, int port, String path);

}
