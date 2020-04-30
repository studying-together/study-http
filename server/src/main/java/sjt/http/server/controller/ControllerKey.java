package sjt.http.server.controller;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import sjt.http.server.servlet.HttpMethod;

/**
 * Created by yusik on 2020/04/29.
 */
@AllArgsConstructor(staticName = "of")
@EqualsAndHashCode
public class ControllerKey {
    private final HttpMethod method;
    private final String path;

    @Override
    public String toString() {
        return "[" + method.name() + "," + path + "]";
    }
}
