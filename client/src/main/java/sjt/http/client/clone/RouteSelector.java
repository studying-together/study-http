package sjt.http.client.clone;

import java.net.Proxy;
import java.net.ProxySelector;
import java.net.URI;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class RouteSelector {
    private final Address address;
    private final URI uri;
    private final ProxySelector proxySelector;
    private final ConnectionPool pool;
    private final Dns dns;
    private final Set<Route> failedRoutes;

    private boolean hasNextProxy;
    private Proxy useSpecifiedProxy;
    private Iterator<Proxy> proxySelectorProxies;

    private final LinkedList<Route> postponedRoutes;

    public RouteSelector(Address address,
                         URI uri,
                         ProxySelector proxySelector,
                         ConnectionPool pool,
                         Dns dns,
                         Set<Route> failedRoutes) {
        this.address = address;
        this.uri = uri;
        this.proxySelector = proxySelector;
        this.pool = pool;
        this.dns = dns;
        this.failedRoutes = failedRoutes;
        this.postponedRoutes = new LinkedList<Route>();

        resetNextProxy(uri, address.proxy);
    }

    private void resetNextProxy(URI uri, Proxy proxy) {
        this.hasNextProxy = true;
        if (proxy != null) {
            this.useSpecifiedProxy = proxy;
        } else {
            List<Proxy> proxyList = proxySelector.select(uri);
            if (proxyList != null) {
                this.proxySelectorProxies = proxyList.iterator();
            }
        }
    }

    public Connection next() {
        Connection pooled = pool.get(address);
        if (pooled != null) {
            return pooled;
        }
        return null;
    }
}
