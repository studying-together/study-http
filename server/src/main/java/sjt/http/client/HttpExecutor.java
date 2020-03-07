package sjt.http.client;

import sjt.http.core.HttpStreamReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.stream.Stream;

public class HttpExecutor {

    private final Socket socket;

    public HttpExecutor(Socket socket) {
        this.socket = socket;
    }

    public void execute() {
        doExecute();
    }

    private void doExecute() {
        try (InputStream in = socket.getInputStream();
             OutputStream out = socket.getOutputStream();) {

            RequestLine requestLine = RequestLine.parse(HttpStreamReader.readStartLine(in));
            Headers headers = headers(in);

            System.out.println(requestLine.toString());
            System.out.println(headers.toString());

            out.write((requestLine.getVersion() + " 200 OK\r\n").getBytes());
            out.write("Connection: close\r\n".getBytes());
            out.write("Content-Type: application/json;charset=UTF-8\r\n".getBytes());
            out.write("\r\n".getBytes());

            System.out.println("[SERVER] connection end");
        } catch (IOException e) {
            throw new RuntimeException("IO failed");
        }
    }

    private Headers headers(InputStream in) throws IOException {
        Headers headers = new Headers();
        Stream.of(HttpStreamReader.readHeader(in).split("\r\n"))
                .filter(s -> s.length() > 0)
                .forEach(h -> headers.put(h.split(": ")[0], h.split(": ")[1]));
        return headers;
    }

}
