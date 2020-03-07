package sjt.http.module.header;

/**
 * https://tools.ietf.org/html/rfc2616#section-4.5
 */
public enum GeneralHeader implements HeaderType {
    CACHE_CONTROL("Cache-Control"),
    CONNECTION("Connection"),
    DATE("Date"),
    PRAGMA("Pragma"),
    TRAILER("Trailer"),
    TRANSFER_ENCODING("Transfer-Encoding"),
    UPGRAGE("Upgrade"),
    VIA("Via"),
    WARNING("Warning"),
    ;

    private String name;

    GeneralHeader(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return this.name;
    }
}
