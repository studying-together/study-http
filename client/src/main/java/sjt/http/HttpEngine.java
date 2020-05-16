package sjt.http;

import com.squareup.okhttp.OkHttpClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.httpclient.HttpClient;
import sjt.exception.TcClientException;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
public class HttpEngine {
    private static final String HTTP_VERSION = "HTTP/1.1";
    private static final String CRLF = "\r\n";
    private static final int MTU = 1500;
    private Connection connection;

    public Response sendRequest(final Request request) {
        log.info("HttpEngine sendRequest start");
        //TODO:: url이 있으면 그 안에서 host, port, path, 프래그먼트 직접 파싱하도록 하도록 하는 기능 추가
        try {
            initHttpEngine(request.getHost(), request.getPort());
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(connection.getOut()), MTU);
            // TODO : Request 정리
            initHeader(request);
            writeStartLine(writer, request);
            writeHeader(writer, request);
            writeBody(writer, request);
            writer.flush();
            log.info("HttpEngine sendRequest end");
            return readResponse();
        } catch (IOException e) {
            log.error("sendRequest 중 IOException 발생!", e);
            throw new TcClientException(e);
        }
    }

    private void initHeader(final Request request) {
        final Map<String, String> headers = Optional.ofNullable(request.getHeaders()).orElseGet(HashMap::new);
        if (HttpMethod.isBodyMethodType(request.getMethod())) {
            headers.putIfAbsent("Content-Type", Optional.ofNullable(request.getContentType()).orElse("application/json"));
        }

        if (HttpMethod.requireRequestBody(request.getMethod()) && request.getBody() != null) {
            headers.putIfAbsent("Content-Length", HttpHeaders.getContentLength(request.getBody()));
        }

        headers.putIfAbsent("Cache-Control", "no-cache");   //아직 cache 지원 못함.
        headers.putIfAbsent("Connection", "close"); //아직 지속연결 못함.
        headers.putIfAbsent("Host", request.getHost());
        headers.putIfAbsent("User-Agent", "TcHttpClient/1.0");
        request.setHeaders(headers);
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
            writer.write(key + ": " + header.get(key) + CRLF);
        }
        writer.write(CRLF);
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
     * 순수 response 반환
     *
     * @return
     */
    private Response readResponse() {
        log.info("HttpEngine readResponse start");
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getIn()), MTU);
            final Response response = new Response(reader);
            afterCompletion();
            return response;
        } catch (IOException e) {
            log.error("readResponse 중 IOException 발생!", e);
            throw new TcClientException(e);
        } finally {
            log.info("HttpEngine readResponse end");
        }
    }

    private void initHttpEngine(final String host, final int port) throws IOException {
        connection = new Connection();
        connection.connect(host, port, Integer.MAX_VALUE);
    }

    /**
     * 후처리
     */
    public void afterCompletion() throws IOException {
        log.info("afterCompletion start");
        connection.close(); //TODO:: 추후 커넥션 풀이 생기면 커넥션 release 하도록 변경
        log.info("afterCompletion send");
    }
}
