package sjt.http.server;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

@Slf4j
public class SjtWebServer {
    public static void main(String[] args) throws IOException {

        int port = 8081;
        ServerSocket serverSocket = new ServerSocket(port); // 서버 생성을 위한 serversocket

        Socket client = serverSocket.accept(); // client와 통신하기 위한 socket, client 접속 대기

        final BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));

        String clientRequest = in.readLine();

        log.info("client request: " + clientRequest);

        PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(client.getOutputStream())), true); // 출력 스트림 만들기
        out.write("HTTP/1.1 200 OK");

        client.close();
    }
}