package ps.asyhc.cf.detective;

import com.google.common.collect.*;

import java.util.*;
import java.util.concurrent.*;

public class TestExecutor {
    public static void main(String[] args) {
        new TestExecutor().method();
    }

    void method() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Future<String> fus = executorService.submit(() -> "hello");
        Future<Integer> fu = executorService.submit(TestExecutor::getSalary);

        try {
            System.out.println(fus.get());
            System.out.println(fu.get());
        } catch (InterruptedException e) {
            throw new RuntimeException(e); // TODO
        } catch (ExecutionException e) {
            throw new RuntimeException(e); // TODO
        }
    }

    void testList() {
        List<Integer> lst = ImmutableList.of(1);

    }

    void useForkJoinPool() {
        int numReaders = 2;
        int numWriters = 1;
        ForkJoinPool pool = new ForkJoinPool(numReaders + numWriters);
//        ForkJoinWorkerThread t = new ForkJoinWorkerThread(pool);


        ForkJoinPool comPool = ForkJoinPool.commonPool();
    }

    static int getSalary() {
        return 10_000;
    }
}
