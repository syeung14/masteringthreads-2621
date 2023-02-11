package jvmthreads.badqueue.runnables;

class MyJob implements Runnable {
  int i = 0;
  @Override
  public void run() {
    System.out.println(Thread.currentThread().getName() + " worker starting");
    for (; i < 10; i++) {
      System.out.println(Thread.currentThread().getName()
        + " i is " + i);
    }
    System.out.println(Thread.currentThread().getName() + " worker completed");
  }
}

public class FirstRunnable {
  public static void main(String[] args) {
    Runnable r = new MyJob();
    Thread t = new Thread(r);
    Thread t2 = new Thread(r);

    System.out.println(Thread.currentThread().getName() +
        " about to start worker job");
//    t.run(); // oops!! this causes simple method invocation (not threading)
    t.start();
    t2.start();



//    try {
//      t.join();
//      t2.join();
//    } catch (InterruptedException e) {
//      throw new RuntimeException(e); // TODO
//    }

    System.out.println(Thread.currentThread().getName() + " about to exit");
  }
}
