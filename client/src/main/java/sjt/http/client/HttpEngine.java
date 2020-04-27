package sjt.http.client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class HttpEngine {
    private static final Logger LOGGER = LoggerFactory.getLogger(HttpEngine.class);
    private static final String HTTP_VERSION = "HTTP/1.1";
    private static final String CRLF = "\r\n";
    private static final int MTU = 1500;
    private final ObjectMapper objectMapper;
    private Connection connection;

    public void sendRequest(final Request request) {
        LOGGER.info("HttpEngine sendRequest start");
        //TODO:: url이 있으면 그 안에서 host, port, path, 프래그먼트 직접 파싱하도록 하도록 하는 기능 추가
        try {
            initHttpEngine(request.getHost(), request.getPort());
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(connection.getOut()), MTU);
            initHeader(request);
            writeStartLine(writer, request);
            writeHeader(writer, request);
            writeBody(writer, request);
            writer.flush();
        } catch (IOException e) {
            LOGGER.error("sendRequest 중 IOException 발생!", e);
            throw new RuntimeException(e);
        }
    }

    private void initHeader(final Request request) {
        final Map<String, String> headers = Optional.ofNullable(request.getHeaders()).orElseGet(HashMap::new);
        if (HttpMethod.isBodyMethodType(request.getMethod())) {
            headers.putIfAbsent("Content-Type", Optional.ofNullable(request.getContentType()).orElse("application/json"));
            headers.putIfAbsent("Content-Length", String.valueOf(Optional.ofNullable(request.getBody()).orElse("").getBytes().length));
        }
        headers.putIfAbsent("Cache-Control", "no-cache");   //아직 cache 지원 못함.
        headers.putIfAbsent("Connection", "close"); //아직 지속연결 못함.
        headers.putIfAbsent("Host", request.getHost());
        headers.putIfAbsent("User-Agent", "TcHttpClient/1.0");
    }

    private void writeStartLine(final BufferedWriter writer, final Request request) throws IOException {
        writer.write(request.getMethod().name() + " " + request.getPath() + " " + HTTP_VERSION + CRLF);
    }

    private void writeHeader(final BufferedWriter writer, final Request request) throws IOException {
        final Map<String, String> header = request.getHeaders();
        if (header == null) {
            return;
        }
        for (String key : header.keySet()) {
            writer.write(key + ": " + header.get(key));
        }
    }

    private void writeBody(final BufferedWriter writer, final Request request) throws IOException {
        if (isWriteable(request)) {
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
    public <T> T readResponse(final Class<T> clazz) {
        Response response = readResponse();
        try {
            return objectMapper.readValue(response.getBody(), clazz);
        } catch (JsonProcessingException e) {
            LOGGER.error("readResponse 중 JsonProcessingException 발생!", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 순수 response 반환
     *
     * @return
     */
    public Response readResponse() {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getIn()), MTU)) {
            return new Response(reader);
        } catch (IOException e) {
            LOGGER.error("readResponse 중 IOException 발생!", e);
            throw new RuntimeException(e);
        }
    }

    private void initHttpEngine(final String host, final int port) throws IOException {
        connection = new Connection();
        connection.connect(host, port, Integer.MAX_VALUE);
    }
}
