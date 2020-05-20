package sjt.http;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import sjt.exception.TcClientException;

import java.io.*;
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
    private Request request;

    public Response execute(final Request HttpRequest) {
        request = HttpRequest;

        sendRequest();
        return readResponse();
    }

    public void sendRequest() {
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

        headers.putIfAbsent("Cache-Control", "no-cache");   //아직 cache 지원 못함.
        headers.putIfAbsent("Connection", "close"); //아직 지속연결 못함.
        headers.putIfAbsent("Host", request.getHost());
        headers.putIfAbsent("User-Agent", "TcHttpClient/1.0");

        if (HttpMethod.allowRequestBody(request.getMethod()) && request.getBody() != null) {
            // data가 인코딩 된다면 재설정될 수 있음
            int contentLength = HttpHeaders.getContentLength(request.getBody());
            headers.putIfAbsent("Content-Length", String.valueOf(contentLength));
        }

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
            final Response response = Response.create(reader);

            if (allowBody(response)) {
                String te = response.header(HttpHeaders.TRANSFER_ENCODING);

                if (te != null && te. equalsIgnoreCase("chunked")) {
                    // Content-Length 상관없이 chunk 형식에 따른다.
                    response.readChunkedBody(reader);
                } else {
                    // Content-Length만큼 읽는다.
                    response.readBody(reader, response.getContentLength());
                }
            }

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

    private boolean allowBody(Response response) {
        if (!HttpMethod.allowResponseBody(request.getMethod())) {
            return false;
        }

        // 정보성 Content-Length는 갖지만 응답 body는 없는 경우 : 1xx, 204, 304
        int statusCode = response.getStatusLine().getStatus().value();
        if (statusCode >= 200 && statusCode != 204 && statusCode != 304) {
            return true;
        }

        if (response.header(HttpHeaders.TRANSFER_ENCODING).equalsIgnoreCase("chunked")) {
            return true;
        }

        if (response.getContentLength() != -1) {
            return true;
        }

        return false;
    }
}
