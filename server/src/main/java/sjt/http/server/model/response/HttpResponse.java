package sjt.http.server.model.response;

import sjt.http.server.model.header.HttpHeader;
import sjt.http.server.model.Status;
import sjt.http.server.model.util.JsonConverter;

import java.util.Map;

public class HttpResponse {
    private String ProtocolVersion;
    private Status statusCode;
    private String headers;
    private String body;

    private Map<HttpHeader, String> responseHeaders;

    public String getProtocolVersion() {
        return ProtocolVersion;
    }

    public void setProtocolVersion(String protocolVersion) {
        ProtocolVersion = protocolVersion;
    }

    public Status getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Status statusCode) {
        this.statusCode = statusCode;
    }

    public Map<HttpHeader, String> getResponseHeaders() {
        return responseHeaders;
    }

    public void setResponseHeaders(Map<HttpHeader, String> responseHeaders) {
        this.responseHeaders = responseHeaders;
    }

    @Override public String toString() {
        return JsonConverter.toString(this);
    }
}
