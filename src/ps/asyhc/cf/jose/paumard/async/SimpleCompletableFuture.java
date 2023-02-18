package ps.asyhc.cf.jose.paumard.async;

import java.util.concurrent.CompletableFuture;

public class SimpleCompletableFuture {

    public static void main(String[] args) {
//        new SimpleCompletableFuture().myTest();
        new SimpleCompletableFuture().originalMethod();
    }

    void myTest() {
        CompletableFuture<Void> cf = new CompletableFuture<>();

        Void nil = cf.join();
    }

    void originalMethod() {

        CompletableFuture<Void> cf = new CompletableFuture<>();

        Runnable task = () -> {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
            }
            cf.complete(null);
        };
        CompletableFuture.runAsync(task);

        Void nil = cf.join();
        System.out.println("We are done");
    }
}
