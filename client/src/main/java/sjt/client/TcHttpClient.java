package sjt.client;

import java.net.CookieHandler;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import sjt.http.HttpEngine;
import sjt.http.Request;
import sjt.http.Response;

/**
 * 실제 통신 수행
 */
@RequiredArgsConstructor
public class TcHttpClient {
    private final ObjectMapper objectMapper;
    private HttpEngine httpEngine;
    private CookieHandler cookieHandler;

    // executes HTTP request
    public <T> T execute(final Request request, final Class<T> clazz) {
        initCookie(request);
        httpEngine = new HttpEngine(objectMapper);
        httpEngine.sendRequest(request);
        return httpEngine.readResponse(clazz);
    }

    public Response execute(final Request request) {
        initCookie(request);
        httpEngine.sendRequest(request);
        return httpEngine.readResponse();
    }

    public void initCookie(final Request request) {
        Map<String, String> headers = request.getHeaders();
        //TODO:: cookieHandler 를 이용해서 cookie를 header에 추가해 주기
    }
}
