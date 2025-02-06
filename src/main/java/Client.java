import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
    public static void main(String[] args) {
        String serverAddress = "localhost";
        int port = 5005;

        try (Socket socket = new Socket(serverAddress, port);
             BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in))) {

            System.out.println("Connected to the server!");

            String message;
            while (true) {
                System.out.print("Enter message: ");
                message = consoleReader.readLine();
                writer.println(message);

                if (message.equalsIgnoreCase("exit")) {
                    break;
                }

                String serverResponse = reader.readLine();
                System.out.println("Server: " + serverResponse);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
