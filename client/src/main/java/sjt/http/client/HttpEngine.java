package sjt.http.client;

import sjt.http.client.module.HttpMethod;
import sjt.http.client.module.converter.JsonMessageConverter;
import sjt.http.client.module.converter.MessageConverter;
import sjt.http.client.module.request.HttpRequest;
import sjt.http.client.module.response.HttpResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HttpEngine {

    static final String HTTP_PROTOCOL_VERSION = "HTTP/1.1";
    private static final List<MessageConverter> messageConverters = new ArrayList<>();

    static {
        messageConverters.add(new JsonMessageConverter());
    }

    public <T> T execute(HttpMethod method, String host, int port, String path, Class<T> clazz, String body) {
        Connection connection = new Connection(host, port);

        try {
            connection.start();

            HttpRequest httpRequest = new HttpRequest(method, path, HTTP_PROTOCOL_VERSION);

            // set header & body
            httpRequest.addRequestHeader("Host", host + ":" + port);
            httpRequest.addRequestHeader("Content-Type", "application/json");
            httpRequest.setRequestBody(body);
            httpRequest.writeHttpRequest(connection.getOutputStream());

            HttpResponse httpResponse = new HttpResponse();
            httpResponse.readHttpResponse(connection.getInputStream());
            String contentType = httpResponse.getResponseHeaders().get("Content-Type");

            MessageConverter messageConverter = messageConverters.stream()
                    .filter(mc -> mc.canParse(contentType))
                    .findAny()
                    .orElseThrow(() -> new RuntimeException("can not find message converter"));

            return messageConverter.parseMessage(clazz, httpResponse.getResponseBody().getBytes());

        } catch (IOException e) {
            connection.close();
            throw new HttpEngineExecuteException(e);
        }

    }

}
