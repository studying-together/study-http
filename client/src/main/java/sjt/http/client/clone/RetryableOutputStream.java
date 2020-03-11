package sjt.http.client.clone;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class RetryableOutputStream extends AbstractOutputStream {

    private final int limit;
    private final ByteArrayOutputStream content;

    public RetryableOutputStream(int limit) {
        this.limit = limit;
        this.content = new ByteArrayOutputStream(limit);
    }

    public synchronized int contentLength() throws IOException {
        close();
        return content.size();
    }
}
