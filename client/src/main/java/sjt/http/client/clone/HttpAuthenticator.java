package sjt.http.client.clone;

import java.io.IOException;
import java.net.*;
import java.util.List;

public class HttpAuthenticator {

    public static final OkAuthenticator SYSTEM_DEFAULT = new OkAuthenticator() {
        @Override
        public Credential authenticate(Proxy proxy, URL url, List<Challenge> challenges) throws IOException {
            for (Challenge challenge : challenges) {
                PasswordAuthentication auth = Authenticator.requestPasswordAuthentication(url.getHost(),
                        getConnectToInetAddres(proxy, url), url.getPort(), url.getProtocol(),
                        challenge.getRealm(), challenge.getScheme(), url, Authenticator.RequestorType.SERVER);
                if (auth != null) {
                    Credential basic = Credential.basic(auth.getUserName(), new String(auth.getPassword()));
                    return basic;
                }
            }
            return null;
        }
        @Override
        public Credential authenticateProxy(Proxy proxy, URL url, List<Challenge> challenges) throws IOException {
            return null;
        }

        private InetAddress getConnectToInetAddres(Proxy proxy, URL url) throws UnknownHostException {
            return (proxy != null && proxy.type() != Proxy.Type.DIRECT)
                    ? ((InetSocketAddress) proxy.address()).getAddress()
                    : InetAddress.getByName(url.getHost());
        }
    };
}
