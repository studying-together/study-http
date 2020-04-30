package sjt.prod;

import sjt.client.TcWebClient;
import sjt.prod.model.User;

public class UserService {

    private TcWebClient webClient;

    public UserService(TcWebClient webClient) {
        this.webClient = webClient;
    }

    public User getUser(String host, int port, String path) {
        return webClient.get(host, port, path, User.class);
    }

    public void postUser(String host, int port, String path, String body) {
        webClient.post(host, port, path, body, User.class);
    }

    public void putUser(String host, int port, String path, String body) {
        webClient.put(host, port, path, body);
    }

    public void deleteUser(String host, int port, String path) {
        webClient.delete(host, port, path);
    }

}
