package sjt.client;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import sjt.http.HttpMethod;
import sjt.http.HttpStatus;
import sjt.http.Request;
import sjt.http.Response;

import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

    @Test
    public void POST_Json_데이터_요청_테스트() {
        Response response = tcHttpClient.execute(Request.builder()
                .method(HttpMethod.POST)
                .host("localhost")
                .port(8080)
                .path("/user")
                .body("{\"id\":\"1\", \"name\":\"heedi\", \"age\":1004}")
                .build());

        System.out.println(response.toString());
    }

    @Test
    public void 응답_본문값_읽지않는_경우_테스트() {
        // HEAD 요청 - 응답 본문값 읽지 않음
        Response response1 = tcHttpClient.execute(Request.builder()
                .method(HttpMethod.HEAD)
                .host("localhost")
                .port(8080)
                .path("/hello")
                .build());

        assertThat(Objects.isNull(response1.getBody()));

        // PUT 요청 - 응답코드 : 204(NO_CONTENT), 304
        Response response2 = tcHttpClient.execute(Request.builder()
                .method(HttpMethod.PUT)
                .host("localhost")
                .port(8080)
                .path("/user")
                .build());

        assertThat(Objects.isNull(response2.getBody()));
        System.out.println(response2.toString());
    }

    @Test
    public void 응답_본문값_있는_경우_테스트() {
        Response response = tcHttpClient.execute(Request.builder()
                .method(HttpMethod.GET)
                .host("localhost")
                .port(8080)
                .path("/hello")
                .build());

        System.out.println(response.toString());
    }

    @Test
    public void 응답_본문값_있는_경우_테스트2() {
        Response response = tcHttpClient.execute(Request.builder()
                .method(HttpMethod.GET)
                .host("localhost")
                .port(8080)
                .path("/message?message=onetwothreefour")
                .build());

        System.out.println(response.toString());
    }
}
