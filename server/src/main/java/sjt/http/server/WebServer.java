package sjt.http.server;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;
import sjt.http.server.servlet.TcHttpServlet;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class WebServer {

    private static final int THREAD_POOL_SIZE = 4;

    public static void main(String[] args) throws LifecycleException, IOException {
        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_POOL_SIZE);

        int port = 8080;
        ServerSocket serverSocket = new ServerSocket(port);

        while(true) {
            Socket socket = serverSocket.accept();
            executorService.execute(() -> new ServerExecutor(socket).execute());
        }

//        Tomcat tomcat = new Tomcat();
//        tomcat.setPort(8080);
//        Context context = tomcat.addContext("/","/");
//
//        TcHttpServlet servlet = new TcHttpServlet();
//        String servletName = "TcServlet";
//        tomcat.addServlet("/", servletName, servlet);
//        context.addServletMapping("/user", servletName);
//
//        tomcat.start();
//        tomcat.getServer().await();
    }

}
