package sjt.exception;

public class TcClientException extends RuntimeException {
    private static final long serialVersionUID = 7266768418671785462L;

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
