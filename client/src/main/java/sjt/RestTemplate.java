package sjt;

import lombok.extern.slf4j.Slf4j;
import sjt.client.TcHttpClient;
import sjt.exception.TcClientException;
import sjt.http.HttpMethod;
import sjt.http.Request;
import sjt.parser.ResponseToObjectParser;

@Slf4j
public class RestTemplate implements WebTemplate {
    private final TcHttpClient tcHttpClient;
    private final ResponseToObjectParser parser;

    public RestTemplate(TcHttpClient tcHttpClient, ResponseToObjectParser parser) {
        this.tcHttpClient = tcHttpClient;
        this.parser = parser;
    }

    @Override
    public <T> T get(final String host, final int port, final String path, final Class<T> clazz) throws TcClientException {
        final Request request = Request.builder()
                                       .method(HttpMethod.GET)
                                       .host(host)
                                       .port(port)
                                       .path(path)
                                       .build();
        return parser.doParse(tcHttpClient.execute(request), clazz);
    }

    @Override
    public <T> T post(final String host, final int port, final String path, final String body, final Class<T> clazz) throws TcClientException {
        final Request request = Request.builder()
                                       .method(HttpMethod.POST)
                                       .host(host)
                                       .port(port)
                                       .path(path)
                                       .body(body)
                                       .build();
        return parser.doParse(tcHttpClient.execute(request), clazz);
    }

    @Override
    public <T> void put(String host, int port, String path, String body, Class<T> clazz) throws TcClientException {
    }

    @Override
    public <T> void delete(String host, int port, String path, Class<T> clazz) throws TcClientException {
    }
}
