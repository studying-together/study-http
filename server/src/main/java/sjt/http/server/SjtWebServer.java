package sjt.http.server;

import sjt.http.module.HttpBody;
import sjt.http.module.HttpMessage;
import sjt.http.module.header.EntityHeader;
import sjt.http.module.header.GeneralHeader;
import sjt.http.module.header.Header;
import sjt.http.module.header.ResponseHeader;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static sjt.http.module.util.Utils.SPACE;

public class SjtWebServer implements Runnable{

    private final Socket socket;

    public SjtWebServer(Socket socket) {
        this.socket = socket;
    }

    @Override public void run() {

        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()))){
            System.out.println("---------------- [SERVER] ----------------");
            System.out.println("## THREAD START : " + Thread.currentThread() + "\r\n");

            HttpMessage httpMessage = HttpMessage.getRequestMessage(bufferedReader);
            Object body = HttpBody.parsing(httpMessage);


            // Send Response Message
            Header header = new Header()
                    .setHeader(GeneralHeader.CONNECTION, "keep-alive")
                    .setHeader(ResponseHeader.SERVER, "nginx")
                    .setHeader(EntityHeader.CONTENT_TYPE, "application/json;charset=UTF-8") //"text/plain;charset=UTF-8"
                    .setHeaders(EntityHeader.CONTENT_LANGUAGE, Stream.of("ko-KR").collect(Collectors.toList()));

            HttpMessage responseMessage = HttpMessage.builder()
                    .startLine("HTTP/1.1" + SPACE + "200" + SPACE + "OK")
                    .header(header)
                    .body("message body")
                    .build();
            responseMessage.sendMessage(bufferedWriter);

            System.out.println("## SERVER THREAD FINISHED \r\n");

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
