package sjt.http.server.servlet;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;

@Slf4j
@RequiredArgsConstructor
public class ControllerContainer {
    private static RequestMappingHandlerMapper requestMappingHandlerMapper;
    private final Controller controller;

    public void init() {
        try {
            requestMappingHandlerMapper = new RequestMappingHandlerMapper();
            requestMappingHandlerMapper.init(controller);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Response service(Request request) {
        System.out.println("TcHttpServlet service called.. ");

        try {
            Method method = requestMappingHandlerMapper.getMappingHandlerMapper(request);

            if (method != null) {
                Object result = method.invoke(new Controller(), request.getBody());

                return Response.builder()
                        .statusCode("200")
                        .reasonPhrase("Ok")
                        .body(result)
                        .build();
            } else {
                throw new RuntimeException("Request - getMappingHandlerMapper failed..");
            }
        } catch (Exception e) {
            return Response.builder()
                    .statusCode("503")
                    .reasonPhrase("Service Unavailable")
                    .build();
        }
    }

}
