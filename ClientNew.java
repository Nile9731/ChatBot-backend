import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Properties;
import java.util.Scanner;

public class ClientNew {

    public static void main(String[] args) {
        String serverIP;
        int port;
        try {
            Properties props= PropertyUtility.read("project.properties");
            serverIP= props.getProperty("serverIP");
            port = Integer.parseInt(props.getProperty("port"));
            Socket socket = new Socket(serverIP, port);
            InputStream inputStream = socket.getInputStream();
            OutputStream outputStream = socket.getOutputStream();
            Scanner in = new Scanner(inputStream, StandardCharsets.UTF_8);
            //to read the  response from server in a character format
            PrintWriter out = new PrintWriter(outputStream, true);

            Scanner scanner = new Scanner(System.in);
            String line;

            do {
                line = scanner.nextLine();
                out.println(line);

                String response = in.nextLine();
                System.out.println("Server: " + response);
            } while (!line.strip().equals("BYE"));

            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
