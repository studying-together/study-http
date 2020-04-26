package sjt.http.client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

/**
 * 실제 통신 수행
 */
@RequiredArgsConstructor
public class TcHttpClient {
    private static final String HTTP_VERSION = "HTTP/1.1";
    private static final String CRLF = "\r\n";
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

    //TODO:: write, read, 헤더초기화를 담당하는 로직을 HttpEngine 으로 분리해보는건 어떨까요?
    private void sendRequest(final Request request) {
        try {
            connect(request.getHost(), request.getPort());
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(connection.getOut()), MTU);
            initHeader(request);
            writeStartLine(writer, request);
            writeHeader(writer, request);
            writeBody(writer, request);
            writer.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void initHeader(final Request request) {
        //TODO:: 헤더 초기화 하는 로직 추가 필요
    }
    private void writeStartLine(final BufferedWriter writer,final Request request) throws IOException {
        writer.write(request.getMethod().name() + " " + request.getPath() + " " + HTTP_VERSION + CRLF);
    }

    private void writeHeader(final BufferedWriter writer, final Request request) throws IOException {
        final Map<String, String> header = request.getHeaders();
        if(header == null) {
            return;
        }
        for (String key : header.keySet()) {
            writer.write(key + ": " + header.get(key));
        }
    }

    private void writeBody(final BufferedWriter writer, final Request request) throws IOException {
        if(isWriteable(request)) {
            writer.write(request.getBody() + CRLF);
        }
    }

    private boolean isWriteable(final Request request) {
        return request.getBody() != null && HttpMethod.isBodyMethodType(request.getMethod());
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
