package sjt.http.server;

import sjt.http.server.model.Status;
import sjt.http.server.model.request.HttpRequest;
import sjt.http.server.model.response.HttpResponse;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpHandler implements Runnable {
    private static final Logger LOGGER = LoggerFactory.getLogger(HttpHandler.class);
    private static final byte[] CRLF = "\r\n".getBytes();
    private final Socket socket;
    private OutputStream outputStream;

    public HttpHandler(Socket socket) {
        this.socket = socket;
        try {
            this.outputStream = socket.getOutputStream();
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
    }

    @Override
    public void run() {
        LOGGER.info("thread's name: {} ", Thread.currentThread().getName());

        HttpRequest request = readRequest();
        if(request == null) {
            writeResponse(Status.INTERNAL_SERVER_ERROR);    //에러를 처리할 수 있는 시간이 없으니 문제발생시 일단 500 반환
            return;
        }
        HttpResponse response = loadResponse(request);
        writeResponse(response);
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private HttpRequest readRequest() {
        try {
            InputStream inputStream = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            HttpRequest httpRequest = new HttpRequest().parseRequest(reader);
            LOGGER.info("request:\n {}", httpRequest);
            return httpRequest;
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        return null;
    }

    private void writeResponse(Status status) {
        try (
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(this.outputStream)
        ) {
            bufferedOutputStream.write(status.getStatusCode().getBytes());
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
    }

    private HttpResponse loadResponse(HttpRequest request) {
        return new HttpResponse().load(request);
    }

    //TODO:: writer 를 만들어서 권한 부여해주기
    private void writeResponse(HttpResponse httpResponse) {
        try (
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(this.outputStream)
        ) {
            // 시작줄
            bufferedOutputStream.write(httpResponse.getProtocolVersion().getBytes());
            bufferedOutputStream.write(" ".getBytes());
            bufferedOutputStream.write(httpResponse.getStatusCode().getStatusCode().getBytes());
            bufferedOutputStream.write(CRLF);

            // 헤더
            bufferedOutputStream.write(httpResponse.getHeadersByte());
            bufferedOutputStream.write(CRLF);

            // 바디
            bufferedOutputStream.write(httpResponse.getBody());
            bufferedOutputStream.write(CRLF);

            bufferedOutputStream.flush();
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
    }
}
