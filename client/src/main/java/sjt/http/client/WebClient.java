package sjt.http.client;

public interface WebClient {

    <T> T get(String host, int port, String path, Class<T> clazz);

    <T> T post(String host, int port, String path, String body, Class<T> clazz);

    <T> void put(String host, int port, String path, String body, Class<T> clazz);

    <T> void delete(String host, int port, String path, Class<T> clazz);

}
