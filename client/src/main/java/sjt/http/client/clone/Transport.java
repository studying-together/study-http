package sjt.http.client.clone;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.CacheRequest;

public interface Transport {
    boolean makeReusable(boolean streamCancelled, OutputStream requestBodyOut, InputStream responseTransferIn);

    OutputStream createRequestBody();

    void writeRequestHeaders();

    void writeRequestBody(RetryableOutputStream requestBodyOut);

    void flushRequest();

    ResponseHeaders readResponseHeaders();

    InputStream getTransferStream(CacheRequest cacheRequest);
}
