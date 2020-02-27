package sjt.http.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class SjtServer extends Thread {
    private Socket socket;

    public SjtServer(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        // request
        readRequest(socket);
    }

    public static void readRequest(Socket socket) {
        try {
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());

            // request 읽기
            String request = dataInputStream.readUTF();
            System.out.println("request : " + request);

            // response 입력
            dataOutputStream.writeUTF("response : " + request);
            dataOutputStream.flush();

            // close
            dataInputStream.close();
            dataOutputStream.close();
            socket.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
