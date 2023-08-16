import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.Transport;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.eclipse.jetty.servlets.CrossOriginFilter;
import org.json.JSONArray;
import org.json.JSONObject;
import org.eclipse.jetty.servlet.FilterHolder;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import java.util.EnumSet;

public class ChatServer {
    private static final Logger logger = LoggerFactory.getLogger(ChatServer.class);

    public static void main(String[] args) throws InterruptedException {
        // Create SocketIO server configuration
        Configuration config = new Configuration();
        config.setHostname("localhost");
        config.setPort(8081);

        // Create SocketIO server instance
        final SocketIOServer server = new SocketIOServer(config);

        // Set up Cross-Origin Resource Sharing (CORS) configuration

        // Event listener for "join" event
        server.addEventListener("join", String.class, (client, room, ackSender) -> {
            // ...
        });

        // Event listener for "chat" event
        server.addEventListener("chat", String.class, (client, message, ackSender) -> {
            // Retrieve the room information from the client's handshake data
            System.out.println("Received message from frontend: " + message); // Log the received message

            String room = client.getHandshakeData().getSingleUrlParam("room");

            // Make a request to the ChatGPT API
            HttpClient httpClient = HttpClients.createDefault();
            HttpPost request = new HttpPost("https://api.openai.com/v1/chat/completions");
            request.setHeader(HttpHeaders.AUTHORIZATION, "Bearer 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");
            request.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");

            JSONObject requestBody = new JSONObject();
            requestBody.put("message", new JSONArray().put(new JSONObject().put("role", "system").put("content", "You: " + message)));
            requestBody.put("max_tokens", 50); // Adjust the value as needed

            try {
                request.setEntity(new StringEntity(requestBody.toString(), "UTF-8"));
                HttpResponse response = httpClient.execute(request);
                HttpEntity entity = response.getEntity();

                if (entity != null) {
                    String responseBody = EntityUtils.toString(entity, "UTF-8");
                    JSONObject jsonResponse = new JSONObject(responseBody);
                    String botResponse = jsonResponse.getJSONArray("choices").getJSONObject(0).getString("message").substring(5);

                    // Send the bot's response to the client
                    client.sendEvent("message", "Bot: " + botResponse);

                    logger.info("Bot response sent to the client"); // Logging statement
                }
            } catch (Exception e) {
                logger.error("An error occurred: ", e);
            }
        });

        // Start the SocketIO server
        server.start();

        // Add a shutdown hook to gracefully stop the server when the program is terminated
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            server.stop();
        }));

        // Keep the server running until terminated
        Thread.currentThread().join();
    }
}
