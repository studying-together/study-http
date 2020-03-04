package sjt.http.server.request;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by yusik on 2020/03/04.
 */
public class HttpRequest {

    private static final String END_OF_LINE = "";
    private static final String HEADER_DELIMITER = ": ";

    private RequestLine requestLine;
    private Map<String, String> headers = new HashMap<>();

    public HttpRequest(InputStream in) throws IOException {

        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        String line = reader.readLine();
        requestLine = RequestLine.parse(line);

        while (!(line = reader.readLine()).equals(END_OF_LINE)) {
            int index = line.indexOf(HEADER_DELIMITER);
            headers.put(line.substring(0, index), line.substring(index + HEADER_DELIMITER.length()));
        }
    }

    public RequestLine getRequestLine() {
        return requestLine;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }
}
