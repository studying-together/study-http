package sjt.http.server;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import sjt.http.server.controller.Controller;
import sjt.http.server.controller.MappingControllerRegistry;
import sjt.http.server.exception.HttpInvalidRequestException;
import sjt.http.server.response.HttpCode;
import sjt.http.server.servlet.Request;
import sjt.http.server.servlet.Response;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

@Slf4j
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
            ObjectMapper mapper = new ObjectMapper();
            Request request = new Request(is);

            Controller<?> mappedController = MappingControllerRegistry.getMappedController(request);
            log.debug(">>> mappedController :: {}", mappedController);
            Object returnValue = mappedController.handle(request);
            os.write(Response.reply(HttpCode.HTTP_OK, mapper.writeValueAsString(returnValue)));
            os.flush();
        } catch (HttpInvalidRequestException e) {
            handleHttpInvalidRequestException(os);
        } catch (IOException e) {
            this.connection.close();
        }
    }

    private void handleHttpInvalidRequestException(OutputStream os) {
        try {
            os.write(Response.reply(HttpCode.HTTP_BAD_REQUEST, null));
            os.flush();
        } catch (IOException e) {
            this.connection.close();
        }
    }
}
