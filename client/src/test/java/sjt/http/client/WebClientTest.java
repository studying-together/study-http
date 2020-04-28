package sjt.http.client;

import java.util.Map;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class WebClientTest {

    private static WebClient webClient;

    @BeforeAll
    static void setUp() {
        // 클래스 내 테스트에서 한번만 실행
        webClient = new TcWebClient();
    }

    @DisplayName("API 조회 테스트")
    @Test
    void getTest() {
        Map<String, String> result = webClient.get("localhost", 8080, "/user/1004", Map.class);
        // AssertJ - write fluent assertions
        assertThat(result).isInstanceOf(Map.class);
    }

    @DisplayName("API 등록 테스트")
    @Test
    void postTest() {
        String result = webClient.post("localhost", 8080, "/user", "{'id':'1','name':'sjt','age':2}", String.class);

        assertThat(result).isEqualTo(null);
    }
}
