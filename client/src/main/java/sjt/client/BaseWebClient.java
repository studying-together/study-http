package sjt.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import sjt.exception.WebClientException;
import sjt.http.HttpMethod;
import sjt.http.Request;

import java.util.Map;

/**
 * Base 라고 명칭을 만든 것은 가장 기본 골격을 가진 기본 클래스라고 생각했어요.
 * 이 클래스를 기반으로 좀 더 확장이 가능해질 수 있도록 구현하면 좋을 거라고 생각했습니다.
 * 예를 들어 class TcHttpWebClient extends BaseWebClient 와 같이 정의하면
 * TcHttpWebClient 는 Base 에서 제공해주지 않은 TcHttpWebclient 의 확장된 기능을 제공해 줄 수 있다고 생각했어요.
 */
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
