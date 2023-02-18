package ps.asyhc.cf.baeldung;

import java.time.*;
import java.util.concurrent.*;

/**
 * https://www.baeldung.com/java-callable-vs-supplier
 */
public class AgeCalculatorCallable implements Callable<Integer> {
    private final LocalDate brithDate;

    public AgeCalculatorCallable(LocalDate brithDate) {
        this.brithDate = brithDate;
    }

    @Override
    public Integer call() throws Exception {
        throw new UnsupportedOperationException(); // TODO
    }
}
