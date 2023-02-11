package jcip.examples.Fundamentals.Composing_Objects.great;

import java.util.*;

import net.jcip.annotations.*;

/**
 * BetterVector
 * <p/>
 * Extending Vector to have a put-if-absent method
 *
 * @author Brian Goetz and Tim Peierls
 */
@ThreadSafe
public class BetterVector <E> extends Vector<E> {
    // When extending a serializable class, you should redefine serialVersionUID
    static final long serialVersionUID = -3963416950630760754L;

    //Vector是线程安全的，继承Vector后，添加的synchronized方法也是线程安全的
    public synchronized boolean putIfAbsent(E x) {
        boolean absent = !contains(x);
        if (absent)
            add(x);
        return absent;
    }
}
