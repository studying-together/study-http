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

    public boolean hasBody() {
        return body != null && body.length() > 0;
    }

    public String header(String s) {
        return headers.get(s);
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
