package sjt.http.client;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import sjt.client.TcWebClient;
import sjt.client.WebClient;

public class WebClientTest {
    private static WebClient webClient;

    @BeforeAll
    static void setUp() {
        webClient = new TcWebClient();
    }

    @DisplayName("GET 테스트")
    @Test
    void getTest() {
    }

    @DisplayName("POST 테스트")
    @Test
    void postTest() {
    }
}
