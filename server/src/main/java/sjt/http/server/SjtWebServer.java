package sjt.http.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class SjtWebServer {

    private static final Logger LOGGER = LoggerFactory.getLogger(SjtWebServer.class);

    private Socket clientSocket;
    private ServerSocket serverSocket;

    public SjtWebServer(ServerSocket serverSocket, Socket clientSocket) {
        this.serverSocket = serverSocket;
        this.clientSocket = clientSocket;
    }

    public static void main(String[] args) throws IOException {

        int port = 8088;
        ServerSocket serverSocket = new ServerSocket(port);

        while (true) {
            LOGGER.info("Server is listening on {} port number.", port);
            SjtWebServer sjtWebServer = new SjtWebServer(serverSocket, serverSocket.accept());
            Thread requestThread = new Thread(sjtWebServer::acceptRequest);
            LOGGER.info("Connection is opened - {} thread!!", requestThread.getName());
            requestThread.start();
        }
    }

    private void acceptRequest() {

        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader = null;

        try {
            inputStream = clientSocket.getInputStream();
            inputStreamReader = new InputStreamReader(inputStream);
            bufferedReader = new BufferedReader(inputStreamReader);

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                LOGGER.info(line);
            }

        } catch (IOException ioe) {
            LOGGER.error("IOException is occurred :: ", ioe);
        }

        OutputStream outputStream = null;
        PrintWriter printWriter = null;
        try {

            outputStream = clientSocket.getOutputStream();
            printWriter = new PrintWriter(outputStream, true);

            printWriter.println("HTTP/1.1 200 OK");
            printWriter.println("Date: " + LocalDateTime.now().atZone(ZoneId.systemDefault()));
            printWriter.println("Server: 127.0.0.1");
            printWriter.println();

            LOGGER.info("send to client!!");
        } catch (IOException ioe) {
            LOGGER.error("IOException is occurred :: ", ioe);
        }
    }

}
