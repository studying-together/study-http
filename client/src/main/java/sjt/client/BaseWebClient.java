package sjt.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import sjt.exception.WebClientException;
import sjt.http.HttpMethod;
import sjt.http.Request;

import java.util.Map;

public class BaseWebClient implements WebClient {

    private TcHttpClient tcHttpClient = new TcHttpClient(new ObjectMapper());

    @Override
    public <T> T get(String url, Class<T> clazz) throws WebClientException {
        return null;
    }

    @Override
    public <T> Map<String, T> getMap(String url, Class<T> clazz) throws WebClientException {
        return null;
    }

    @Override
    public <T> T post(String url, Class<T> clazz) throws WebClientException {
        return null;
    }

    @Override
    public <V> Map<String, V> postMap(String url, Class<V> clazz, Map<String, ?> params) throws WebClientException {
        return null;
    }

    @Override
    public <T> T put(String url, Class<T> clazz, Map<String, ?> param) throws WebClientException {
        return null;
    }

    @Override
    public <T> T delete(String url, Class<T> clazz) throws WebClientException {
        return null;
    }

}
