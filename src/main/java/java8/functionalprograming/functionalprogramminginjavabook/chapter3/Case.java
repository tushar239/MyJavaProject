package java8.functionalprograming.functionalprogramminginjavabook.chapter3;


import java8.functionalprograming.functionalprogramminginjavabook.chapter12.Tuple;
import java8.functionalprograming.functionalprogramminginjavabook.chapter7.Result;

import java.util.function.Supplier;

/**
 * @author Tushar Chokshi @ 8/29/16.
 */

// If you want to understand Java 8 style way of writing this class, then look at Matcher.java and CompositeMatchers.java
public class Case<T> extends Tuple<Supplier<Boolean>, Supplier<Result<T>>> {
    private Case(Supplier<Boolean> booleanSupplier,
                 Supplier<Result<T>> resultSupplier) {
        super(booleanSupplier, resultSupplier);
    }

    public static <T> Case<T> mcase(Supplier<Boolean> condition,
                                    Supplier<Result<T>> value) {
        return new Case<>(condition, value);
    }

    public static <T> DefaultCase<T> mcase(Supplier<Result<T>> value) {
        return new DefaultCase<>(() -> true, value);
    }

    private static class DefaultCase<T> extends Case<T> {
        private DefaultCase(Supplier<Boolean> booleanSupplier,
                            Supplier<Result<T>> resultSupplier) {
            super(booleanSupplier, resultSupplier);
        }
    }

    @SafeVarargs
    public static <T> Result<T> match(DefaultCase<T> defaultCase,
                                      Case<T>... matchers) {
        for (Case<T> aCase : matchers) {
            if (aCase._1.get()) return aCase._2.get();
        }
        return defaultCase._2.get();
    }
}
