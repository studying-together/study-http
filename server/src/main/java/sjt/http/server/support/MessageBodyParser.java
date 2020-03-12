package sjt.http.server.support;

/**
 * Created by kohyusik on 2020/03/11.
 */
public interface MessageBodyParser<T> {

    boolean supports(String type);

    T parse(String messageBody);
}
