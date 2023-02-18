package ps.asyhc.cf.jose.paumard.async;

import ps.asyhc.cf.*;

import java.util.*;
import java.util.concurrent.*;
import java.util.function.*;

public class MultiThreadingPattern {
    public static void main(String[] args) throws Exception {
        new MultiThreadingPattern().runnableMethod4();
    }

    void runnableMethod() {
        Runnable task = () -> System.out.println("hi");

        ExecutorService service = Executors.newSingleThreadExecutor();

        //classical executor pattern
        Future<?> future = service.submit(task);

        //completable Future pattern
        CompletableFuture.runAsync(task);
    }

    void testSupplier() {
        Supplier<String> task = () -> readPage("http");
        ExecutorService service = Executors.newSingleThreadExecutor();

        CompletableFuture<String> completableFuture =
            CompletableFuture.supplyAsync(task); //static method
    }

    void runnableMethod2() {
        Runnable task = () -> System.out.println("hi");

        ExecutorService service = Executors.newSingleThreadExecutor();

        //classical executor pattern
        Future<?> future = service.submit(task);

        CompletableFuture<Void> completableFuture =
            CompletableFuture.runAsync(task, service);
    }

    void runnableMethod3() {
        ExecutorService service = Executors.newSingleThreadExecutor();
        Future<?> future = service.submit(() -> System.out.println("hello world."));

        CompletableFuture<?> completableFuture =
            CompletableFuture.runAsync(() -> System.out.println("hello world"), service);

        CompletableFuture<String> completableFuture2 =
            CompletableFuture.supplyAsync(() -> readPage("http:"), service);
    }

    /**
     * https://stackoverflow.com/questions/45490316/completablefuturet-class-join-vs-get
     * <p>
     * get() vs join()
     */
    void runnableMethod4() throws InterruptedException, ExecutionException {
        Supplier<String> supplier = () -> {
            return Thread.currentThread().getName();
        };

//        CompletableFuture.supplyAsync(supplier);
//        Thread.sleep(200);

        CompletableFuture<String> comFu = CompletableFuture.supplyAsync(supplier);
        System.out.println(comFu.join());

//        CompletableFuture<List<String>> cf = CompletableFuture
//            .supplyAsync(this::process)
//            .exceptionally(this::getFallbackListOfStrings) // Here you can catch e.g. {@code join}'s CompletionException
//            .thenAccept(this::processFurther);
    }

    private List<String> processFurther(List<String> lst) {
        throw new UnsupportedOperationException(); // TODO
    }

    private List<String> getFallbackListOfStrings(Throwable throwable) {
        throw new UnsupportedOperationException(); // TODO
    }

    private List<String> process() {
        return Collections.emptyList();
    }

    private String readPage(String http) {
        return "";
    }

    /**
     *
     */
    void testChain() {
        List<String> lst = Collections.emptyList();
        CompletableFuture<List<Long>> cf1 =
            CompletableFuture.supplyAsync(() -> List.of(1l, 2l, 3l))
                .thenApply(list -> readUser(list));
    }

    void fetchIds() {
        Supplier<List<Long>> userIdsSupplier = () -> remoteService();
        Function<List<Long>, CompletableFuture<List<User>>> userFromIds =
            ids -> (CompletableFuture<List<User>>) fetchFromDB(ids);

        CompletableFuture<List<User>> cf =
            CompletableFuture.supplyAsync(userIdsSupplier)
                .thenCompose(userFromIds);
    }

    private Function<List<Long>, CompletableFuture<List<User>>> fetchFromDB(List<Long> ids) {
        throw new UnsupportedOperationException(); // TODO
    }

    private List<Long> remoteService() {
        throw new UnsupportedOperationException(); // TODO
    }

    private List<Long> readUser(List<Long> list) {
        throw new UnsupportedOperationException(); // TODO
    }

    void testChain2() {

        CompletableFuture<List<Long>> cf1 = CompletableFuture.supplyAsync(() -> List.of(1l, 2l, 3l));
        cf1.thenApply(list -> readUser(list));
    }
}
