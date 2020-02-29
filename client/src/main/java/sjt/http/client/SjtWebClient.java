package sjt.http.client;

import sjt.http.server.HttpHeader;
import sjt.http.server.HttpHeaders;
import sjt.http.server.HttpMethod;

import javax.xml.ws.spi.http.HttpHandler;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class SjtWebClient {

    public static void main(String[] args) throws IOException {

        Socket socket = new Socket("127.0.0.1", 8080);

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

        HttpHeader httpHeader = new HttpHeader();
        httpHeader.setRequestLine(HttpMethod.GET + " /index.html HTTP/1.1");
        httpHeader.setConnection("keep-alive");
        httpHeader.setHost("https://github.com/Study-Java-Together/study-http");

        sendHeader(bufferedWriter, httpHeader);

        String response = bufferedReader.readLine();
        System.out.println("[Server] : " + response);

        socket.close();
    }

    private static void sendHeader(BufferedWriter bufferedWriter, HttpHeader httpHeader) throws IOException {
        bufferedWriter.write(HttpHeaders.REQUEST_LINE + " : " + httpHeader.getRequestLine() + " \r\n");
        bufferedWriter.write(HttpHeaders.CONNECTION + " : " + httpHeader.getConnection() + " \r\n");
        bufferedWriter.write(HttpHeaders.HOST + " : " + httpHeader.getHost() + " \r\n");
        bufferedWriter.write(" \r\n");

        bufferedWriter.flush();
    }

}
