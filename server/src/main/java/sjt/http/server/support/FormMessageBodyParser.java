package sjt.http.server.support;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Created by kohyusik on 2020/03/11.
 */
public class FormMessageBodyParser implements MessageBodyParser<Map<String, String>> {

    @Override
    public boolean supports(String type) {
        return Optional.ofNullable(type)
                .map(s -> s.contains("application/x-www-form-urlencoded"))
                .orElse(false);
    }

    @Override
    public Map<String, String> parse(String messageBody) {
        Map<String, String> params = new HashMap<>();
        String[] tokens = messageBody.split("&");
        for (String token : tokens) {
            String[] split = token.split("=");
            params.put(split[0], split[1]);
        }
        return params;
    }

}
