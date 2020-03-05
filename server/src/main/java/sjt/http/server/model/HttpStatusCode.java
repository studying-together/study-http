package sjt.http.server.model;

public enum HttpStatusCode {
    // 2XX
    HTTP_STATUS_OK(200, "OK"),
    HTTP_STATUS_CREATED(201, "Created"),
    // 3XX
    HTTP_STATUS_MOVED_PERMANETLY(301, "Moved Permanently"),
    // 4XX
    HTTP_STATUS_BAD_REQUEST(400, "Bad request"),
    HTTP_STATUS_NOT_FOUND(404, "Not Found"),
    // 5XX
    HTTP_STATUS_SERVICE_UNAVAILABLE(503, "Service Unavailable"),
    ;

    private int code;
    private String description;

    HttpStatusCode(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public String getStatusCode() { return code + " " + description; }
}
