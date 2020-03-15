package model.request;

import model.HttpMethodType;
import model.header.HttpHeader;
import model.header.HttpHeaders;

import java.util.HashMap;
import java.util.Map;

public class HttpRequest {
    private HttpMethodType method;
    private String uri;
    private String protocolVersion;
    private Map<HttpHeader, String> requestHeader;
    private Object requestBody;

    public HttpRequest() {
        requestHeader = new HashMap<>();
    }

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

    public Object getRequestBody() {
        return requestBody;
    }

    public void setRequestBody(Object requestBody) {
        this.requestBody = requestBody;
    }

    /**
     * 리퀘스트 라인 문자열을 메서드, uri, protocoltype 으로 변환한다.
     *
     * @param requestLine
     */
    public void parseRequestLine(String requestLine) {
        String[] requestDatas = requestLine.split(" ");

        if (requestDatas.length == 3) {
            setMethod(HttpMethodType.valueOf(requestDatas[0]));
            setUri(requestDatas[1]);
            setProtocolVersion(requestDatas[2]);
        }
    }

    /**
     * 리퀘스트 헤더를 파싱한다.
     *
     * @param requestHeaderLine
     */
    public void parseRequestHeader(String requestHeaderLine) {
        String[] splitedHeader = requestHeaderLine.split(": ");
        HttpHeader header = HttpHeaders.requestHeaders.get(splitedHeader[0]);

        if (header != null) {
            requestHeader.put(header, splitedHeader[1]);
        }
    }

    public boolean containHeader(HttpHeader httpHeader) {
        return requestHeader.containsKey(httpHeader);
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
