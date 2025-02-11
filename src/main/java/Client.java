import java.io.*;
import java.net.*;
import java.util.concurrent.CountDownLatch;

public class Client {
    public static void main(String[] args) {
        int totalRequests = 10;
        // Operators: A = addition, S = subtraction, M = multiplication, D = division, R = modulus.
        String[] operators = {"A", "S", "M", "D", "R"};
        CountDownLatch latch = new CountDownLatch(totalRequests);
        long startTime = System.currentTimeMillis();

        // Start 10 client threads simultaneously
        for (int i = 0; i < totalRequests; i++) {
            final int index = i;
            new Thread(() -> {
                try (Socket socket = new Socket("localhost", 5002);
                     BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                     PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

                    // Select operation in a cycle to get different operations for each request
                    String op = operators[index % operators.length];
                    // Vary numbers slightly for different results: e.g., num1 = 10+index, num2 = 5+index
                    int num1 = 10 + index;
                    int num2 = 5 + index;
                    String request = num1 + " " + num2 + " " + op;
                    out.println(request);
                    String result = in.readLine();
                    System.out.println("Request: " + request + " => Result: " + result);
                } catch(IOException e) {
                    e.printStackTrace();
                } finally {
                    latch.countDown();
                }
            }).start();
        }

        // Wait until all client threads finish
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long endTime = System.currentTimeMillis();
        System.out.println("Total time taken for " + totalRequests + " client requests: "
                + (endTime - startTime)/1000.0 + " seconds");
    }
}
