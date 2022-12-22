package masteringthreads.learning;

import java.lang.management.*;
import java.util.stream.*;

public class Puzzle {
    private synchronized static double random() {
        return Math.random();
    }

    public static void main(String[] args) {
        ThreadMXBean tmb = ManagementFactory.getThreadMXBean();
        tmb.setThreadContentionMonitoringEnabled(true);
        long time = System.nanoTime();
        try {
            double sum = IntStream.range(1, 50_000_000)
                .parallel()
                .mapToDouble(i -> random())
                .sum();

            System.out.println("sum = " + sum);
        } finally {
            time = System.nanoTime() - time;
            System.out.printf("time = %dms%n", (time / 1_000_000));
        }
        IntStream.range(0, Runtime.getRuntime().availableProcessors())
            .parallel()
            .mapToObj(i -> Thread.currentThread())
            .forEach(thread -> {
                ThreadInfo info = tmb.getThreadInfo(thread.getId());
                System.out.printf("%s blocked_count=%,d blocked_time=%dms%n",
                    thread.getName(),
                    info.getBlockedCount(),
                    info.getBlockedTime());
            });
    }
}
