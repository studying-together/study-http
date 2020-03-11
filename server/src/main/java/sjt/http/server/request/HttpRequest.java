package sjt.http.server.request;

import sjt.http.server.HttpMethod;

import java.net.URL;

public class HttpRequest {

    private HttpMethod httpMethod;
    private String path;
    private String httpVersion;
    private GeneralHeader generalHeader;
    private RequestHeader requestHeader;
    private EntityHeader entityHeader;

    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    public void setHttpMethod(HttpMethod httpMethod) {
        this.httpMethod = httpMethod;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getHttpVersion() {
        return httpVersion;
    }

    public void setHttpVersion(String httpVersion) {
        this.httpVersion = httpVersion;
    }

    public GeneralHeader getGeneralHeader() {
        return generalHeader;
    }

    public void setGeneralHeader(GeneralHeader generalHeader) {
        this.generalHeader = generalHeader;
    }

    public RequestHeader getRequestHeader() {
        return requestHeader;
    }

    public void setRequestHeader(RequestHeader requestHeader) {
        this.requestHeader = requestHeader;
    }

    public EntityHeader getEntityHeader() {
        return entityHeader;
    }

    public void setEntityHeader(EntityHeader entityHeader) {
        this.entityHeader = entityHeader;
    }
}
