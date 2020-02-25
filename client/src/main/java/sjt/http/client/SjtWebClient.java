package sjt.http.client;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

@Slf4j
public class SjtWebClient {
    public static void main(String[] args) {

        try {
            Socket socket = new Socket("127.0.0.1", 8081); // 통신할 server socket

        Socket socket = new Socket("127.0.0.1", 8081);

            final OutputStream out = socket.getOutputStream();
            PrintWriter writer = new PrintWriter(out, true);
            writer.println("GET /index.html HTTP/1.1");

            final BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream())); //response : server 응답을 받을 입력 스트림

            log.info("response : " + in.readLine());

            socket.close();
        } catch (UnknownHostException e) {
            log.error("not found host : " + e);
        } catch (IOException e) {
            log.error("i/o exception" + e);
        }
    }

}
