package sjt.http.client.clone;

import com.sun.xml.internal.ws.util.xml.CDATA;

import java.net.URI;
import java.util.Date;
import java.util.List;
import java.util.Map;

public final class RequestHeaders {

    private final URI uri;
    private final RawHeaders headers;

    private boolean onlyIfCached;

    private int contentLength = -1;
    private String userAgent;
    private String host;
    private String connection;
    private String acceptEncoding;
    private String contentType;
    private String ifModifiedSince;

    public RequestHeaders(URI uri, RawHeaders headers) {
        this.uri = uri;
        this.headers = headers;
    }

    public boolean isOnlyIfCached() {
        return onlyIfCached;
    }

    public RawHeaders getHeaders() {
        return null;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String defaultUserAgent) {
        if (this.userAgent != null) {
            headers.removeAll("User-Agent");
        }
        headers.add("User-Agent", userAgent);
        this.userAgent = userAgent;
    }

    public String getHosts() {
        return host;
    }

    public void setHost(String host) {
        if (this.host != null) {
            headers.removeAll("Host");
        }
        headers.add("Host", host);
        this.host = host;
    }

    public String getConnection() {
        return connection;
    }

    public void setConnection(String connection) {
        if (this.connection != null) {
            headers.removeAll("Connection");
        }
        headers.add("Connection", connection);
        this.connection = connection;
    }

    public String getAcceptEncoding() {
        return acceptEncoding;
    }

    public void setAcceptEncoding(String acceptEncoding) {
        if (this.acceptEncoding != null) {
            headers.removeAll("Accept-Encoding");
        }
        headers.add("Accept-Encoding", acceptEncoding);
        this.acceptEncoding = acceptEncoding;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        if (this.contentType != null) {
            headers.removeAll("Content-Type");
        }
        headers.add("Content-Type", contentType);
        this.contentType = contentType;
    }

    public void setIfModifiedSince(Date date) {
        if (ifModifiedSince != null) {
            headers.removeAll("If-Modified-Since");
        }
        String formattedDate = HttpDate.format(date);
        headers.add("If-Modified-Since", formattedDate);
        ifModifiedSince = formattedDate;
    }

    public void addCookies(Map<String, List<String>> allCookieHeaders) {
        for (Map.Entry<String, List<String>> entry : allCookieHeaders.entrySet()) {
            String key = entry.getKey();
            if ("Cookie".equalsIgnoreCase(key) || "Cookie2".equalsIgnoreCase(key)) {
                headers.addAll(key, entry.getValue());
            }
        }
    }

    public void setContentLength(int contentLength) {
        if (this.contentLength != -1) {
            headers.removeAll("Content-Length");
        }
        headers.add("Content-Length", Integer.toString(contentLength));
        this.contentLength = contentLength;
    }
}
