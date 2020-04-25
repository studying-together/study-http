package sjt.http.client;

import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import sjt.http.server.servlet.Response;

public class TcWebClient implements WebClient {

    private TcHttpClient tcHttpClient = new TcHttpClient();

    public <T> T get(String host, int port, String path, Class<T> clazz) {
        tcHttpClient.startSession(host, port);
        GetMethod getMethod = new GetMethod(path);

        Response response = tcHttpClient.execute(getMethod);

        return (T) response;
    }

    public <T> T post(String host, int port, String path, String body, Class<T> clazz) {
        tcHttpClient.startSession(host, port);
        PostMethod postMethod = new PostMethod(path);
        postMethod.addParameter("body", body);

        Response response = tcHttpClient.execute(postMethod);

        return (T) response;
    }

    public void put(String host, int port, String path, String body) {

    }

    public void delete(String host, int port, String path) {

    }
}
