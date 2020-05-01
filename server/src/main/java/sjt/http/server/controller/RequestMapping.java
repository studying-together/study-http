package sjt.http.server.controller;

import sjt.http.server.servlet.HttpMethod;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequestMapping {

    String value();

    HttpMethod method() default HttpMethod.GET;
}
