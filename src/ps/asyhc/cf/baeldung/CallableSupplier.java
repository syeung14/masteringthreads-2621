package ps.asyhc.cf.baeldung;

import ps.asyhc.cf.*;

import java.time.*;
import java.util.concurrent.*;

public class CallableSupplier {
    public User execute(User user) {
        ExecutorService executorService = Executors.newCachedThreadPool();

        CompletableFuture<Integer> ageFut
            = CompletableFuture.supplyAsync(() -> Period.between(user.getBirthDate(), LocalDate.now()).getYears(), executorService).exceptionally((throwable -> null));

        CompletableFuture<Boolean> canDriveACarFut
            = ageFut.thenComposeAsync(age -> CompletableFuture.supplyAsync(() -> age > 18, executorService)).exceptionally((ex) -> false);

        user.setAge(ageFut.join());
        user.setCanDriveACar(canDriveACarFut.join());
        return user;
    }


    public User execute2(User user) {
        ExecutorService executorService = Executors.newCachedThreadPool();
        CompletableFuture<Integer> ageFut
            = CompletableFuture.supplyAsync(() -> Period.between(user.getBirthDate(), LocalDate.now()).getYears(), executorService);

        CompletableFuture<Boolean> canDriveACarFut
            = ageFut.thenComposeAsync(age -> CompletableFuture.supplyAsync(() -> age > 18, executorService)).exceptionally((ex) -> false);

        user.setAge(ageFut.join());
        user.setCanDriveACar(canDriveACarFut.join());
        return user;

    }

}
