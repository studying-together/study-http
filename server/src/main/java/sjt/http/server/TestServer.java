//package sjt.http.server;
//
//import java.io.IOException;
//
//@SpringBootApplication
//@RestController
//public class TestServer {
//
//    public static void main(String[] args) throws IOException {
//        SpringApplication.run(SjtWebServer.class, args);
//    }
//
//    @GetMapping("/{message}")
//    public String test(@PathVariable String message) {
//        return message.toUpperCase();
//    }
//
//}