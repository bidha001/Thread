import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client {
    private static final int PORT = 5006; //

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            try (Socket socket = new Socket("localhost", PORT);
                 BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                 PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

                System.out.print("Enter first number (or 'exit' to quit): ");
                String input = scanner.nextLine();
                if (input.equalsIgnoreCase("exit")) break;

                System.out.print("Enter second number: ");
                int num2 = Integer.parseInt(scanner.nextLine());

                System.out.print("Enter operation (A = addition, S = subtraction, M = multiplication, D = division): ");
                char operator = scanner.nextLine().charAt(0);

                String request = input + " " + num2 + " " + operator;
                out.println(request);

                String result = in.readLine();
                System.out.println("Result: " + result);

            } catch (IOException | NumberFormatException e) {
                System.out.println("Invalid input. Please try again.");
            }
        }

        scanner.close();
    }
}
