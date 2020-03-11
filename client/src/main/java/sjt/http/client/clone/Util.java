package sjt.http.client.clone;

public class Util {
    public static final byte[] EMPTY_BYTE_ARRAY = new byte[0];

    public static int getDefaultPort(String scheme) {
        if ("http".equalsIgnoreCase(scheme)) {
            return 80;
        } else if ("https".equalsIgnoreCase(scheme)) {
            return 443;
        } else {
            return -1;
        }
    }
}
