import java.io.*;
import java.net.*;

public class ClientHandler implements Runnable {
    private Socket socket;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

            System.out.println("Connected to client: " + socket.getInetAddress());

            // Read all requests first
            String[] requests = new String[10]; // Allow up to 10 requests
            int requestCount = 0;

            String input;
            while ((input = in.readLine()) != null) {
                if (input.equalsIgnoreCase("exit")) {
                    System.out.println("Client disconnected.");
                    break;
                }
                requests[requestCount++] = input; // Store request
            }

            // Process all stored requests
            for (int i = 0; i < requestCount; i++) {
                String[] parts = requests[i].split(" ");
                if (parts.length != 3) {
                    out.println("ERROR: Invalid request format.");
                    continue;
                }

                try {
                    int num1 = Integer.parseInt(parts[0]);
                    int num2 = Integer.parseInt(parts[1]);
                    char operator = parts[2].charAt(0);

                    int result = calculate(num1, num2, operator);
                    out.println(result); // Send result
                    System.out.println("Processed: " + num1 + " " + operator + " " + num2 + " = " + result);
                } catch (NumberFormatException e) {
                    out.println("ERROR: Invalid number format.");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException ignored) {}
        }
    }

    private int calculate(int num1, int num2, char operator) {
        return switch (operator) {
            case 'A' -> num1 + num2;
            case 'S' -> num1 - num2;
            case 'M' -> num1 * num2;
            case 'D' -> (num2 != 0 ? num1 / num2 : 0);
            default -> 0;
        };
    }
}
