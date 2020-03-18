package sjt.http.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sjt.http.client.module.HttpMethod;
import sjt.http.client.module.request.HttpRequest;
import sjt.http.client.module.response.HttpResponse;

import java.io.IOException;

public class HttpEngine {

    Logger logger = LoggerFactory.getLogger(HttpEngine.class);
    static final String HTTP_PROTOCOL_VERSION = "HTTP/1.1";
    ObjectMapper objectMapper = new ObjectMapper();

    public <T> T execute(HttpMethod method, String host, int port, String path, Class<T> clazz, String body) {
        Connection connection = new Connection(host, port);

        try {
            connection.start();

            // 1. request 보내기
            HttpRequest httpRequest = new HttpRequest(method, path, HTTP_PROTOCOL_VERSION);

            // set header & body
            httpRequest.addRequestHeader("Host", host + ":" + port);
            httpRequest.addRequestHeader("Content-Type", "application/json");
            httpRequest.setRequestBody(body);
            httpRequest.writeHttpRequest(connection.getOutputStream());

            // 2. response 받기
            HttpResponse httpResponse = new HttpResponse();
            httpResponse.readHttpResponse(connection.getInputStream());

            return parseResponse(httpResponse.getResponseBody(), clazz);

        } catch (IOException e) {
            // TODO : wrapping exception vs log 남기기
            throw new HttpEngineExecuteException(e);
        }

    }

    private <T> T parseResponse(String responseBody, Class<T> clazz) {
        try {
            return objectMapper.readValue(responseBody, clazz);
        } catch (JsonProcessingException e) {
            logger.warn("Json Deserialize Failed  responseBody :: {}, clazz :: {}, error :: {} ", responseBody, clazz, e);
            return null;
        }
    }

}
