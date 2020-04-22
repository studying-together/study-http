package sjt.http.client.core;

import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

public class HttpRequest {

    private HttpMethod method;
    private String requestUri;
    private Version version = Version.HTTP_1_1;
    private OutputStream outputStream;

    private Map<String, String> headers = new HashMap<String, String>();

    private String contents;

    public HttpRequest(OutputStream outputStream,
                       HttpMethod method,
                       String requestUri,
                       String contents) {
        this.outputStream = outputStream;
        this.method = method;
        this.requestUri = requestUri;
        this.contents = contents;
    }

    public void sendRequest() {

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
