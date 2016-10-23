package java8.functionalprograming.functionalprogramminginjavabook.chapter4;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

/**
 * @author Tushar Chokshi @ 9/5/16.
 */
public class Memoizer<T, U> {
    private final Map<T, U> cache = new ConcurrentHashMap<>();

    private Memoizer() {
    }

    public static <T, U> Function<T, U> memoize(Function<T, U> function) {
        return new Memoizer<T, U>().doMemoize(function);
    }

    // Java 8 style memoization
    private Function<T, U> doMemoize(Function<T, U> originalFunction) {
        return input -> cache.computeIfAbsent(input, (input1) -> originalFunction.apply(input1)); // caching the result computed by original function
    }

    // or
    /*private U doMemoize(T input, Function<T, U> originalFunction) {
        return cache.computeIfAbsent(input, (input1) -> originalFunction.apply(input1));
    }*/
}
