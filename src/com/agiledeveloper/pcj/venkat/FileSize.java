package com.agiledeveloper.pcj.venkat;

import java.io.*;
import java.util.*;
import java.util.concurrent.*;

public class FileSize {
    private final static ForkJoinPool forkJoinPool = new ForkJoinPool();

    private static class FileSizeFinder extends RecursiveTask<Long> {
        final File file;

        private FileSizeFinder(File file) {
            this.file = file;
        }

        @Override
        protected Long compute() {
            long size = 0;
            if (file.isFile()) {
                size = file.length();
            } else {
                final File[] children = file.listFiles();
                if (children != null) {
                    List<ForkJoinTask<Long>> tasks = new ArrayList<>();
                    for (final File child : children) {
                        if (child.isFile()) {
                            size += child.length();
                        } else {
                            tasks.add(new FileSizeFinder(child));
                        }
                    }

                    for (final ForkJoinTask<Long> task : invokeAll(tasks)) {
                        size += task.join();
                    }
                } //fi
            }
            return size;
        }
    }

    public static void main(String[] args) {
        long start = System.nanoTime();
        long total = forkJoinPool.invoke(new FileSizeFinder(new File(args[0])));
        long end = System.nanoTime();
        System.out.println("Total size: " + total);
        System.out.println("Time taken: " + (end - start) / 1.0e9);
    }
}
