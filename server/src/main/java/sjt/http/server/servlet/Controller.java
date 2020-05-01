package sjt.http.server.servlet;

import java.io.BufferedWriter;
import java.io.IOException;

public class Controller {

    @RequestMapping(value = "/user", method = HttpMethod.POST)
    public static String addUser(String user) {
        // TODO : user 저장
        return "200 OK";
    }

    @RequestMapping(value = "/user/1", method = HttpMethod.GET)
    public String getUser(String id) {
        return null;
    }

}
