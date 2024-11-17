package ps.asyhc.cf.baeldung;

import java.util.concurrent.*;

public class CompletableFutureExceptionsHandlingUnitTest {

    void whenCompletableFutureIsScheduled_thenHandleStageIsAlwaysInvoked(int radius, long expected)
        throws ExecutionException, InterruptedException {

        long actual = CompletableFuture.supplyAsync(() -> {
            if (radius <= 0) {
                throw new IllegalArgumentException("radius is wrong");
            }
            return Math.round(Math.pow(radius, 2) * Math.PI);
        }).handle((res, err) -> {
            if (err == null) return res;
            else return -1L;
        }).get();
    }

    void whenCompletableFutureIsScheduled_thenExceptionallyExecutedOnlyOnFailure(int a, int b, int c, long expected)
        throws ExecutionException, InterruptedException {
        long actual = CompletableFuture.supplyAsync(() -> {
            if (a <= 0 || b <= 0 || c <= 0) {
                throw new IllegalArgumentException("params are bad");
            }
            return a * b * c;

        }).exceptionally((ex) -> -1).get();
    }



}
