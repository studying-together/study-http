package sjt.http.server;

import lombok.Getter;
import lombok.ToString;

public class HttpRequest {
    @ToString
    @Getter
    public static class HttpHeaders {
        private StartLine startLine;
        private RequestHeaders requestHeaders;
        private GeneralHeaders generalHeaders;
        private EntityHeaders entityHeaders;

        public HttpHeaders() {
            this.startLine = new StartLine();
        }

        public HttpHeaders(String request) {
            this.startLine = new StartLine(request);
        }
    }

    @Getter
    @ToString
    public static class StartLine {
        private HttpMethod httpMethod;
        private String requestTarget;
        private String protocolVersion;

        public StartLine() {
            this.protocolVersion = "HTTP/1.0"; // 기본 메서드 지정해줘야 할까?
        }

        public StartLine(String startLine) {
            String[] request = startLine.split(" ");
            this.httpMethod = HttpMethod.valueOf(request[0]);
            this.requestTarget = request[1];
            this.protocolVersion = request[2];
        }
    }

    public static class RequestHeaders {
    }

    public static class GeneralHeaders {
    }

    public static class EntityHeaders {
    }
}
