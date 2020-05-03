package sjt.http.server.servlet;

import sjt.http.server.response.HttpCode;

public class Response {
    private static final String CRLF = "\r\n";

    public static byte[] reply(HttpCode httpCode, String body) {
        StringBuilder builder = new StringBuilder();
        builder.append("HTTP/1.1 ").append(httpCode.getCode()).append(" ").append(httpCode.getMsg()).append(CRLF);

        if (body != null && body.length() != 0) {
            builder.append("Content-Length: ").append(body.length()).append(CRLF);
            builder.append("Content-Type: application/json").append(CRLF).append(CRLF);
            builder.append(body).append(CRLF);
        } else {
            builder.append("Content-Length: 0").append(CRLF);
        }

        return builder.toString().getBytes();
    }
}
