package sjt.http.server.controller;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import sjt.http.server.servlet.HttpMethod;

@Builder
@EqualsAndHashCode
public class RequestMappingInfo {
    private HttpMethod method;
    private String uri;

    public RequestMappingInfo(HttpMethod method, String value) {
        this.method = method;
        this.uri = value;
    }
}
