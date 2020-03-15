package sjt.http.client.clone;

import javax.swing.text.html.parser.AttributeList;
import java.net.InetSocketAddress;
import java.net.Proxy;

public class Route {

    final Address address;
    final Proxy proxy;
    final InetSocketAddress inetSocketAddress;
    final boolean modernTls;

    public Route(Address address, Proxy proxy, InetSocketAddress inetSocketAddress, boolean modernTls) {
        this.address = address;
        this.proxy = proxy;
        this.inetSocketAddress = inetSocketAddress;
        this.modernTls = modernTls;
    }

    public Object getAddress() {
        return address;
    }

    public Proxy getProxy() {
        return proxy;
    }
}
