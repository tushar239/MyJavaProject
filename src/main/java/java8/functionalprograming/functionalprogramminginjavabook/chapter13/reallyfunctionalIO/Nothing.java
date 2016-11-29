package java8.functionalprograming.functionalprogramminginjavabook.chapter13.reallyfunctionalIO;

/**
 * @author Tushar Chokshi @ 8/28/16.
 */
public class Nothing<A> implements IO<A> {
    public static Nothing instance = new Nothing();

    @Override
    public A run() {
        return null;
    }
}
