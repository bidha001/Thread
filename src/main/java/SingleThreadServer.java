import java.io.*;
import java.net.*;

public class SingleThreadServer {
    private static final int PORT = 5004;

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Single-threaded server started on port " + PORT);

            while (true) { // Keep the server running for multiple clients
                Socket socket = serverSocket.accept();
                handleClient(socket);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void handleClient(Socket socket) {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

            String input = in.readLine();
            if (input == null) {
                out.println("ERROR: Empty request");
                return;
            }

            String[] parts = input.split(" ");
            if (parts.length < 3) {
                out.println("ERROR: Invalid request format.");
                return;
            }

            int num1 = Integer.parseInt(parts[0]);
            int num2 = Integer.parseInt(parts[1]);
            char operator = parts[2].charAt(0);

            int result = calculate(num1, num2, operator);
            out.println(result); // Send result to client
            System.out.println("Processed: " + num1 + " " + operator + " " + num2 + " = " + result);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException ignored) {}
        }
    }

    private static int calculate(int num1, int num2, char operator) {
        return switch (operator) {
            case 'A' -> num1 + num2;
            case 'S' -> num1 - num2;
            case 'M' -> num1 * num2;
            case 'D' -> (num2 != 0 ? num1 / num2 : 0);
            default -> 0;
        };
    }
}
