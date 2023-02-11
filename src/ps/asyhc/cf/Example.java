package ps.asyhc.cf;

import java.util.concurrent.*;
import java.util.function.*;

public class Example {
    public static void main(String[] args) {
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
}
