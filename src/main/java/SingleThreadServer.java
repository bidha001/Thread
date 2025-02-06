import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class SingleThreadServer {
    public static void main(String[] args) {
        int port = 5005;

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Single-threaded Server started on port " + port);

            // Accept one client at a time
            Socket clientSocket = serverSocket.accept();
            System.out.println("Client is connected!");

            // Handle client communication
            BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter output = new PrintWriter(clientSocket.getOutputStream(), true);

            String clientMessage;
            while ((clientMessage = input.readLine()) != null) {
                System.out.println("Client: " + clientMessage);

                if (clientMessage.equalsIgnoreCase("exit")) {
                    output.println("Goodbye!");
                    break;
                } else {
                    output.println("Received: " + clientMessage);
                }
            }

            // Close connection after client disconnects
            clientSocket.close();
            System.out.println("Client disconnected.");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
