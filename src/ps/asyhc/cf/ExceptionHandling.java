package ps.asyhc.cf;

import java.util.*;
import java.util.concurrent.*;
import java.util.function.*;
import java.util.stream.*;

public class ExceptionHandling {
    public static void main(String[] args) {
        Supplier<List<Long>> supplyIDs = () -> {
            sleep(200);
            throw new IllegalStateException("No data");
            // return Arrays.asList(1L, 2L, 3L);
        };

        Function<List<Long>, List<User>> fetchUsers = ids -> {
            sleep(300);
            return ids.stream().map(User::new).collect(Collectors.toList());
        };

        Consumer<List<User>> displayer = users -> users.forEach(System.out::println);

        CompletableFuture<List<Long>> supply = CompletableFuture.supplyAsync(supplyIDs);

        CompletableFuture<List<Long>> exception =
            supply.handle(
                (ids, e) -> {
                    if (e != null) {
                        System.out.println(e.getMessage());
                        e.printStackTrace();
                        return List.of();
                    } else {
                        return ids;
                    }
                });

        CompletableFuture<List<Long>> exception2 =
            supply.whenComplete((ids, e) -> {
                if (e != null) {
                    System.out.println(e.getMessage());
                    e.printStackTrace();
                }
            });

        CompletableFuture<List<User>> fetch = exception.thenApply(fetchUsers);
        CompletableFuture<Void> display = fetch.thenAccept(displayer);

        sleep(1_000);
        System.out.println("Supply  : done=" + supply.isDone() +
            " exception=" + supply.isCompletedExceptionally());

        System.out.println("Fetch   : done=" + fetch.isDone() +
            " exception=" + fetch.isCompletedExceptionally());

        System.out.println("Display : done=" + display.isDone() +
            " exception=" + display.isCompletedExceptionally());
    }

    private static void sleep(int timeout) {
        try {
            Thread.sleep(timeout);
        } catch (InterruptedException e) {
        }
    }
}
