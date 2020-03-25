package sjt.http.client;

import com.squareup.okhttp.*;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class OkHttpClientDebugger {

    public static void main(String[] args) throws IOException {
        useOkHttpClient();
    }

    private static void useOkHttpClient() throws IOException {
        ConnectionPool connectionPool = new ConnectionPool(10, 10 * 60 * 1000);
        Cache cache = new Cache(new File("./cache"), 100);

        OkHttpClient client = new OkHttpClient();
        client.setConnectionPool(connectionPool);
        client.setCache(cache);

        Request request = new Request.Builder()
                .url(new URL("http://localhost:9090/hello"))
                .get()
                .build();
        Call call = client.newCall(request);
        Response response = call.execute();
        System.out.println(response);

        Call call2 = client.newCall(request);
        Response response2 = call2.execute();
        System.out.println(response2);

        System.out.println(cache);
        System.out.println(cache.getHitCount());

    }

}
