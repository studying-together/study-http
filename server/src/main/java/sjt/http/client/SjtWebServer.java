package sjt.http.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@SpringBootApplication
@RestController
public class SjtWebServer {

    public static void main(String[] args) throws IOException {
        SpringApplication.run(SjtWebServer.class, args);
    }

    @GetMapping("/{message}")
    public String test(@PathVariable String message) {
        return message.toUpperCase();
    }

}
