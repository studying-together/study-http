package sjt.http.client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.stream.Stream;

/**
 * 실제 통신 수행
 */
public class TcHttpClient {
    private static final String HTTP_VERSION = "HTTP/1.1";
    private static final String CR = "\r\n";
    private static final int MTU = 1500;
    private Connection connection;

    // executes HTTP request
    public <T> T execute(HttpMethod httpMethod, String host, int port, String path, String body, Class<T> clazz) {
        init(host,port);
        StringBuilder stringBuilder = new StringBuilder()
            .append("GET /home.html HTTP/1.1\r\n");
        sendRequest(httpMethod, host, port, path, body);
        return readResponse();
    }

    private void sendRequest(HttpMethod httpMethod, String host, int port, String path, String body) {
//        initTcHttpClient(host, port);

        // TODO : request 생성
        // TODO : header, body write
        try {
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(connection.getOut()), MTU);
            writer.write(httpMethod.name() + " " + path + " " + HTTP_VERSION + CR);
            writer.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private <T> T readResponse() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getIn()), MTU);
        Stream<String> response = reader.lines();
        // 서버에서 받은 응답 값 출력.
        response.forEach(System.out::println);
        return null;
    }

    private void init(String host, int port) {
        connection = new Connection();
        try {
            connection.connect(host, port, Integer.MAX_VALUE);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
