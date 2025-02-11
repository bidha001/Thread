import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MultiClient {
    private static final int PORT = 5006;
    private static final int THREAD_COUNT = 5;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("How many operations do you want to send?");
        int requestCount = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        ExecutorService executor = Executors.newFixedThreadPool(THREAD_COUNT);

        for (int i = 0; i < requestCount; i++) {
            System.out.print("Enter first number: ");
            int num1 = scanner.nextInt();

            System.out.print("Enter second number: ");
            int num2 = scanner.nextInt();

            System.out.print("Enter operation (A = addition, S = subtraction, M = Multiplication, D = division): ");
            char operator = scanner.next().charAt(0);
            scanner.nextLine(); // **FIXED: Changed from scanner.nextInt() to scanner.nextLine()**

            String request = num1 + " " + num2 + " " + operator;

            // **FIXED: Actually sending the request**
            executor.execute(() -> sendRequest(request));
        }

        executor.shutdown();
        scanner.close();
    }

    private static void sendRequest(String request) {
        try (Socket socket = new Socket("localhost", PORT);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

            out.println(request);
            String result = in.readLine();
            System.out.println("Request: " + request + " => Result: " + result);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
