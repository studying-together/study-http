package sjt.http.client.clone;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLPeerUnverifiedException;
import java.io.IOException;
import java.net.URL;
import java.security.cert.Certificate;
import java.util.Set;

public class HttpsURLConnectionImpl extends HttpsURLConnection {
    public HttpsURLConnectionImpl(URL url, OkHttpClient client, OkResponseCache responseCache, Set<Route> failedRoutes) {
        super(url);
    }

    @Override
    public String getCipherSuite() {
        return null;
    }

    @Override
    public Certificate[] getLocalCertificates() {
        return new Certificate[0];
    }

    @Override
    public Certificate[] getServerCertificates() throws SSLPeerUnverifiedException {
        return new Certificate[0];
    }

    @Override
    public void disconnect() {

    }

    @Override
    public boolean usingProxy() {
        return false;
    }

    @Override
    public void connect() throws IOException {

    }
}
