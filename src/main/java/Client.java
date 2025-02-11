import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.util.concurrent.CountDownLatch;

public class Client {
    private static final int PORT = 5005; // Change to 5006 for Single-threaded Server

    public static void main(String[] args) {
        int totalRequests;
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the number of requests: ");
        totalRequests = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        CountDownLatch latch = new CountDownLatch(totalRequests);
        long startTime = System.currentTimeMillis();

        for (int i = 0; i < totalRequests; i++) {
            new Thread(() -> {
                try (Socket socket = new Socket("localhost", PORT);
                     BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                     PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                     Scanner inputScanner = new Scanner(System.in)) {

                    // Get user input for numbers and operation
                    System.out.print("Enter first number: ");
                    int num1 = inputScanner.nextInt();

                    System.out.print("Enter second number: ");
                    int num2 = inputScanner.nextInt();

                    System.out.print("Enter operation (A = addition, S = subtraction, M = multiplication, D = division): ");
                    char operator = inputScanner.next().charAt(0);

                    // Send request to the server
                    String request = num1 + " " + num2 + " " + operator;
                    out.println(request);

                    // Receive and print the result
                    String result = in.readLine();
                    System.out.println("Request: " + request + " => Result: " + result);
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    latch.countDown();
                }
            }).start();
        }

        // Wait until all clients finish
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long endTime = System.currentTimeMillis();
        System.out.println("Total time taken for " + totalRequests + " client requests: " + (endTime - startTime) / 1000.0 + " seconds");

        scanner.close();
    }
}
