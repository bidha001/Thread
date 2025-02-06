import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SingleThread extends Thread{
    public static void main(String[] args) throws IOException {
        ServerSocket socket = new ServerSocket("local host",5005);
        System.out.println("Single Thread is started");
    }
        public void run(){
            for (int i = 1; i <= 5; i++){
                System.out.println(i);

                try{
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                }
            }

        }
    public static void main(String[] args) {
            SingleThread thread = new SingleThread();
            SingleThread thread1 = new SingleThread();

            thread.start();
            thread1.start();

        }
}