package sjt.client;

import java.net.CookieHandler;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;
import sjt.exception.TcClientException;
import sjt.http.HttpEngine;
import sjt.http.Request;
import sjt.http.Response;

/**
 * 실제 통신 수행
 */
@Slf4j
public class TcHttpClient {
    private final HttpEngine httpEngine;
    private ObjectMapper objectMapper;
    private CookieHandler cookieHandler;

    public TcHttpClient(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        this.httpEngine = new HttpEngine();
    }

    // executes HTTP request
    public <T> T execute(final Request request, final Class<T> clazz) {
        initCookie(request);
        final Response response = httpEngine.sendRequest(request);
        if (response != null) {
            return parse(response, clazz);
        } else {
            throw new TcClientException("The response should not be null.");
        }
    }

    /**
     * parse 이후 <T> 반환
     *
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> T parse(Response response, final Class<T> clazz) {
        try {
            log.debug("response : {}", response.toString());
            if (response.hasBody()) {
                return objectMapper.readValue(response.getBody(), clazz);
            } else {
                return null;
            }
        } catch (JsonProcessingException e) {
            log.error("readResponse 중 JsonProcessingException 발생!", e);
            throw new TcClientException(e);
        }
    }

    public void initCookie(final Request request) {
        Map<String, String> headers = request.getHeaders();
        //TODO:: cookieHandler 를 이용해서 cookie를 header에 추가해 주기
    }
}
