package jcip.examples.Structuring_Concurrent_Applications.Task_Execution.great;

import java.util.concurrent.*;

public class QueueingFuture<V> extends FutureTask<V> {
    public QueueingFuture(Callable<V> callable) {
        super(callable);
    }
}
