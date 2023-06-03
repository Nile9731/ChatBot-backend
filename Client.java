import java.net.*;
import java.io.*;

public class Client {
    public static void main(String [] args) {
        try {
            // Create a socket and connect to the server
            Socket socket = new Socket("localhost", 5000);
            System.out.println("Connected to server");

            // Create input and output streams for the socket
            InputStream inputStream = socket.getInputStream();
            OutputStream outputStream = socket.getOutputStream();

            // Read and write data from/to the socket
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            PrintWriter writer = new PrintWriter(outputStream, true);
            BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
            String inputLine, outputLine;
            while ((inputLine = consoleReader.readLine()) != null) {
                writer.println(inputLine);
                outputLine = reader.readLine();
                System.out.println("Server response: " + outputLine);
            }

            // Close the socket
            socket.close();
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
