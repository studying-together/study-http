package sjt.http.server.servlet;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.Getter;
import sjt.http.server.exception.HttpInvalidRequestException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

@Getter
public class Request {

    private static final String END_OF_LINE = "";
    private static final String HEADER_DELIMITER = ": ";
    private static final String LINE_SEPARATOR = " ";
    private static final ObjectMapper MAPPER = new ObjectMapper();

    private final Map<String, String> headers;

    private HttpMethod httpMethod;

    private String path;

    @Getter(AccessLevel.NONE)
    private String httpVersion;

    private String body;

    public Request(InputStream is) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        String line = reader.readLine();
        if (line == null) {
            throw new HttpInvalidRequestException("invalid Request");
        }
        parseRequestLine(line);

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

    private String readBody(BufferedReader reader, int contentLength) throws IOException {
        char[] body = new char[contentLength];
        reader.read(body, 0, contentLength);
        return String.copyValueOf(body);
    }

    public <T> T getParsedBody(Class<T> clazz) {
        try {
            return MAPPER.readValue(body, clazz);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("cannot parse request body to " + clazz.getSimpleName());
        }
    }
}
