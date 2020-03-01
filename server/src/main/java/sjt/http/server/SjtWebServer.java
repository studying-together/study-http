package sjt.http.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class SjtWebServer {

    private static final Logger LOGGER = LoggerFactory.getLogger(SjtWebServer.class);

    private Socket clientSocket;

    private SjtWebServer(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    public static void main(String[] args) throws IOException {

        int port = 8088;
        ServerSocket serverSocket = new ServerSocket(port);

        while (true) {
            LOGGER.info("Server is listening on {} port number.", port);
            SjtWebServer sjtWebServer = new SjtWebServer(serverSocket.accept());
            Thread requestThread = new Thread(sjtWebServer::acceptRequest);
            LOGGER.info("Connection is opened - {} thread!!", requestThread.getName());
            requestThread.start();
        }
    }

    private void acceptRequest() {

        try (
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream(), StandardCharsets.UTF_8));
                PrintWriter printWriter = new PrintWriter(clientSocket.getOutputStream())
        ) {

            while (bufferedReader.ready()) {
                LOGGER.info(bufferedReader.readLine());
            }

            printWriter.print("HTTP/1.1 200 OK\r\n");
            printWriter.print("Date: " + LocalDateTime.now().atZone(ZoneId.systemDefault()) + "\r\n");
            printWriter.print("Server: 127.0.0.1\r\n");
            printWriter.print("Content-Type: application/json;charset=UTF-8\r\n");
            printWriter.print("\r\n");
            printWriter.print("{\"data\":[],\"httpStatus\":\"OK\",\"httpCode\":200}\r\n");
            printWriter.flush();
        } catch (IOException ioe) {
            LOGGER.error("IOException is occurred :: ", ioe);
        }
    }

}
