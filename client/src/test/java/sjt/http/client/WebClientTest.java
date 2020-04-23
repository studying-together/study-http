package sjt.http.client;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class WebClientTest {

    private WebClient webClient;

    @BeforeAll
    void setUp() {
        // 클래스 내 테스트에서 한번만 실행
        webClient = new SimpleWebClient();
    }

    @DisplayName("API 조회 테스트")
    @Test
    void getTest() {
        String result = webClient.get("localhost", 8080, "/index", String.class);
        // AssertJ - write fluent assertions
        assertThat(result).isEqualTo(null);
    }
}
