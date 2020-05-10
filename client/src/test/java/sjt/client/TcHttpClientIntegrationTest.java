package sjt.client;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import sjt.http.HttpMethod;
import sjt.http.HttpStatus;
import sjt.http.Request;
import sjt.http.Response;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

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
                                                        .host("localhost")
                                                        .port(8080)
                                                        .path("/hello")
                                                        .build());
        assertAll(
            () -> assertThat(response).isNotNull(),
            () -> assertThat(response).isInstanceOf(Response.class),
            //1. 상태줄
            () -> assertThat(response.getStatusLine()).isNotNull(),
            //1_1. 상태
            () -> assertThat(response.getStatusLine().getStatus()).isEqualTo(HttpStatus.OK),
            //1_2. 프로토콜
            () -> assertThat(response.getStatusLine().getProtocolVersion()).isNotNull(),
            () -> assertThat(response.getStatusLine().getProtocolVersion().getProtocol()).isEqualTo("HTTP"),
            () -> assertThat(response.getStatusLine().getProtocolVersion().getVersion()).isEqualTo(1.1),
            //2. 헤더
            () -> assertThat(response.getHeaders()).isNotNull(),
            () -> assertThat(response.getHeaders().size()).isNotZero(),
            //3. 바디
            () -> assertThat(response.getBody()).isNotNull(),
            () -> assertThat(response.getBody()).isNotEmpty()
        );
    }
}