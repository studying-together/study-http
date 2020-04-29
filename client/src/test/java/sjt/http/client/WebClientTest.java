package sjt.http.client;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import sjt.client.TcWebClient;
import sjt.client.WebClient;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class WebClientTest {

    private static WebClient webClient;

    @BeforeAll
    static void setUp() {
        webClient = new TcWebClient();
    }

    @DisplayName("API 조회 테스트")
    @Test
    void getTest() {

        Map<String, String> result = webClient.getMap("http://localhost:8080/user/1004", String.class);
        assertThat(result).isInstanceOf(Map.class);
    }

    @DisplayName("API 등록 테스트")
    @Test
    void postTest() {

        Map<String, String> params = new HashMap<>();
        params.put("id", "1");
        params.put("name", "sjt");
        params.put("age", "2");

        Map<String, String> result = webClient.postMap("http://localhost:8080/user", String.class, params);
        assertEquals(result.get("id"), "1004");
    }
}
