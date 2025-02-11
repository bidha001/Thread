import java.util.Scanner;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MultiClient {
    private static final int PORT = 5006;
    private static final int THREAD_COUNT = 5;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("How many operation do you want to send?");
        int requestCount = scanner.nextInt();
        scanner.nextLine();

        ExecutorService executor = Executors.newFixedThreadPool(THREAD_COUNT);

        for (int i = 0; i < requestCount; i++){
            System.out.print("Enter first number: ");
            int num1 = scanner.nextInt();

            System.out.print("Enter second number: ");
            int num2 = scanner.nextInt();

            System.out.print("Enter operation (A = addition, S = subtraction, M = Multiplication, D = division): ");
            char operator = scanner.next().charAt(0);
            scanner.nextInt();

            String request = num1 + " " + num2 + " " + operator;
        }

        executor.shutdown();
        scanner.close();
    }
}
