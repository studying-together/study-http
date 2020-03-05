package sjt.http.server;

import sjt.http.server.model.HttpStatusCode;
import sjt.http.server.model.request.HttpRequest;
import sjt.http.server.model.response.HttpResponse;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class SjtServer implements Runnable {
    private Socket socket;
    private final String RESOURCE_PATH = "server/src/main/resource";

    public SjtServer(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        System.out.println(">>> thread info :: " + Thread.currentThread().getName());
        readRequest(socket);
    }

    private void readRequest(Socket socket) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            HttpRequest httpRequest = new HttpRequest();

            // 1. request line
            String requestLine = reader.readLine();

            if (requestLine != null) {
                httpRequest.parseRequestLine(requestLine);
            }

            // 2. request header
            String requestHeaderLine;
            StringBuffer requestHeader = new StringBuffer();

            while (reader.ready() && (requestHeaderLine = reader.readLine()) != null) {
                if("".equals(requestHeaderLine)){
                    break;    // 빈 줄 --> header 끝
                }
                requestHeader.append(requestHeaderLine);
                requestHeader.append("\n");
            }

            //httpRequest.setRequestHeader(requestHeader.toString());

            // 3. request body
            String requestBodyLine;
            StringBuffer requestBody = new StringBuffer();

            while (reader.ready() && (requestBodyLine = reader.readLine()) != null) {
                requestBody.append(requestBodyLine);
                requestBody.append("\n");
            }


            // request 출력
            System.out.println("request : " + httpRequest);

            HttpResponse httpResponse = new HttpResponse();
            writeResponse(httpRequest, httpResponse, socket.getOutputStream());

            reader.close();
            socket.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void writeResponse(HttpRequest httpRequest, HttpResponse httpResponse, OutputStream outputStream) {
        FileInputStream fileInputStream = null;
        BufferedOutputStream bufferedOutputStream = null;

        try {
            Path path = Paths.get(RESOURCE_PATH + httpRequest.getUri());
            String contentType = Files.probeContentType(path);

            fileInputStream = new FileInputStream(path.toString());
            bufferedOutputStream = new BufferedOutputStream(outputStream);

            int readCount = 0;
            byte[] buffer = new byte[1024];

            bufferedOutputStream.write(HttpStatusCode.HTTP_STATUS_OK.getStatusCode().getBytes());

            while((readCount = fileInputStream.read(buffer)) != -1){
                bufferedOutputStream.write(buffer,0, readCount);
            }

            fileInputStream.close();
            bufferedOutputStream.close();


        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            try{ fileInputStream.close(); }catch(Exception ignore){}
            try{ bufferedOutputStream.close(); }catch (Exception ignore){}
        }

    }

    private void getResource(String uri, HttpResponse response, OutputStream outputStream) {

    }

}
