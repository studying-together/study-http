package sjt.http.client;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import sjt.http.server.servlet.Request;
import sjt.http.server.servlet.Response;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

/**
 * 실제 통신 수행
 */
public class TcHttpClient extends HttpClient {
    private Request request;
    private Response response;

    // executes HTTP request
    public Response execute(HttpMethod httpMethod) {

        sendRequest(httpMethod);
        readResponse();
        return new Response();
    }

    private void sendRequest(HttpMethod httpMethod) {
        initTcHttpClient();

        try {
            sendRequestHeader(httpMethod);

            String method = httpMethod.getName();
            if (method.equals("GET")) {
                // GET
            } else if (method.equals("POST")) {
                // POST
            } else {
                // HEAD, PUT ..
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (HttpException e) {
            e.printStackTrace();
        }

    }

    private void readResponse() {
    }


    // socket 초기화
    private void initTcHttpClient() {
        try {
            setSocket(new Socket(sessionHost, sessionPort));
            openConnection();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
