public class SingleThread extends Thread{
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
            MultiThread thread = new MultiThread();
            thread.start();

        }
}