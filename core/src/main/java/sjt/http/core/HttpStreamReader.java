package sjt.http.core;

import java.io.IOException;
import java.io.InputStream;
import java.util.function.Predicate;

public class HttpStreamReader {

    public static String readStartLine(InputStream in) throws IOException {
        return read(in, sb -> sb.charAt(sb.length() - 1) == '\r');
    }

    private static Predicate<StringBuilder> endHeader =
            sb -> sb.charAt(sb.length() - 1) == '\r'
                    && sb.charAt(sb.length() - 2) == '\n'
                    &&  sb.charAt(sb.length() - 3) == '\r';

    public static String readHeader(InputStream in) throws IOException {
        return read(in, endHeader);
    }

    private static String read(InputStream in, Predicate<StringBuilder> end) throws IOException {
        StringBuilder message = new StringBuilder();
        while (true) {
            int c = in.read();
            if (c == 10 && end.test(message)) {
                message.append((char) c);
                break;
            }
            message.append((char) c);
        }
        return message.toString();
    }

}
