package sjt.http.client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.stream.Stream;

public class SjtWebClient {

    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("127.0.0.1", 8081);

        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

        //writer.write("POST /cgi-bin/process.cgi HTTP/1.1\r\n");
        getRequest(writer);
        writer.flush();

        // 서버에서 받은 응답 값 출력.
        Stream<String> reponse = reader.lines();
        reponse.forEach(System.out::println);

        socket.close();
    }

    public static void getRequest(BufferedWriter writer) throws IOException{
        writer.write("GET /text.txt HTTP/1.1\r\n");
        writer.write("User-Agent: Mozilla/4.0 (compatible; MSIE5.01; Windows NT)\r\n");
        writer.write("Host: www.tutorialspoint.com\r\n");
        writer.write("Accept-Language: en-us\r\n");
        writer.write("Accept-Encoding: gzip, deflate\r\n");
        writer.write("Connection: Keep-Alive\r\n");
        writer.write("\r\n");
    }

    public static void postRequest(BufferedWriter writer) throws IOException{
        writer.write("POST /info HTTP/1.1\r\n");
        writer.write("User-Agent: Mozilla/4.0 (compatible; MSIE5.01; Windows NT)\r\n");
        writer.write("Host: www.tutorialspoint.com\r\n");
        writer.write("Content-Type: application/json\r\n");
        writer.write("Content-Length: length\r\n");
        writer.write("Accept-Language: en-us\r\n");
        writer.write("Accept-Encoding: gzip, deflate\r\n");
        writer.write("Connection: Keep-Alive\r\n");
        writer.write("\r\n");
        writer.write("        {\n"
                + "            \"followersCount\" : 220,\n"
                + "            \"tweettext\":\"#Stack Overflow rocks\",\n"
                + "            \"Name\": \"John Doe\"\n"
                + "        }\r\n");
    }

}
