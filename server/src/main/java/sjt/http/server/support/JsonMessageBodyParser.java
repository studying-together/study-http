package sjt.http.server.support;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;
import java.util.Optional;

/**
 * Created by kohyusik on 2020/03/11.
 */
public class JsonMessageBodyParser implements MessageBodyParser<Map<String, String>> {

    @Override
    public boolean supports(String type) {
        return Optional.ofNullable(type)
                .map(s -> s.contains("application/json"))
                .orElse(false);
    }

    @Override
    public Map<String, String> parse(String messageBody) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(messageBody, Map.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

}
