package sjt.http.module.header;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Map;

public enum ContentType {
    TEXT_PLAIN("text/plain") {
        public String parse(String body) {
            return body;
        }
    },
    TEXT_HTML("text/html"){
        public String parse(String body) {
            return null;
        }
    },
    APPLICATION_JSON("application/json"){ // RFC 4627
        public Map<String, Object> parse(String body) {
            body = body.replaceAll("[\r\n]", "");

            ObjectMapper objectMapper = new ObjectMapper();
            try {
                return objectMapper.readValue(body, Map.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    },
    APPLICATION_X_WWW_FORM_URLENCODE("application/x-www-form-urlencode"){
        public String parse(String body) {
            return null;
        }
    },
    MULTIPART_FORM_DATA("multipart/form-data"){
        public String parse(String body) {
            return null;
        }
    },
    ;

    private String text;

    public static ContentType findContentType(String contentType) {
        contentType = contentType.substring(0, contentType.indexOf(";"));

        for(ContentType contentTypeEnum : ContentType.values()) {
            if(contentTypeEnum.getText().equalsIgnoreCase(contentType)) {
                return contentTypeEnum;
            }
        }
        return null;
    }

    public abstract Object parse(String body);

    ContentType(String text) {
        this.text = text;
    }

    public String getText() {
        return this.text;
    }
}
