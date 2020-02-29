package sjt.http.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class SjtWebServer {

    public static void main(String[] args) throws IOException {

        int port = 8080;
        ServerSocket serverSocket = new ServerSocket(port);
        Socket socket = serverSocket.accept();

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

        String request = bufferedReader.readLine();
        System.out.println("[Client] : " + request);

        bufferedWriter.write("200 OK ! \r\n");
        bufferedWriter.flush();

        socket.close();
    }

}
