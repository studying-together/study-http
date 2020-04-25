package sjt.http.client.core;

import sjt.http.client.core.HttpResponse.Result;
import sjt.http.core.log.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class HttpClient {

    private int socketTimeout;

    private Socket socket;
    private InputStream inputStream;
    private OutputStream outputStream;

    public HttpClient(int socketTimeout) {
        this.socketTimeout = socketTimeout;
    }

    public Result execute(HttpMethod method, String host, int port, String path, String body) {
        try {
            openConnection(host, port);
            HttpRequest request = new HttpRequest(outputStream, method, createRequestUri(host, port, path), body);
            HttpResponse response = new HttpResponse(inputStream);

            request.sendRequest();
            response.readResponse();
            return response.toResult();
        } catch (IOException e) {
            throw new HttpException(e);
        }
    }

    private String createRequestUri(String host, int port, String path) {
        return host + port + path;
    }

    private void openConnection(String host, int port) throws IOException {
        socket = new Socket(host, port);
        socket.setSoTimeout(socketTimeout);
        inputStream = socket.getInputStream();
        outputStream = socket.getOutputStream();
        Logger.log(this, "connection opened");
    }
}
