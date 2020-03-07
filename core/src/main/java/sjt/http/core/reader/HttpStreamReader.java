package sjt.http.core.reader;

import java.io.IOException;
import java.io.InputStream;
import java.util.function.Predicate;

public class HttpStreamReader {

    public String readStartLine(InputStream in) throws IOException {
        String startLine = read(in, sb -> sb.charAt(sb.length() - 1) == '\r');
        return startLine.substring(0, startLine.length() - 2);
    }

    private static Predicate<StringBuilder> endHeader =
            sb -> sb.charAt(sb.length() - 1) == '\r'
                    && sb.charAt(sb.length() - 2) == '\n'
                    &&  sb.charAt(sb.length() - 3) == '\r';

    public String readHeader(InputStream in) throws IOException {
        String header = read(in, endHeader);
        return header.substring(0, header.length() - 4);
    }

    private String read(InputStream in, Predicate<StringBuilder> end) throws IOException {
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

    public byte[] readContent(InputStream in, int contentLength) throws IOException {
        byte[] content = new byte[contentLength];
        in.read(content, 0, contentLength);
        return content;
    }

}
