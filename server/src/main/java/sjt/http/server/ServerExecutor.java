package sjt.http.server;

import java.io.*;
import java.net.Socket;

public class ServerExecutor {
    private final Socket socket;

    public ServerExecutor(Socket socket) {
        this.socket = socket;
    }

    public void execute() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            String requestTest = reader.readLine();
            System.out.println("request: " + requestTest);

        } catch (IOException e) {
//            e.printStackTrace();
            throw new RuntimeException("IOException occurred");
        }
    }
}
