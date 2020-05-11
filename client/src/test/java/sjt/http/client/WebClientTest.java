package sjt.http.client;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class WebClientTest {

    private static WebClient webClient;

    @BeforeAll
    static void setUp() {
        // 클래스 내 테스트에서 한번만 실행
        webClient = new TgWebClient();
    }

    @DisplayName("API 조회 테스트")
    @Test
    void getTest() {
        String result = webClient.get("localhost", 8888, "/echo/test", String.class);
        // AssertJ - write fluent assertions
    }

    @DisplayName("Content-Length")
    @Test
    void contentLength() {
    }

    @DisplayName("Content-Type")
    @Test
    void contentType() {
    }

    @DisplayName("Cache-Control")
    @Test
    void cacheControl() {
    }
}
