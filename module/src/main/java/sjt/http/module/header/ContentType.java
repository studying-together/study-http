package sjt.http.module.header;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

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
    public Map<String, String> parse(String body) {
        // 스프링 컨트롤러에서 @RequestMapping과 함께 @RequestBody로 요청 페이로드를 Jackson ObjectMapper를 통해 JSON으로 받을 수 있다. ?????
        // object / array
        Map<String, String> jsonObject = new HashMap<>();
        body = body.replaceAll("[\"\r\n\\s]", "");
        body = body.substring(1, body.length()-1);

        Stream.of(body.split(","))
                .forEach(b -> jsonObject.put(b.substring(0, b.indexOf(":")), b.substring(b.indexOf(":")+1)));

        return jsonObject;
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
