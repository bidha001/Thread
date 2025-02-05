import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class SingleThread {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(5001);
        System.out.println("Single thread server is started!");

        while (true) {
            Socket clientSocket = serverSocket.accept();
            handleClient(clientSocket);
        }
    }

    private static void handleClient(Socket clientSocket) {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {

            String request = in.readLine();
            String[] parts = request.split(" ");
            int num1 = Integer.parseInt(parts[0]);
            int num2 = Integer.parseInt(parts[1]);
            String operator = parts[2];

            int result = performOperation(num1, num2, operator);
            out.println(result);

        } catch (IOException | NumberFormatException e) {
            System.err.println("Error handling client: " + e.getMessage());
        }
    }

    private static int performOperation(int num1, int num2, String operator) {
        return switch (operator) {
            case "A" -> num1 + num2;
            case "S" -> num1 - num2;
            case "M" -> num1 * num2;
            case "D" -> num2 != 0 ? num1 / num2 : 0;
            default -> 0;
        };
    }
}
