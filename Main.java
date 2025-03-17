package org.example;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;
import org.example.MessengerPackage.Servlets.MessageServlet;
import org.example.MessengerPackage.Servlets.UserServlet;

import java.io.File;
import java.sql.SQLException;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) throws LifecycleException, SQLException {
        Tomcat tomcat = new Tomcat();
        tomcat.setBaseDir("tomcatSvr");
        tomcat.setPort(8080);

        // Set context path and document base
        String contextPath = "";
        String docBase = new File("./src/main/webapp").getAbsolutePath();

        // Create context
        Context context = tomcat.addContext(contextPath, new File(docBase).getAbsolutePath());

        // Add DefaultServlet to handle static files (including index.html)
        Tomcat.addServlet(context, "default", "org.apache.catalina.servlets.DefaultServlet");
        context.addServletMappingDecoded("/", "default");

        // Add your DataServlet
        Tomcat.addServlet(context, "messageServlet", new MessageServlet());
        context.addServletMappingDecoded("/message", "messageServlet");

        Tomcat.addServlet(context, "userServlet", new UserServlet());
        context.addServletMappingDecoded("/user", "userServlet");

        tomcat.start();
        tomcat.getConnector();
        tomcat.getServer().await();
    }
}