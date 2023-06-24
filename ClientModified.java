import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

public class ClientModified{
    public static void main(String[] args) {
        String configFile = "server_addresses.txt"; // Path to the configuration file

        List<String> serverAddresses;
        try {
            serverAddresses = Files.readAllLines(Paths.get(configFile));
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        for (String serverAddress : serverAddresses) {
            Thread clientThread = new Thread(() -> {
                try {
                    Socket socket = new Socket(serverAddress, 8190);

                    InputStream inputStream = socket.getInputStream();
                    OutputStream outputStream = socket.getOutputStream();
                    Scanner in = new Scanner(inputStream, StandardCharsets.UTF_8);
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
            });

            clientThread.start();
        }
    }
}

