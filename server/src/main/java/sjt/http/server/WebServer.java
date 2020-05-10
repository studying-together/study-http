package sjt.http.server;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

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
}