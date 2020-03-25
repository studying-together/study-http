package sjt.http.client;

import com.squareup.okhttp.ConnectionPool;
import com.squareup.okhttp.HttpResponseCache;
import com.squareup.okhttp.OkHttpClient;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class OkHttpClientDebugger {

    public static void main(String[] args) throws IOException {
        useOkHttpClient();
    }

    private static void useOkHttpClient() throws IOException {
        ConnectionPool connectionPool = new ConnectionPool(10, 10 * 60 * 1000);
        HttpResponseCache httpResponseCache = new HttpResponseCache(new File("./cache"), 100);

        OkHttpClient client = new OkHttpClient();

        client.setResponseCache(httpResponseCache);
        client.setConnectionPool(connectionPool);

        HttpURLConnection connection = client.open(new URL("http://localhost:9090/hello"));
        connection.setUseCaches(true);
        connection.setConnectTimeout(10 * 60 * 1000);

        String contentType = connection.getContentType();
        System.out.println(contentType);

        HttpURLConnection connection2 = client.open(new URL("http://localhost:9090/hello"));
        connection2.setUseCaches(true);
        connection2.setConnectTimeout(10 * 60 * 1000);
        String contentType2 = connection2.getContentType();
        System.out.println(contentType2);
    }

}
