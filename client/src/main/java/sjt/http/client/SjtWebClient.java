package sjt.http.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SjtWebClient {

    public static void main(String[] args) throws IOException {

        Socket socket = new Socket("127.0.0.1", 8081);

        String body = "id=1&name=yusik&age=10";
        String charset = "utf-8";

        Map<String, String> headers = new HashMap<>();
        headers.put("Host", "localhost:8081");
        headers.put("Connection", "keep-alive");
        headers.put("Cache-Control", "max-age=0");
        headers.put("Content-Type", "application/x-www-form-urlencoded; charset=" + charset);
        headers.put("Content-Length", String.valueOf(body.getBytes().length));

        OutputStream out = socket.getOutputStream();

        // request line
        out.write("GET /index.html HTTP/1.1\r\n".getBytes());

        // header
        for (Map.Entry<String, String> entry : headers.entrySet()) {
            String header = entry.getKey() + ": " + entry.getValue() + "\r\n";
            out.write(header.getBytes());
        }
        out.write("\r\n".getBytes()); // end header

        // body
        out.write(body.getBytes(Charset.forName(charset)));

        // print response
        List<String> lines = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String line;
        do {
            line = reader.readLine();
            lines.add(line);
        } while (!line.isEmpty());

        lines.forEach(System.out::println);

        reader.close();
        out.close();
        socket.close();
    }

}
