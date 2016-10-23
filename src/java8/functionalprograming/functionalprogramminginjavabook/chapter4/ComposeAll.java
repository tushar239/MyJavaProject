package java8.functionalprograming.functionalprogramminginjavabook.chapter4;


import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.IntStream;

/**
 * @author Tushar Chokshi @ 9/4/16.
 */
public class ComposeAll {
    /* static <T> Function<T, T> composeAllRecursive2(List<Function<T, T>> nilList) {
         return foldRight(
                 nilList,
                 Function.<T>identity(),
                 (Function<T, T> f1) -> (Function<T, T> f2) -> f1.composeAllRecursive2(f2));
     }

     public static <T> TailCall<T> foldRight(List<T> ts,
                                             T identity,
                                             Function<T, Function<T, T>> f) {
         T result = identity;

         if (ts == null || ts.size() == 0) {
             return TailCall.getFinalValueContainer(result);
         }

         T newResult = f.apply(result).apply(ts.get(ts.size() - 1));

         return TailCall.getSupplierContainer(() -> foldRight(ts.subList(0, ts.size() - 1), newResult, f));
     }
 */
    public static void main(String[] args) {
        composeAllRecursive2();
    }

    static void composeAllRecursive2() {
        List<Function<Integer, Integer>> list = new ArrayList<>();
        IntStream.range(0, 5).forEach(i -> list.add(x -> x+1));

        Function<Integer, Integer> composedFunction = composeAllRecursive2(Function.identity(), list);
        int result = composedFunction.apply(0);// 0 is an input to identity function
        System.out.println(result);
    }

    private static Integer composeAllIterative(Integer identity, List<Function<Integer, Integer>> list) {
        for (Function<Integer, Integer> function : list) {
            identity = function.apply(identity); // in this way, output of one function becomes an input to another
        }
        return identity;
    }

    private static Function<Integer, Integer> composeAllIterative(List<Function<Integer, Integer>> list) {
        return (x) -> {

            Integer y = x; // identity

            for (Function<Integer, Integer> function : list) {
                y = function.apply(y);
            }
            return y;
        };
    }

    private static Function<Integer, Integer> composeAllRecursive1(List<Function<Integer, Integer>> list) {
        return (x) -> {

            if (list == null || list.size() == 0) {
                return x;
            }

            Function<Integer, Integer> firstFunction = list.get(0);
            Integer outputFromFirstFunction = firstFunction.apply(x);

            return composeAllRecursive1(list.subList(1, list.size())).apply(outputFromFirstFunction);

        };
    }
    private static Function<Integer, Integer> composeAllRecursive2(Function<Integer, Integer> identity, List<Function<Integer, Integer>> list) {
        if (list == null || list.size() == 0) {
            return identity;
        }

        Function<Integer, Integer> newIdentity = identity.compose(list.get(0));// compose returns a function - (V v) -> identity.apply(before.apply(v))

        return composeAllRecursive2(newIdentity, list.subList(1, list.size()));
    }
}
