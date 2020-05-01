package sjt.http.server.servlet;

import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Request(httpMethod+uri)로 매핑되는 메소드 관리소
 */
@Getter
@Setter
public class RequestMappingHandlerMapper {
    private Map<RequestMappingInfo, Method> mapper;

    public void init(Controller controller) {
        mapper = new HashMap<>();

        Class<?> clazz = controller.getClass();

        for (Method method : clazz.getDeclaredMethods()) {
            if (method.isAnnotationPresent(RequestMapping.class)) {
                System.out.println("catch method.. name : "+ method.getName());
                registerMethod(method.getAnnotation(RequestMapping.class), method);
            }
        }
    }

    private void registerMethod(RequestMapping annotation, Method method) {
        RequestMappingInfo requestMappingInfo = RequestMappingInfo.builder()
                .method(annotation.method())
                .uri(annotation.value())
                .build();

        mapper.put(requestMappingInfo, method);
    }

    public Method getMappingHandlerMapper(Request request) {
        RequestMappingInfo requestMappingInfo = RequestMappingInfo.builder()
                .method(request.getHttpMethod())
                .uri(request.getUri())
                .build();

        return mapper.get(requestMappingInfo);
    }
}
