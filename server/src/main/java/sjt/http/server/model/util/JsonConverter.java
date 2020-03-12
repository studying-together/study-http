package sjt.http.server.model.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public final class JsonConverter {
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static String toString(Object o) {
        return gson.toJson(o);
    }
}
