package sjt.http.server.model.request;

import sjt.http.server.model.HttpMethodType;
import sjt.http.server.model.util.JsonConverter;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class HttpRequest {
    transient private HttpMethodType method;
    transient private String uri;
    transient private String protocolVersion;
    transient private Map<String, String> headers;
    transient private String body;

    public HttpMethodType getMethod() {
        return method;
    }

    public String getUri() {
        return uri;
    }

    public String getProtocolVersion() {
        return protocolVersion;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public String getBody() {
        return body;
    }

    public void parseStartLine(String requestLine) {
        String[] startLineData = requestLine.split(" ");
        if (startLineData.length == 3) {
            this.method = HttpMethodType.valueOf(startLineData[0]);
            this.uri = startLineData[1];
            this.protocolVersion = startLineData[2];
        }
    }

    public HttpRequest parseRequest(BufferedReader reader) throws IOException {
        return new HttpRequest()
            .parseStartLine(reader)
            .parseHeader(reader)
            .parseBody(reader);
    }

    private HttpRequest parseStartLine(BufferedReader reader) throws IOException {
        final String startLine = reader.readLine();
        if (startLine == null) {
            throw new IllegalArgumentException();
        }
        this.parseStartLine(startLine);
        return this;
    }

    private HttpRequest parseHeader(BufferedReader reader) throws IOException {
        String headerLine;
        while (reader.ready() && (headerLine = reader.readLine()) != null) {
            if ("".equals(headerLine)) {
                break;    // 빈 줄 --> header 끝
            }
            String[] keyValueHeaders = headerLine.split(": ");
            String key = keyValueHeaders[0];
            String value = keyValueHeaders[1];
            if (headers == null) {
                this.headers = new HashMap<>();
            }
            headers.put(key, value);
        }
        return this;
    }

    private HttpRequest parseBody(BufferedReader reader) throws IOException {
        //TODO:: 본문 정보 HttpRequest에 넣어주기
        String requestBodyLine;
        StringBuffer requestBody = new StringBuffer();
        while (reader.ready() && (requestBodyLine = reader.readLine()) != null) {
            requestBody.append(requestBodyLine);
            requestBody.append("\n");
        }
        return this;
    }

    @Override
    public String toString() {
        return JsonConverter.toString(new RequestPrinter(this));
    }
}
