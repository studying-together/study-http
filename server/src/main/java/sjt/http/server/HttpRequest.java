package sjt.http.server;

public class HttpRequest {
    // request line
    private HttpMethodType method;
    private String uri;
    private String protocolType;
    // TODO : 세분화해서 구현 예정 (헤더 정보..)
    private String requestHeader;
    private String requestBody;

    public HttpMethodType getMethod() {
        return method;
    }

    public void setMethod(HttpMethodType method) {
        this.method = method;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getProtocolType() {
        return protocolType;
    }

    public void setProtocolType(String protocolType) {
        this.protocolType = protocolType;
    }

    public String getRequestHeader() {
        return requestHeader;
    }

    public void setRequestHeader(String requestHeader) {
        this.requestHeader = requestHeader;
    }


    public String getRequestBody() {
        return requestBody;
    }

    public void setRequestBody(String requestBody) {
        this.requestBody = requestBody;
    }

    @Override public String toString() {
        return "HttpRequest{" + '\n' +
                "==================================\n"+
                "requestLine=" + '\n' +
                "    method=" + method + '\n' +
                "    uri='" + uri + '\'' + '\n' +
                "    protocolType='" + protocolType + '\'' + '\n' +
                "==================================\n"+
                "requestHeader= \n'" + requestHeader + '\'' + '\n' +
                "==================================\n"+
                "requestBody= \n'" + requestBody + '\'' + '\n' +
                '}';
    }
}
