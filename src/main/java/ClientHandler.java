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

            String input = in.readLine();
            if (input == null || input.trim().isEmpty()) {
                out.println("ERROR: Empty request received.");
                return;
            }

            String[] parts = input.split(" ");
            if (parts.length != 3) {
                out.println("ERROR: Invalid request format. Expected format: <num1> <num2> <operator>");
                return;
            }

            try {
                int num1 = Integer.parseInt(parts[0]);
                int num2 = Integer.parseInt(parts[1]);
                char operator = parts[2].charAt(0);

                int result = calculate(num1, num2, operator);
                out.println(result); // Send result to client
                System.out.println("Processed: " + num1 + " " + operator + " " + num2 + " = " + result);
            } catch (NumberFormatException e) {
                out.println("ERROR: Invalid number format.");
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
