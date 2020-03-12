package sjt.http.client;

import sjt.http.module.HttpMessage;
import sjt.http.module.HttpMethods;
import sjt.http.module.header.EntityHeader;
import sjt.http.module.header.GeneralHeader;
import sjt.http.module.header.Header;
import sjt.http.module.header.RequestHeader;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static sjt.http.module.util.Utils.CARRIAGE_RETURN;
import static sjt.http.module.util.Utils.LINE_FEED;
import static sjt.http.module.util.Utils.SPACE;

public class SjtWebClient {

    public static void main(String[] args) throws IOException {

        Socket socket = new Socket("127.0.0.1", 8080);

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));


        // Send Request Message
        Header header = new Header()
                .setHeader(GeneralHeader.CONNECTION, "keep-alive")
                .setHeader(RequestHeader.HOST, "https://github.com/Study-Java-Together/study-http")
                .setHeader(EntityHeader.CONTENT_TYPE, "application/json;charset=UTF-8") //"text/plain;charset=UTF-8"
//                .setHeader(EntityHeader.CONTENT_TYPE, "text/html;")
                .setHeaders(RequestHeader.ACCEPT_LANGUAGE, Stream.of("ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7")
                        .collect(Collectors.toList()));

        HttpMessage requestMessage = HttpMessage.builder()
                .startLine(HttpMethods.GET + SPACE + "/index.html" + SPACE + "HTTP/1.1")
                .header(header)
                .body("{\"name\":\"heedi\", \"age\":\"3\", \"favorite\":{\"food\":\"japchae\", \"place\":\"in my bed\"}}")
//                .body("<title>First parse</title>" + "<p>Parsed HTML into a doc.</p>")
                .build();
        requestMessage.sendMessage(bufferedWriter);


        // Get Response Message
        HttpMessage responseMessage = HttpMessage.getRequestMessage(bufferedReader);

        socket.close();
    }


}
