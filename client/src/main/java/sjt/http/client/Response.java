package sjt.http.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import lombok.Getter;

public class Response {
    private static final String HEADER_DELIMITER = ": ";
    @Getter
    private String statusLine;
    @Getter
    private Map<String, String> headers = new HashMap<>();
    @Getter
    private String body;

    public Response(BufferedReader reader) throws IOException {
        statusLine = reader.readLine();
        readHeader(reader);
        readBody(reader);
    }

    private void readHeader(BufferedReader reader) throws IOException {
        String headerLine;
        while (reader.ready() && (headerLine = reader.readLine()) != null) {
            if ("".equals(headerLine)) {
                break;
            }
            String[] splitHeader = headerLine.split(HEADER_DELIMITER);
            headers.put(splitHeader[0], splitHeader[1]);
        }
    }

    private void readBody(BufferedReader reader) throws IOException {
        String bodyLine;
        StringBuilder stringBuilder = new StringBuilder();
        while (reader.ready() && (bodyLine = reader.readLine()) != null) {
            stringBuilder.append(bodyLine).append("\r\n");
        }
        body = stringBuilder.toString();
    }
}
