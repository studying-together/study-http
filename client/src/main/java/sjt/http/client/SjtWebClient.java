package sjt.http.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;

import static java.lang.Thread.sleep;

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

        Socket clientSocket = null;

        OutputStream outputStream = null;
        OutputStreamWriter outputStreamWriter = null;
        BufferedWriter bufferedWriter = null;
        try {
            clientSocket = new Socket(host, 8088);
            outputStream = clientSocket.getOutputStream();
            outputStreamWriter = new OutputStreamWriter(outputStream);
            bufferedWriter = new BufferedWriter(outputStreamWriter);

            bufferedWriter.write("HEAD " + path + " HTTP/1.1");
            bufferedWriter.write("Host: " + host);

            LOGGER.info("send to server!!!!!");

        } catch (UnknownHostException ukhe) {
            LOGGER.error("UnknownHostException is occurred :: ", ukhe);
        } catch (IOException ioe) {
            LOGGER.error("IOException is occurred :: ", ioe);
        }

        try {
            sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader = null;

        try {
            inputStream = clientSocket.getInputStream();
            inputStreamReader = new InputStreamReader(inputStream);
            bufferedReader = new BufferedReader(inputStreamReader);
            bufferedReader.lines().forEach(line -> {
                if (!(line == null || line.length() == 0)) {
                    LOGGER.info(line);
                }
            });
            LOGGER.info("finished");
        } catch (IOException ioe) {
            LOGGER.error("IOException is occurred :: ", ioe);
        }

        try {
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
