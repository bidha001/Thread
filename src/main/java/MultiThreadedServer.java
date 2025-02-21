import java.io.*;
import java.net.*;
import java.util.concurrent.*;

public class MultiThreadedServer {
    private static final int PORT = 5008; // Port for multi-threaded clients
    private static final int THREAD_POOL_SIZE = 20; // Maximum concurrent clients

    public static void main(String[] args) {
        ExecutorService threadPool = Executors.newFixedThreadPool(THREAD_POOL_SIZE);

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Multi-threaded server started on port " + PORT + " (Listening for MultiClient)");

            while (true) { // Keep server running to accept multiple clients
                Socket socket = serverSocket.accept();
                Thread.sleep(1000);
                threadPool.execute(new ClientHandler(socket)); // Handle each client in a new thread
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
