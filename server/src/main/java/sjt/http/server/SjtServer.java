package sjt.http.server;

import model.request.HttpRequest;
import model.response.HttpResponse;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class SjtServer implements Runnable {
    private Socket socket;
    private final String RESOURCE_PATH = "server/src/main/resource";
    private HttpReader reader;
    private HttpWriter writer;

    public SjtServer(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        System.out.println(">>> thread info :: " + Thread.currentThread().getName());
        start(socket);
    }

    private void start(Socket socket) {
        InputStream inputStream = null;
        OutputStream outputStream = null;

        try {

            inputStream = socket.getInputStream();
            outputStream = socket.getOutputStream();

            reader = new HttpReader(inputStream);

            HttpRequest request = reader.readRequest();
            HttpResponse response = new HttpResponse();

            writer = new HttpWriter(outputStream);
            response = writer.writeResponse(request);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try{ inputStream.close(); }catch(Exception ignore){}
            try{ outputStream.close(); }catch (Exception ignore){}
            try{ socket.close(); }catch(Exception ignore){}
        }

    }


    private void echo(HttpRequest httpRequest, HttpResponse httpResponse, OutputStream outputStream){

    }

    private void getResource(String uri, HttpResponse response, OutputStream outputStream) {

    }

}
