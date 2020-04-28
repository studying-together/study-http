package sjt.http.client;

import com.fasterxml.jackson.databind.ObjectMapper;

public class TcWebClient implements WebClient {

    private TcHttpClient tcHttpClient = new TcHttpClient(new ObjectMapper());

    public <T> T get(final String host, final int port, final String path, final Class<T> clazz) {
        final Request request = Request.builder()
                                 .method(HttpMethod.GET)
                                 .host(host)
                                 .port(port)
                                 .path(path)
                                 .build();
        return tcHttpClient.execute(request, clazz);
    }

    public <T> T post(final String host, final int port, final String path, final String body, final Class<T> clazz) {
        final Request request = Request.builder()
                                 .method(HttpMethod.POST)
                                 .host(host)
                                 .port(port)
                                 .path(path)
                                 .build();
        return tcHttpClient.execute(request, clazz);
    }

    public void put(final String host, final int port, final String path, final String body) {

    }

    public void delete(final String host, final int port, final String path) {

    }
}
