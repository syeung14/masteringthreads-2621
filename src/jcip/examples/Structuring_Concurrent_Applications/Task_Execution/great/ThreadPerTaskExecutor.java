package jcip.examples.Structuring_Concurrent_Applications.Task_Execution.great;

import java.util.concurrent.*;

/**
 * ThreadPerTaskExecutor
 * <p/>
 * Executor that starts a new thread for each task
 *
 * @author Brian Goetz and Tim Peierls
 */
public class ThreadPerTaskExecutor implements Executor {
    public void execute(Runnable r) {
        new Thread(r).start();
    };
}
