package sjt.http.client.module.converter;

public interface MessageConverter {

    boolean canParse(String contentType);

    <T> T parseMessage(Class<? extends T> clazz, byte[] message);
}
