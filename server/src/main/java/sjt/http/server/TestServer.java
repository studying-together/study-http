package sjt.http.server;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

// TODO : 테스트용 코드 입니다. 구현 후 지워주세요.
public class TestServer implements Runnable {
    private Socket socket;

    public TestServer(Socket socket) {
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

            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            System.out.println(reader.readLine());

            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream);
            bufferedOutputStream.write("200 OK \r\n".getBytes());
            bufferedOutputStream.write("\r\n".getBytes());
            bufferedOutputStream.write("{\"name\":\"java\",\"age\":22}".getBytes());
            bufferedOutputStream.flush();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try{ inputStream.close(); }catch(Exception ignore){}
            try{ outputStream.close(); }catch (Exception ignore){}
            try{ socket.close(); }catch(Exception ignore){}
        }

    }



}