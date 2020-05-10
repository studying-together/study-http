package sjt.parser;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;
import sjt.exception.TcClientException;
import sjt.http.Response;

@Slf4j
public class JsonParser implements HttpResponseParser {
    private static final String APPLICATION_JSON_CONTENT_TYPE = "application/json";
    private final ObjectMapper objectMapper;

    public JsonParser() {
        this.objectMapper = new ObjectMapper();
    }

    public JsonParser(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public boolean canParse(final String contentType) {
        return APPLICATION_JSON_CONTENT_TYPE.equals(contentType);
    }

    @Override
    public <T> T doParse(Response response, final Class<T> clazz) {
        try {
            log.debug("response : {}", response.toString());
            if (response.hasBody()) {
                return objectMapper.readValue(response.getBody(), clazz);
            } else {
                return null;
            }
        } catch (JsonProcessingException e) {
            log.error("readResponse 중 JsonProcessingException 발생!", e);
            throw new TcClientException(e);
        }
    }
}
