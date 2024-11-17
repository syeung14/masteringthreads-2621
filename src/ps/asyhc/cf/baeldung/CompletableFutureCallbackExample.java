package ps.asyhc.cf.baeldung;

import java.util.concurrent.*;

public class CompletableFutureCallbackExample {
    public static void main(String[] args) {
        CompletableFuture<String> cf = new CompletableFuture<>();
        Runnable runnable = downloadFile(cf);

        cf.whenComplete((res, err) -> {
            if (err != null) {

                System.out.println("error occur");
            } else {
                System.out.println("delivered " + res);
            }
        });

        new Thread(runnable).start();
    }


    private static Runnable downloadFile(CompletableFuture<String> cf) {
        return () -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            cf.complete("pic.jpg");
        };
    }
}
