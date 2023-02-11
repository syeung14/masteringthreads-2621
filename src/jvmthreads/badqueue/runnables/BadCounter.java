package jvmthreads.badqueue.runnables;

class MyCountingJob implements Runnable {
    public volatile long counter = 0;

    @Override
    public void run() {
        for (int i = 0; i < 1_000_000_000; i++) {
            counter++;
        }
    }
}

public class BadCounter {
    public static void main(String[] args) throws Throwable {
        MyCountingJob j = new MyCountingJob();
//    j.run();
//    j.run();
        Thread t1 = new Thread(j);
        t1.start();
        Thread t2 = new Thread(j);
        t2.start();
        t1.join();
        t2.join();
        System.out.println("Value of count is " + j.counter);
    }
}
