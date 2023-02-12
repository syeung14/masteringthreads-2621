package ps.asyhc.cf;

import java.util.concurrent.*;
import java.util.function.*;

public class Example {
    public static void main(String[] args) {

//        CompletableFuture.runAsync(() -> System.out.println("running a tihing"));

        new Example().chainFuture();
    }

    void chainFuture() {
        CompletableFuture<Void> cf = new CompletableFuture<>();

        Runnable task = () -> {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e); // TODO
            }
            cf.complete(null);
        };

        CompletableFuture.runAsync(task);
        Void nil = cf.join();
        System.out.println("we re done.");
    }

    void method1() {
        ExecutorService exec = Executors.newSingleThreadExecutor();

        Supplier<String> supplier = () -> {
            try {
                Thread.sleep(500);
            } catch (Exception e) {
                throw new RuntimeException(e); // TODO
            }
            return Thread.currentThread().getName();
        };
    }

    void onlyFuture() {
        Supplier<String> supplier = () -> {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e); // TODO
            }
            return Thread.currentThread().getName();
        };

        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(supplier);
        String res = completableFuture.join();

        completableFuture.obtrudeValue("too long");
        System.out.println("Result = " + res);
    }

    void runFuture() {
        Runnable task = () -> System.out.println("Hello world");
        ExecutorService service = Executors.newSingleThreadExecutor();

        Future<?> future = service.submit(task);

//        CompletableFuture<Void> completableFuture = CompletableFuture.runAsync(task, service);

        service.shutdown();
    }

    String readPage(String url) {
        return url;
    }

    void runFuture2() {
        Supplier<String> task = () -> readPage("Http://google.com");
        ExecutorService service = Executors.newSingleThreadExecutor();

        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(task, service);
    }
}
