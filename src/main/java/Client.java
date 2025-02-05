import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
    public static void main(String[] args) {
        String[] operations = {"A", "S", "M", "D"};
        int[][] numbers = {
                {50, 20},
                {30, 10},
                {15, 3},
                {100, 25}
        };

        for (int i = 0; i < numbers.length; i++) {
            final int clientId = i;
            final String request = numbers[i][0] + " " + numbers[i][1] + " " + operations[i % operations.length];
            new Thread(() -> sendRequest(clientId, request)).start();
        }
    }

    private static void sendRequest(int clientId, String request) {
        try (Socket socket = new Socket("localhost", 5000);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

            // Send request to the server
            out.println(request);

            // Receive and print response from the server
            String response = in.readLine();
            System.out.println("Client " + clientId + " received: " + response);

        } catch (IOException e) {
            System.err.println("Client error: " + e.getMessage());
        }
    }
}
