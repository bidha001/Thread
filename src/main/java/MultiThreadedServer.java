import java.io.*;
import java.net.*;
import java.util.concurrent.*;

public class MultiThreadedServer {
    private static final int PORT = 5006; // Handles MultiClient
    private static final int THREAD_POOL_SIZE = 10; // Max concurrent clients

    public static void main(String[] args) {
        ExecutorService threadPool = Executors.newFixedThreadPool(THREAD_POOL_SIZE);

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Multi-threaded server started on port " + PORT + " (Listening for MultiClient)");

            while (true) { // Keeps server running for multiple clients
                Socket socket = serverSocket.accept();
                threadPool.execute(new ClientHandler(socket)); // Handle each client in a new thread
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
