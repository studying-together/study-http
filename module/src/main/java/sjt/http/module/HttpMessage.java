package sjt.http.module;

import lombok.Builder;
import sjt.http.module.header.Header;

import java.io.BufferedWriter;
import java.io.IOException;

@Builder
public class HttpMessage {
    private String startLine; // Request-Line | Status-Line
    private Header header;
    private String body;

    public String getStartLine() {
        return startLine;
    }

    public void setStartLine(String startLine) {
        this.startLine = startLine;
    }

    @Override
    public String toString() {
        return startLine + "\r\n" + header.toString() + "\r\n" + body + "\r\n";
    }

    public static HttpMessage from(StringBuilder request) {
        String[] requestMessage = request.toString().split("\r\n");
        String startLine = requestMessage[0];
        Header headers = new Header(); // request에서 header정보 찾기"
        String body = "body 내용";

        return HttpMessage.builder()
                .startLine(startLine)
                .header(headers)
                .body(body)
                .build();
    }

    public void sendMessage(BufferedWriter bufferedWriter) throws IOException {
        bufferedWriter.write(this.toString());
        bufferedWriter.write(" \r\n");

        bufferedWriter.flush();
    }
}
