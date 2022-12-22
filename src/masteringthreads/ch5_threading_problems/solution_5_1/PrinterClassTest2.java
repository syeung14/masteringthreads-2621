package masteringthreads.ch5_threading_problems.solution_5_1;

import masteringthreads.ch5_threading_problems.exercise_5_1.*;

import java.util.concurrent.*;

public class PrinterClassTest2 {
    public static void main(String[] args) throws InterruptedException {
        PrinterClass pc = new PrinterClass();
        ExecutorService pool = Executors.newFixedThreadPool(2);

        pool.submit(() -> {
            pc.print("hello world");
        });
        pool.submit(() -> {
            pc.setPrintingEnabled(false);
        });
        pool.shutdown();
        if (!pool.awaitTermination(500, TimeUnit.MILLISECONDS)) {
            System.out.println("Suspected Deadlock");;
        }
    }
}
