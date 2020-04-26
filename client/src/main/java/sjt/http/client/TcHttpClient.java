package sjt.http.client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

/**
 * 실제 통신 수행
 */
@RequiredArgsConstructor
public class TcHttpClient {
    private static final String HTTP_VERSION = "HTTP/1.1";
    private static final String CR = "\r\n";
    private static final int MTU = 1500;
    private final ObjectMapper objectMapper;
    private Connection connection;

    // executes HTTP request
    public <T> T execute(final Request request, final Class<T> clazz) {
        sendRequest(request);
        return readResponse(clazz);
    }

    public Response execute(final Request request) {
        sendRequest(request);
        return readResponse();
    }

    private void sendRequest(final Request request) {
        // TODO : header, body write
        try {
            connect(request.getHost(), request.getPort());
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(connection.getOut()), MTU);
            writer.write(request.getMethod().name() + " " + request.getPath() + " " + HTTP_VERSION + CR);
            writer.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * parse 이후 <T> 반환
     *
     * @param clazz
     * @param <T>
     * @return
     */
    private <T> T readResponse(final Class<T> clazz) {
        Response response = readResponse();
        try {
            return objectMapper.readValue(response.getBody(), clazz);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 순수 response 반환
     *
     * @return
     */
    private Response readResponse() {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getIn()), MTU)) {
            return new Response(reader);
        } catch(IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void connect(final String host, final int port) throws IOException {
        connection = new Connection();
        connection.connect(host, port, Integer.MAX_VALUE);
    }
}
