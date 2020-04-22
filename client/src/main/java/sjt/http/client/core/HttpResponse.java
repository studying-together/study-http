package sjt.http.client.core;

import java.io.InputStream;

public class HttpResponse {

    private InputStream inputStream;

    public HttpResponse(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public void readResponse() {

    }
}
