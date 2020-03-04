package sjt.http.server.handler;

import sjt.http.server.request.HttpRequest;
import sjt.http.server.response.HttpResponse;
import sjt.http.server.response.HttpStatus;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Writer;
import java.net.Socket;
import java.util.Map;

/**
 * Created by yusik on 2020/03/04.
 */
public class RequestHandler implements Runnable {

    private static final String HTTP_VERSION = "HTTP/1.1";
    private final static String CRLF = "\r\n";
    private static final String SP = " ";
    private static final String RESPONSE_HEADER_DELIMITER = ": ";

    private final Socket connection;

    public RequestHandler(Socket connection) {
        this.connection = connection;
    }

    @Override
    public void run() {

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {

            HttpRequest httpRequest = new HttpRequest(in);
            HttpResponse httpResponse = new HttpResponse(out);

            // test
            System.out.println(httpRequest.getHeaders());
            httpResponse.setStatus(HttpStatus.OK);
            httpResponse.addHeader("WHO-AM-I", "TEST");

            // response
            HttpStatus status = httpResponse.getStatus();
            String statusLine = String.join(SP, HTTP_VERSION, String.valueOf(status.getCode()), status.getMessage(), CRLF);

            Writer writer = httpResponse.getWriter();
            writer.write(statusLine);
            for (Map.Entry<String, String> header : httpResponse.getHeaders().entrySet()) {
                writer.write(header.getKey() + RESPONSE_HEADER_DELIMITER + header.getValue() + CRLF);
            }
            writer.write(CRLF);
            writer.flush();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (IOException ignored) {
            }
        }

    }
}
