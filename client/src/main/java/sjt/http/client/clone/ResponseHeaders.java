package sjt.http.client.clone;

import sjt.http.client.ResponseSource;

import java.net.URI;

public final class ResponseHeaders {

    private static final String SENT_MILLIS = "X-Android-Sent-Millis";

    private static final String RECEIVED_MILLIS = "X-Android-Received-Millis";

    static final String RESPONSE_SOURCE = "X-android-Response-Source";

    private final URI uri;
    private final RawHeaders headers;

    private long sentRequestMillis;

    private long receivedResponseMillis;

    private String contentEncoding;
    private int contentLength = -1;

    public ResponseHeaders(URI uri, RawHeaders headers) {
        this.uri = uri;
        this.headers = headers;
    }

    public boolean isContentEncodingGzip() {
        return "gzip".equalsIgnoreCase(contentEncoding);
    }

    public void stripContentEncoding() {
        contentEncoding = null;
        headers.removeAll("Content-Encoding");
    }

    public void stripContentLength() {
        contentLength = -1;
        headers.removeAll("Content-Length");
    }

    public ResponseSource chooseResponseSource(long nowMillis, RequestHeaders request) {
        return null;
    }

    public void setResponseSource(ResponseSource responseSource) {
        headers.set(RESPONSE_SOURCE, responseSource.toString() + " " + headers.getResponseCode());
    }

    public void setLocalTimestamps(long sentRequestMillis, long receivedResponseMillis) {
        this.sentRequestMillis = sentRequestMillis;
        headers.add(SENT_MILLIS, Long.toString(sentRequestMillis));
        this.receivedResponseMillis = receivedResponseMillis;
        headers.add(RECEIVED_MILLIS, Long.toString(receivedResponseMillis));
    }

    public boolean validate(ResponseHeaders networkResponse) {
        return false;
    }

    public ResponseHeaders combine(ResponseHeaders network) {
        return null;
    }

    public RawHeaders getHeaders() {
        return headers;
    }
}
