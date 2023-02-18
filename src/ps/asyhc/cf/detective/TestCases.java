package ps.asyhc.cf.detective;

import com.google.common.collect.*;

import java.util.*;
import java.util.concurrent.*;

public class TestCases {
    public static void main(String[] args) throws InterruptedException {
        new TestCases().pollingFromSdc();
    }

    public void pollingFromSdc() throws InterruptedException {
        ExecutorService pool = Executors.newSingleThreadExecutor();
        CompletableFuture<?> signal = new CompletableFuture<>();
        try {
            Set<String> target = Sets.newHashSet("1000");
            pool.submit(() -> {
                System.out.println("inside a thread.");
                signal.join();
                System.out.println("+inside a thread.");
            });
        } finally {
            signal.complete(null);
            pool.shutdown();
            pool.awaitTermination(1, TimeUnit.SECONDS);
//            sleep(2000);
        }

        System.out.println("finished.");
    }

    private static void sleep(int i) {
        try {
            Thread.sleep(i);
        } catch (InterruptedException e) {
            throw new RuntimeException(e); // TODO
        }
    }
}
