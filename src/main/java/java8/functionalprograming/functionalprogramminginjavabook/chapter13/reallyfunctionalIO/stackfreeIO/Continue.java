package java8.functionalprograming.functionalprogramminginjavabook.chapter13.reallyfunctionalIO.stackfreeIO;

import java8.functionalprograming.functionalprogramminginjavabook.chapter2.Function;

/**
 * @author Tushar Chokshi @ 11/29/16.
 */
public class Continue<T, U> extends StackFreeIO<T> {

    public final StackFreeIO<T> sub;
    public final Function<T, StackFreeIO<U>> f;

    protected Continue(StackFreeIO<T> sub, Function<T, StackFreeIO<U>> f) {
        this.sub = sub;
        this.f = f;
    }

    @Override
    public boolean isReturn() {
        return false;
    }

    @Override
    public boolean isSuspend() {
        return false;
    }
}
