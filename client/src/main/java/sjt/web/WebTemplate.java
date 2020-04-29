package sjt.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import sjt.http.Connection;
import sjt.http.HttpMethod;

import java.io.IOException;
import java.net.URI;

public class WebTemplate {

    private ObjectMapper objectMapper;

    public WebTemplate(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    protected <T> T doExecute(URI url, HttpMethod httpMethod) {

        Connection connection = null;

        try {
            connection = new Connection();

            // 커넥션 가져와서 objectmapper로 타입 변경해주는 것 구현

        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            try {
                assert connection != null;
                connection.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        return null;
    }
}
