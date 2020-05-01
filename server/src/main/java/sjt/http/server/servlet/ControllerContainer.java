package sjt.http.server.servlet;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;

@Slf4j
public class ControllerContainer {
    private static RequestMappingHandlerMapper requestMappingHandlerMapper;

    public void init() {
        // 서블릿 초기화
        try {
            requestMappingHandlerMapper = new RequestMappingHandlerMapper();
            requestMappingHandlerMapper.init(new Controller());

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public <T> T service(Request request) {
        System.out.println("TcHttpServlet service called.. ");

        try {
            Method method = requestMappingHandlerMapper.getMappingHandlerMapper(request);

            if (method != null) {
                return (T) method.invoke(new Controller(), request.getBody());
            } else {
                throw new RuntimeException("Request - getMappingHandlerMapper failed..");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
