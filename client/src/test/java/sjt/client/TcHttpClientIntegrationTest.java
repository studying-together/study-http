package sjt.client;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import sjt.http.HttpMethod;
import sjt.http.Request;
import sjt.http.Response;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * integration test
 * 단위 데스트는 다음에
 */
public class TcHttpClientIntegrationTest {
    private TcHttpClient tcHttpClient;

    @BeforeEach
    void setUp() {
        this.tcHttpClient = new TcHttpClient();
    }

    @Test
    public void execute_return_response_when_use_get_method() {
        Response response = tcHttpClient.execute(Request.builder()
                                                        .method(HttpMethod.GET)
                                                        .host("kostat.go.kr")
                                                        .port(80)
                                                        .path("/")
                                                        .build());
        assertThat(response).isInstanceOf(Response.class);
    }

}