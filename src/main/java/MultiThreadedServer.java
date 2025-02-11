import java.io.*;
import java.net.*;
import java.util.concurrent.*;

public class MultiThreadedServer {
    private static final int PORT = 5005;
    private static final int TOTAL_REQUESTS = 10;

    public static void main(String[] args) {
        CountDownLatch latch = new CountDownLatch(TOTAL_REQUESTS);
        ExecutorService threadPool = Executors.newFixedThreadPool(TOTAL_REQUESTS);

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Multi-threaded server started on port " + PORT);
            long startTime = System.currentTimeMillis();

            for (int i = 0; i < TOTAL_REQUESTS; i++) {
                Socket socket = serverSocket.accept();
                threadPool.execute(new ClientHandler(socket, latch));
            }

            latch.await(); // Wait until all threads finish processing
            threadPool.shutdown(); // Shutdown the thread pool
            long endTime = System.currentTimeMillis();
            System.out.println("Total processing time (Multi-threaded): " + (endTime - startTime) / 1000.0 + " seconds");
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
