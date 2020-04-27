package sjt.http.server.servlet;

import sjt.http.server.response.HttpCode;

public class Response {
    private static final String CRLF = "\r\n";

    public static byte[] reply(int code, String body) {
        StringBuilder builder = new StringBuilder();
        builder.append("HTTP/1.1 ").append(code).append(HttpCode.msg(code)).append(CRLF);

        if (body != null && body.length() != 0) {
            builder.append("Content-Length: ").append(body.length()).append(CRLF);
            builder.append("Content-Type: application/json").append(CRLF).append(CRLF);
            //todo body
//            builder.append(body);
        } else {
            builder.append("Content-Length: 0").append(CRLF);
        }

        return builder.toString().getBytes();
    }
}
