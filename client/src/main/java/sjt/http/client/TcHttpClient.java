package sjt.http.client;

import sjt.http.server.servlet.Request;
import sjt.http.server.servlet.Response;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

/**
 * 실제 통신 수행
 */
public class TcHttpClient {
    private Request request;
    private Response response;
    private BufferedReader reader;
    private BufferedWriter writer;

    // executes HTTP request
    public <T> T execute(HttpMethod httpMethod, String host, int port, String path, String body, Class<T> clazz) {
        sendRequest(httpMethod, host, port, path, body);
        return readResponse();
    }

    private void sendRequest(HttpMethod httpMethod, String host, int port, String path, String body) {
        initTcHttpClient(host, port);

        // TODO : request 생성
        // TODO : header, body write
    }

    private <T> T readResponse() {
        return null;
    }

    // socket 초기화
    private void initTcHttpClient(String host, int port) {
        try {
            Socket socket = new Socket(host, port);
            writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
