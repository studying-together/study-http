package sjt.http.server;

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
            System.out.println(">>> thread info :: " + Thread.currentThread().getName());
            Request request = new Request(is);
            if (request.getStartLine() == null) {
                this.connection.close();
                return;
            }

            //todo 테스트 데이터 처리..
            os.write(Response.reply(HttpCode.HTTP_OK, "{\"name\":\"java\",\"age\":22}"));
            os.flush();
        } catch (IOException e) {
            this.connection.close();
        }
    }
}
