package sjt.http.client.module.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class JsonMessageConverter<T> implements MessageConverter<T> {
    ObjectMapper objectMapper = new ObjectMapper();
    final String contentType = "application/json";

    @Override public boolean canParse(String contentType) {
        return this.contentType.equals(contentType);
    }

    @Override public T parseMessage(Class<? extends T> clazz, byte[] message) {
        try {
            return objectMapper.readValue(message, 0, message.length, clazz);
        } catch (IOException e) {
            log.warn("Json Deserialize Failed  responseBody :: {}, clazz :: {}, error :: {} ", message, clazz, e);
            return null;
        }
    }
}
