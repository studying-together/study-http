package sjt.http.module.header;

/**
 * https://tools.ietf.org/html/rfc2616#section-7.1
 */
public enum EntityHeader implements HeaderType {
    ALLOW("Allow"),
    CONTENT_ENCODING("Content-Encoding"),
    CONTENT_LENGTH("Content-Length"),
    CONTENT_LOCATION("Content-Location"),
    CONTENT_LANGUAGE("Content-Langauge"),
    CONTENT_MD5("Content-Md5"),
    CONTENT_RANGE("Content-Range"),
    CONTENT_TYPE("Content-Type"),
    EXPIRES("Expires"),
    LAST_MODIFIED("Last-Modified"),
    EXTENSION_HEADER("Extension-Header"),
    ;

    private String name;

    EntityHeader(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return this.name;
    }
}
