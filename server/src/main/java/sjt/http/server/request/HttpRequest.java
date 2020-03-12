package sjt.http.server.request;

import sjt.http.server.common.HttpHeaders;
import sjt.http.server.support.FormMessageBodyParser;
import sjt.http.server.support.JsonMessageBodyParser;
import sjt.http.server.support.MessageBodyParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yusik on 2020/03/04.
 */
public class HttpRequest {


    private static List<MessageBodyParser<?>> parsers = new ArrayList<>();

    static {
        parsers.add(new JsonMessageBodyParser());
        parsers.add(new FormMessageBodyParser());
    }

    private static final String END_OF_LINE = "";
    private static final String HEADER_DELIMITER = ": ";

    private RequestLine requestLine;
    private HttpHeaders headers = new HttpHeaders();
    private String bodyString;

    public HttpRequest(InputStream in) throws IOException {

        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        String line = reader.readLine();
        requestLine = RequestLine.parse(line);

        while (!(line = reader.readLine()).equals(END_OF_LINE)) {
            int indexOfDelimiter = line.indexOf(HEADER_DELIMITER);
            headers.put(line.substring(0, indexOfDelimiter), line.substring(indexOfDelimiter + HEADER_DELIMITER.length()));
        }

        int contentLength = headers.getContentLength();
        char[] buf = new char[contentLength];
        reader.read(buf, 0, contentLength);
        bodyString = String.valueOf(buf);
        System.out.println(bodyString);

        final Object body = parsers.stream()
                .filter(messageBodyParser -> messageBodyParser.supports(headers.getContentType()))
                .findFirst()
                .map(messageBodyParser -> messageBodyParser.parse(bodyString))
                .orElseThrow(UnsupportedOperationException::new);

        System.out.println(body);

    }

    public RequestLine getRequestLine() {
        return requestLine;
    }

    public HttpHeaders getHeaders() {
        return headers;
    }

    public String getBodyString() {
        return bodyString;
    }
}
