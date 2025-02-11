import java.io.*;
import java.net.*;

 class SingleThreadedServer {
    public static void main(String[] args) {
        int totalRequests = 10;
        try (ServerSocket serverSocket = new ServerSocket(5000)) {
            System.out.println("Single-threaded server started...");
            long startTime = System.currentTimeMillis();
            // Handle 10 requests sequentially
            for (int i = 0; i < totalRequests; i++) {
                Socket socket = serverSocket.accept();
                handleClient(socket);
            }
            long endTime = System.currentTimeMillis();
            System.out.println("Time taken for " + totalRequests + " requests (Single-threaded): "
                    + (endTime - startTime)/1000.0 + " seconds");
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    // Handles a single client request
    public static void handleClient(Socket socket) {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {
            String input = in.readLine(); // Format: "num1 num2 operator"
            String[] parts = input.split(" ");
            int num1 = Integer.parseInt(parts[0]);
            int num2 = Integer.parseInt(parts[1]);
            char operator = parts[2].charAt(0);
            int result = calculate(num1, num2, operator);
            out.println(result);
        } catch(IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch(IOException ex) {
                // Ignore error on closing
            }
        }
    }

    // Performs calculation based on the operator
    public static int calculate(int num1, int num2, char operator) {
        return switch(operator) {
            case 'A' -> num1 + num2;
            case 'S' -> num1 - num2;
            case 'M' -> num1 * num2;
            case 'D' -> (num2 != 0 ? num1 / num2 : 0);
            case 'R' -> (num2 != 0 ? num1 % num2 : 0); // Modulus
            default -> 0;
        };
    }
}