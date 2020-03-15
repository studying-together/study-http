package model.parser;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

public class JsonParser implements Parser {
    @Override
    public Object parseBody(String body) {
        try {
            return new Gson().fromJson(body, JsonObject.class);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }
}
