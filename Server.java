
import java.net.*;
import java.io.*;

public class Server {
    public static void main(String [] args) {
        try {
            // Create a server socket
            ServerSocket serverSocket = new ServerSocket(5000);
            System.out.println("Server is listening on port 5000");

            // Wait for a client to connect
            Socket clientSocket = serverSocket.accept();
            System.out.println("A client has connected");

            // Create input and output streams for the socket
            InputStream inputStream = clientSocket.getInputStream();
            OutputStream outputStream = clientSocket.getOutputStream();

            // Read and write data from/to the socket
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            PrintWriter writer = new PrintWriter(outputStream, true);
            String inputLine;
            while ((inputLine = reader.readLine()) != null) {
                System.out.println("Client sent: " + inputLine);
                writer.println("Server received: " + inputLine);
            }

            // Close the socket
            clientSocket.close();
            serverSocket.close();
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
