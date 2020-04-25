package sjt.http.server;

import sjt.http.core.HttpProtocolUtil;
import sjt.http.core.log.Logger;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

public class HttpResponse {

    private OutputStream outputStream;
    private int statusCode;
    private String reason;
    private Map<String, String> headers = new HashMap<String, String>();
    private String body;

    private HttpResponse(OutputStream outputStream) {
        this.outputStream = outputStream;
        headers.put("Connection", "close");
    }

    public static HttpResponse create(OutputStream outputStream) {
        return new HttpResponse(outputStream);
    }

    public HttpResponse statusCode(HttpStatus status) {
        this.statusCode = status.code;
        this.reason = status.name();
        return this;
    }

    public HttpResponse body(String body) {
        this.body = body;
        return this;
    }

    public void write() {
        try {
            writeStatusLine();
            writeHeaders();
            writeBody();
            Logger.log(this, "http response write OK");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void writeStatusLine() throws IOException {
        outputStream.write((statusCode + " " + reason).getBytes("UTF-8"));
    }

    private void writeHeaders() throws IOException {
        outputStream.write(HttpProtocolUtil.mapHeaderToText(headers).getBytes("UTF-8"));
    }

    private void writeBody() throws IOException {
        outputStream.write(body.getBytes("UTF-8"));
    }

    enum HttpStatus {
        OK(200);

        int code;

        HttpStatus(int code) {
            this.code = code;
        }
    }

}
