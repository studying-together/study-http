package sjt.http.client.clone;

import javax.net.ssl.SSLSocket;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Platform {
    private static final Platform PLATFORM = findPlatform();

    private static Platform findPlatform() {
        Method getMtu;

        try {
            getMtu = NetworkInterface.class.getMethod("getMTU");
        } catch (NoSuchMethodException e) {
            return new Platform();
        }

        Class<?> openSslSocketClass;
        Method setUseSeetionTickets;
        Method setHostname;

        try {
            openSslSocketClass = Class.forName("org.apache.harmony.xnet.provider.jsse.OpenSSLSocketImpl");
            setUseSeetionTickets = openSslSocketClass.getMethod("setUseSessionTickets", boolean.class);
            setHostname = openSslSocketClass.getMethod("setHostname", String.class);

            try {
                Method setNpnProtocols = openSslSocketClass.getMethod("SetNpnProtocols", byte[].class);
                Method getNpnSelectedProtocol = openSslSocketClass.getMethod("getNpnSelectedProtocol");
                return new Android41(getMtu, openSslSocketClass, setUseSeetionTickets, setHostname, setNpnProtocols, getNpnSelectedProtocol);
            } catch (NoSuchMethodException ignored) {
                return new Android23(getMtu, openSslSocketClass, setUseSeetionTickets, setHostname);
            }
        } catch (ClassNotFoundException ignored) {

        } catch (NoSuchMethodException ignored) {

        }

        try {
            String npnClassName = "org.eclipse.jetty.npn.NextProtoNego";
            Class<?> nextProtoNegoClass = Class.forName(npnClassName);
            Class<?> providerClass = Class.forName(npnClassName + "$Provider");
            Class<?> clientProviderClass = Class.forName(npnClassName + "$ClientProvider");
            Class<?> serverProviderClass = Class.forName(npnClassName + "$ServerProvider");
            Method putMethod = nextProtoNegoClass.getMethod("put", SSLSocket.class, providerClass);
            Method getMethod = nextProtoNegoClass.getMethod("get", SSLSocket.class);
            return new JdkWithJettyNpnPlatform(getMtu, putMethod, getMethod, clientProviderClass, serverProviderClass);
        } catch (ClassNotFoundException | NoSuchMethodException ignored) {

        }
        return getMtu != null ? new Java5(getMtu) : new Platform();
    }

    public int getMtu(Socket socket) throws IOException {
        return 1400;
    }

    public byte[] getNpnSelectedProtocol(SSLSocket socket) {
        return null;
    }

    public void enableTlsExtensions(SSLSocket socket, String uriHost) {
    }

    public void setNpnProtocols(SSLSocket socket, byte[] npnProtocols) {
    }

    public static Platform get() {
        return PLATFORM;
    }

    public URI toUriLenient(URL url) throws URISyntaxException {
        return url.toURI();
    }

    private static class Java5 extends Platform {
        private final Method getMtu;

        private Java5(Method getMtu) {
            this.getMtu = getMtu;
        }

        @Override public int getMtu(Socket socket) throws IOException {
            try {
                NetworkInterface networkInterface = NetworkInterface.getByInetAddress(
                        socket.getLocalAddress());
                if (networkInterface == null) {
                    return super.getMtu(socket); // There's no longer an interface with this local address.
                }
                return (Integer) getMtu.invoke(networkInterface);
            } catch (IllegalAccessException e) {
                throw new AssertionError(e);
            } catch (InvocationTargetException e) {
                if (e.getCause() instanceof IOException) throw (IOException) e.getCause();
                throw new RuntimeException(e.getCause());
            }
        }

    }

    /**
     * Android version 2.3 and newer support TLS session tickets and server name
     * indication (SNI).
     */
    private static class Android23 extends Java5 {
        protected final Class<?> openSslSocketClass;
        private final Method setUseSessionTickets;
        private final Method setHostname;

        private Android23(Method getMtu, Class<?> openSslSocketClass, Method setUseSessionTickets,
                          Method setHostname) {
            super(getMtu);
            this.openSslSocketClass = openSslSocketClass;
            this.setUseSessionTickets = setUseSessionTickets;
            this.setHostname = setHostname;
        }

        @Override public void enableTlsExtensions(SSLSocket socket, String uriHost) {
            super.enableTlsExtensions(socket, uriHost);
            if (openSslSocketClass.isInstance(socket)) {
                // This is Android: use reflection on OpenSslSocketImpl.
                try {
                    setUseSessionTickets.invoke(socket, true);
                    setHostname.invoke(socket, uriHost);
                } catch (InvocationTargetException e) {
                    throw new RuntimeException(e);
                } catch (IllegalAccessException e) {
                    throw new AssertionError(e);
                }
            }
        }
    }

    /** Android version 4.1 and newer support NPN. */
    private static class Android41 extends Android23 {
        private final Method setNpnProtocols;
        private final Method getNpnSelectedProtocol;

        private Android41(Method getMtu, Class<?> openSslSocketClass, Method setUseSessionTickets,
                          Method setHostname, Method setNpnProtocols, Method getNpnSelectedProtocol) {
            super(getMtu, openSslSocketClass, setUseSessionTickets, setHostname);
            this.setNpnProtocols = setNpnProtocols;
            this.getNpnSelectedProtocol = getNpnSelectedProtocol;
        }

        @Override public void setNpnProtocols(SSLSocket socket, byte[] npnProtocols) {
            if (!openSslSocketClass.isInstance(socket)) {
                return;
            }
            try {
                setNpnProtocols.invoke(socket, new Object[] {npnProtocols});
            } catch (IllegalAccessException e) {
                throw new AssertionError(e);
            } catch (InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        }

        @Override public byte[] getNpnSelectedProtocol(SSLSocket socket) {
            if (!openSslSocketClass.isInstance(socket)) {
                return null;
            }
            try {
                return (byte[]) getNpnSelectedProtocol.invoke(socket);
            } catch (InvocationTargetException e) {
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                throw new AssertionError(e);
            }
        }
    }

    /**
     * OpenJDK 7 plus {@code org.mortbay.jetty.npn/npn-boot} on the boot class
     * path.
     */
    private static class JdkWithJettyNpnPlatform extends Java5 {
        private final Method getMethod;
        private final Method putMethod;
        private final Class<?> clientProviderClass;
        private final Class<?> serverProviderClass;

        public JdkWithJettyNpnPlatform(Method getMtu, Method putMethod, Method getMethod,
                                       Class<?> clientProviderClass, Class<?> serverProviderClass) {
            super(getMtu);
            this.putMethod = putMethod;
            this.getMethod = getMethod;
            this.clientProviderClass = clientProviderClass;
            this.serverProviderClass = serverProviderClass;
        }

        @Override public void setNpnProtocols(SSLSocket socket, byte[] npnProtocols) {
            try {
                List<String> strings = new ArrayList<String>();
                for (int i = 0; i < npnProtocols.length; ) {
                    int length = npnProtocols[i++];
                    strings.add(new String(npnProtocols, i, length, "US-ASCII"));
                    i += length;
                }
                Object provider = Proxy.newProxyInstance(Platform.class.getClassLoader(),
                        new Class[] {clientProviderClass, serverProviderClass},
                        new JettyNpnProvider(strings));
                putMethod.invoke(null, socket, provider);
            } catch (UnsupportedEncodingException e) {
                throw new AssertionError(e);
            } catch (InvocationTargetException e) {
                throw new AssertionError(e);
            } catch (IllegalAccessException e) {
                throw new AssertionError(e);
            }
        }

        @Override public byte[] getNpnSelectedProtocol(SSLSocket socket) {
            try {
                JettyNpnProvider provider =
                        (JettyNpnProvider) Proxy.getInvocationHandler(getMethod.invoke(null, socket));
                if (!provider.unsupported && provider.selected == null) {
                    Logger logger = Logger.getLogger(OkHttpClient.class.getName());
                    logger.log(Level.INFO,
                            "NPN callback dropped so SPDY is disabled. " + "Is npn-boot on the boot class path?");
                    return null;
                }
                return provider.unsupported ? null : provider.selected.getBytes("US-ASCII");
            } catch (UnsupportedEncodingException e) {
                throw new AssertionError();
            } catch (InvocationTargetException e) {
                throw new AssertionError();
            } catch (IllegalAccessException e) {
                throw new AssertionError();
            }
        }
    }

    /**
     * Handle the methods of NextProtoNego's ClientProvider and ServerProvider
     * without a compile-time dependency on those interfaces.
     */
    private static class JettyNpnProvider implements InvocationHandler {
        private final List<String> protocols;
        private boolean unsupported;
        private String selected;

        public JettyNpnProvider(List<String> protocols) {
            this.protocols = protocols;
        }

        @Override public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            String methodName = method.getName();
            Class<?> returnType = method.getReturnType();
            if (args == null) {
                args = Util.EMPTY_STRING_ARRAY;
            }
            if (methodName.equals("supports") && boolean.class == returnType) {
                return true;
            } else if (methodName.equals("unsupported") && void.class == returnType) {
                this.unsupported = true;
                return null;
            } else if (methodName.equals("protocols") && args.length == 0) {
                return protocols;
            } else if (methodName.equals("selectProtocol")
                    && String.class == returnType
                    && args.length == 1
                    && (args[0] == null || args[0] instanceof List)) {
                // TODO: use OpenSSL's algorithm which uses both lists
                List<?> serverProtocols = (List) args[0];
                this.selected = protocols.get(0);
                return selected;
            } else if (methodName.equals("protocolSelected") && args.length == 1) {
                this.selected = (String) args[0];
                return null;
            } else {
                return method.invoke(this, args);
            }
        }
    }
}
