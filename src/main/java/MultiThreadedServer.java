import java.io.*;
import java.net.*;
import java.util.concurrent.CountDownLatch;

public class MultiThreadedServer {
    public static void main(String[] args) {
        int totalRequests = 10;
        // Use a latch to wait until all 10 requests are processed
        CountDownLatch latch = new CountDownLatch(totalRequests);
        try (ServerSocket serverSocket = new ServerSocket(5005)) {
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
