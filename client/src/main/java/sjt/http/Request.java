package sjt.http;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Map;

@Builder
@RequiredArgsConstructor
@EqualsAndHashCode final public class Request {
    @Getter
    private final HttpMethod method;
    @Getter
    private final String host;
    @Getter
    private final String path;
    @Getter
    private final Integer port;
    @Getter
    private final String url;
    @Getter
    private final Map<String, String> headers;
    @Getter
    private final String body;
    @Getter
    private final String contentType;
}
