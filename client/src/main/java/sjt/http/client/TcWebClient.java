package sjt.http.client;

import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import sjt.http.server.servlet.Response;

public class TcWebClient implements WebClient {

    private TcHttpEngine tcHttpEngine = new TcHttpEngine();

    public <T> T get(String host, int port, String path, Class<T> clazz) {
        tcHttpEngine.startSession(host, port);
        GetMethod getMethod = new GetMethod(path);

        Response response = tcHttpEngine.execute(getMethod);

        return (T) response;
    }

    public <T> T post(String host, int port, String path, String body, Class<T> clazz) {
        tcHttpEngine.startSession(host, port);
        PostMethod postMethod = new PostMethod(path);
        postMethod.addParameter("body", body);

        Response response = tcHttpEngine.execute(postMethod);

        return (T) response;
    }

    public void put(String host, int port, String path, String body) {

    }

    public void delete(String host, int port, String path) {

    }
}
