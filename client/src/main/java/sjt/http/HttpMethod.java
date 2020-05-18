package sjt.http;

public enum HttpMethod {

    GET, HEAD, POST, PUT, PATCH, DELETE, OPTIONS, TRACE;

    public static boolean isBodyMethodType(final HttpMethod method) {
        return HttpMethod.POST.equals(method) || HttpMethod.PUT.equals(method);
    }

    public static boolean allowRequestBody(HttpMethod method) {
        return method.equals(POST)
                || method.equals(PUT)
                || method.equals(PATCH)
                || method.equals(OPTIONS);
    }

    public static boolean allowResponseBody(HttpMethod method) {
        // not allow : HEAD, PUT, TRACE
        // allow : GET, POST(201), PATCH, DELETE(200)
        return method.equals(GET)
                || method.equals(POST)
                || method.equals(PATCH)
                || method.equals(DELETE);
    }
}
