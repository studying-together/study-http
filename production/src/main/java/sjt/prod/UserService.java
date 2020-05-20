package sjt.prod;

import sjt.RestTemplate;
import sjt.prod.model.User;

public class UserService {

    private RestTemplate webClient;

    public UserService(RestTemplate webClient) {
        this.webClient = webClient;
    }

    public User getUser(String host, int port, String path) {
        return webClient.get(host, port, path, User.class);
    }

    public <T> T postUser(String host, int port, String path, String body) {
        return (T) webClient.post(host, port, path, body, User.class);
    }

    public <T> void putUser(String host, int port, String path, String body, Class<T> clazz) {
        webClient.put(host, port, path, body, clazz);
    }

    public void deleteUser(String host, int port, String path) {
        webClient.delete(host, port, path, null);
    }

}
