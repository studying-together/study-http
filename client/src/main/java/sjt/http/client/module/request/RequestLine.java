package sjt.http.client.module.request;

import sjt.http.client.module.HttpMethod;

public class RequestLine {
    private HttpMethod method;
    private String uri;
    private String version;

    public RequestLine(HttpMethod method, String uri, String version) {
        this.method = method;
        this.uri = uri;
        this.version = version;
    }

    public RequestLine(String requestLine) {
        String[] requestLineDatas = requestLine.split(" ");

        if (requestLineDatas.length == 3) {
            setMethod(HttpMethod.valueOf(requestLineDatas[0]));
            setUri(requestLineDatas[1]);
            setVersion(requestLineDatas[2]);
        }
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

    @Override
    public String toString() {
        return method.name() + " " + uri + " " + version + "\r\n";
    }
}
