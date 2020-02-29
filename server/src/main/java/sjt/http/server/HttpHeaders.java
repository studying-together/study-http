package sjt.http.server;

public enum HttpHeaders {
    REQUEST_LINE("requestLine") {
        void apply(String text, HttpHeader httpHeader) {
            httpHeader.setRequestLine(text);
        }
    },
    CONNECTION("connection") {
        void apply(String connection, HttpHeader httpHeader) {
            httpHeader.setConnection(connection);
        }
    },
    HOST("host") {
        void apply(String host, HttpHeader httpHeader) {
            httpHeader.setHost(host);
        }
    },
    ACCEPT("accept") {
        void apply(String accept, HttpHeader httpHeader) {
            httpHeader.setAccept(accept);
        }
    }
    ;

    private String text;
    abstract void apply(String text, HttpHeader httpHeader);

    HttpHeaders(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

}
