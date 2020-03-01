package sjt.http.server;

public class HttpResponse {

    private HttpHeaders httpHeaders;

    public String toResponse() {
        return httpHeaders.toResponse();
    }

    private HttpResponse(HttpHeaders httpHeaders) {
        this.httpHeaders = httpHeaders;
    }

    public static HttpResponse ok(HttpRequest.HttpHeaders requestHeaders) {
        return new HttpResponse(HttpHeaders.ok(requestHeaders.getStartLine().getProtocolVersion()));
    }

    public static HttpResponse badRequest(HttpRequest.HttpHeaders requestHeaders) {
        return new HttpResponse(HttpHeaders.badRequest(requestHeaders.getStartLine().getProtocolVersion()));
    }

    public static class HttpHeaders {
        private StartLine startLine;

        public HttpHeaders(StartLine startLine) {
            this.startLine = startLine;
        }

        public String toResponse() {
            return startLine.toResponse();
        }

        public static HttpHeaders ok(String protocolVersion) {
            return new HttpHeaders(StartLine.ok(protocolVersion));
        }

        public static HttpHeaders badRequest(String protocolVersion) {
            return new HttpHeaders(StartLine.badRequest(protocolVersion));
        }

        private static class StartLine {
            private String protocolVersion;
            private StatusCode statusCode;

            private StartLine(String protocolVersion, StatusCode statusCode) {
                this.protocolVersion = protocolVersion;
                this.statusCode = statusCode;
            }

            public static StartLine ok(String protocolVersion) {
                return new StartLine(protocolVersion, StatusCode.OK);
            }

            public static StartLine badRequest(String protocolVersion) {
                return new StartLine(protocolVersion, StatusCode.BAD_REQUEST);
            }

            public String toResponse() {
                return protocolVersion + " " + statusCode.getCode() + " " + statusCode.getMessage();
            }
        }
    }

}
