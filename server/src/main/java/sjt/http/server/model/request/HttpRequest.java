package sjt.http.server.model.request;

import sjt.http.server.model.HttpMethodType;
import sjt.http.server.model.header.HttpHeader;
import sjt.http.server.model.util.JsonConverter;

import java.util.Map;

public class HttpRequest {
    transient private HttpMethodType method;
    transient private String uri;
    transient private String protocolVersion;
    transient private Map<HttpHeader, String> headers;
    transient private String body;

    public HttpMethodType getMethod() {
        return method;
    }

    public String getUri() {
        return uri;
    }

    public String getProtocolVersion() {
        return protocolVersion;
    }

    public Map<HttpHeader, String> getHeaders() {
        return headers;
    }

    public String getBody() {
        return body;
    }

    public HttpRequest parseStartLine(String requestLine) {
        String[] startLineData = requestLine.split(" ");
        if (startLineData.length == 3) {
            this.method = HttpMethodType.valueOf(startLineData[0]);
            this.uri = startLineData[1];
            this.protocolVersion = startLineData[2];
        } else {
            return null;
        }
        return this;
    }

    public void parseRequestHeader(String requestHeaderLine) {
        String[] splitedHeader = requestHeaderLine.split(": ");
    }

    @Override
    public String toString() {
        return JsonConverter.toString(new RequestPrint(this));
    }
}
