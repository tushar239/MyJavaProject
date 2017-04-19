package java8.functionalprograming.functionalprogramminginjavabook.chapter3;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Tushar Chokshi @ 4/11/17.
 */
public class CompositeMatchers<R> {
    private List<Matcher<Object, R>> matchers = new LinkedList<>();

    public CompositeMatchers(List<Matcher<Object, R>> matchers) {
        this.matchers = matchers;
    }

    public R execute(Object input, R defaultResult) { // Think R as Consumer<String>
        R res = matchers.stream()
                .map(matcher -> matcher.execute(input)) // returns Stream<Optional<R>> // matcher.execute(...) returns Optional<Consumer<String>>
                .filter(result -> result.isPresent()) // returns Stream<Optional<R>> // check whether result is not same as Optional.empty()
                .findFirst() // Optional<Optional<R>> // Optional<Optional<Consumer<String>>>
                .flatMap(result -> result) // Optional<R> // Optional<Consumer<String>>
                .orElse(defaultResult);

        return res;

    }
}
