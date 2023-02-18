package ps.asyhc.cf.jose.paumard.async;

import ps.asyhc.cf.*;

import java.util.*;
import java.util.concurrent.*;
import java.util.function.*;
import java.util.stream.*;

public class AsyncExampleMultiBranchEither {

	public static void main(String[] args) {

		ExecutorService executor1 = Executors.newSingleThreadExecutor();

		Supplier<List<Long>> supplyIDs = () -> {
			sleep(200);
			return Arrays.asList(1L, 2L, 3L);
		};


		Function<List<Long>, CompletableFuture<List<User>>> fetchUsers1 = ids -> {
			sleep(150);
			Supplier<List<User>> userSupplier =
					() -> ids.stream().map(User::new).collect(Collectors.toList());
			return CompletableFuture.supplyAsync(userSupplier);
		};

		Function<List<Long>, CompletableFuture<List<User>>> fetchUsers2 = ids -> {
			sleep(5000);
			Supplier<List<User>> userSupplier =
					() -> ids.stream().map(User::new).collect(Collectors.toList());
			return CompletableFuture.supplyAsync(userSupplier);
		};

		Consumer<List<User>> displayer = users -> users.forEach(System.out::println);

		CompletableFuture<List<Long>> completableFuture = CompletableFuture.supplyAsync(supplyIDs);

		CompletableFuture<List<User>> users1 = completableFuture.thenComposeAsync(fetchUsers1);
		CompletableFuture<List<User>> users2 = completableFuture.thenComposeAsync(fetchUsers2);

		users1.thenRun(() -> System.out.println("Users 1"));
		users2.thenRun(() -> System.out.println("Users 2"));

		users1.acceptEither(users2, displayer);

		sleep(6_000);
		executor1.shutdown();
	}

	private static void sleep(int timeout) {
		try {
			Thread.sleep(timeout);
		} catch (InterruptedException e) {
		}
	}
}
