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

    private void setMethod(HttpMethodType method) {
        this.method = method;
    }

    public String getUri() {
        return uri;
    }

    private void setUri(String uri) {
        this.uri = uri;
    }

    public String getProtocolVersion() {
        return protocolVersion;
    }

    private void setProtocolVersion(String protocolVersion) {
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

    public HttpRequest parseStartLine(String requestLine) {
        String[] startLineData = requestLine.split(" ");
        if (startLineData.length == 3) {
            setMethod(HttpMethodType.valueOf(startLineData[0]));
            setUri(startLineData[1]);
            setProtocolVersion(startLineData[2]);
        } else {
            return null;
        }
        return this;
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
