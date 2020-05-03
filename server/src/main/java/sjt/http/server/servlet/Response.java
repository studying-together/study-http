package sjt.http.server.servlet;

import lombok.Builder;

import java.io.BufferedWriter;
import java.io.IOException;

@Builder
public class Response {
    private static final String CRLF = "\r\n";
    private static final String HTTP_VERSION = "HTTP/1.1";
    private static final String SP = " ";

    private String statusLine;
    private String statusCode;
    private String reasonPhrase;
    private Object body;

    public void writeTest(BufferedWriter writer) throws IOException {
        statusLine = HTTP_VERSION + SP + statusCode + SP + reasonPhrase;

        final String mockStartLine = statusLine + CRLF;
        final String mockHeader = "Cache-Control: no-cache\n"
                + "Connection: Keep-Alive\n"
                + "Content-Type: application/json; charset=UTF-8\n"
                + "Date: Thu, 12 Mar 2020 09:28:35 GMT\n" + CRLF;
        writer.write(mockStartLine);
        writer.write(mockHeader);

        // TODO : 수정 필요
        if(hasBodyData()) {
            writer.write(body.toString());
        }
    }

    private boolean hasBodyData() {
        return body != null;
    }
}
