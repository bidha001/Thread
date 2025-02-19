import java.io.*;
import java.net.*;
import java.util.Scanner;

public class MultiClient {
    private static final int PORT = 5006; // Connects to the multi-threaded server

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try (Socket socket = new Socket("localhost", PORT);
             BufferedReader in = new BufferedReader(
                     new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

            System.out.println("Connected to MultiThreadedServer. Type 'exit' to quit.");

            System.out.print("Enter number of operations to send: ");
            int numOperations = scanner.nextInt();
            scanner.nextLine(); // Consume the newline

            // Collect all operations from the user first
            for (int i = 0; i < numOperations; i++) {
                System.out.print("Enter first number: ");
                int num1 = scanner.nextInt();

                System.out.print("Enter second number: ");
                int num2 = scanner.nextInt();

                System.out.print("Enter operation (A = addition, S = subtraction, M = multiplication, D = division): ");
                char operator = scanner.next().charAt(0);
                scanner.nextLine(); // Consume the newline

                String request = num1 + " " + num2 + " " + operator;
                out.println(request);
            }
            out.println("exit"); // Signal end of requests

            // Delay to ensure the server processes all requests
            Thread.sleep(500);

            // No need to wait for responses in this multi-threaded scenario,
            // as each thread logs its own result and time to the server console.
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        scanner.close();
        System.out.println("MultiClient disconnected.");
    }
}
