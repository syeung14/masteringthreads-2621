package ps.asyhc.cf.baeldung;

import java.util.concurrent.*;

public class CarDriverValidatorCallable implements Callable<Boolean> {
    private final Integer age;

    public CarDriverValidatorCallable(Integer age) {
        this.age = age;
    }

    @Override
    public Boolean call() throws Exception {
        return age > 18;
    }
}
