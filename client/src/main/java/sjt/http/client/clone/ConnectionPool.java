package sjt.http.client.clone;

import com.squareup.okhttp.internal.Platform;
import com.squareup.okhttp.internal.Util;

import java.net.SocketException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.*;

public class ConnectionPool {

    private static final int MAX_CONNECTION_TO_CLEAN_UP = 2;

    private int maxIdleConnections;
    private long keepAliveDurationNs;

    private final LinkedList<Connection> connections = new LinkedList<>();

    private final ExecutorService executorService = new ThreadPoolExecutor(0, 1, 60L,
            TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(),
            Util.daemonThreadFactory("OkHttp ConnectionPool"));

    private final Callable<Void> connectionsCleanupCallable = () -> {
        List<Connection> expiredConnections = new ArrayList<>(MAX_CONNECTION_TO_CLEAN_UP);
        int idleConnectionCount = 0;
        synchronized (ConnectionPool.this) {
            for (ListIterator<Connection> i = connections.listIterator(connections.size());
            i.hasPrevious(); ) {
                Connection connection = i.previous();
                if (!connection.isAlive() || connection.isExpired(keepAliveDurationNs)) {
                    i.remove();
                    expiredConnections.add(connection);
                    if (expiredConnections.size() == MAX_CONNECTION_TO_CLEAN_UP) {
                        break;
                    }
                } else if (connection.isIdle()) {
                    idleConnectionCount++;
                }
            }

            for (ListIterator<Connection> i = connections.listIterator(connections.size());
                 i.hasPrevious() && idleConnectionCount > maxIdleConnections; ) {
                Connection connection = i.previous();
                if (connection.isIdle()) {
                    expiredConnections.add(connection);
                    i.remove();
                    --idleConnectionCount;
                }
            }
        }
        for (Connection expiredConnection : expiredConnections) {
            Util.closeQuietly(expiredConnection);
        }
        return null;
    };

    public ConnectionPool(int maxIdleConnections, long keepAliveDurationMs) {
        this.maxIdleConnections = maxIdleConnections;
        this.keepAliveDurationNs = keepAliveDurationMs * 1000 * 1000;
    }

    public void recycle(Connection connection) {
        executorService.submit(connectionsCleanupCallable);

        if (connection.isSpdy()) {
            return;
        }

        if (!connection.isAlive()) {
            Util.closeQuietly(connection);
            return;
        }

        try {
            Platform.get().untagSocket(connection.getSocket());
        } catch (SocketException e) {
            Platform.get().logW("Unable to untagSocket(): " + e);
            Util.closeQuietly(connection);
            return;
        }

        synchronized (this) {
            connections.addFirst(connection);
            connection.resetIdleStartTime();
        }
    }
}
