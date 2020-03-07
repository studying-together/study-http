package sjt.http.server;

import sjt.http.module.HttpMessage;
import sjt.http.module.header.EntityHeader;
import sjt.http.module.header.Header;
import sjt.http.module.header.GeneralHeader;
import sjt.http.module.header.ResponseHeader;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SjtWebServer {

    private static final String CARRIAGE_RETURN = "\r";
    private static final String LINE_FEED = "\n";
    private static final String SPACE = " ";

    public static void main(String[] args) throws IOException {

        int port = 8080;
        ServerSocket serverSocket = new ServerSocket(port);
        Socket socket = serverSocket.accept();

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));


        // Get Request Message
        StringBuilder request = new StringBuilder();
        String msg;
        do {
            msg = bufferedReader.readLine();
            request.append(msg).append(CARRIAGE_RETURN).append(LINE_FEED);
        } while (!msg.equals(SPACE));

        System.out.println("------ [CLIENT] ------");
        System.out.println(request.toString());
        HttpMessage requestMessage = HttpMessage.from(request);


        // Send Response Message
        Header header = new Header();
        header.setHeader(GeneralHeader.CONNECTION, "keep-alive"); // default
        header.setHeader(ResponseHeader.SERVER, "nginx");
        String acceptLanguage = "ko-KR";
        header.setHeaders(EntityHeader.CONTENT_LANGUAGE, Stream.of(acceptLanguage).collect(Collectors.toList()));

        HttpMessage responseMessage = HttpMessage.builder()
                .startLine("HTTP/1.1" + SPACE + "200" + SPACE + "OK")
                .header(header)
                .body("message body")
                .build();
        responseMessage.sendMessage(bufferedWriter);


        socket.close();
    }

}
