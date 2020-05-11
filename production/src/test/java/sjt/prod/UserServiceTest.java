package sjt.prod;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import sjt.RestTemplate;
import sjt.prod.model.User;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class UserServiceTest {

    static String HOST = "127.0.0.1";
    static int PORT = 8080;

    static String SAMPLE_ID = "1004";
    static String SAMPLE_NAME = "java";
    static int SAMPLE_AGE = 22;
    static int SAMPLE_UPDATED_AGE = 2;

    ObjectMapper mapper = new ObjectMapper();

    @DisplayName("사용자 추가/수정/삭제/조회 테스트")
    @Test
    void userStory() throws JsonProcessingException {
        /*
         * 과제
         *
         * 목을 제거하고 구현체를 사용하자
         */
        RestTemplate webClient = new RestTemplate();

        UserService userService = new UserService(webClient);

        String sampleUser = mapper.writeValueAsString(new User(SAMPLE_ID, SAMPLE_NAME, SAMPLE_AGE));

        // 샘플 사용자 등록
        userService.postUser(HOST, PORT, "/user", sampleUser);

        // 샘플 사용자 조회 및 검증
        User insertedUser = userService.getUser(HOST, PORT, "/user/100");
        assertEquals(SAMPLE_ID, insertedUser.getId());
        assertEquals(SAMPLE_NAME, insertedUser.getName());
        assertEquals(SAMPLE_AGE, insertedUser.getAge());

        // 샘플 사용자 수정
        String updatedSampleUser = mapper.writeValueAsString(new User(SAMPLE_ID, SAMPLE_NAME, SAMPLE_UPDATED_AGE));

        // 셈플 사용자 조회 및 검증
        userService.putUser(HOST, PORT, "/user", updatedSampleUser);
        User updatedUser = userService.getUser(HOST, PORT, "/user/1004");
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
