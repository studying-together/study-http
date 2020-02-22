package sjt.http.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class SjtWebServer {

    private static final Logger LOGGER = LoggerFactory.getLogger(SjtWebServer.class);

    private Socket clientSocket;

    private SjtWebServer(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    private void acceptRequest() {

        BufferedReader bufferedReader = null;
        BufferedOutputStream bufferedOutputStream = null;
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            bufferedOutputStream = new BufferedOutputStream(clientSocket.getOutputStream());

            String input = bufferedReader.readLine();
            LOGGER.info("REQUEST :: {}", input);
            bufferedOutputStream.flush();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                assert bufferedReader != null;
                bufferedReader.close();
                assert bufferedOutputStream != null;
                bufferedOutputStream.close();
            } catch (Exception e) {
                System.err.println("Error closing stream : " + e.getMessage());
            }

        }
    }

    public static void main(String[] args) throws IOException {

        int port = 8088;
        ServerSocket serverSocket = new ServerSocket(port);

        while (true) {
            LOGGER.info("SERVER ON");
            SjtWebServer sjtWebServer = new SjtWebServer(serverSocket.accept());
            Thread requestThread = new Thread(sjtWebServer::acceptRequest);
            LOGGER.info("Connection opened with - {} thread! ", requestThread.getName());
            requestThread.start();
        }
    }

}
