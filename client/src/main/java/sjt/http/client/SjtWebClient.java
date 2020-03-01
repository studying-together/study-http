package sjt.http.client;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

@Slf4j
public class SjtWebClient {
    public static void main(String[] args) {

        try (
                Socket socket = new Socket("127.0.0.1", 8081); // 통신할 server socket
                PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            writer.println("GET /index.html HTTP/1.1"); // http request start line
//            writer.println("GET"); // bad request
            log.info("response : " + in.readLine());
        } catch (UnknownHostException e) {
            log.error("not found host : " + e);
        } catch (IOException e) {
            log.error("i/o exception" + e);
        }
    }

}
