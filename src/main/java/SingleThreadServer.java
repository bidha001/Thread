import java.io.*;
import java.net.*;

public class SingleThreadServer {
    public static void main(String[] args) {
        int totalRequests = 10;
        try (ServerSocket serverSocket = new ServerSocket(5006)) { // Different port
            System.out.println("Single-threaded server started...");
            long startTime = System.currentTimeMillis();

            for (int i = 0; i < totalRequests; i++) {
                Socket socket = serverSocket.accept();
                handleClient(socket);
            }

            long endTime = System.currentTimeMillis();
            System.out.println("Time taken: " + (endTime - startTime) / 1000.0 + " seconds");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void handleClient(Socket socket) {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

            socket.setSoTimeout(5000); // Prevent blocking forever

            String input = in.readLine();
            if (input == null) return;

            String[] parts = input.split(" ");
            int num1 = Integer.parseInt(parts[0]);
            int num2 = Integer.parseInt(parts[1]);
            char operator = parts[2].charAt(0);

            int result = calculate(num1, num2, operator); // Now calling a local method
            out.println(result);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException ignored) {}
        }
    }

    // Moved calculate() method inside SingleThreadServer
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
