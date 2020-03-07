package sjt.http.core;

import java.util.HashMap;
import java.util.stream.Stream;

public class Headers extends HashMap<String, String>{

    public Headers(String headers) {
        Stream.of(headers.split("\r\n"))
                .filter(s -> s.length() > 0)
                .forEach(h -> put(h.split(": ")[0], h.split(": ")[1]));
    }

    public boolean hasBody() {
        String length = get("Content-Length");
        return length != null;
    }

    public int getContentLength() {
        return hasBody() ? Integer.parseInt(get("Content-Length")) : 0;
    }

    public String getContentType() {
        String contentType = get("Content-Type");
        if (contentType == null) {
            return null;
        }
        return contentType.split(";")[0];
    }
}
