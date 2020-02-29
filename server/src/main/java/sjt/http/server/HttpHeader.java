package sjt.http.server;

import java.util.Arrays;
import java.util.List;

public class HttpHeader {

    private String requestLine;
    private String connection;
    private String host;
    private String accept;

    public String getRequestLine() {
        return requestLine;
    }

    public String getConnection() {
        return connection;
    }

    public String getHost() {
        return host;
    }

    public String getAccept() {
        return accept;
    }

    public void setRequestLine(String requestLine) {
        this.requestLine = requestLine;
    }

    public void setConnection(String connection) {
        this.connection = connection;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public void setAccept(String accept) {
        this.accept = accept;
    }

    public static HttpHeader getHeader(StringBuilder request) {
        List<String> headers = Arrays.asList(request.toString().split(","));
        HttpHeader httpHeader = new HttpHeader();

        System.out.println("parsing Request Message : " + headers);
        headers.forEach(text -> {
            for (HttpHeaders httpHeaders : HttpHeaders.values()) {
                if (text.contains(httpHeaders.name())) {
                    httpHeaders.apply(text.replaceAll(httpHeaders.name() + " : ", ""), httpHeader);
                }
            }
        });

        return httpHeader;

    }
}
