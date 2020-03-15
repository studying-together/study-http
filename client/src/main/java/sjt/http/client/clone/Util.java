package sjt.http.client.clone;

import java.io.Closeable;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadFactory;

public class Util {
    public static final byte[] EMPTY_BYTE_ARRAY = new byte[0];
    public static final Object[] EMPTY_STRING_ARRAY = new String[0];

    public static int getEffectivePort(URI uri) {
        return getEffectivePort(uri.getScheme(), uri.getPort());
    }

    private static int getEffectivePort(String scheme, int specifiedPort) {
        return specifiedPort != -1 ? specifiedPort : getDefaultPort(scheme);
    }

    public static int getDefaultPort(String scheme) {
        if ("http".equalsIgnoreCase(scheme)) {
            return 80;
        } else if ("https".equalsIgnoreCase(scheme)) {
            return 443;
        } else {
            return -1;
        }
    }

    public static <T> List<T> immutableList(List<T> list) {
        return Collections.unmodifiableList(new ArrayList<>(list));
    }

    public static void closeQuietly(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (RuntimeException rethrown) {
                throw rethrown;
            } catch (Exception ignored) {}
        }

    }

    public static ThreadFactory daemonThreadFactory(final String name) {
        return (runnable) -> {
            Thread result = new Thread(runnable, name);
            result.setDaemon(true);
            return result;
        };
    }

    public static String readAsciiLine(InputStream in) throws IOException {
        StringBuilder result = new StringBuilder(80);
        while (true) {
            int c = in.read();
            if (c == -1) {
                throw new EOFException();
            } else if (c == '\n') {
                break;
            }
            result.append((char) c);
        }
        int length = result.length();
        if (length > 0 && result.charAt(length - 1) == '\r') {
            result.setLength(length - 1);
        }
        return result.toString();
    }

}
