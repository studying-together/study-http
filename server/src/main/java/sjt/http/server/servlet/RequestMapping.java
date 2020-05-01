package sjt.http.server.servlet;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequestMapping {

    String value();

    HttpMethod method() default HttpMethod.GET;
}
