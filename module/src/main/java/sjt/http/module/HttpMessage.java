package sjt.http.module;

import lombok.Builder;
import sjt.http.module.header.Header;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

import static sjt.http.module.util.Utils.CARRIAGE_RETURN;
import static sjt.http.module.util.Utils.LINE_FEED;

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

    public Header getHeader() {
        return header;
    }

    public String getBody() {
        return body;
    }

    @Override
    public String toString() {
        return startLine + "\r\n" + header.toString() + "\r\n" + body + "\r\n";
    }

    public static HttpMessage from(String startLine, String header, String body) {
        Header headers = new Header(header);

        return HttpMessage.builder()
                .startLine(startLine)
                .header(headers)
                .body(body)
                .build();
    }

    public static HttpMessage getRequestMessage(BufferedReader bufferedReader) throws IOException {
        StringBuilder request = new StringBuilder();
        String msg;
        String startLine = "";
        String header = "";
        boolean startLineRead = false;
        boolean headerRead = false;

        do {
            // TODO : 브라우저로 요청시 여기에서 NPE 발생 -> ready() 사용하니 증상은 해결됐으나, Client의 요청값을 제대로 받지 못하는 문제 발생
            // msg = bufferedReader.ready()? bufferedReader.readLine() : null;
            // while((msg = bufferedReader.readLine() ) != null){
            // readLine()은 개행문자가 포함되어야 내부 blocking이 풀리면서 wihle문이 실행한다.
            msg = bufferedReader.readLine();
            if (msg == null) {
                break;
            }

            request.append(msg).append(CARRIAGE_RETURN).append(LINE_FEED);

            if(!startLineRead) {
                startLine = request.toString();
                request = new StringBuilder();
            }

            startLineRead = true;

            // TODO : 메세지 끝 처리 고민
            if (msg.equals("") && headerRead) {
                break;
            }

            if (msg.equals("")) {
                headerRead = true;
                header = request.toString();
                request = new StringBuilder();

            }

        } while (true);

        System.out.println("[CLIENT] : " + startLine + "\r\n" +  header +  "\r\n" + request.toString());

        return HttpMessage.from(startLine, header, request.toString());
    }

    public void sendMessage(BufferedWriter bufferedWriter) throws IOException {
        bufferedWriter.write(this.toString());
        bufferedWriter.write("\r\n");

        bufferedWriter.flush();
    }
}
