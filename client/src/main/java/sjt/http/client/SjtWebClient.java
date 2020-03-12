package sjt.http.client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SjtWebClient {
    private static String host = "127.0.0.1";
    private static int port = 8081;
    private static final Logger LOGGER = LoggerFactory.getLogger(SjtWebClient.class);

    public static void main(String[] args) {
        try (
            Socket socket = new Socket(host, port);
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()))
        ) {
            StringBuilder stringBuilder = new StringBuilder()
                .append("GET /home.html HTTP/1.1\r\n")
                .append("User-Agent: Mozilla/4.0 (compatible; MSIE5.01; Windows NT)\r\n")
                .append("Host: www.tutorialspoint.com\n")
                .append("Content-Type: application/json;charset=UTF-8\r\n")
                .append("Content-Length: length\r\n")
                .append("Accept-Language: en-us\r\n")
                .append("Accept-Encoding: gzip, deflate\r\n")
                .append("Connection: Keep-Alive\r\n")
                .append("\r\n");
            writer.write(stringBuilder.toString());
            writer.flush();
            Stream<String> response = reader.lines();
            // 서버에서 받은 응답 값 출력.
            response.forEach(System.out::println);
        } catch (UnknownHostException e) {
            LOGGER.error("UnknownHostException is occurred :: ", e);
        } catch (IOException e) {
            LOGGER.error("IOException is occurred :: ", e);
        }
    }
}
