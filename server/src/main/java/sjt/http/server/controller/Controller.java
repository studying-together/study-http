package sjt.http.server.controller;

import sjt.http.server.servlet.Request;

/**
 * Created by yusik on 2020/04/29.
 */
@FunctionalInterface
public interface Controller<T> {
    T handle(Request request);
}
