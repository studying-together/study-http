package sjt.http.server.servlet;

import lombok.Builder;
import lombok.EqualsAndHashCode;

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
