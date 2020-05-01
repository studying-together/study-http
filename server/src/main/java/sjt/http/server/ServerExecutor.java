package sjt.http.server;

import sjt.http.server.servlet.Controller;
import sjt.http.server.servlet.ControllerContainer;
import sjt.http.server.servlet.Request;
import sjt.http.server.servlet.Response;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class ServerExecutor {
    private final Socket socket;

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

            Response response = doService(request);
            response.writeTest(writer);

            System.out.println("writer flushed");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Response doService(Request request) {
        if (controllerContainer == null) {
            controllerContainer = new ControllerContainer(new Controller());
            controllerContainer.init();
        }

        // TODO : Response 리턴 - 이슈 발생시, 상태코드 등의 처리
        return controllerContainer.service(request);
    }

}
