package sjt.http.core;

import sjt.http.core.log.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toMap;

public class HttpReaderUtil {

    public static final String CRLF = "\r\n";

    public static String readStartLine(InputStream inputStream) throws IOException {
        StringBuilder sb = new StringBuilder();
        while (true) {
            sb.append((char) inputStream.read());
            if (endStartLine(sb)) {
                Logger.log(new Object(), sb.toString());
                return sb.toString();
            }
        }
    }

    private static boolean endStartLine(StringBuilder sb) {
        return sb.length() > 1 && CRLF.equals(sb.substring(sb.length() - 2));
    }

    public static Map<String, String> readHeaders(InputStream inputStream) throws IOException {
        StringBuilder sb = new StringBuilder();
        do {
            int read = inputStream.read();
            sb.append(read);
        } while (!endHeaders(sb));

        return Stream.of(sb.toString().split(CRLF))
                .map(String::trim)
                .map("="::split)
                .collect(toMap(arr -> arr[0], arr -> arr[1]));
    }

    private static boolean endHeaders(StringBuilder sb) {
        return sb.length() > 3 && (CRLF + CRLF).equals(sb.substring(sb.length() - 4));
    }

    public static String readBody(InputStream inputStream) {
        return null;
    }
}
