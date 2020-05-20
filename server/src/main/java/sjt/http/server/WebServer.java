package sjt.http.server;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Slf4j
@SpringBootApplication
@Controller
public class WebServer {
    public static void main(String[] args) {
        new SpringApplicationBuilder(WebServer.class)
                .build()
                .run(args);
    }

    @GetMapping(value = "/hello", produces = "text/html")
    public String getHello() {
        return "hello";
    }

    @GetMapping(value = "/{message}", produces = "text/plain")
    public @ResponseBody String getMessage(@PathVariable String message) {
        log.info("get Message : {}" , message);
        return message;
    }

    @ResponseBody
    @PostMapping(value = "/user")
    public boolean sendMessage(@RequestBody User user) {
        log.info("send Message User : {}", user.toString());
        return user.getAge() == 1004;
    }

    @PutMapping(value = "/user")
    public ResponseEntity putMessage() {
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

}