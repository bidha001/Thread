public class MultiThread extends Thread {
        @Override
        public void run(){
            for (int i = 1; i <= 10; i++){
                System.out.println(i);

                try{
                    Thread.sleep(500);
                }catch(InterruptedException e){
                }
            }
        }
    public static void main(String[] args) {
        for (int i = 0; i < 5; i++) {
            MultiThread thread = new MultiThread();
            thread.start();

        }
    }
}