import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        String serverAddress = "localhost";
        int port = 5005;

        try (Socket socket = new Socket(serverAddress, port);
             BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
             Scanner scanner = new Scanner(System.in)) {

            System.out.println("Connected to server.");
            System.out.println("Enter operation (A for add, S for subtract, M for multiply, D for divide): ");
            String operation = scanner.next().toUpperCase();

            System.out.print("Enter first number: ");
            int num1 = scanner.nextInt();
            System.out.print("Enter second number: ");
            int num2 = scanner.nextInt();

            // Send request to server
            writer.println(operation + " " + num1 + " " + num2);

            // Receive and print response from server
            String response = reader.readLine();
            System.out.println("Server response: " + response);

        } catch (IOException e) {
            System.err.println("Error: Could not connect to server.");
            e.printStackTrace();
        }
    }
}
