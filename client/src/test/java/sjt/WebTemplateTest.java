package sjt;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.sun.tools.javac.util.List;

import sjt.client.TcHttpClient;
import sjt.parser.JsonParser;

public class WebTemplateTest {
    private WebTemplate webTemplate;

    @BeforeAll
    void setUp() {
        this.webTemplate = new RestTemplate(new TcHttpClient(), List.of(new JsonParser()));
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
