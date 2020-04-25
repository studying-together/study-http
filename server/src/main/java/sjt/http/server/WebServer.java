package sjt.http.server;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.startup.Tomcat;

import javax.servlet.ServletException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class WebServer {

    public static void main(String[] args) throws IOException, ServletException, LifecycleException {

//        int port = 8080;
//        ServerSocket serverSocket = new ServerSocket(port);

        Tomcat tomcat = new Tomcat();
        String webPort = "8080";
        tomcat.setPort(Integer.parseInt(webPort));

        Connector connector = tomcat.getConnector();
        connector.setURIEncoding("UTF-8");

        tomcat.start();
        tomcat.getServer().await();
    }

}
