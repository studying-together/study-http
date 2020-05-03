package sjt.http.client.module.converter;

public interface MessageConverter<T> {

    boolean canParse(String contentType);

    T parseMessage(Class<? extends T> clazz, byte[] message);
}
