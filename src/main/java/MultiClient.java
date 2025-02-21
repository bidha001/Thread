
import java.io.*;
import java.net.*;
import java.util.Scanner;

public class MultiClient {
    private static final int PORT = 5008; // Connects to the multi-threaded server

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try (Socket socket = new Socket("localhost", PORT);
             BufferedReader in = new BufferedReader(
                     new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

            System.out.println("Connected to MultiThreadedServer. Type 'exit' to quit.");

            // Ask for the number of operations
            System.out.print("Enter number of operations to send: ");
            int numOperations = scanner.nextInt();
            scanner.nextLine(); // Consume the newline
            out.println(numOperations); // Send the count to the server

            // Collect all operations from the user first
            String[] operations = new String[numOperations];
            for (int i = 0; i < numOperations; i++) {
                System.out.println("\nOperation " + (i + 1) + ":");
                System.out.print("Enter first number: ");
                String firstNumber = scanner.nextLine();

                System.out.print("Enter second number: ");
                String secondNumber = scanner.nextLine();

                System.out.print("Enter operation (A = addition, S = subtraction, M = multiplication, D = division): ");
                char operator = scanner.nextLine().charAt(0);

                operations[i] = firstNumber + " " + secondNumber + " " + operator;
            }

            // Send all operations to the server at once
            for (String operation : operations) {
                out.println(operation);
            }

            // Wait for all results from the server
            for (int i = 0; i < numOperations; i++) {
                String result = in.readLine();
                System.out.println("Result for operation " + (i + 1) + ": " + result);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        scanner.close();
        System.out.println("MultiClient disconnected.");
    }
}