package jvmthreads.badqueue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

class BadQueue<E> {
  private static final int SIZE = 10;
  private E[] data = (E[])new Object[SIZE];
  private int count = 0;
  private Object rendezvous = new Object();

  public void put(E e) throws InterruptedException {
    // optimizers are permitted to move code inside a nearby
    // synchronized block (it CANNOT move code OUT!)
    synchronized (this.rendezvous) {
      // what if the queue is full already??
      // many lines of transactional problem!!!
      while (count == SIZE) { // MUST have a loop here (always)
        // hang about??? MUST give the key back
        // AND THEN RECOVER THE KEY before continuing
        // CAREFUL!! NOT transactionally protected DURING the wait!!!
        // wait (and other blocking methods) test for interrupt
        // BEFORE releasing the lock..
        this.rendezvous.wait();
      }
      data[count++] = e;
      // notify wakes at most one other thread
      // NO guarantee of WHICH thread :)
//      this.rendezvous.notify();
      this.rendezvous.notifyAll();
      // what's needed is a mechanism that allows us to "direct"
      // a notification to a thread waiting for the right reason...
      // Look ReentrantLock... This can do that.
    }
  }

  public E take() throws InterruptedException {
    synchronized (this.rendezvous) {
      // what if there's nothing there!?
      // many lines of transactional problem
      while (count == 0) {
        // hang about
        this.rendezvous.wait();
      }
      E result = data[0];
      System.arraycopy(data, 1, data, 0, --count);
//      this.rendezvous.notify();
      // notifyAll is horribly not-scalable
      this.rendezvous.notifyAll();
      return result;
    }
  }
}

public class UseBadQueue {
  public static void main(String[] args) {
//    final BadQueue<int[]> q = new BadQueue<>();
    final BlockingQueue<int[]> q = new ArrayBlockingQueue<>(10);

    new Thread(()->{
      try {
        for (int i = 0; i < 10_000; i++) {
          int[] data = {0, i}; // transactionally "invalid"
          if (i < 100) {
            Thread.sleep(1);
          }
          data[0] = i; // phew, transactionally valid :)
          if (i == 5_000) data[0] = -1; // test the test!!!
          q.put(data); data = null;
        }
      } catch (InterruptedException ie) {
        System.out.println("Strange, something requested producer to shut-down");
      }
    }).start();

    new Thread(()->{
      try {
        for (int i = 0; i < 10_000; i++) {
          int[] data = q.take();
          if (i > 9_900) {
            Thread.sleep(1);
          }
          // validate the data is good
          if (i != data[0] || data[0] != data[1]) {
            System.out.println("**** ERROR at index " + i);
          }
        }
      } catch (InterruptedException ie) {
        System.out.println("Strange, something requested producer to shut-down");
      }
    }).start();

    System.out.println("Started...");
//    BadQueue<Integer> bqi = new BadQueue<>();
//    bqi.put(1);
//    bqi.put(2);
//    System.out.println(bqi.take());
//    bqi.put(3);
//    System.out.println(bqi.take());
//    System.out.println(bqi.take());

  }
}
