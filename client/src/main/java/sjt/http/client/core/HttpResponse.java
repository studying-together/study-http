package sjt.http.client.core;

import sjt.http.core.HttpReaderUtil;
import sjt.http.core.log.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public class HttpResponse {

    private int statusCode;
    private String reason;
    private Map<String, String> headers;
    private String body;

    private InputStream inputStream;

    public HttpResponse(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public void readResponse() {
        Logger.log(this, "reading request");
        try {
            setStatusCodeAndReason(HttpReaderUtil.readStartLine(inputStream));
            this.headers = HttpReaderUtil.readHeaders(inputStream);
            this.body = HttpReaderUtil.readBody(inputStream);
        } catch (IOException e) {
            throw new HttpException("read failed");
        }

        Logger.log(this, statusCode + " " + reason + " " + body);
        Logger.log(this, "read response completed");
    }

    private void setStatusCodeAndReason(String statusLine) {
        this.statusCode = Integer.parseInt(" ".split(statusLine)[0]);
        this.reason = " ".split(statusLine)[1];
    }

    public Result toResult() {
        return new Result();
    }

    class Result {

    }

}
