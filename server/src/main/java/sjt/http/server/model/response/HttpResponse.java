package sjt.http.server.model.response;

import sjt.http.server.model.header.HttpHeader;
import sjt.http.server.model.Status;

import java.util.Map;

public class HttpResponse {
    private String ProtocolVersion;
    private Status statusCode;

    private Map<HttpHeader, String> responsHeaders;

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

    public Map<HttpHeader, String> getResponsHeaders() {
        return responsHeaders;
    }

    public void setResponsHeaders(Map<HttpHeader, String> responsHeaders) {
        this.responsHeaders = responsHeaders;
    }

    @Override public String toString() {
        return "HttpResponse{" +
                "ProtocolVersion='" + ProtocolVersion + '\'' +
                ", statusCode=" + statusCode +
                ", responsHeaders=" + responsHeaders +
                '}';
    }
}
