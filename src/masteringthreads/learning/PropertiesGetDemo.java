package masteringthreads.learning;

import java.util.*;
import java.util.stream.*;

public class PropertiesGetDemo {
    public static void main(String[] args) {
        var i = 0;

        new Thread(() -> {

        });

        new PropertiesGetDemo().testReadProperties();
    }

    private void testReadProperties() {
        long time = System.nanoTime();
        try {
            Properties prop = System.getProperties();
            IntStream.range(0, 100_000_000)
                .parallel()
                .mapToObj(i -> prop.get("java.version"))
                .distinct()
                .forEach(System.out::println);
        } finally {
            time = System.nanoTime() - time;
            System.out.printf("time = %dms%n", time / 1_000_000);
        }
    }
}

