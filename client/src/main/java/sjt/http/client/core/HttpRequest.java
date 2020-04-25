package sjt.http.client.core;

import sjt.http.core.HttpProtocolUtil;
import sjt.http.core.log.Logger;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

import static sjt.http.core.HttpReaderUtil.CRLF;

public class HttpRequest {

    private static final String DEFAULT_CHARSET = "UTF-8";

    private HttpMethod method;
    private String requestUri;
    private Version version = Version.HTTP_1_1;
    private OutputStream outputStream;

    private Map<String, String> headers = new HashMap<String, String>();

    private String body;

    public HttpRequest(OutputStream outputStream,
                       HttpMethod method,
                       String requestUri,
                       String body) {
        this.outputStream = outputStream;
        this.method = method;
        this.requestUri = requestUri;
        this.body = body;
        headers.put("Host", getHostName());
    }

    private String getHostName() {
        try {
            return InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            throw new HttpException(e);
        }
    }

    public void sendRequest() {
        try {
            outputStream.write(requestLine().getBytes(DEFAULT_CHARSET));
            outputStream.write(HttpProtocolUtil.mapHeaderToText(headers).getBytes("UTF-8"));
            outputStream.flush();
            if (body != null) {
                outputStream.write(body.getBytes(DEFAULT_CHARSET));
            }
        } catch (IOException e) {
            throw new HttpException("send request failed");
        }
        Logger.log(this, "send request completed");
    }

    public String requestLine() {
        return method.name() + " " + requestUri + " " + version.name + CRLF;
    }

    public void setVersion(Version version) {
        this.version = version;
    }

    enum Version {
        HTTP_1_1("HTTP/1.1");

        Version(String name) {
            this.name = name;
        }

        String name;
    }
}
