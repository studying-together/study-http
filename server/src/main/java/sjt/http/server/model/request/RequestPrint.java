package sjt.http.server.model.request;

import java.util.Map;
import java.util.Optional;

import sjt.http.server.model.util.JsonConverter;

public class RequestPrint {
    private String startLine;
    private String header;
    private String body;

    public RequestPrint(HttpRequest request) {
        this.startLine = request.getMethod() + " " + request.getUri() + "/" + request.getProtocolVersion();
        this.header = Optional.ofNullable(request.getHeaders()).map(Map::toString).orElse(null);
        this.body = Optional.ofNullable(request.getBody()).orElse(null);
    }

    @Override
    public String toString() {
        return JsonConverter.toString(this);
    }
}
