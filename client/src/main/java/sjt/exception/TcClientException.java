package sjt.exception;

public class TcClientException extends RuntimeException {
    private static final long serialVersionUID = -3971402795119809092L;

    public TcClientException() {
        super();
    }

    public TcClientException(final Throwable cause) {
        super(cause);
    }

    public TcClientException(final String message) {
        super(message);
    }
}
