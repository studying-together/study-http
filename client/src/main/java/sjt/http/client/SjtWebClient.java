package sjt.http.client;

import com.squareup.okhttp.OkHttpClient;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class SjtWebClient {

    public static void main(String[] args) throws IOException {

        OkHttpClient client = new OkHttpClient();
        HttpURLConnection connection = client.open(new URL("https://www.naver.com"));
        String contentType = connection.getContentType();
        System.out.println(contentType);
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
