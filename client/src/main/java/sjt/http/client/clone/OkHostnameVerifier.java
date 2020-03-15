package sjt.http.client.clone;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

public class OkHostnameVerifier implements HostnameVerifier {
    @Override
    public boolean verify(String s, SSLSession sslSession) {
        return false;
    }
}
