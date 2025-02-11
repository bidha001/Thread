import java.io.*;
import java.net.*;
import java.util.concurrent.CountDownLatch;

public class ClientHandler implements Runnable {
    private Socket socket;
    private CountDownLatch latch;

    public ClientHandler(Socket socket, CountDownLatch latch) {
        this.socket = socket;
        this.latch = latch;
    }

    @Override
    public void run() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

            System.out.println("Thread " + Thread.currentThread().getName() + " started processing request.");
            String input = in.readLine();

            if (input == null) {
                System.out.println("Invalid request received.");
                return;
            }

            String[] parts = input.split(" ");
            int num1 = Integer.parseInt(parts[0]);
            int num2 = Integer.parseInt(parts[1]);
            char operator = parts[2].charAt(0);

            int result = calculate(num1, num2, operator); // Now calling a local method
            out.println(result);

            System.out.println("Thread " + Thread.currentThread().getName() + " finished processing. Result: " + result);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            latch.countDown();
            try {
                socket.close();
            } catch (IOException ignored) {}
        }
    }

    // Moved calculate() method inside ClientHandler
    private int calculate(int num1, int num2, char operator) {
        return switch (operator) {
            case 'A' -> num1 + num2;
            case 'S' -> num1 - num2;
            case 'M' -> num1 * num2;
            case 'D' -> (num2 != 0 ? num1 / num2 : 0);
            default -> 0;
        };
    }
}
