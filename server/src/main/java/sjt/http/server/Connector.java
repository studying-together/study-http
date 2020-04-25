package sjt.http.server;

import sjt.http.core.log.Logger;

import java.io.IOException;
import java.net.Socket;

public class Connector implements Runnable {

    private Connection connection;

    public Connector(Socket socket) throws IOException {
        this.connection = new Connection(socket);
    }

    public void run() {
        try {
            Logger.log(this, "client connected");
            connection.execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
