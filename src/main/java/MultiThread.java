import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class MultiThread {
    public static void main(String[] args) {
        int port = 5005;
        try (ServerSocket ServerSocket = new ServerSocket(5005)) {
            System.out.println("Server started on porst" + 5005);

            while () {
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client is connected");

                new Thread(() -> handleClient(clientSocket)).start();
            }
        } catch (IOException e){
        e.printStackTrace();
    }
    }
    private static void handleClient(Socket clientSocket) {
        try (
                BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter output = new PrintWriter(clientSocket.getOutputStream(), true)
        ) {
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
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}


