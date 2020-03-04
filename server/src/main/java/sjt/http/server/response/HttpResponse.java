package sjt.http.server.response;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by yusik on 2020/03/04.
 */
public class HttpResponse {

    private HttpStatus status;
    private Map<String, String> headers = new HashMap<>();
    private Writer writer;

    public HttpResponse(OutputStream out) {
        this.writer = new OutputStreamWriter(out);
    }

    public HttpStatus getStatus() {
        return status;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public Writer getWriter() {
        return writer;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public void addHeader(String key, String value) {
        headers.put(key, value);
    }
}
