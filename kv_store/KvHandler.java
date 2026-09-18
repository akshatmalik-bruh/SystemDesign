 import com.sun.net.httpserver.HttpHandler;
 import com.sun.net.httpserver.HttpExchange;
 import java.io.IOException;
 import java.io.OutputStream;
 import java.io.InputStream;
import java.nio.charset.StandardCharsets;

 
 public class KvHandler implements HttpHandler {
         KvStore kvStore;
        public KvHandler(KvStore kvStore ){
            this.kvStore = kvStore;
        }
        @Override
        public void handle(HttpExchange exchange) throws IOException 
        {
            // Handle the request
            String method = exchange.getRequestMethod();
            
            if(method.equals("GET")){
                this.handleGet(exchange);
            
            }
            else if(method.equals("POST")){
                this.handlePost(exchange);
                
            }
            else if(method.equals("DELETE")){
                this.handleDelete(exchange);
            }
            else{
                exchange.sendResponseHeaders(405, 0);
                exchange.getResponseBody().close();
            }

            
            
        }
        public void handleGet(HttpExchange exchange)throws IOException{
            String path = exchange.getRequestURI().getPath();
            String[] arr = path.split("/");
            String key = arr[2];
            String value = kvStore.get(key);
            if(value == null){
                exchange.sendResponseHeaders(404, 0);
                exchange.getResponseBody().close();
                return;
            }
            exchange.sendResponseHeaders(200, value.getBytes().length);
            OutputStream os = exchange.getResponseBody();
            os.write(value.getBytes());
            os.close();
        }
        public void handlePost(HttpExchange exchange)throws IOException{
            InputStream data = exchange.getRequestBody();
            String query = exchange.getRequestURI().getQuery();
            String[] params = query.split("=");
            String key = params[1];
            String value = new String(data.readAllBytes(), StandardCharsets.UTF_8);
            
            kvStore.put(key, value);
            String response = "Added key :" + key + "and value : " + value;



            exchange.sendResponseHeaders(200, response.getBytes().length);
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes(StandardCharsets.UTF_8));
            os.close();

        }
        public void handleDelete(HttpExchange exchange)throws IOException{
            InputStream data = exchange.getRequestBody();
            String query = exchange.getRequestURI().getQuery();
            String[] params = query.split("=");
            String key = params[1];
      
            
            kvStore.delete(key);
            String response = "Deleted key :" + key;



            exchange.sendResponseHeaders(200, response.getBytes().length);
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes(StandardCharsets.UTF_8s));
            os.close();

        }
    }