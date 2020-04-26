package sjt.http.client.module.request;

import sjt.http.client.module.HttpMethod;

public class RequestLine {
    private HttpMethod method;
    private String uri;
    private String version;

    public RequestLine() {

    }

    public RequestLine(HttpMethod method, String uri, String version) {
        this.method = method;
        this.uri = uri;
        this.version = version;
    }

    public static RequestLine parse(String requestLine) {
        String[] requestLineDatas = requestLine.split(" ");
        if (requestLineDatas.length != 3) {
            throw new IllegalArgumentException("requestLine은 method, uri, version 3가지 값을 반드시 가져야 합니다.");
        }

        return new RequestLine(HttpMethod.valueOf(requestLineDatas[0]), requestLineDatas[1], requestLineDatas[2]);
    }

    public HttpMethod getMethod() {
        return method;
    }

    public void setMethod(HttpMethod method) {
        this.method = method;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getRequestLineString() {
        return method.name() + " " + uri + " " + version + "\r\n";
    }
}
