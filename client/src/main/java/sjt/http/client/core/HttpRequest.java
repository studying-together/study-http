package sjt.http.client.core;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import static sjt.http.client.core.HttpClient.CRLF;

public class HttpRequest {

    private static final String DEFAULT_CHARSET = "UTF-8";

    private HttpMethod method;
    private String requestUri;
    private Version version = Version.HTTP_1_1;
    private OutputStream outputStream;

    private Map<String, String> headers = new HashMap<String, String>();

    private String body;

    public HttpRequest(OutputStream outputStream,
                       HttpMethod method,
                       String requestUri,
                       String body) {
        this.outputStream = outputStream;
        this.method = method;
        this.requestUri = requestUri;
        this.body = body;
    }

    public void sendRequest() {
        try {
            outputStream.write(requestLine().getBytes(DEFAULT_CHARSET));
            outputStream.write(headers().getBytes(DEFAULT_CHARSET));

            if (body != null) {
                outputStream.write(body.getBytes(DEFAULT_CHARSET));
            }
        } catch (IOException e) {
            throw new HttpException("send request failed");
        }
    }

    public String requestLine() {
        return method.name() + " " + requestUri + " " + version.name + CRLF;
    }

    public String headers() {
        StringBuilder sb = new StringBuilder();
        for (Entry<String, String> e : headers.entrySet()) {
            sb.append(e.getKey())
                    .append("=")
                    .append(e.getValue())
                    .append(CRLF);
        }
        return sb.toString();
    }

    public void setVersion(Version version) {
        this.version = version;
    }

    enum Version {
        HTTP_1_1("HTTP/1.1");

        Version(String name) {
            this.name = name;
        }

        String name;
    }
}
