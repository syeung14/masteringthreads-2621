package ps.asyhc.cf.baeldung;

import com.google.common.util.concurrent.*;

import java.util.concurrent.*;

public class ListenableFutureCallbackExample {

    public static void main(String[] args) {
        ExecutorService es = Executors.newFixedThreadPool(1);
        ListeningExecutorService pool = MoreExecutors.listeningDecorator(es);
        ListenableFuture<String> listenableFuture = pool.submit(downloadFile());

        Futures.addCallback(listenableFuture, new FutureCallback<String>() {
            @Override
            public void onSuccess(String s) {
                System.out.println("success");
            }

            @Override
            public void onFailure(Throwable throwable) {
                System.out.println("onfailure");
            }
        }, es);

    }

    private static Callable<String> downloadFile() {
        return () -> {

            Thread.sleep(2000/0);
            return "pic.jpg";
        };
    }
}
