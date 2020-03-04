package sjt.http.server.request;

/**
 * Created by yusik on 2020/03/04.
 */
public class RequestLine {

    private static final String REQUEST_LINE_SEPARATOR = " ";

    private HttpMethod method;
    private String uri;
    private String version;

    public RequestLine(HttpMethod method, String uri, String version) {
        this.method = method;
        this.uri = uri;
        this.version = version;
    }

    public static RequestLine parse(String readLine) {
        String[] divided = readLine.split(REQUEST_LINE_SEPARATOR);
        return new RequestLine(HttpMethod.valueOf(divided[0]), divided[1], divided[2]);
    }

    public HttpMethod getMethod() {
        return method;
    }

    public String getUri() {
        return uri;
    }

    public String getVersion() {
        return version;
    }
}
