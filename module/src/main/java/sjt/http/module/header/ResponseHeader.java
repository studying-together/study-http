package sjt.http.module.header;

/**
 * https://tools.ietf.org/html/rfc2616#section-6.2
 */
public enum ResponseHeader implements HeaderType {
    ACCEPT_RANGE("Accept-Ranges"),
    AGE("Age"),
    ETAG("ETag"),
    LOCATION("Location"),
    PROXY_AUTHENTICATE("Proxy-Authenticate"),
    RETRY_AFTER("Retry-After"),
    SERVER("Server"),
    VARY("Vary"),
    WWW_AUTHENTICATE("WWW-Authenticate"),
    ;

    private String name;

    ResponseHeader(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return this.name;
    }
}
