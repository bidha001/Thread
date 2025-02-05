import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            int finalI = i;
            new Thread(() -> sendRequest(finalI)).start();
        }
    }

    private static void sendRequest(int clientId) {
        try (Socket socket = new Socket("localhost", 5000);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

            String request = "50 20 A"; // Example request
            out.println(request);
            String response = in.readLine();

            System.out.println("Client " + clientId + " received: " + response);

        } catch (IOException e) {
            System.err.println("Client error: " + e.getMessage());
        }
    }
}
