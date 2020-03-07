package sjt.http.core;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Map;

public enum ContentType {

    APPLICATION_JSON("application/json") {
        @Override
        public Object parse(String source) {
            ObjectMapper mapper = new ObjectMapper();
            try {
                return mapper.readValue(source, Map.class);
            } catch (IOException e) {
                throw new IllegalArgumentException(source + " invalid");
            }
        }
    },
    TEXT_PLAIN("text/plain") {
        @Override
        public Object parse(String source) {
            return source;
        }
    };

    private String name;

    ContentType(String name) {
        this.name = name;
    }

    public abstract Object parse(String source);

    public static ContentType of(String name) {
        for (ContentType value : values()) {
            if (name.equalsIgnoreCase(value.name)) {
                return value;
            }
        }

        throw new IllegalArgumentException("Not Matched");
    }

}
