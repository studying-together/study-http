package sjt.http.client.clone;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSocketFactory;
import java.net.Proxy;
import java.util.List;

public class Address {
    final Proxy proxy;
    final String uriHost;
    final int uriPort;
    final SSLSocketFactory sslSocketFactory;
    final HostnameVerifier hostnameVerifier;
    final OkAuthenticator authenticator;
    final List<String> transports;


    public Address(String uriHost,
                   int uriPort,
                   SSLSocketFactory sslSocketFactory,
                   HostnameVerifier hostnameVerifier,
                   OkAuthenticator authenticator,
                   Proxy proxy,
                   List<String> transports) {
        this.proxy = proxy;
        this.uriHost = uriHost;
        this.uriPort = uriPort;
        this.sslSocketFactory = sslSocketFactory;
        this.hostnameVerifier = hostnameVerifier;
        this.authenticator = authenticator;
        this.transports = Util.immutableList(transports);
    }
}
