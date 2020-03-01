package sjt.http.server;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

@Slf4j
public class SjtWebServer {
    public static void main(String[] args) throws IOException {
        // 서버 생성을 위한 serversocket
        try (ServerSocket serverSocket = new ServerSocket(8081)) {
            while (true) {
                try (
                        Socket client = serverSocket.accept(); // client와 통신하기 위한 socket, client 접속 대기
                        BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                        PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(client.getOutputStream())))) {

                    String request = in.readLine();
                    out.write(server(request));
                }
            }
        }
    }

    private static String server(String request) {
        HttpRequest.HttpHeaders httpHeaders = new HttpRequest.HttpHeaders();

        try {
            httpHeaders = new HttpRequest.HttpHeaders(request);
            log.info("client request: " + httpHeaders.toString());
            return HttpResponse.ok(httpHeaders).toResponse();
        } catch (IllegalArgumentException | ArrayIndexOutOfBoundsException e) {
            return HttpResponse.badRequest(httpHeaders).toResponse();
        }
    }
}