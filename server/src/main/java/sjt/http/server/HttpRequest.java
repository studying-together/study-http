package sjt.http.server;

import sjt.http.core.HttpReaderUtil;
import sjt.http.core.log.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public class HttpRequest {

    private InputStream inputStream;

    private int statusCode;
    private String reason;
    private Map<String, String> headers;
    private String body;

    private HttpRequest(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public static HttpRequest create(InputStream inputStream) {
        return new HttpRequest(inputStream);
    }

    public HttpRequest read() throws IOException {
        setStatusLine(HttpReaderUtil.readStartLine(inputStream));
        this.headers = HttpReaderUtil.readHeaders(inputStream);
//        this.body = HttpReaderUtil.readBody(inputStream);

        Logger.log(this, "http request read OK");
        Logger.log(this, statusCode + " " + reason);
        Logger.log(this, body);

        return this;
    }

    private void setStatusLine(String statusLine) {
        String[] status = " ".split(statusLine);
        this.statusCode = Integer.parseInt(status[0]);
        this.reason = status[1];
    }

}
