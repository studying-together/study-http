package model.response;

import model.header.HttpHeader;
import model.HttpStatusCode;

import java.util.HashMap;
import java.util.Map;

public class HttpResponse {
    private String protocolVersion;
    private HttpStatusCode statusCode;
    private Map<HttpHeader, String> responsHeaders;
    private Object responseBody;

    public HttpResponse() {
        responsHeaders = new HashMap<>();
    }

    public String getProtocolVersion() {
        return protocolVersion;
    }

    public void setProtocolVersion(String protocolVersion) {
        this.protocolVersion = protocolVersion;
    }

    public HttpStatusCode getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(HttpStatusCode statusCode) {
        this.statusCode = statusCode;
    }

    public Map<HttpHeader, String> getResponsHeaders() {
        return responsHeaders;
    }

    public void setResponsHeaders(Map<HttpHeader, String> responsHeaders) {
        this.responsHeaders = responsHeaders;
    }

    public Object getResponseBody() {
        return responseBody;
    }

    public void setResponseBody(Object responseBody) {
        this.responseBody = responseBody;
    }

    public void addResponseHeaders(HttpHeader httpHeader, String value) {
        this.responsHeaders.put(httpHeader, value);
    }

    public String getStatusLine() {
        return protocolVersion + " " + statusCode.getStatusCode() + "\r\n";
    }

    @Override public String toString() {
        return "HttpResponse{" +
                "protocolVersion='" + protocolVersion + '\'' +
                ", statusCode=" + statusCode +
                ", responsHeaders=" + responsHeaders +
                '}';
    }
}
