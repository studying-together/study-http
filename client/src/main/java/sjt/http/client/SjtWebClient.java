package sjt.http.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;

public class SjtWebClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(SjtWebClient.class);

    public static void main(String[] args) {

        if (args.length < 1) {
            LOGGER.error("접속할 도메인을 입력해 주세요.");
            return;
        }

        String host;
        String path;
        try {
            URL url = new URL(args[0]);
            host = url.getHost();
            path = url.getPath();
        } catch (MalformedURLException e) {
            LOGGER.error("MalformedURLException is occurred :: ", e);
            return;
        }

        try (
                Socket clientSocket = new Socket(host, 8088);
                PrintWriter printWriter = new PrintWriter(clientSocket.getOutputStream());
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream(), StandardCharsets.UTF_8))
        ) {
            printWriter.print("POST " + path + " HTTP/1.1\r\n");
            printWriter.print("Host: " + host + "\r\n");
            printWriter.print("Content-Type: application/json;charset=UTF-8\r\n");
            printWriter.print("\r\n");
            printWriter.print("{\"hong\":\"hee\"}\r\n");
            printWriter.flush();

            bufferedReader.lines().forEach(LOGGER::info);

        } catch (UnknownHostException ukhe) {
            LOGGER.error("UnknownHostException is occurred :: ", ukhe);
        } catch (IOException ioe) {
            LOGGER.error("IOException is occurred :: ", ioe);
        }
    }
}
