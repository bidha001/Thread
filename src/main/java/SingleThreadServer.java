import java.io.*;
import java.net.*;

public class SingleThreadServer {
    private static final int PORT = 5006;
    private static final int TOTAL_REQUESTS = 10;

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Single-threaded server started on port " + PORT);
            long startTime = System.currentTimeMillis();

            for (int i = 0; i < TOTAL_REQUESTS; i++) {
                Socket socket = serverSocket.accept();
                handleClient(socket);
            }

            long endTime = System.currentTimeMillis();
            System.out.println("Total processing time (Single-threaded): " + (endTime - startTime) / 1000.0 + " seconds");
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

            int result = calculate(num1, num2, operator);
            out.println(result);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException ignored) {}
        }
    }

    // Performs calculations
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
