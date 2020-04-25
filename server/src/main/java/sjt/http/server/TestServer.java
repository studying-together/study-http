package sjt.http.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class TestServer {

    public static void main(String[] args) {
        SpringApplication.run(TestServer.class, args);
    }

    @PutMapping("/user")
    public void putUser(String body) {
    }

    @GetMapping("/user/{userId}")
    public String getUser(@PathVariable String userId) {
        return "userInfo";
    }
}
