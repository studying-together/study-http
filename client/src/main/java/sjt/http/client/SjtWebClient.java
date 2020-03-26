package sjt.http.client;

import com.squareup.okhttp.OkHttpClient;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.methods.GetMethod;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.time.Duration;

public class SjtWebClient {

    public static void main(String[] args) throws IOException {
//        jdkClient();
//        useOkHttpClient();
        httpClient();
    }

    private static void httpClient() throws IOException {
        MultiThreadedHttpConnectionManager connectionManager = new MultiThreadedHttpConnectionManager();
        connectionManager.setMaxConnectionsPerHost(2);
        connectionManager.setMaxTotalConnections(4);
        HttpClient client = new HttpClient(connectionManager);
        client.setHttpConnectionFactoryTimeout(2000);
        client.setConnectionTimeout(1000);
        client.setTimeout(1000);
        GetMethod getMethod = new GetMethod("http://localhost:8080/upper/testabc");
        int code1 = client.executeMethod(getMethod);
        System.out.println(code1);
        System.out.println(new String(getMethod.getResponseBody()));
        int code2 = client.executeMethod(new GetMethod("http://localhost:8080/lower/testabc"));
        System.out.println(code2);
        int code3 = client.executeMethod(new GetMethod("http://localhost:8080/upper/testabc"));
        System.out.println(code3);
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
