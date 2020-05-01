package sjt.http;

import lombok.*;

import java.util.Map;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Request {
    private HttpMethod method;
    private String host;
    private String path;
    private Integer port;
    private String url;
    private String body;
    private String contentType;

    private Map<String, String> headers;

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }
}

