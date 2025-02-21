import java.io.*;
import java.net.*;

public class ClientHandler implements Runnable {
    private Socket socket;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {


        try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

            System.out.println("Connected to client: " + socket.getInetAddress());

            String input;
            // Process each incoming operation until "exit" is received
            while ((input = in.readLine()) != null) {
                if (input.equalsIgnoreCase("exit")) {
                    System.out.println("Client disconnected.");
                    break;
                }
                String[] parts = input.split(" ");
                if (parts.length != 3) {
                    out.println("ERROR: Invalid request format.");
                    continue;
                }
                try {
                    int num1 = Integer.parseInt(parts[0]);
                    int num2 = Integer.parseInt(parts[1]);
                    char operator = parts[2].charAt(0);

                    long totalStartTime = System.currentTimeMillis();
                    int i=0;
                    // Calculate result without measuring individual operation time
                    while(i<100000) {
                        int result = calculate(num1, num2, operator);
                        i++;
                    }
                    long totalEndTime = System.currentTimeMillis();
                    double totalTimeSec = (totalEndTime - totalStartTime) / 1_000_000_000.0;
                    System.out.println("Total time taken for client " + socket.getInetAddress() + " operations: " + totalTimeSec + " seconds");
                    out.println();
                    System.out.println("Processed: " + num1 + " " + operator + " " + num2 + " = ");
                } catch (NumberFormatException e) {
                    out.println("ERROR: Invalid number format.");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException ignored) {}
        }

    }

    // Method for performing arithmetic operations
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
