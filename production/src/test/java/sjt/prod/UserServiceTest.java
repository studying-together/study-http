package sjt.prod;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import sjt.http.client.TgWebClient;
import sjt.http.client.WebClient;
import sjt.prod.model.User;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

public class UserServiceTest {

    static String HOST = "127.0.0.1";
    static int PORT = 8080;

    static String SAMPLE_ID = "1";
    static String SAMPLE_NAME = "sjt";
    static int SAMPLE_AGE = 2;
    static int SAMPLE_UPDATED_AGE = 3;

    ObjectMapper mapper = new ObjectMapper();

    @DisplayName("사용자 통합 테스트")
    @Test
    void userIntegrationTest() throws JsonProcessingException {
        WebClient webClient = new TgWebClient();

        UserService userService = new UserService(webClient);

        String sampleUser = mapper.writeValueAsString(new User(SAMPLE_ID, SAMPLE_NAME, SAMPLE_AGE));

        // 샘플 사용자 등록
        userService.postUser(HOST, PORT, "/user", sampleUser);

        // 샘플 사용자 조회 및 검증
        User insertedUser = userService.getUser(HOST, PORT, "/user/1");
        assertEquals(SAMPLE_ID, insertedUser.getId());
        assertEquals(SAMPLE_NAME, insertedUser.getName());
        assertEquals(SAMPLE_AGE, insertedUser.getAge());

        // 샘플 사용자 수정
        String updatedSampleUser = mapper.writeValueAsString(new User(SAMPLE_ID, SAMPLE_NAME, SAMPLE_UPDATED_AGE));

        // 셈플 사용자 조회 및 검증
        userService.putUser(HOST, PORT, "/user", updatedSampleUser);
        User updatedUser = userService.getUser(HOST, PORT, "/user/1");
        assertEquals(SAMPLE_ID, updatedUser.getId());
        assertEquals(SAMPLE_NAME, updatedUser.getName());
        assertEquals(SAMPLE_UPDATED_AGE, updatedUser.getAge());

        // 샘플 사용자 삭제
        userService.deleteUser(HOST, PORT, "/user/1");

        // 샘플 사용자 조회 및 검증
        User deletedUser = userService.getUser(HOST, PORT, "/user/1");
        assertNull(deletedUser);
    }
}
