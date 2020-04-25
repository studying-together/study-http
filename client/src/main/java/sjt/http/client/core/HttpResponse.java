package sjt.http.client.core;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toMap;
import static sjt.http.client.core.HttpClient.CRLF;

public class HttpResponse {

    private int statusCode;
    private String reason;
    private Map<String, String> headers;
    private String body;

    private InputStream inputStream;

    public HttpResponse(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public void readResponse() {
        try {
            setStatusCodeAndReason(readStatusLine());
            this.headers = readHeaders();
            this.body = readBody();
        } catch (IOException e) {
            throw new HttpException("read failed");
        }
    }

    private void setStatusCodeAndReason(String statusLine) {
        this.statusCode = Integer.parseInt(" ".split(statusLine)[0]);
        this.reason = " ".split(statusLine)[1];
    }

    private String readStatusLine() throws IOException {
        StringBuilder sb = new StringBuilder();
        while (true) {
            int read = inputStream.read();
            sb.append(read);
            if (endStatusLine(sb)) {
                return sb.toString();
            }
        }
    }

    private boolean endStatusLine(StringBuilder sb) {
        return sb.length() > 1 && CRLF.equals(sb.substring(sb.length() - 2));
    }

    private Map<String, String> readHeaders() throws IOException {
        StringBuilder sb = new StringBuilder();
        do {
            int read = inputStream.read();
            sb.append(read);
        } while (!endHeaders(sb));

        return Stream.of(sb.toString().split(CRLF))
                .map(String::trim)
                .map("="::split)
                .collect(toMap(arr -> arr[0], arr -> arr[1]));
    }

    private boolean endHeaders(StringBuilder sb) {
        return sb.length() > 3 && (CRLF + CRLF).equals(sb.substring(sb.length() - 4));
    }

    private String readBody() {
        return null;
    }

    public Result toResult() {
        return new Result();
    }

    class Result {

    }

}
