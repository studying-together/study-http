package sjt;

import sjt.exception.TcClientException;

/**
 * WAS(web application server)에서 사용하기 위한 Template 입니다.
 * MSA 환경에서 사용하기 편하게 사용할 수 있습니다.
 * 사용자 입장에서 필요한 body 데이터만 object로 변환해서 전달하는 용도로 설계했습니다.
 */
public interface WebTemplate {
    <T> T get(String host, int port, String path, Class<T> clazz) throws TcClientException;

    <T> T post(String host, int port, String path, String body, Class<T> clazz) throws TcClientException;

    <T> void put(String host, int port, String path, String body, Class<T> clazz) throws TcClientException;

    <T> void delete(String host, int port, String path, Class<T> clazz) throws TcClientException;
}
