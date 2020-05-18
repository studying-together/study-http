package sjt.http;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import sjt.exception.TcClientException;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.util.StringUtils;

@ToString
@Slf4j
public class Response {
    private static final String HEADER_DELIMITER = ": ";
    private static final String CRLF = "\r\n";
    @Getter
    private StatusLine statusLine;
    @Getter
    private Map<String, String> headers = new HashMap<>();
    @Getter
    private String body;

    public static Response create(BufferedReader reader) {
        Response response = new Response();

        try {
            response.readStatus(reader);
            response.readHeader(reader);
        } catch (IOException e) {
            log.error("Response 생성 중 IOException 발생!", e);
            throw new TcClientException(e);
        }
        return response;
    }

    private void readStatus(final BufferedReader reader) throws IOException {
        final String line = reader.readLine();
        if (StringUtils.isEmpty(line)) {
            throw new TcClientException("statusLine는 empty 일 수 없습니다.");
        }
        final String[] splitStatus = line.split(" ");
        final String[] protocolLine = splitStatus[0].split("/");
        final String protocolName = protocolLine[0];
        final double protocolVersion = Double.parseDouble(protocolLine[1]);
        final int statusCode = Integer.parseInt(splitStatus[1]);
        this.statusLine = new StatusLine(protocolName, protocolVersion, statusCode);
    }

    private void readHeader(final BufferedReader reader) throws IOException {
        String headerLine;
        while (reader.ready() && (headerLine = reader.readLine()) != null) {
            if ("".equals(headerLine)) {
                break;
            }
            String[] splitHeader = headerLine.split(HEADER_DELIMITER);
            String key = splitHeader[0];
            String value = splitHeader[1];
            headers.put(key, value);
        }
    }

    public void readBody(final BufferedReader reader) throws IOException {
        String bodyLine;
        StringBuilder stringBuilder = new StringBuilder();
        while (reader.ready() && (bodyLine = reader.readLine()) != null) {
            stringBuilder.append(bodyLine).append(CRLF);
        }
        body = stringBuilder.toString();
    }

    public void readBody(final BufferedReader reader, int length) throws IOException {
        char[] buf = new char[length];
        int bodySize = reader.read(buf);

        if (bodySize != length) {
            // Content-Length가 잘못된 정보거나 전송 중 서버에 문제가 발생했을 경우
            log.debug("Response Body Size and Content-Length header value do not match .. ");
        }

        body = String.valueOf(buf);
    }

    public void readChunkedBody(BufferedReader reader) throws IOException {
        // chunck data 길이 <CR><LF>
        // chunck data
        String bodyLine;
        int len = -1;
        StringBuilder stringBuilder = new StringBuilder();

        while (reader.ready() && (bodyLine = reader.readLine()) != null) {
            if (len == 0) {
                break;
            }

            if (len != -1) {
                stringBuilder.append(bodyLine).append(CRLF);
                len = -1;
                continue;
            }

            len = Integer.parseInt(bodyLine);
        }

        body = stringBuilder.toString();
    }

    public boolean hasBody() {
        return body != null && body.length() > 0;
    }

    public String header(HttpHeaders httpHeaders) {
        return headers.get(httpHeaders.name());
    }

    public int getContentLength() {
        // 0 혹은 0보다 큰 숫자는 valid value
        if(this.header(HttpHeaders.CONTENT_LENGTH) == null) {
            return -1;
        } else {
            try {
                return Integer.parseInt(this.header(HttpHeaders.CONTENT_LENGTH));
            } catch (NumberFormatException e) {
                log.warn("Response - Content-Length header occur NumberFormatException : " + e);
                return -1;
            }
        }
    }

    public static class StatusLine {
        @Getter
        private final ProtocolVersion protocolVersion;
        @Getter
        private final HttpStatus status;

        public StatusLine(final String protocolName, final double protocolVersion, final int statusCode) {
            this.protocolVersion = new ProtocolVersion(protocolName, protocolVersion);
            this.status = HttpStatus.valueOf(statusCode);
        }

        @Override
        public String toString() {
            return protocolVersion + " " + status;
        }
    }

    @RequiredArgsConstructor
    public static class ProtocolVersion {
        @Getter
        private final String protocol;
        @Getter
        private final double version;

        @Override
        public String toString() {
            return protocol + "/" + version;
        }
    }
}
