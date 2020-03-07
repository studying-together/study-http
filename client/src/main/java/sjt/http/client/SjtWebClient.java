package sjt.http.client;

import sjt.http.module.header.EntityHeader;
import sjt.http.module.header.GeneralHeader;
import sjt.http.module.header.Header;
import sjt.http.module.header.RequestHeader;
import sjt.http.module.HttpMessage;
import sjt.http.module.HttpMethods;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SjtWebClient {
    private static final String CARRIAGE_RETURN = "\r";
    private static final String LINE_FEED = "\n";
    private static final String SPACE = " ";

    public static void main(String[] args) throws IOException {

        Socket socket = new Socket("127.0.0.1", 8080);

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));


        // Send Request Message
        Header header = new Header()
                .setHeader(GeneralHeader.CONNECTION, "keep-alive")
                .setHeader(RequestHeader.HOST, "https://github.com/Study-Java-Together/study-http")
                .setHeader(EntityHeader.CONTENT_TYPE, "application/json;charset=UTF-8") //"text/plain;charset=UTF-8"
                .setHeaders(RequestHeader.ACCEPT_LANGUAGE, Stream.of("ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7")
                        .collect(Collectors.toList()));

        HttpMessage requestMessage = HttpMessage.builder()
                .startLine(HttpMethods.GET + SPACE + "/index.html" + SPACE + "HTTP/1.1")
                .header(header)
                .body("message body")
                .build();
        requestMessage.sendMessage(bufferedWriter);


        // Get Response Message
        StringBuilder response = new StringBuilder();
        String msg;
        do {
            msg = bufferedReader.readLine();
            response.append(msg).append(CARRIAGE_RETURN).append(LINE_FEED);
        } while (!msg.equals(SPACE));

        System.out.println("------ [SERVER] ------");
        System.out.println(response.toString());

        socket.close();
    }


}
