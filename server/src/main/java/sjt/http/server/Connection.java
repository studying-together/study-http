package sjt.http.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Connection {

    private Socket socket;
    private InputStream inputStream;
    private OutputStream outputStream;

    public Connection(Socket socket) throws IOException {
        this.socket = socket;
        this.inputStream = socket.getInputStream();
        this.outputStream = socket.getOutputStream();
    }

    public HttpRequest readRequest() throws IOException {
        return HttpRequest.create(inputStream).read();
    }

    public void writeResponse() {
        HttpResponse.create(outputStream)
                .statusCode(HttpResponse.HttpStatus.OK)
                .body("body is ok")
                .write();
    }

    public void execute() throws IOException {
        HttpRequest request = readRequest();
        writeResponse();
    }
}