package sjt.http.server;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;
import sjt.http.server.servlet.TcHttpServlet;

public class WebServer {

    public static void main(String[] args) throws LifecycleException {

//        int port = 8080;
//        ServerSocket serverSocket = new ServerSocket(port);

        Tomcat tomcat = new Tomcat();
        tomcat.setPort(8080);
        Context context = tomcat.addContext("/","/");

        TcHttpServlet servlet = new TcHttpServlet();
        String servletName = "TcServlet";
        tomcat.addServlet("/", servletName, servlet);
        context.addServletMapping("/user", servletName);

        tomcat.start();
        tomcat.getServer().await();
    }

}
