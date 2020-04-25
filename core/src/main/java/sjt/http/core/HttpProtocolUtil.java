package sjt.http.core;

import java.util.Map;

import static sjt.http.core.HttpReaderUtil.CRLF;

public class HttpProtocolUtil {

    public static String mapHeaderToText(Map<String, String> headers) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> e : headers.entrySet()) {
            sb.append(e.getKey())
                    .append(": ")
                    .append(e.getValue())
                    .append(CRLF);
        }
        return sb.append(CRLF).toString();
    }

}
