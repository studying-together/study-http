package sjt.http.module.header;

/**
 * https://tools.ietf.org/html/rfc2616#section-5.3
 */
public enum RequestHeader implements HeaderType {
    ACCEPT("Accept"),
    ACCEPT_CHARSET("Accept-Charset"),
    ACCEPT_ENCODING("Accept-Encoding"),
    ACCEPT_LANGUAGE("Accept-Language"),
    pAUTHORIZATION("Authorization"),
    EXPECT("Expect"),
    FROM("From"),
    HOST("Host"),
    RANGE("Range"),
    USER_AGENT("User-Agent"),
    ;

    private String name;

    RequestHeader(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return this.name;
    }
}
