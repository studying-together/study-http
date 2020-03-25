package sjt.http.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class TestServer {

    public static void main(String[] args) {
        SpringApplication.run(TestServer.class, args);
    }

    @GetMapping("/{message}")
    public String test(@PathVariable String message) {
        return message.toUpperCase();
    }

}