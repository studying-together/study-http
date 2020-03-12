package sjt.http.server.model.request;

import java.util.Map;
import java.util.Optional;

import sjt.http.server.model.util.JsonConverter;

public class RequestPrinter {
    private String startLine;
    private Map<String, String> headers;
    private String body;

    public RequestPrinter(HttpRequest request) {
        this.startLine = request.getMethod() + " " + request.getUri() + " " + request.getProtocolVersion();
        this.headers = request.getHeaders();
        this.body = Optional.ofNullable(request.getBody()).orElse(null);
    }

    @Override
    public String toString() {
        return JsonConverter.toString(this);
    }
}
