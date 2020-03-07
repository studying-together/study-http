package sjt.http.client;

import sjt.http.core.Headers;
import sjt.http.core.Method;
import sjt.http.core.reader.HttpStreamReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class SjtWebClient {

    public static void main(String[] args) throws IOException {
        System.out.println("[CLIENT] start");
        call(Method.GET, "index.html",  new HashMap<String, String>(){{
            put("Accept", "*.*");
            put("Content-Type", "application/json;charset=UTF-8");
        }}, "{ \"name\" : \"jun\", \"age\" : 34, \"hobby\" : \"football\" }");
        call(Method.GET, "index.html",  new HashMap<String, String>(){{
            put("Accept", "*.*");
            put("Content-Type", "text/plain;charset=UTF-8");
        }}, "hello my name is jun");
        System.out.println("[CLIENT] end");
    }

    public static void call(Method method, String url, Map<String, String> header) throws IOException {
        call(method, url, header, null);
    }

    public static void call(Method method, String url, Map<String, String> header, String body) throws IOException {
        System.out.println("-----> CALL START");
        Socket socket = new Socket("127.0.0.1", 8081);
        HttpStreamReader streamReader = new HttpStreamReader();

        try (InputStream in = socket.getInputStream();
             OutputStream out = socket.getOutputStream()) {

            String headerMessage = header.entrySet()
                    .stream()
                    .map(e -> e.getKey()  + ": " + e.getValue())
                    .collect(Collectors.joining("\r\n", "", "\r\n"));

            out.write((method.toString() + " "  + url +  " HTTP/1.1\r\n").getBytes());
            out.write(headerMessage.getBytes());
            if (body != null) {
                out.write(("Content-Length: " + body.length() + "\r\n").getBytes());
            }
            out.write("\r\n".getBytes());

            if (body != null) {
                out.write(body.getBytes());
            }

            String statusLine = streamReader.readStartLine(in);
            Headers headers = new Headers(streamReader.readHeader(in));
            String responseBody = null;

            if (headers.hasBody()) {
                responseBody = new String(streamReader.readContent(in, headers.getContentLength()));
            }

            System.out.println(statusLine);
            System.out.println(headers);
            System.out.println(responseBody);
        }
        socket.close();
        System.out.println("-----> CALL END");
    }

}
