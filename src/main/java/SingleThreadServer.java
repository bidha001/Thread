import java.io.*;
import java.net.*;

public class SingleThreadServer {
    private static final int PORT = 5004; // Port for the single-threaded server

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Single-threaded server started on port " + PORT + " (Listening for Client)");

            while (true) {
                Socket socket = serverSocket.accept();
                handleClient(socket); // Process each client sequentially
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Modified to calculate and log both individual and total time for all operations (in seconds)
    public static void handleClient(Socket socket) {
        try (BufferedReader in = new BufferedReader(
                new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

            // Read the number of operations from the client
            String countLine = in.readLine();
            if (countLine == null) {
                out.println("ERROR: Missing operation count.");
                return;
            }
            int numOperations = Integer.parseInt(countLine.trim());
            System.out.println("Expecting " + numOperations + " operations from the client.");

            // Record start time for total processing
            long totalStartTime = System.nanoTime();

            // Process each operation one by one
            for (int i = 0; i < numOperations; i++) {
                String input = in.readLine();
                if (input == null) {
                    out.println("ERROR: Missing request for operation " + (i + 1));
                    continue;
                }
                String[] parts = input.split(" ");
                if (parts.length < 3) {
                    out.println("ERROR: Invalid request format for operation " + (i + 1));
                    continue;
                }
                try {
                    int num1 = Integer.parseInt(parts[0]);
                    int num2 = Integer.parseInt(parts[1]);
                    char operator = parts[2].charAt(0);

                    // Measure time for each individual operation in seconds
                    long startTime = System.nanoTime();
                    int result = calculate(num1, num2, operator);
                    long endTime = System.nanoTime();
                    long timeTakenNs = endTime - startTime;
                    double timeTakenSec = timeTakenNs / 1_000_000_000.0;
                    System.out.println("Operation " + (i + 1) + ": " + num1 + " " + operator + " " + num2 +
                            " = " + result + " | Time taken: " + timeTakenSec + " seconds");
                    out.println(result);
                } catch (NumberFormatException e) {
                    out.println("ERROR: Invalid number format in operation " + (i + 1));
                }
            }
            // Record total processing time and log it in seconds
            long totalEndTime = System.nanoTime();
            long totalTimeNs = totalEndTime - totalStartTime;
            double totalTimeSec = totalTimeNs / 1_000_000_000.0;
            System.out.println("Total time taken for all " + numOperations + " operations: " + totalTimeSec + " seconds");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException ignored) {}
        }
    }

    // Method for performing arithmetic operations
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
