package sjt.http.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class SjtWebClient {

    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("127.0.0.1", 8081);

        DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
        DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());

        dataOutputStream.writeUTF("GET /index.html HTTP/1.1");
        dataOutputStream.flush();

        // 서버에서 받은 응답 값 출력.
        String response = dataInputStream.readUTF();
        System.out.println(response);

        socket.close();
    }

}
