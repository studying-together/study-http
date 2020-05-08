package sjt.client;

import sjt.exception.TcClientException;

public interface WebClient {
    <T> T get(String host, int port, String path, Class<T> clazz) throws TcClientException;

    <T> T post(String host, int port, String path, String body, Class<T> clazz) throws TcClientException;

    <T> void put(String host, int port, String path, String body, Class<T> clazz) throws TcClientException;

    <T> void delete(String host, int port, String path, Class<T> clazz) throws TcClientException;
}
