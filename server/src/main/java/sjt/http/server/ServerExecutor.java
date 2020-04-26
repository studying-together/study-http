package sjt.http.server;

import java.io.*;
import java.net.Socket;

public class ServerExecutor {
    private final Socket socket;

    public ServerExecutor(final Socket socket) {
        this.socket = socket;
    }

    public void execute() {
        System.out.println("execute() called");
        doExecute();
    }

    public void doExecute() {
        System.out.println("doExecute() called");
        try ( BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
              BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()))) {
            String requestTest = reader.readLine();
            System.out.println("request: " + requestTest);
            writer.write("서버 응답: 목데이터");
            writer.flush();
            System.out.println("writer flushed");
        } catch (IOException e) {
            throw new RuntimeException("IOException occurred");
        }
    }

    public void parseHeaders() {

    }
}
