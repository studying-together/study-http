package sjt.http.server;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

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
}