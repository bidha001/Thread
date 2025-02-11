import java.io.*;
import java.net.*;
import java.util.concurrent.CountDownLatch;

public class MultiThreadedServer {
    public static void main(String[] args) {
        int totalRequests = 10;
        // Use a latch to wait until all 10 requests are processed
        CountDownLatch latch = new CountDownLatch(totalRequests);
        try (ServerSocket serverSocket = new ServerSocket(5000)) {
            System.out.println("Multi-threaded server started...");
            long startTime = System.currentTimeMillis();
            // Accept 10 clients and start a separate thread for each
            for (int i = 0; i < totalRequests; i++) {
                Socket socket = serverSocket.accept();
                new Thread(new ClientHandler(socket, latch)).start();
            }
            // Wait until all threads are finished
            latch.await();
            long endTime = System.currentTimeMillis();
            System.out.println("Time taken for " + totalRequests + " requests (Multi-threaded): "
                    + (endTime - startTime)/1000.0 + " seconds");
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}

// Handles each client request in its own thread
class ClientHandler implements Runnable {
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
            String input = in.readLine(); // Format: "num1 num2 operator"
            String[] parts = input.split(" ");
            int num1 = Integer.parseInt(parts[0]);
            int num2 = Integer.parseInt(parts[1]);
            char operator = parts[2].charAt(0);
            int result = SingleThreadedServer.calculate(num1, num2, operator);
            out.println(result);
            System.out.println("Thread " + Thread.currentThread().getName() + " finished processing. Result: " + result);
        } catch(IOException e) {
            e.printStackTrace();
        } finally {
            latch.countDown(); // Mark this request as finished
            try {
                socket.close();
            } catch(IOException ex) {
                // Ignore error on closing
            }
        }
    }
}
