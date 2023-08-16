import java.io.*;
import java.net.HttpURLConnection;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Properties;
import java.util.Scanner;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;


public class ServerNew {
    public static void main(String[] args) throws IOException {

        try{
            Properties props= PropertyUtility.read("project.properties");
            int port = Integer.parseInt(props.getProperty("port"));
            ServerSocket s= new ServerSocket(port);
            int i=1;
            while (true)
            {
                Socket incoming= s.accept();
                System.out.println("Connected");
                Runnable r= new ThreadedEchoHandler(incoming);
              Thread thread= new Thread( r);
              thread.start();
              i++;
            }
        } catch (IOException e) {
           // throw new RuntimeException(e);
            e.printStackTrace();
        }

    }
}
 class ThreadedEchoHandler implements  Runnable
 {
      private Socket incoming;

     public ThreadedEchoHandler(Socket incoming) {
         this.incoming = incoming;
     }

     @Override
     public void run() {
         try (InputStream inputStream = incoming.getInputStream();
              OutputStream outputStream = incoming.getOutputStream();
              Scanner in = new Scanner(inputStream, StandardCharsets.UTF_8);
              PrintWriter out = new PrintWriter(outputStream, true)) {

             System.out.println("Hello, Enter Bye to Exit!");

             String line;
             do {
                 line = in.nextLine();
                 System.out.println("Client: " + line);
                 String response = sendChatGPTRequest(line);
                 System.out.println("ChatGPT: " + response);
                 out.println(response);
             } while (!line.strip().equals("BYE"));

         } catch (IOException e) {
             throw new RuntimeException(e);
         }
     }

     private String sendChatGPTRequest(String message) throws IOException {
         String apiKey ="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" +
                 "\n";
         String apiUrl = "https://api.openai.com/v1/chat/completions";
         String model = "gpt-3.5-turbo";
         int maxTokens = 100;

         try {
            URI uri = new URI(apiUrl);
            URL url = uri.toURL();
HttpURLConnection connection = (HttpURLConnection) url.openConnection();
         //opens the url connection
         connection.setRequestMethod("POST");
         connection.setRequestProperty("Authorization", "Bearer " + "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");
         connection.setRequestProperty("Content-Type", "application/json");
         connection.setDoOutput(true);

         String body = "{\"model\": \"" + model + "\", \"messages\": [{\"role\": \"system\", \"content\": \"You are a helpful assistant.\"}, {\"role\": \"user\", \"content\": \"" + message + "\"}], \"max_tokens\": " + maxTokens + "}";
         connection.getOutputStream().write(body.getBytes());
            // Rest of your code...
 BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
         StringBuilder response = new StringBuilder();
         String line;
         while ((line = reader.readLine()) != null) {
             response.append(line);
         }
         reader.close();

         return response.toString();
     }
        } catch (URISyntaxException | MalformedURLException e) {
            e.printStackTrace();
}
       
        



 }
