package ps.asyhc.cf.jose.paumard.async;

import ps.asyhc.cf.*;

import java.util.*;
import java.util.concurrent.*;
import java.util.function.*;
import java.util.stream.*;

public class TriggerExample {

	public static void main(String[] args) {
//        new TriggerExample().method();
        new TriggerExample().originalMethod();
    }

    void originalMethod() {

		ExecutorService executor = Executors.newSingleThreadExecutor();

		Supplier<List<Long>> supplyIDs = () -> {
			sleep(200);
			return Arrays.asList(1L, 2L, 3L);
		};

		Function<List<Long>, List<User>> fetchUsers = ids -> {
			sleep(300);
			return ids.stream().map(User::new).collect(Collectors.toList());
		};

		Consumer<List<User>> displayer = users -> {
			System.out.println("In thread " + Thread.currentThread().getName());
			users.forEach(System.out::println);
		};

        //fake Cfuture?!
		CompletableFuture<Void> start = new CompletableFuture<>();

		CompletableFuture<List<Long>> supply = start.thenApply(nil -> supplyIDs.get());
		CompletableFuture<List<User>> fetch  = supply.thenApply(fetchUsers);
		CompletableFuture<Void> display      = fetch.thenAccept(displayer);

		start.completeAsync(() -> null, executor);
//        start.complete(null);

//		sleep(1_000);
		executor.shutdown();
	}

    void method() {
        Supplier<List<Long>> supplyIDs = () -> {
            sleep(200);
            return Arrays.asList(1L, 2L, 3L);
        };

        List<Long> res= supplyIDs.get();
        System.out.println(res);
    }

	private static void sleep(int timeout) {
		try {
			Thread.sleep(timeout);
		} catch (InterruptedException e) {
		}
	}
}
