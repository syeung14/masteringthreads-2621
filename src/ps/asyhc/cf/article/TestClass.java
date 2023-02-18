package ps.asyhc.cf.article;

import java.util.concurrent.*;
import java.util.function.*;

public class TestClass {
    static final ExecutorService executor = Executors.newSingleThreadExecutor();
    static final CompletableFuture<String> future = CompletableFuture.supplyAsync(new Supplier<String>() {
        @Override
        public String get() {
            return "hello world";
        }
    }, executor);

    static final CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> "hi, there", executor);

    public static void main(String[] args) {
        boolean res = new TestClass().f1.complete("39");
        System.out.println(res);

    }


    CompletableFuture<String> f1 = future2;
    CompletableFuture<Integer> f2 = f1.thenApply(Integer::parseInt);
}
