package sjt.http.client;

import sjt.http.core.parser.BodyParser;
import sjt.http.core.Headers;
import sjt.http.core.reader.HttpStreamReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicInteger;

public class HttpExecutor {

    private final Socket socket;
    private static AtomicInteger idGenerator = new AtomicInteger(1);
    private HttpStreamReader streamReader = new HttpStreamReader();

    public HttpExecutor(Socket socket) {
        this.socket = socket;
    }

    public void execute() {
        int id = idGenerator.getAndAdd(1);
        System.out.println("----->  " + id + " Client Connected");
        doExecute();
        System.out.println("----->  " + id + " Client Closed");
    }

    private void doExecute() {
        try (InputStream in = socket.getInputStream();
             OutputStream out = socket.getOutputStream()) {

            RequestLine requestLine = RequestLine.parse(streamReader.readStartLine(in));
            Headers headers = parseHeaders(in);
            System.out.println(headers);
            String body = null;

            if (headers.hasBody()) {
                body = getBody(in, headers.getContentLength());
            }

            System.out.println("RequestLine: " + requestLine.toString());
            System.out.println("Header: " + headers.toString());
            System.out.println("Content: " + body);

            // 2번째 과제
            if (body != null) {
                Object content = BodyParser.parse(body, headers.getContentType());
                System.out.println(content);
            }

            String bodyResponse = "body~";

            out.write((requestLine.getVersion() + " 200 OK\r\n").getBytes());
            out.write("Connection: close\r\n".getBytes());
            out.write("Content-Type: application/json;charset=UTF-8\r\n".getBytes());
            out.write(("Content-Length: " + bodyResponse.length() + "\r\n").getBytes());
            out.write("\r\n".getBytes());
            out.write(bodyResponse.getBytes());

        } catch (IOException e) {
            throw new RuntimeException("IO failed");
        }
    }

    private Headers parseHeaders(InputStream in) throws IOException {
        return new Headers(streamReader.readHeader(in));
    }

    private String getBody(InputStream in, int contentLength) throws IOException {
        return new String(streamReader.readContent(in, contentLength));
    }

}
