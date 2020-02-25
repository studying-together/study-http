package sjt.http.client;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

public class SjtWebClient {

    public static void main(String[] args) throws IOException, InterruptedException {

        // 서버 접속요청
        Socket socket = new Socket("127.0.0.1", 8080);
        InputStream inputStream = socket.getInputStream();
        OutputStream outputStream = socket.getOutputStream();

        // 서버에게 메세지 보내기
        sendRequestMessage(outputStream);

        Thread.sleep(3000);
        // 메세지 받기
        getResponseMessage(inputStream);

        // 종료
        Thread.sleep(1000);

//        inputStream.close();
//        outputStream.close();
//        socket.close();
        System.out.println("client close");
    }

    private static void getResponseMessage(InputStream inputStream) throws IOException {
        System.out.println("get Message.. - " + System.currentTimeMillis());
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        System.out.println(bufferedReader.readLine());
    }

    private static void sendRequestMessage(OutputStream outputStream) throws IOException {
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream);
        bufferedOutputStream.write("GET /index.html HTTP/1.1".getBytes());
        bufferedOutputStream.flush();
        System.out.println("send Message..");
        bufferedOutputStream.close();
    }

}
