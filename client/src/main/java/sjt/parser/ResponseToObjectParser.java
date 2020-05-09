package sjt.parser;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;
import sjt.exception.TcClientException;
import sjt.http.Response;

@Slf4j
public class ResponseToObjectParser {

    private ObjectMapper objectMapper;

    public ResponseToObjectParser() {
        this.objectMapper = new ObjectMapper();
    }

    public ResponseToObjectParser(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    /**
     * parse 이후 <T> 반환
     *
     * @param clazz
     * @param <T>
     * @return
     */
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
