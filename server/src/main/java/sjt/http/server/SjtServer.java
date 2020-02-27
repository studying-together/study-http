package sjt.http.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class SjtServer implements Runnable {
    private Socket socket;

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
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            HttpRequest httpRequest = new HttpRequest();

            // 1. request line
            String requestLine = reader.readLine();

            if (requestLine != null) {
                parsingRequestLine(requestLine, httpRequest);
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

            if (requestHeader.length() > 2) {
                requestHeader.setLength(requestHeader.length() - 1);
                httpRequest.setRequestHeader(requestHeader.toString());
            }


            // 3. request body
            String requestBodyLine;
            StringBuffer requestBody = new StringBuffer();

            while (reader.ready() && (requestBodyLine = reader.readLine()) != null) {
                requestBody.append(requestBodyLine);
                requestBody.append("\n");
            }

            if (requestBody.length() > 2) {
                requestBody.setLength(requestBody.length() - 1); // 마지막 \n 제거
                httpRequest.setRequestBody(requestBody.toString());
            }


            // request 출력
            System.out.println("request : " + httpRequest);

            // response 입력
            writer.write("HTTP/1.1 200 OK \r\n");
            writer.write(httpRequest.toString());
            writer.flush();

            // close
            reader.close();
            writer.close();
            socket.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void parsingRequestLine(String request, HttpRequest httpRequest) {
        // Request-Line = Method SP Request-URI SP HTTP-Version CRLF
        String[] requestDatas = request.split(" ");

        // TODO : null check , 예외 처리
        if (requestDatas.length == 3) {
            httpRequest.setMethod(HttpMethodType.valueOf(requestDatas[0]));
            httpRequest.setUri(requestDatas[1]);
            httpRequest.setProtocolType(requestDatas[2]);
        }

    }

}
