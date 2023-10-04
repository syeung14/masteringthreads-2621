package com.test;

import java.util.concurrent.*;

public class TestOnly {

    public static void main(String[] args) {
        final int threadCount = 1;
        final ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<>(threadCount);

        try {
            queue.put("10000");
            System.out.println( queue.take());
        } catch (InterruptedException e) {
            throw new RuntimeException(e); // TODO
        }

        System.out.println("determinated.");
    }

    static class Producer {

    }
}
