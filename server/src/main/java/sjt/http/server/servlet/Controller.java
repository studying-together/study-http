package sjt.http.server.servlet;


public class Controller {

    @RequestMapping(value = "/user", method = HttpMethod.POST)
    public static Object addUser(String user) {
        // TODO : user 저장
        String result = "{\n"
                + "   \"id\":\"1004\",\n"
                + "   \"name\":\"java\",\n"
                + "   \"age\":22\n"
                + "}";

        return result;
    }

    @RequestMapping(value = "/user/1", method = HttpMethod.GET)
    public String getUser(String id) {
        return null;
    }

}
