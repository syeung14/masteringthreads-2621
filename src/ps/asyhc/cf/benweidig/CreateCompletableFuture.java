package ps.asyhc.cf.benweidig;

import java.util.concurrent.*;
import java.util.function.*;

public class CreateCompletableFuture {
    void create() {
        var exectorSerivce = ForkJoinPool.commonPool();


        CompletableFuture<Void> cf =
            CompletableFuture.runAsync(() -> System.out.println("not returning a value"));


        CompletableFuture<Integer> future1 = CompletableFuture.supplyAsync(() -> 42);
        CompletableFuture<Integer> future2 = CompletableFuture.supplyAsync(() -> 23);

        BiFunction<Integer, Integer, CompletableFuture<Integer>> task =
            (lhs, rhs) -> CompletableFuture.supplyAsync(() -> lhs + rhs);

        CompletableFuture<Integer> combined = future1.thenCombine(future2, task)
            .thenCompose(Function.identity());


        CompletableFuture<String> notFailed =
            CompletableFuture.supplyAsync(() -> "Success!");

        CompletableFuture<String> failed =
            CompletableFuture.supplyAsync(() -> {
                throw new RuntimeException();
            });

// NO OUTPUT BECAUSE THE PREVIOUS STAGE FAILED
        var rejected = failed.acceptEither(notFailed, System.out::println);

// OUTPUT BECAUSE THE PREVIOUS STAGE COMPLETED NORMALLY
        var resolved = notFailed.acceptEither(failed, System.out::println);
// => Success!
    }

}
