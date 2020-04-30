package sjt.http.server.servlet;

import java.util.Arrays;

/**
 * Created by yusik on 2020/04/29.
 */
public enum HttpMethod {
    GET,
    POST,
    PUT,
    DELETE,
    ;

    public static boolean contains(String method) {
        return Arrays.stream(values())
                .anyMatch(httpMethod -> httpMethod.name().equals(method));
    }
}
