package java8.functionalprograming.functionalprogramminginjavabook.chapter3;

import java.util.Optional;
import java.util.function.Predicate;

/**
 * @author Tushar Chokshi @ 4/11/17.
 */
// This class and CompositeMatfchers class are a Java 8 style replacement of Case class
public class Matcher<I, R> {
    private Predicate<I> condition;
    private R result;

    public Matcher(Predicate<I> condition, R result) {
        this.condition = condition;
        this.result = result;
    }

    public Optional<R> execute(I input) {
        if(condition.test(input)) return Optional.of(result);
        return Optional.empty();
    }
}
