package java8.functionalprograming.functionalprogramminginjavabook.chapter13.reallyfunctionalIO.stackfreeIO;

import java.util.function.Supplier;

/**
 * @author Tushar Chokshi @ 11/29/16.
 */
public class Suspend<T> extends StackFreeIO<T> {

    public final Supplier<T> resume;

    protected Suspend(Supplier<T> resume) {
        this.resume = resume;
    }

    @Override
    public boolean isReturn() {
        return false;
    }

    @Override
    public boolean isSuspend() {
        return true;
    }
}
