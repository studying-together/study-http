package sjt.http.client;

public class RequestLine {

    private Method method;
    private String url;
    private String version;

    private RequestLine(Method method, String url, String version) {
        this.method = method;
        this.url = url;
        this.version = version;
    }

    static RequestLine parse(String requestLine) {
        String[] attributes = requestLine.split(" ");
        Method method = Method.valueOf(attributes[0]);
        String url = attributes[1];
        String version = attributes[2];
        return new RequestLine(method, url, version);
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "\n Method: " + method.name() + "\n URL: " + url + "\n Version: " + version;
    }
}
