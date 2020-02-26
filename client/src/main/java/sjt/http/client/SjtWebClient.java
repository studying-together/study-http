package sjt.http.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class SjtWebClient {

    public static void main(String[] args) throws IOException {

        Socket socket = new Socket("127.0.0.1", 8081);

        OutputStream out = socket.getOutputStream();
        out.write("GET /index.html HTTP/1.1\r\n".getBytes());
        out.write("Host: localhost:8081\r\n".getBytes());
        out.write("Connection: keep-alive\r\n".getBytes());
        out.write("Cache-Control: max-age=0\r\n".getBytes());
        out.write("\r\n".getBytes());

        List<String> lines = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String line;
        do {
            line = reader.readLine();
            lines.add(line);
        } while (!line.isEmpty());

        System.out.println(lines);

        reader.close();
        out.close();
        socket.close();
    }

}
