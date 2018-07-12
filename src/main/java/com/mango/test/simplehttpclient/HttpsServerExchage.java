package com.mango.test.simplehttpclient;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.KeyStore;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLParameters;
import javax.net.ssl.TrustManagerFactory;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpsConfigurator;
import com.sun.net.httpserver.HttpsExchange;
import com.sun.net.httpserver.HttpsParameters;
import com.sun.net.httpserver.HttpsServer;
import com.sun.net.httpserver.HttpServer;

public class HttpsServerExchage {
	
	private static final int port = 8000;

    public static class MyHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            String response = "This is the response";
            
			//HttpsExchange httpsExchange = (HttpsExchange) t;
            HttpExchange httpExchange = (HttpExchange) t;
            //t.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
            t.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
            System.out.println("Response  served at:"+port);
        }
    }


    public static void main(String[] args) throws Exception {
    	
		/*Runnable httpsServerExchange = new Runnable() {

			// @Override
			public void run() {
				httpsServerExchange();
			}
		};
		ExecutorService executor = Executors.newCachedThreadPool();
		executor.submit(httpsServerExchange);
		
		Runnable httpSocketExchange = new Runnable() {
			
			//@Override
			public void run() {
				htttpSocketExchange();
			}
		};
		executor.submit(httpSocketExchange);*/
		
    	httpsServerExchange();
    	//htttpSocketExchange();
        
    }
    
    public static void httpsServerExchange() {
    	
    	try {
            // setup the socket address
        	//int port = 8000;
            InetSocketAddress address = new InetSocketAddress(port);

            // initialise the HTTPS server
            //HttpsServer httpsServer = HttpsServer.create(address, 0);
            HttpServer httpServer = HttpServer.create(address,0);

            /*SSLContext sslContext = SSLContext.getInstance("TLS");

            // initialise the keystore
            char[] password = "123456789".toCharArray();
            KeyStore ks = KeyStore.getInstance("JKS");
            FileInputStream fis = new FileInputStream("KeyStore.jks");
            ks.load(fis, password);

            // setup the key manager factory
            KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
            kmf.init(ks, password);

            // setup the trust manager factory
            TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509");
            tmf.init(ks);

            // setup the HTTPS context and parameters
            sslContext.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);

            httpsServer.setHttpsConfigurator(new HttpsConfigurator(sslContext) {
                public void configure(HttpsParameters params) {
                    try {
                        // initialise the SSL context
                        SSLContext c = SSLContext.getDefault();
                        SSLEngine engine = c.createSSLEngine();
                        params.setNeedClientAuth(false);
                        params.setCipherSuites(engine.getEnabledCipherSuites());
                        params.setProtocols(engine.getEnabledProtocols());

                        // get the default parameters
                        SSLParameters defaultSSLParameters = c.getDefaultSSLParameters();
                        params.setSSLParameters(defaultSSLParameters);

                    } catch (Exception ex) {
                        System.out.println("Failed to create HTTPS port");
                    }
                }
            });*/
            httpServer.createContext("/test", new MyHandler());
            //httpsServer.createContext("/test", new MyHandler());
            //httpsServer.setExecutor(null); // creates a default executor
            httpServer.setExecutor(null);
            httpServer.start();
            //httpsServer.start();
            System.out.println("Server started on:"+ port);

        } catch (Exception exception) {
            System.out.println("Failed to create HTTPS server on port " + 8000 + " of localhost");
            exception.printStackTrace();

        }
    	
    }
    
    public static void htttpSocketExchange() {
    	
    	int port = 9997;
    	 
        try (ServerSocket serverSocket = new ServerSocket(port)) {
 
            System.out.println("Server is listening on port " + port);
 
            while (true) {
                Socket socket = serverSocket.accept();
                
 
                System.out.println("New client connected");
 
                OutputStream output = socket.getOutputStream();
                PrintWriter out = new PrintWriter(output, true);
 
                out.write("HTTP/1.0 200 OK\r\n");
                out.write("Date: Fri, 31 Dec 1999 23:59:59 GMT\r\n");
                out.write("Server: Apache/0.8.4\r\n");
                out.write("Content-Type: text/html\r\n");
                out.write("Content-Length: 57\r\n");
                out.write("Expires: Sat, 01 Jan 2000 00:59:59 GMT\r\n");
                out.write("Last-modified: Fri, 09 Aug 1996 14:21:40 GMT\r\n");
                out.write("\r\n");
                out.write("<TITLE>Exemple</TITLE>");
                out.write("<P>hello</P>");
                out.flush();

                out.close();
            }
 
        } catch (IOException ex) {
            System.out.println("Server exception: " + ex.getMessage());
            ex.printStackTrace();
        }
    	
    }

}
