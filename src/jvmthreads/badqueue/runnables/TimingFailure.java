package jvmthreads.badqueue.runnables;

class Waiter implements Runnable {
    // volatile ensures "visibility" (but nothing else!)
    public /*volatile*/ boolean stop = false;

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName()
            + " started... waiting for stop flag");

        while (!stop)
            ;

        System.out.println(Thread.currentThread().getName()
            + " stop flag set, exiting");
    }
}

public class TimingFailure {
    public static void main(String[] args) throws Throwable {
        Waiter w = new Waiter();
        Thread wt = new Thread(w);
        wt.start();
        System.out.println(Thread.currentThread().getName()
            + " has started the waiter thread!");

        Thread.sleep(1_000);
        w.stop = true;
        System.out.println(Thread.currentThread().getName()
            + " has set the stop flag! (exiting)");
    }
}
