package sjt.client;

import sjt.exception.WebClientException;

import java.util.Map;

public interface WebClient {

    <T> T get(String url, Class<T> clazz) throws WebClientException;

    <T> Map<String, T> getMap(String url,  Class<T> clazz) throws WebClientException;

    <T> T post(String url, Class<T> clazz) throws WebClientException;

    <V> Map<String, V> postMap(String url, Class<V> clazz, Map<String, ?> params) throws WebClientException;

    <T> T put(String url, Class<T> clazz, Map<String, ?> param) throws WebClientException;

    <T> T delete(String url, Class<T> clazz) throws WebClientException;
}
