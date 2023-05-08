package jcip.examples.Structuring_Concurrent_Applications.Task_Execution.bad;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.*;

/**
 * SingleThreadWebServer
 * <p/>
 * Sequential web server
 *
 * @author Brian Goetz and Tim Peierls
 */

public class SingleThreadWebServer {
    public static void main(String[] args) throws IOException {
        ServerSocket socket = new ServerSocket(80);
        while (true) {
            Socket connection = socket.accept();
            handleRequest(connection);
        }
    }

    private static void handleRequest(Socket connection) {
        // request-handling logic here
    }


    static void threadVersion() throws IOException {
        ServerSocket ss = new ServerSocket(80);
        while (true) {
            final Socket conn = ss.accept();
            new Thread(() -> {
                handleRequest(conn);
            });
        }
    }

    static final int NTHREADS = 100;
    static final Executor exec = Executors.newFixedThreadPool(NTHREADS);

    static void testExecVersion() throws IOException {
        ServerSocket ss = new ServerSocket(80);
        while(true) {
            final Socket conn = ss.accept();
            exec.execute(() -> {
                handleRequest(conn);
            });
        }
    }
}
