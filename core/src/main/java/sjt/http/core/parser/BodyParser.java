package sjt.http.core.parser;

import sjt.http.core.ContentType;

public class BodyParser {

    public static Object parse(String body, String contentType) {
        ContentType type = ContentType.of(contentType);
        return type.parse(body);
    }

}
