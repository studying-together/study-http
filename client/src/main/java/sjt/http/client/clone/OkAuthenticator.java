package sjt.http.client.clone;

import com.squareup.okhttp.internal.Base64;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.Proxy;
import java.net.URL;
import java.util.List;
import java.util.Objects;

public interface OkAuthenticator {

    Credential authenticate(Proxy proxy, URL url, List<Challenge> challenges) throws IOException;

    Credential authenticateProxy(Proxy proxy, URL url, List<Challenge> challenges) throws IOException;

    public final class Challenge {
        private final String scheme;
        private final String realm;

        public Challenge(String scheme, String realm) {
            this.scheme = scheme;
            this.realm = realm;
        }

        public String getScheme() {
            return scheme;
        }

        public String getRealm() {
            return realm;
        }

        @Override
        public boolean equals(Object o) {
            return o instanceof Challenge
                    && ((Challenge) o).scheme.equals(scheme)
                    && ((Challenge) o).realm.equals(realm);
        }

        @Override
        public int hashCode() {
            return scheme.hashCode() + 31 * realm.hashCode();
        }

        @Override
        public String toString() {
            return "Challenge{" + "scheme='" + scheme + '\'' + ", realm='" + realm + '\'' + '}';
        }
    }

    public final class Credential {
        private final String headerValue;

        private Credential(String headerValue) {
            this.headerValue = headerValue;
        }

        public static Credential basic(String userName, String password) {
            try {
                String usernameAndPassword = userName + ":" + password;
                byte[] bytes = usernameAndPassword.getBytes("ISO-8859-1");
                String encoded = Base64.encode(bytes);
                return new Credential("Basic " + encoded);
            } catch (UnsupportedEncodingException e) {
                throw new AssertionError();
            }
        }


    }

}
