package sjt.parser;

import sjt.http.Response;

public interface HttpResponseParser {
    /**
     * parsing 가능 여부 반환
     * @param contentType
     * @return
     */
    boolean canParse(String contentType);

    /**
     * parse 이후 <T> 반환
     *
     * @param clazz
     * @param <T>
     * @return
     */
    <T> T doParse(Response response, final Class<T> clazz);
}
