package sjt.http.server.model.response;

import sjt.http.server.model.Status;
import sjt.http.server.model.request.HttpRequest;
import sjt.http.server.model.util.JsonConverter;

import com.google.common.primitives.Bytes;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Map;

public class HttpResponse {
    transient private final String RESOURCE_PATH = "server/src/main/resources";
    private String protocolVersion;
    private Status statusCode;
    private byte[] body = new byte[0];
    private Map<String, String> headers;

    public String getProtocolVersion() {
        return protocolVersion;
    }

    public Status getStatusCode() {
        return statusCode;
    }

    public byte[] getBody() {
        return body;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public HttpResponse load(HttpRequest request) {
        return new HttpResponse()
            .loadProtocolVersion(request)
            .loadBody(request)
            .loadHeader(request)
            .selectStatusCode();
    }

    private HttpResponse loadProtocolVersion(HttpRequest request) {
        this.protocolVersion = request.getProtocolVersion();
        return this;
    }

    private HttpResponse loadBody(HttpRequest request) {
        Path path = Paths.get(RESOURCE_PATH + request.getUri());
        byte[] buffer = new byte[1024];
        int length = 0;
        try (FileInputStream fileInputStream = new FileInputStream(path.toString())) {
            while ((length = fileInputStream.read(buffer)) != -1) {
                this.body = Bytes.concat(this.body, Arrays.copyOf(buffer, length));
            }
        } catch (FileNotFoundException e) {
            this.statusCode = Status.NOT_FOUND;
        } catch (IOException e) {
            this.statusCode = Status.INTERNAL_SERVER_ERROR;
        }
        return this;
    }

    //TODO:: 구현 완료하기
    private HttpResponse loadHeader(HttpRequest request) {
//        this.headers = request.getHeaders();
        return this;
    }

    private HttpResponse selectStatusCode() {
        if (statusCode == null) {
            this.statusCode = Status.OK;
        }
        return this;
    }

    //TODO:: 구현 완료하기
    public byte[] getHeadersByte() {
//        StringBuilder builder = new StringBuilder();
//        for (String key : this.headers.keySet()) {
//            String value = this.headers.get(key);
//            builder.append(key + ": " + value + "\n");
//        }
//        return builder.toString().getBytes();
        final String mockHeader = "Access-Control-Allow-Credentials: true\n"
            + "Cache-Control: no-cache\n"
            + "Connection: Keep-Alive\n"
            + "Content-Type: text/html; charset=ko-KR\n"
            + "Date: Thu, 12 Mar 2020 09:28:35 GMT\n"
            + "Keep-Alive: timeout=20, max=996\n"
            + "Server: Apache\n";
        return mockHeader.getBytes();
    }

    @Override
    public String toString() {
        return JsonConverter.toString(this);
    }
}
