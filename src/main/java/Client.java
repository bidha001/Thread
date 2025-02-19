import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client {
    private static final int PORT = 5004; // Connects to the single-threaded server

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try (Socket socket = new Socket("localhost", PORT);
             BufferedReader in = new BufferedReader(
                     new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

            // Ask for the number of operations
            System.out.print("Enter number of operations: ");
            int numOperations = Integer.parseInt(scanner.nextLine());
            // Send the count to the server
            out.println(numOperations);

            // For each operation, collect the input, send the request, and wait for the response
            for (int i = 0; i < numOperations; i++) {
                System.out.println("\nOperation " + (i + 1) + ":");

                System.out.print("Enter first number: ");
                String firstNumber = scanner.nextLine();

                System.out.print("Enter second number: ");
                String secondNumber = scanner.nextLine();

                System.out.print("Enter operation (A = addition, S = subtraction, M = multiplication, D = division): ");
                char operator = scanner.nextLine().charAt(0);

                String request = firstNumber + " " + secondNumber + " " + operator;
                out.println(request);

                // Read and display the response immediately
                String result = in.readLine();
                System.out.println("Result: " + result);
            }
        } catch (IOException | NumberFormatException e) {
            System.out.println("Invalid input or connection error. Please try again.");
            e.printStackTrace();
        }
        scanner.close();
    }
}
