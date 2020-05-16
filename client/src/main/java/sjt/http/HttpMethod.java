package sjt.http;

public enum HttpMethod {

    GET, HEAD, POST, PUT, PATCH, DELETE, OPTIONS, TRACE;

    public static boolean isBodyMethodType(final HttpMethod method) {
        return HttpMethod.POST.equals(method) || HttpMethod.PUT.equals(method);
    }

    public static boolean requireRequestBody(HttpMethod method) {
        return method.equals(POST)
                || method.equals(PUT);
    }
}
