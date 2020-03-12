package sjt.http.server;

import sjt.http.server.model.Status;
import sjt.http.server.model.request.HttpRequest;
import sjt.http.server.model.response.HttpResponse;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpHandler implements Runnable {
    private static final Logger LOGGER = LoggerFactory.getLogger(SjtWebServer.class);
    private final String RESOURCE_PATH = "server/src/main/resources";
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
        HttpRequest httpRequest = readRequest();
        if(httpRequest == null) {
            writeResponse(Status.INTERNAL_SERVER_ERROR);    //에러를 처리할 수 있는 시간이 없으니 문제발생시 일단 500 반환
            return;
        }
        HttpResponse httpResponse = new HttpResponse();
        writeResponse(httpRequest, httpResponse);
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

    //TODO:: 보내는 부분과 반환 데이터를 만드는 부분을 분리하기
    private void writeResponse(HttpRequest httpRequest, HttpResponse httpResponse) {
        Path path = Paths.get(RESOURCE_PATH + httpRequest.getUri());
        try (
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(this.outputStream)
        ) {
            int readCount = 0;
            byte[] buffer = new byte[1024];
            try (FileInputStream fileInputStream = new FileInputStream(path.toString())) {
                bufferedOutputStream.write(Status.OK.getStatusCode().getBytes());
                while ((readCount = fileInputStream.read(buffer)) != -1) {
                    bufferedOutputStream.write(buffer, 0, readCount);
                    bufferedOutputStream.flush();
                }
            } catch (FileNotFoundException e) {
                bufferedOutputStream.write(Status.NOT_FOUND.getStatusCode().getBytes());
                bufferedOutputStream.flush();
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
    }
}
