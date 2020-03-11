package sjt.http.client.clone;

import java.io.Closeable;
import java.io.IOException;

public class SpdyConnection implements Closeable {

    private long idleStartTimeNs = System.nanoTime();

    @Override
    public void close() throws IOException {

    }

    public boolean isIdle() {
        return idleStartTimeNs != 0L;
    }

    public synchronized long getIdleStartTimeNs() {
        return idleStartTimeNs;
    }
}
