package sjt.http.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kohyusik on 2020/02/26.
 */
public class Echo implements Runnable {

    private final static int CARRIAGE_RETURN = '\r';
    private final static int LINE_FEED = '\n';
    private final static String CRLF = String.valueOf(new char[]{CARRIAGE_RETURN, LINE_FEED});
    private final static int END_OF_STREAM = -1;

    private final Socket connection;

    public Echo(Socket connection) {
        this.connection = connection;
    }

    @Override
    public void run() {

        System.out.println("## " + Thread.currentThread().getName());
        System.out.println("connect from " + connection.getRemoteSocketAddress());
        try (InputStream in = connection.getInputStream();
             OutputStream out = connection.getOutputStream()) {

            List<String> lines = new ArrayList<>();

            int prevChar = -1;
            int currentChar;

            do {
                StringBuilder buffer = new StringBuilder();
                for (; ; ) {
                    long start = System.currentTimeMillis();
                    currentChar = in.read();

                    if (currentChar == END_OF_STREAM) {
                        long finish = System.currentTimeMillis();
                        System.out.printf("timeout: %d\n", finish - start);
                        return;
                    }
                    buffer.append((char) currentChar);
                    if (prevChar == CARRIAGE_RETURN && currentChar == LINE_FEED) break;
                    prevChar = currentChar;
                }
                lines.add(buffer.toString());
            } while (!CRLF.equals(lines.get(lines.size() - 1)));

            System.out.println(lines);

            out.write("HTTP/1.1 200 OK\r\n".getBytes());
            for (String line : lines) {
                out.write(line.getBytes());
            }
            out.write("\r\n".getBytes());
            out.flush();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            System.out.println("## finally " + Thread.currentThread().getName());
            try {
                connection.close();
            } catch (IOException ignored) {
            }
        }

    }
}
