import java.util.Scanner;

public class MultiClient {
    private static final int PORT = 5006;
    private static final int THREAD_COUNT = 5;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("How many operation do you want to send?");
        int requestCount = scanner.nextInt();
        scanner.nextLine();
    }
}
