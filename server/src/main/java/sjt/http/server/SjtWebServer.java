package sjt.http.server;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class SjtWebServer {

    public static void main(String[] args) throws IOException, InterruptedException {

        int port = 8080;

        // 클라이언트 요청받을 준비
        ServerSocket serverSocket = new ServerSocket(port);

        // 클라이언트 요청 받기
        Socket socket = serverSocket.accept();
        InputStream inputStream = socket.getInputStream();
        OutputStream outputStream = socket.getOutputStream();

        // 클라이언트 요청 기다리기
        Thread.sleep(1000);

        // 클라이언트가 보낸 데이터 확인 및 보관
        String message = getRequestMessageByBuffredReader(inputStream);

        if(message != null){
            String[] messages = message.split(" ");
            // 클라이언트에게 메세지 보내기
            sendResponseMessage(outputStream, messages);
        }

        // 종료
        Thread.sleep(9000);

//        inputStream.close();
//        outputStream.close();
//        socket.close();
        System.out.println("server close - " + System.currentTimeMillis());
    }

    private static void sendResponseMessage(OutputStream outputStream, String[] message) throws IOException {
        System.out.println("sendResponseMessage open.. ");

        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));
        bufferedWriter.write("200 OK ! " + message[0]);
        bufferedWriter.flush();
        System.out.println("sendResponseMessage closed.. ");
        bufferedWriter.close();
    }

    private static String getRequestMessageByBuffredReader(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        System.out.println("getMessageByBuffredReader open.. ");

        String message = bufferedReader.readLine();
        System.out.println(message);
        System.out.println("getMessageByBuffredReader closed.. ");
        return message;
    }

    private static void getRequestMessageByBufferedInputStream(InputStream inputStream) throws IOException {
        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);

        while(bufferedInputStream.available() > 0) {
            System.out.print(Character.toString((char)bufferedInputStream.read()));
        }
        System.out.println();
        System.out.println("getMessageByBufferedInputStream close.. ");
    }

}
