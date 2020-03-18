package sjt.http.server.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class Request {

    private static final String END_OF_LINE = "";
    private static final String HEADER_DELIMITER = ": ";
    private static final String LINE_SEPARATOR = " ";
    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Getter
    private final Map<String, String> headers;

    @Getter
    private final String startLine;

    @Getter
    private HttpMethod httpMethod;

    @Getter
    private String path;

    private String httpVersion;

    @Getter
    private String body;

    public Request(InputStream is) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));

        this.startLine = reader.readLine();
        parseRequestLine(this.startLine);

        String line;
        headers = new HashMap<>();
        while (!(line = reader.readLine()).equals(END_OF_LINE)) {
            int indexOfDelimiter = line.indexOf(HEADER_DELIMITER);
            headers.put(line.substring(0, indexOfDelimiter), line.substring(indexOfDelimiter + HEADER_DELIMITER.length()));
        }

        String contentLength = headers.get("Content-Length");
        if (contentLength != null) {
            body = readBody(reader, Integer.parseInt(contentLength));
        }

    }

    private void parseRequestLine(String requestLine) {
        String[] tokens = requestLine.split(LINE_SEPARATOR);
        if (!HttpMethod.contains(tokens[0])) {
            throw new IllegalArgumentException("Not support method." + tokens[0]);
        }

        httpMethod = HttpMethod.valueOf(tokens[0]);
        path = tokens[1];
        httpVersion = tokens[2];

    }

    public String getStartLine() {
        return this.startLine;
    }
}
