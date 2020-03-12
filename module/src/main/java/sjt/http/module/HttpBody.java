package sjt.http.module;

import sjt.http.module.header.ContentType;
import sjt.http.module.header.EntityHeader;

import java.util.Optional;

public class HttpBody {

    public static Object parsing(HttpMessage httpMessage) {
        String body = httpMessage.getBody();

        String contentType = Optional.of(httpMessage.getHeader().getHeaders())
                .map(map -> map.get(EntityHeader.CONTENT_TYPE.getName()))
                .map(list -> list.get(0))
                .map(text -> text.replaceAll(" ", ""))
                .orElse("");

        return doParsing(body, contentType);
    }

    private static Object doParsing(String body, String contentType) {
        return Optional.ofNullable(ContentType.findContentType(contentType))
                .map(contentTypeEnum -> contentTypeEnum.parse(body))
                .orElse("");

    }
}
