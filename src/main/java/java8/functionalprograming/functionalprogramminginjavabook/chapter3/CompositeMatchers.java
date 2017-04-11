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

    public R execute(Object input, R defaultResult) {
        R res = matchers.stream()
                .map(matcher -> matcher.execute(input))
                .filter(result -> result.isPresent())
                .findFirst() // Optional<Optional<R>>
                .flatMap(result -> result)
                .orElse(defaultResult);

        return res;

    }
}
