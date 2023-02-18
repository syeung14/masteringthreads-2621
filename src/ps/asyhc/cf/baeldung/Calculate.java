package ps.asyhc.cf.baeldung;

import org.junit.*;

import java.util.concurrent.*;
import java.util.stream.*;

import static org.junit.Assert.*;

/**
 * https://www.baeldung.com/java-completablefuture
 */
public class Calculate {

    public Future<String> calculateAsync() throws InterruptedException {
        CompletableFuture<String> completableFuture;// = CompletableFuture.completedFuture("hello!");
        completableFuture = new CompletableFuture<>();

        Executors.newCachedThreadPool().submit(() -> {
            Thread.sleep(500);
            completableFuture.obtrudeValue("hello!");
            return null;
        });
        return completableFuture;
    }

    @Test
    public void testFuture() {
        Future<String> compleFu = null;
        try {
            compleFu = calculateAsync();
            String res = compleFu.get();
            assertEquals("hello!", res);
        } catch (InterruptedException e) {
            throw new RuntimeException(e); // TODO
        } catch (ExecutionException e) {
            throw new RuntimeException(e); // TODO
        }
    }

    @Test
    public void testSupplyAsync() throws ExecutionException, InterruptedException {
        CompletableFuture<String> future
            = CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread().getName());
            return "Hello";
        });
        assertEquals("Hello", future.get());
    }

    @Test
    public void testSupplyThenApply() throws ExecutionException, InterruptedException {
        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> "Hello");
        CompletableFuture<String> future = completableFuture.thenApply(x -> x + " World");

        assertEquals("Hello World", future.get());
    }

    @Test
    public void testSupplyThenNoReturn() throws ExecutionException, InterruptedException {
        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> "Hello");
//        CompletableFuture<Void> future = completableFuture.thenAccept(s -> System.out.println("Computation returned: " + s));
//        assertNull(future.get());

        CompletableFuture<Void> future = completableFuture.thenRun(() -> System.out.println("all finished."));
        assertNull(future.get());
    }

    /**
     * we use the thenCompose method to chain two Futures sequentially.
     * Notice that this method takes a function that returns a CompletableFuture
     * instance. The argument of this function is the result of the previous
     * computation step. This allows us to use this value inside the next
     * CompletableFuture‘s lambda:
     * <p>
     * The `thenCompose` method, together with `thenApply`, implements the basic
     * building blocks of the monadic pattern. They closely relate to the map
     * and flatMap methods of Stream and Optional classes,
     */
    @Test
    public void testComposeFutures() throws ExecutionException, InterruptedException {
        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> "Hello")
            .thenCompose(s -> CompletableFuture.supplyAsync(() -> s + " World"));
        assertEquals("Hello World", completableFuture.get());
    }

    /**
     * If we want to execute two independent Futures and do something with
     * their results, we can use the thenCombine method that accepts a Future
     * and a Function with two arguments to process both results:
     */
    @Test
    public void testCombineFutures() throws ExecutionException, InterruptedException {
        CompletableFuture<String> completableFuture =
            CompletableFuture.supplyAsync(() -> "Hello")
                .thenCombine(CompletableFuture.supplyAsync(() -> " World"), (a, b) -> a + b);
        assertEquals("Hello World", completableFuture.get());

    }

    @Test
    public void testAcceptBoth() throws ExecutionException, InterruptedException {
        CompletableFuture<Void> future =
            CompletableFuture.supplyAsync(() -> "Hello")
                .thenAcceptBoth(CompletableFuture.supplyAsync(() -> " World"), (a, b) -> System.out.println(a + b));

        assertNull(future.get());
    }

    /**
     * thenApply()
     * thenCompose()
     */

    @Test
    public void testWaitForAll() throws ExecutionException, InterruptedException {
        CompletableFuture<String> future1
            = CompletableFuture.supplyAsync(() -> "Hello");
        CompletableFuture<String> future2
            = CompletableFuture.supplyAsync(() -> "Beautiful");
        CompletableFuture<String> future3
            = CompletableFuture.supplyAsync(() -> "World");

//        CompletableFuture<Void> combinedFuture = CompletableFuture.allOf(future1, future2, future3);
//        combinedFuture.get();

//        CompletableFuture.allOf(future1, future2, future3).join();
        CompletableFuture.allOf(future1, future2, future3).get();

        assertTrue(future1.isDone());
        assertTrue(future2.isDone());
        assertTrue(future3.isDone());
    }

    @Test
    public void testWaitForAll2() throws ExecutionException, InterruptedException {
        CompletableFuture<String> future1
            = CompletableFuture.supplyAsync(() -> "Hello");
        CompletableFuture<String> future2
            = CompletableFuture.supplyAsync(() -> "Beautiful");
        CompletableFuture<String> future3
            = CompletableFuture.supplyAsync(() -> "World");

        String combined = Stream.of(future1, future2, future3)
            .map(CompletableFuture::join)
            .collect(Collectors.joining(" "));

        assertEquals("Hello Beautiful World", combined);
    }

    /**
     * Method 2
     * CompletableFuture<String> completableFuture = new CompletableFuture<>();
     *
     * // ...
     *
     * completableFuture.completeExceptionally(
     *   new RuntimeException("Calculation failed!"));
     *
     * // ...
     *
     * completableFuture.get(); // ExecutionException
     */
    @Test
    public void testWithException() throws ExecutionException, InterruptedException {
        String name = null;
        CompletableFuture<String> completableFuture
            = CompletableFuture.supplyAsync(() -> {
            if (name == null) {
                throw new RuntimeException("Computation error!");
            }
            return "Hello, " + name;
        }).handle((s, t) -> s != null ? s : "Hello, Stranger!");

        assertEquals("Hello, Stranger!", completableFuture.get());
    }

    @Test
    public void testWithAsyncMethod() throws ExecutionException, InterruptedException {
        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> "Hello");

        CompletableFuture<String> future = completableFuture.thenApplyAsync(s -> s + " World");

        assertEquals("Hello World", future.get());
    }
}
