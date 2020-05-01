package sjt.http.server.servlet;

import lombok.Builder;
import lombok.Getter;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

@Getter
public class Request {
    private static final String HEADER_DELIMITER = ": ";
    private static final String SP = " ";

    private BufferedReader reader;

    private String requestLine;
    private Map<String, String> headers;
    private String body;
    private HttpMethod httpMethod;
    private String uri;

    public Request(BufferedReader reader) {
        this.reader = reader;
    }

    public void read() throws IOException {
        readRequestLine();
        readHeader();

        if (hasBody()) {
            readBody();
        }
    }

    private void readRequestLine() throws IOException {
        this.requestLine = reader.readLine();

        if(requestLine.length() != 0) {
            StringTokenizer tokenizer = new StringTokenizer(requestLine, SP);

            httpMethod = HttpMethod.valueOf(tokenizer.nextToken());
            uri = tokenizer.nextToken();
            String version = tokenizer.nextToken();
        }
    }

    private void readHeader() {
        String headerLine = "";
        headers = new HashMap<>(); // 데이터타입 고민

        try {
            while (reader.ready() && (headerLine = reader.readLine()) != null) {
                if ("".equals(headerLine)) {
                    break;
                }
                String[] splitHeader = headerLine.split(HEADER_DELIMITER);
                headers.put(splitHeader[0], splitHeader[1]);
            }
        } catch (IOException e) {
            System.out.println("headerLine : " + headerLine + ", e : " + e);
        }
    }

    private boolean hasBody() {
        return this.headers.containsKey("Content-Length");
    }

    private void readBody() throws IOException {
        String bodyLine;

        StringBuilder stringBuilder = new StringBuilder();
        while (reader.ready() && (bodyLine = reader.readLine()) != null) {
            stringBuilder.append(bodyLine).append("\r\n");
        }

        body = stringBuilder.toString();
    }

}
