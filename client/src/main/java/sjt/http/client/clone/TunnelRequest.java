package sjt.http.client.clone;

import static sjt.http.client.clone.Util.getDefaultPort;

public class TunnelRequest {

    final String host;
    final int port;
    final String userAgent;
    final String proxyAuthorization;

    public TunnelRequest(String host, int port, String userAgent, String proxyAuthorization) {
        this.host = host;
        this.port = port;
        this.userAgent = userAgent;
        this.proxyAuthorization = proxyAuthorization;
    }

    RawHeaders getRequestHeaders() {
        RawHeaders result = new RawHeaders();
        result.setRequestLine("CONNECT " + host + ":" + port + "HTTP/1.1");

        result.set("HOST", port == getDefaultPort("https") ? host : (host + ":" + port));
        result.set("User-Agent", userAgent);

        if (proxyAuthorization != null) {
            result.set("Proxy-Authorization", proxyAuthorization);
        }

        result.set("Proxy-Connection", "Keep-Alive");
        return result;
    }
}
