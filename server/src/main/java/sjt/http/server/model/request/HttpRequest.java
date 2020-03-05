package sjt.http.server.model.request;

import sjt.http.server.model.HttpMethodType;
import sjt.http.server.model.header.HttpHeader;
import sjt.http.server.model.header.HttpRequestHeader;

import java.util.Map;

public class HttpRequest {
    // request line
    private HttpMethodType method;
    private String uri;
    private String protocolVersion;
    private Map<HttpHeader, String> requestHeader;
    private String requestBody;

    // getter setter
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

    public String getProtocolVersion() {
        return protocolVersion;
    }

    public void setProtocolVersion(String protocolVersion) {
        this.protocolVersion = protocolVersion;
    }

    public Map<HttpHeader, String> getRequestHeader() {
        return requestHeader;
    }

    public void setRequestHeader(Map<HttpHeader, String> requestHeader) {
        this.requestHeader = requestHeader;
    }

    public String getRequestBody() {
        return requestBody;
    }

    public void setRequestBody(String requestBody) {
        this.requestBody = requestBody;
    }

    public void parseRequestLine(String requestLine) {
        String[] requestDatas = requestLine.split(" ");

        if (requestDatas.length == 3) {
            setMethod(HttpMethodType.valueOf(requestDatas[0]));
            setUri(requestDatas[1]);
            setProtocolVersion(requestDatas[2]);
        }
    }

    public void parseRequestHeader(String requestHeaderLine) {
        String[] splitedHeader = requestHeaderLine.split(": ");

    }

    @Override public String toString() {
        return "HttpRequest{" + '\n' +
                "==================================\n"+
                "requestLine=" + '\n' +
                "    method=" + method + '\n' +
                "    uri='" + uri + '\'' + '\n' +
                "    protocolVersion='" + protocolVersion + '\'' + '\n' +
                "==================================\n"+
                "requestHeader= \n'" + requestHeader + '\'' + '\n' +
                "==================================\n"+
                "requestBody= \n'" + requestBody + '\'' + '\n' +
                '}';
    }
}
