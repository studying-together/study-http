package sjt.http.server;

import sjt.http.server.exception.NotSupportHttpMethod;

import java.util.Arrays;

public enum HttpMethod {

    OPTION("OPTION"),
    GET("GET"),
    POST("POST"),
    HEAD("HEAD"),
    PUT("PUT"),
    PATCH("PATCH"),
    DELETE("DELETE"),
    TRACE("TRACE"),
    CONNECT("CONNECT"),
    EMPTY("EMPTY");

    private String name;

    HttpMethod(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static Boolean valid(String name) {
        return Arrays.stream(HttpMethod.values()).anyMatch(method -> method.getName().equals(name));
    }

    public static HttpMethod getHttpMethod(String httpMethod) {
        if (!valid(httpMethod)) {
            throw new NotSupportHttpMethod();
        }

        return Arrays.stream(HttpMethod.values()).filter(method -> method.getName().equals(httpMethod)).findAny().orElse(EMPTY);
    }
}
