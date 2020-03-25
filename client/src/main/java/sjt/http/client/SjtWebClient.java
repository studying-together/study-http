package sjt.http.client;

import com.squareup.okhttp.OkHttpClient;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class SjtWebClient {

    public static void main(String[] args) throws IOException {
//        jdkClient();
        useOkHttpClient();
    }

    private static void jdkClient() throws IOException {
        URL url = new URL("http://localhost:8080/testabc");
        URLConnection urlConnection = url.openConnection();
        InputStream inputStream = urlConnection.getInputStream();
        byte[] buffer = new byte[512];
        StringBuilder sb = new StringBuilder();
        while (inputStream.read(buffer) != -1) {
            sb.append(new String(buffer));
        }
        System.out.println(sb.toString());
    }

    private static void useOkHttpClient() throws IOException {
        OkHttpClient client = new OkHttpClient();
        HttpURLConnection connection = client.open(new URL("http://localhost:8080/testabc"));
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
