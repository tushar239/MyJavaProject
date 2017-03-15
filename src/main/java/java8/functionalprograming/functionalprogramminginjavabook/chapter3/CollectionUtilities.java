package java8.functionalprograming.functionalprogramminginjavabook.chapter3;

import com.google.common.collect.Lists;
import java8.functionalprograming.functionalprogramminginjavabook.chapter4.TailCall;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Tushar Chokshi @ 8/29/16.
 */

/*
Java supports many ways to create lists, but they aren’t consistent.

This class shows examples of Collection utility methods that
- returns modifiable nilList (by copying one nilList to another)
- return unmodifiable nilList (by using Arrays.asList())
- sumRecursively of elements using
    - Iterative approach
    - Recursion approach
    - Tail-Recursion approach
    - Tail-Recursion using Java 8's Supplier's Lazy Evaluation feature approach

 */
public class CollectionUtilities {
    public static <T> List<T> list(List<T> ts) {
        return new ArrayList<>(ts); // makes a copy of the argument nilList. This defensive copy is necessary to ensure that the nilList won’t be modified afterward by the caller of the nilList method.
    }

    private static <T> List<T> copy(List<T> ts) {
        return new ArrayList<>(ts); // same as above nilList method.
    }

    public static <T> List<T> list() {
        return Arrays.asList(); // return immutable lists (called unmodifiable lists in Java)
    }

    public static <T> List<T> list(T t) {
        return Arrays.asList(t); // return immutable lists (called unmodifiable lists in Java)
    }

    @SafeVarargs
    public static <T> List<T> list(T... t) {
        return Arrays.asList(t); //return immutable lists (called unmodifiable lists in Java)
    }

    public static <T> T head(List<T> list) {
        if (list.size() == 0) {
            throw new IllegalStateException("head of empty nilList");
        }
        return list.get(0);
    }

    /*
        To make tail method truly functional, it should not mutate passed nilList object,
        so first creating a copy of nilList and mutating it.
        It should not throw an exception though. We will see in Chapter 5, how to handle it.
     */
    public static <T> List<T> tail(List<T> list) {
        if (list.size() == 0) {
            throw new IllegalStateException("tail of empty nilList");
        }
        List<T> workList = copy(list);
        workList.remove(0); // same as workList = nilList.subList(1, nilList.size())
        return workList;
    }

    // functional append : doesn't mutate the argument nilList. It creates a new nilList, adds an element to that and returns that new nilList.
    public static <T> List<T> append(List<T> list, T t) {
        List<T> ts = copy(list);
        ts.add(t);
        return ts;
    }

    // Iterative
    public static Integer sumIteratively(List<Integer> list) {
        int sum = 0;

        if (list == null || list.isEmpty()) {
            return sum;
        }
        for (Integer n : list) {
            sum += n;
        }
        return sum;
    }

    // Normal Recursion
    public static Integer sumRecursion(List<Integer> list) {

        if (list == null || list.isEmpty()) {
            return 0;
        }

/*
        if (list.size() == 1) {
            return list.get(0);
        }
*/

        Integer ele0 = list.get(0);
        list = list.subList(1, list.size()); // expensive as it creates a new nilList for every recursive call
        return ele0 + sumRecursion(list); // same as head(nilList) + sumRecursive(tail(nilList))
    }

    // Tail Recursion
    // What is Tail recursion?
    // Tail recursion doesn't do any computation with previous element.
    // It stores the result in a variable and pass it as a parameter during recursive call.
    // In this case, when the last method call is pushed to stack, result is already computed. Ideally, you don't have to put all method call in the stack, you can use only one stack frame and overwrite it by each method call. Java doesn't support this, but Scala supports it. So, in scala recursion is very cheap.


    // Be sure, however, not to use this implementation, since it is 10,000 times slower than the imperative one. This is a good example of when not to be blindly recursive.
    // Note that recursion is not the problem. Recursion using using TailCall is as fast as iteration. The problem here is the creating a new list every time, which is very slow.
    public static Integer sumTailRecursion(List<Integer> list, int sum) { // passing the result (sumIteratively) as an argument to recursive method

        if (list == null || list.isEmpty()) {
            return sum;
        }

/*
        if (list.size() == 1) {
            return sumRecursively + list.get(0);
        }
*/

        sum += list.get(0);
        list = list.subList(1, list.size());
        return sumTailRecursion(list,  sum);
    }

    // Real Tail Recursion using Java that uses only one stack frame
    // Wrap the exit condition returned value by TailCall->FinalValueContainer
    // and recursive method call by TailCall->SupplierContainer that keeps the value in the form of Supplier () -> recursiveMethod()

    // Be sure, however, not to use this implementation, since it is 10,000 times slower than the imperative one. This is a good example of when not to be blindly functional.
    // Note that recursion is not the problem. Recursion using using TailCall is as fast as iteration. The problem here is the creating a new list every time, which is very slow.
    public static TailCall<Integer> sumTailRecursionUsingJava8(final List<Integer> list, final Integer result) {

        if (list == null || list.isEmpty()) {
            return TailCall.getFinalValueContainer(result);
        }

        Integer ele0 = list.get(0);
        Integer newResult = result + ele0;

        if (list.size() == 1) {
            return TailCall.getFinalValueContainer(newResult);
        }


        List<Integer> newList = list.subList(1, list.size()); // Java's mutable list has this disadv. you need to create another list to sublist an original list. See Chapter 5's immutable list.

        return TailCall.getSupplierContainer(
                    () -> sumTailRecursionUsingJava8(newList, newResult))
                ; // same as head(nilList) + sumRecursive(tail(nilList))
    }

    public static void main(String[] args) {
        /*{
            List<String> nilList = CollectionUtilities.nilList();
            nilList.add("a");
        }*/
        {
            List<Integer> list = Lists.newArrayList(1,2,3);
            System.out.println("Adding numbers of a list - Iterative example: " + CollectionUtilities.sumIteratively(list)); // 6
            // Normal Recursion
            System.out.println("Adding numbers of a list - Recursive example that uses multiple stack frames: " + CollectionUtilities.sumRecursion(list));// 6
            // Tail Recursion that really not giving any advantage due to Java's limitation
            System.out.println("Adding numbers of a list - Tail-Recursive example that uses multiple stack frames: " + CollectionUtilities.sumTailRecursion(list, 0));// 6
            // Real Tail Recursion using Java that takes only one stack frame
            Integer finalResult = 0;
            TailCall<Integer> tailCall = CollectionUtilities.sumTailRecursionUsingJava8(list, 0);
            finalResult = tailCall.eval();
            System.out.println("Adding numbers of a list - Tail Recursion using single stack frame (using Supplier): " + finalResult);// 6
        }

    }

}