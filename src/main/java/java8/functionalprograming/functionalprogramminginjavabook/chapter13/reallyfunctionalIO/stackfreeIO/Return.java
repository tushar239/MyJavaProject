package java8.functionalprograming.functionalprogramminginjavabook.chapter13.reallyfunctionalIO.stackfreeIO;

/**
 * @author Tushar Chokshi @ 11/29/16.
 */
public class Return<T> extends StackFreeIO<T> {

    public final T value;

    public Return(T value) {
        this.value = value;
    }

    @Override
    public boolean isReturn() {
        return true;
    }

    @Override
    public boolean isSuspend() {
        return false;
    }

}
