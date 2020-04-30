package sjt.http.server.controller;

import sjt.http.server.db.InMemoryDb;
import sjt.http.server.db.User;
import sjt.http.server.servlet.Request;

import java.util.HashMap;

import static sjt.http.server.servlet.HttpMethod.*;

/**
 * Created by yusik on 2020/04/29.
 */
public class MappingControllerRegistry {

    private static final HashMap<ControllerKey, Controller<?>> REGISTRY = new HashMap<>();

    static {
        // set controller
        REGISTRY.put(ControllerKey.of(POST, "/user"), request -> InMemoryDb.saveUser(request.getParsedBody(User.class)));
        REGISTRY.put(ControllerKey.of(GET, "/user/1"), request -> InMemoryDb.findUserById("1"));
        REGISTRY.put(ControllerKey.of(PUT, "/user"), request -> InMemoryDb.updateUser(request.getParsedBody(User.class)));
        REGISTRY.put(ControllerKey.of(DELETE, "/user/1"), request -> InMemoryDb.deleteUserById("1"));
    }

    public static Controller<?> getMappedController(ControllerKey key) {
        Controller<?> mappedController = REGISTRY.get(key);
        if (mappedController == null) {
            throw new IllegalArgumentException("매핑 된 Controller 를 찾을 수 없습니다. key={" + key + "}");
        }
        return mappedController;
    }

    public static Controller<?> getMappedController(Request request) {
        return getMappedController(ControllerKey.of(request.getHttpMethod(), request.getPath()));
    }
}
