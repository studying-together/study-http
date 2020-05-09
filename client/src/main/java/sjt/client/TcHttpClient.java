package sjt.client;

import java.net.CookieHandler;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;
import sjt.exception.TcClientException;
import sjt.http.HttpEngine;
import sjt.http.Request;
import sjt.http.Response;

/**
 * 순수하게 http 관련 client 입니다.
 */
@Slf4j
public class TcHttpClient {
    private final HttpEngine httpEngine;
    private CookieHandler cookieHandler;

    public TcHttpClient() {
        this.httpEngine = new HttpEngine();
    }

    // executes HTTP request
    public Response execute(final Request request) {
        initCookie(request);
        final Response response = httpEngine.sendRequest(request);
        if (response != null) {
            return response;
        }
        throw new TcClientException("The response should not be null.");
    }

    public void initCookie(final Request request) {
        Map<String, String> headers = request.getHeaders();
        //TODO:: cookieHandler 를 이용해서 cookie를 header에 추가해 주기
    }
}
