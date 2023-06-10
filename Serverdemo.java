import java.io.*;
import java.net.*;
import com.sun.net.httpserver.*;

public class ServerDemo {
    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
        server.createContext("/greet", new GreetHandler());
        server.setExecutor(null);
        server.start();


        System.out.println("Server started on port 8000");
    }

    static class GreetHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if (exchange.getRequestMethod().equalsIgnoreCase("POST")) {
                // Read the request body
                InputStream requestBody = exchange.getRequestBody();
                InputStreamReader inputStreamReader = new InputStreamReader(requestBody);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String requestJson = bufferedReader.readLine();

                // Parse the request JSON
                String name = null;
                if (requestJson != null) {
                    name = requestJson.split(":")[1].replace("\"", "").trim();
                }

                // Generate the greeting message
                String message;
                if (name != null && !name.isEmpty()) {
                    message = "Hello, " + name + "!";
                } else {
                    message = "Hello!";
                }

                // Prepare the response JSON
                String responseJson = "{\"message\":\"" + message + "\"}";

                // Send the response
                exchange.getResponseHeaders().set("Content-Type", "application/json");
                exchange.sendResponseHeaders(200, responseJson.length());
                OutputStream responseBody = exchange.getResponseBody();
                responseBody.write(responseJson.getBytes());
                responseBody.close();
            } else {
                // Handle unsupported HTTP methods
                exchange.sendResponseHeaders(405, -1); // 405 Method Not Allowed
            }
        }
    }
}

