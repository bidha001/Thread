import java.io.*;
import java.net.*;
import java.util.Scanner;

public class MultiClient {
    private static final int PORT = 5006;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try (Socket socket = new Socket("localhost", PORT);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

            System.out.println("Connected to MultiThreadedServer. Type 'exit' to quit.");

            System.out.print("Enter number of operations to send: ");
            int numOperations = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            String[] requests = new String[numOperations];

            // **Step 1: Collect all user inputs before sending**
            for (int i = 0; i < numOperations; i++) {
                System.out.print("Enter first number: ");
                int num1 = scanner.nextInt();

                System.out.print("Enter second number: ");
                int num2 = scanner.nextInt();

                System.out.print("Enter operation (A = addition, S = subtraction, M = multiplication, D = division): ");
                char operator = scanner.next().charAt(0);
                scanner.nextLine(); // Consume newline

                requests[i] = num1 + " " + num2 + " " + operator;
            }

            // **Step 2: Send all requests first**
            for (String request : requests) {
                out.println(request);
            }

            out.println("exit"); // Tell server no more requests

            // **Step 3: Wait before reading responses (Give time for server processing)**
            Thread.sleep(500); // **Small delay to ensure responses are received**

            // **Step 4: Receive all responses**
            System.out.println("\nReceiving results from server...");
            for (int i = 0; i < numOperations; i++) {
                String result = in.readLine();
                if (result != null) {
                    System.out.println("Result " + (i + 1) + ": " + result);
                } else {
                    System.out.println("Result " + (i + 1) + ": ERROR (Server did not respond)");
                }
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        scanner.close();
        System.out.println("Client disconnected.");
    }
}
