package ps.asyhc.cf;

import java.util.*;
import java.util.concurrent.*;
import java.util.function.*;
import java.util.logging.*;
import java.util.stream.*;

public class TriggeringTask {
    public static void main(String[] args) {
        new TriggeringTask().task1();
    }

    static Logger logger = Logger.getLogger(TriggeringTask.class.getName());

    void task1() {
        Supplier<List<Long>> supplyIds = () -> {
            sleep(200);
            return Arrays.asList(1l, 2l, 3l);
        };

        Function<List<Long>, List<User>> fetchUsers = ids -> {
            sleep(300);
            return ids.stream().map(User::new).collect(Collectors.toList());
        };

        Consumer<List<User>> displayer = users -> users.forEach(System.out::println);

        CompletableFuture<List<Long>> cf = CompletableFuture.supplyAsync(supplyIds);
        cf.thenApply(fetchUsers)
            .thenAccept(displayer);

        sleep(1000);
    }

    private void sleep(int i) {
        try {
            Thread.sleep(i);
        } catch (InterruptedException e) {
            throw new RuntimeException(e); // TODO
        }
    }

    void chainRunnable() {
        //non return
/*
        CompletableFuture<Void> cf = CompletableFuture.runAsync(() -> updateDB())
            .thenRun(() -> logger.info("update done!"));

*/
        CompletableFuture<Void> cf2 = CompletableFuture.runAsync(() -> updateDB())
            .thenAccept(s -> System.out.println(s));
    }

    void composeFuture() {
        Supplier<List<Long>> userIds = () -> remoteService();
        Function<List<Long>, CompletableFuture<List<User>>> userFromIds = ids -> fetchFromDB(ids);

        CompletableFuture<List<User>> cf = CompletableFuture.supplyAsync(userIds)
            .thenCompose(userFromIds);


    }

    private CompletableFuture<List<User>> fetchFromDB(List<Long> ids) {
        return null;
    }

    private List<Long> remoteService() {
        throw new UnsupportedOperationException(); // TODO
    }

    private void updateDB() {
        throw new UnsupportedOperationException(); // TODO
    }

}
