package sjt.http.client;

import com.squareup.okhttp.OkHttpClient;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.Socket;
import java.net.URL;

public class OkHttpClientDebugger {

    public static void main(String[] args) throws IOException {
        useOkHttpClient();
    }


    private static void useOkHttpClient() throws IOException {
        OkHttpClient client = new OkHttpClient();
        HttpURLConnection connection = client.open(new URL("http://localhost:8000"));
        String contentType = connection.getContentType();
        HttpURLConnection connection2 = client.open(new URL("http://localhost:8000"));
        System.out.println("pool size: " + client.getConnectionPool().getHttpConnectionCount());

        String contentType2 = connection.getContentType();
        System.out.println(contentType);
        System.out.println("pool size: " + client.getConnectionPool().getHttpConnectionCount());




        System.out.println(connection.getResponseCode());
        System.out.println(connection.getResponseMessage());
        System.out.println(connection.getHeaderFields().toString());

        InputStream inputStream = connection.getInputStream();
        byte[] buffer = new byte[512];
        StringBuilder sb = new StringBuilder();
        while (inputStream.read(buffer) != -1) {
            sb.append(new String(buffer));
        }
        System.out.println(sb.toString());
    }

}
