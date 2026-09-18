import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

public class KvServer
{

    public static void main(String[] args) 
    {
        try {
            // Create an HttpServer instance
            HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);

            // Create a context for a specific path and set the handler
            KvStore store = new KvStore();
            server.createContext("/add", new KvHandler(store));
            server.createContext("/delete/",new KvHandler(store));
            server.createContext("/get/",new KvHandler(store));

            // Start the server
            server.setExecutor(null); // Use the default executor
            server.start();

            System.out.println("Server is running on port 8000");
        } catch (IOException e) {
            System.out.println("Error starting the server: " + e.getMessage());
        }
    }

   

}