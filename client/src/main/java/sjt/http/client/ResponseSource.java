package sjt.http.client;

public enum ResponseSource {

    CACHE,
    CONDITIONAL_CACHE,
    NETWORK;

    public boolean requiresConnection() {
        return this == CONDITIONAL_CACHE || this == NETWORK;
    }
}
