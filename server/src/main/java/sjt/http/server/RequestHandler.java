package sjt.http.server;

import com.fasterxml.jackson.databind.ObjectMapper;
import sjt.http.server.controller.Controller;
import sjt.http.server.controller.MappingControllerRegistry;
import sjt.http.server.response.HttpCode;
import sjt.http.server.servlet.Request;
import sjt.http.server.servlet.Response;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class RequestHandler implements Runnable {
    Connection connection;
    OutputStream os;
    InputStream is;

    public RequestHandler(Socket socket) throws IOException {
        connection = new Connection(socket, socket.getInputStream(), socket.getOutputStream());
        this.os = this.connection.getOutputStream();
        this.is = this.connection.getInputStream();
    }

    public void run() {
        try {
            System.out.println("\n>>> thread info :: " + Thread.currentThread().getName());
            ObjectMapper mapper = new ObjectMapper();
            Request request = new Request(is);
            if (request.getStartLine() == null) {
                this.connection.close();
                return;
            }

            //todo 테스트 데이터 처리..
            Controller<?> mappedController = MappingControllerRegistry.getMappedController(request);
            Object returnValue = mappedController.handle(request);
            os.write(Response.reply(HttpCode.HTTP_OK, mapper.writeValueAsString(returnValue)));
            os.flush();
        } catch (IOException e) {
            this.connection.close();
        }
    }
}
