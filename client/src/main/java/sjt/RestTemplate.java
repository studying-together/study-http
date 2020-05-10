package sjt;

import java.util.List;

import lombok.extern.slf4j.Slf4j;
import sjt.client.TcHttpClient;
import sjt.exception.TcClientException;
import sjt.http.HttpMethod;
import sjt.http.Request;
import sjt.http.Response;
import sjt.parser.HttpResponseParser;

@Slf4j
public class RestTemplate implements WebTemplate {
    private final TcHttpClient tcHttpClient;
    private final List<HttpResponseParser> parsers;

    public RestTemplate(TcHttpClient tcHttpClient, List<HttpResponseParser> parsers) {
        this.tcHttpClient = tcHttpClient;
        this.parsers = parsers;
    }

    @Override
    public <T> T get(final String host, final int port, final String path, final Class<T> clazz) throws TcClientException {
        final Request request = Request.builder()
                                       .method(HttpMethod.GET)
                                       .host(host)
                                       .port(port)
                                       .path(path)
                                       .build();
        return execute(request, clazz);
    }

    @Override
    public <T> T post(final String host, final int port, final String path, final String body, final Class<T> clazz) throws
                                                                                                                     TcClientException {
        final Request request = Request.builder()
                                       .method(HttpMethod.POST)
                                       .host(host)
                                       .port(port)
                                       .path(path)
                                       .body(body)
                                       .build();
        return execute(request, clazz);
    }

    @Override
    public <T> void put(String host, int port, String path, String body, Class<T> clazz) throws TcClientException {
        final Request request = Request.builder()
                                       .method(HttpMethod.POST)
                                       .host(host)
                                       .port(port)
                                       .path(path)
                                       .body(body)
                                       .build();
        execute(request, clazz);
    }

    @Override
    public <T> void delete(String host, int port, String path, Class<T> clazz) throws TcClientException {
        final Request request = Request.builder()
                                       .method(HttpMethod.POST)
                                       .host(host)
                                       .port(port)
                                       .path(path)
                                       .build();
        execute(request, clazz);
    }

    private <T> T execute(final Request request, final Class<T> clazz) throws TcClientException {
        final Response response = tcHttpClient.execute(request);
        return parsers.stream()
                      .filter(parser -> parser.canParse(response.getHeaders().get("Content-Type")))
                      .findAny()
                      .map(parser -> parser.doParse(response, clazz))
                      .orElseThrow(() -> new TcClientException(""));
    }
}
