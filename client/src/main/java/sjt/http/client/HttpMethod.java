package sjt.http.client;

public enum HttpMethod {
    GET, POST, HEAD, PUT, DELETE;

    public static boolean isBodyMethodType(final HttpMethod method) {
        return HttpMethod.POST.equals(method) || HttpMethod.PUT.equals(method);
    }
}
