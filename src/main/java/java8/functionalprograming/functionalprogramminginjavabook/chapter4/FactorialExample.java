package java8.functionalprograming.functionalprogramminginjavabook.chapter4;

/**
 * @author Tushar Chokshi @ 9/3/16.
 */

/*
There are many ways you can write factorial algorithm.
- imperative approach
- recursive approach
- tail recursive approach
- tail recursive using Java 8 approach

Below example shows how can you convert imperative approach to tail-recursion very easily.
 */
public class FactorialExample {

    /*
    public static int factorialImperative(int n) {
        if (n == 0) return 0;
        if (n == 1) return n;

        int result = 1;
        for (int i = n; i >= 2; i--) {
            result = result * i;
        }
        return result;
    }*/

    public static int factorialImperative1(int n) {
        if (n == 0) return n;

        int result = 1;
        for (int i = 1; i <= n; i++) {
            result = result * i;
        }
        return result;
    }
    // It is NOT a good way of of thinking imperative first and then trying to convert into recursion. But initially it is ok.
    // Better way to think recursive directly without thinking about imperative approach.

    // Turning above imperative approach to recursive approach without much thinking.
    // 1. there is only one loop, so that loop can be converted to recursive method call
    //    if there is another loop inside first loop, then you need another recursive method (see List.java's splitListAtIteratively and splitListAtRecursiveTailRecursive)
    // 2. all variables that you are using inside for loop, pass them as parameters to recursive method
    // 3. condition to return from for loop will become an exit condition
    // 4. manipulate passed parameters as they are manipulated in imperative approach
    public static int factorialTailRecursive1(int n, int i, int result) { // start i from 1, result from 1
        if (n == 0) return 0;
        if (i > n) return result;
        return factorialTailRecursive1(n, i + 1, i * result);
    }

    // Thinking Recursive directly (without Imperative approach in mind)
    // Reducing the problem by 1.
    public static int factorialRecursive(int n) {
        if (n == 0) return 0;
        if (n == 1) return n;
        return n * factorialRecursive(n - 1);
    }


    // converting Recursive method to Tail-Recursive by passing result as a parameter
    public static int factorialTailRecursive(int n, int result) {
        if (n == 0) return 0;
        if (n == 1) return result;
        result = result * n;
        result = factorialTailRecursive(n - 1, result);
        return result;
    }

    // Converting Tail-Recursion to Java 8 style Tail-Recursion by wrapping the returned valued under TailCall can calling eval() on returned TailCall.
    // Unlike to Scala, Java doesn't support using only one stack frame, if you use tail recursion.
    // To make it work with Java, use Java 8's Supplier.
    /*
        factorialTailRecursiveUsingJava8(4, 1).eval()
            - returns SupplierContainer(()->fact(3, 4)), its get()
                - returns SupplierContainer(()->add(2, 12)), its get()
                    - returns SupplierContainer(()->add(1, 24)), its get()
                    - returns FinalValueContainer(24), its eval()
                        - returns 24

     */
    public static TailCall<Integer> factorialTailRecursiveUsingJava8(Integer n, Integer result) {
        if (n == 0) return TailCall.getFinalValueContainer(0);
        if (n == 1) return TailCall.getFinalValueContainer(result);
        return TailCall.getSupplierContainer(() -> factorialTailRecursiveUsingJava8(n - 1, result * n));
    }

    public static void main(String[] args) {
        int n = 4;
        System.out.println("Using Imperative approach: " + FactorialExample.factorialImperative1(n));

        System.out.println("Using Normal Recursion - uses multiple stack frames: " + FactorialExample.factorialRecursive(n)); // 4! = 4*3*2*1 = 24

        System.out.println("Using Tail Recursion - uses multiple stack frames: " + FactorialExample.factorialTailRecursive1(n, 1, 1));

        System.out.println("Using Tail Recursion - uses multiple stack frames: " + FactorialExample.factorialTailRecursive(n, 1));// 24

        System.out.println("Using Tail Recursion using Java 8's Supplier's Lazy Evaluation feature: " + FactorialExample.factorialTailRecursiveUsingJava8(n, 1).eval());
    }
}
