package sjt.http.server;

import sjt.http.server.servlet.ControllerContainer;
import sjt.http.server.servlet.Request;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.net.Socket;

public class ServerExecutor {
    private final Socket socket;
    private static final String CRLF = "\r\n";

    private ControllerContainer controllerContainer;

    public ServerExecutor(final Socket socket) {
        this.socket = socket;
    }

    public void execute() {
        System.out.println("execute() called");
        doExecute();
    }

    public void doExecute() {
        System.out.println("doExecute() called");
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()))) {

            Request request = new Request(reader);
            request.read();

            doService(request);

            writeMockResponse(writer);
            System.out.println("writer flushed");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void writeMockResponse(BufferedWriter writer) throws IOException {
        final String mockStartLine = "HTTP1.1 200 OK" + CRLF;
        final String mockHeader = "Cache-Control: no-cache\n"
                + "Connection: Keep-Alive\n"
                + "Content-Type: application/json; charset=UTF-8\n"
                + "Date: Thu, 12 Mar 2020 09:28:35 GMT\n" + CRLF;
        final String body = "{\n"
                + "   \"id\":\"1004\",\n"
                + "   \"name\":\"java\",\n"
                + "   \"age\":22\n"
                + "}";
        writer.write(mockStartLine);
        writer.write(mockHeader);
        writer.write(body);
    }

    private <T> T doService(Request request) throws InvocationTargetException, IllegalAccessException {
        if (controllerContainer == null) {
            controllerContainer = new ControllerContainer();
            controllerContainer.init();
        }

        return controllerContainer.service(request);
    }

}
