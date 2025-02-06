import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class MultiThreadServer {
    public static void main(String[] args) {
        int port = 5005;

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Multi-threaded Server started on port " + port);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                new Thread(() -> handleClient(clientSocket)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void handleClient(Socket clientSocket) {
        try (BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             PrintWriter output = new PrintWriter(clientSocket.getOutputStream(), true)) {

            String clientMessage = input.readLine();
            System.out.println("Client: " + clientMessage);

            // Riktig svar sendes tilbake til klienten
            String response = processMathOperation(clientMessage);
            output.println(response);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String processMathOperation(String message) {
        String[] parts = message.split(" ");
        if (parts.length != 3) return "Error: Invalid input";

        String operation = parts[0].toUpperCase();
        int num1, num2;

        try {
            num1 = Integer.parseInt(parts[1]);
            num2 = Integer.parseInt(parts[2]);
        } catch (NumberFormatException e) {
            return "Error: Invalid number format.";
        }

        switch (operation) {
            case "A": return "Result: " + (num1 + num2);
            case "S": return "Result: " + (num1 - num2);
            case "M": return "Result: " + (num1 * num2);
            case "D": return num2 != 0 ? "Result: " + (num1 / num2) : "Error: Division by zero";
            default: return "Error: Invalid operation. Use A, S, M, or D.";
        }
    }
}
