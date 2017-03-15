package java8.functionalprograming.functionalprogramminginjavabook.chapter4;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.function.Function;

/**
 * @author Tushar Chokshi @ 9/4/16.
 */

/*
'fold' operation is explained better in Chapter 5's List.java.

Remember fold method is abstraction of recursion. Any recursive method of a stream, list, tree etc. can be written using fold method.
Read Chapter 5.
 */
public class FoldLeftExample {

    public static Integer foldLeft(List<Integer> ts) {
        Integer result = 0;
        for (Integer t : ts) { // go from first to last element
            result += t;
        }
        return result;
    }

    public static Integer foldRight(List<Integer> ts) {
        Integer result = 0;
        for (int i = ts.size() - 1; i >= 0; i++) { // go from last to first element
            result += ts.get(i);
        }
        return result;
    }


    public static <T, U> U foldLeftFunctionallyIteratively(List<T> ts,
                                                           U identity,
                                                           Function<U, Function<T, U>> f) {
        U result = identity;
        for (T t : ts) { // start from the first element
            result = f.apply(result).apply(t);
        }
        return result;
    }

    public static <T, U> U foldLeftFunctionallyTailRecursively(List<T> ts,
                                                               U identity,
                                                               Function<U, Function<T, U>> f) {
        U result = identity;

        if(ts == null || ts.size() == 0) {
            return result;
        }

        result = f.apply(result).apply(ts.get(0));

        return foldLeftFunctionallyTailRecursively(ts.subList(1, ts.size()), result, f);
    }

    // This will eliminate the recursion and so it will use only one stack frame like how scala does
    public static <T, U> TailCall<U> foldLeftFunctionallyTailRecursivelyUsingJava8(List<T> ts, // mutable java List
                                                               U identity,
                                                               Function<U, Function<T, U>> f) {
        U result = identity;

        if(ts == null || ts.size() == 0) {
            return TailCall.getFinalValueContainer(result);
        }

        U newResult = f.apply(result).apply(ts.get(0));// if you use immutable java list (Chapter 5), then you use ts.head() instead of ts.get(0) and ts.tail() instead of ts.subList(1, ts.size())

        return TailCall.getSupplierContainer(() -> foldLeftFunctionallyTailRecursivelyUsingJava8(ts.subList(1, ts.size()), newResult, f));
    }

    public static <T, U> TailCall<U> foldRightFunctionallyTailRecursivelyUsingJava8(List<T> ts,
                                                                                   U identity,
                                                                                   Function<U, Function<T, U>> f) {
        U result = identity;

        if(ts == null || ts.size() == 0) {
            return TailCall.getFinalValueContainer(result);
        }

        U newResult = f.apply(result).apply(ts.get(ts.size()-1));

        return TailCall.getSupplierContainer(() -> foldLeftFunctionallyTailRecursivelyUsingJava8(ts.subList(0, ts.size()-1), newResult, f));
    }

    public static void main(String[] args) {
        List<Integer> list = Lists.newArrayList(1, 2, 3, 4, 5);
        System.out.println(foldLeftFunctionallyIteratively(list, 0, x -> y -> x + y)); //15
        System.out.println(foldLeftFunctionallyTailRecursively(list, 0, x -> y -> x + y)); //15
        System.out.println(foldLeftFunctionallyTailRecursivelyUsingJava8(list, 0, x -> y -> x + y).eval());//15

        System.out.println(foldRightFunctionallyTailRecursivelyUsingJava8(list, 0, x -> y -> x + y).eval());//15
    }
}
