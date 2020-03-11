package sjt.http.client;

import sjt.http.client.clone.OkHttpClient;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class TestClient {

    public static void main(String[] args) throws MalformedURLException {
        OkHttpClient client = new OkHttpClient();
        HttpURLConnection connection = client.open(new URL("http://localhost:8080"));
    }

}
