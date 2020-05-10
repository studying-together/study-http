package sjt.parser;

import lombok.extern.slf4j.Slf4j;
import sjt.exception.TcClientException;
import sjt.http.Response;

@Slf4j
public class TextParser implements HttpResponseParser {
    private static final String JSON_CONTENT_TYPE = "text/plain";

    @Override
    public boolean canParse(final String contentType) {
        return JSON_CONTENT_TYPE.equals(contentType);
    }

    @Override
    public <T> T doParse(Response response, final Class<T> clazz) {
        if(clazz != String.class) {
            throw new TcClientException("not support any type except String Type");
        }

        log.debug("response : {}", response.toString());
        if (response.hasBody()) {
            try {
                return (T) response.getBody();
            } catch (RuntimeException e) {
                throw new TcClientException(e);
            }
        }
        throw new TcClientException("response shouldn't null");
    }
}
