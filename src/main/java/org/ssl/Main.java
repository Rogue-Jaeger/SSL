package org.ssl;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.security.KeyStore;

public class Main {
    public static void main(String[] args) {
        try {
            int port = 8080;
            HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);

            String contextPath = "/";
            // Create HttpHandler to process requests
            HttpHandler handler = exchange -> {
                String response = "Hello from Basic HTTP Server!";
                exchange.getResponseHeaders().set("Content-Type", "text/plain");
                exchange.sendResponseHeaders(200, response.length());
                OutputStream os = exchange.getResponseBody();
                os.write(response.getBytes());
                os.close();
            };
            server.createContext(contextPath, handler);

            SSL ssl = new SSL();
            ssl.handleSSL();

            server.start();
            System.out.println("Server started on port " + port);
        } catch (IOException ioe) {
            System.out.println("Unable to create https server. Exception is: " + ioe.getMessage());
        }
    }
}