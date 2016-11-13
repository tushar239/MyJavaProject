package java8.functionalprograming.functionalprogramminginjavabook.chapter4;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Tushar Chokshi @ 9/3/16.
 */
/*
What is Fibonacci Sequence?
https://www.mathsisfun.com/numbers/fibonacci-sequence.html

n    =	0	1	2	3	4	5	6	7	8	9	10	11	12	 13	    14	...
f(n) =	0	1	1	2	3	5	8	13	21	34	55	89	144	 233    377	...

Below code shows how can you write Fibonacci sequence code using
- Normal Recursion          - uses multiple stack frames
- Tail Recursion            - uses multiple stack frames in Java, but just one in Scala
- Tail Recursion using Java8 - uses one stack frame
- Memoized Recursion
*/
public class FibonacciExample {

    /*
                i
    0   1   2   3   4   5   6   7   8   9
    0   1   1   2   3   5   8   13  21  34
        r1  r2



                    i
            r1  r2


                        i
                r1  r2

    start i = 3, r1=1, r2=1, result=r1+r2
    as i increments, r1=r2, r2=result, result=r1+r2

*/
    public static int fibImperative(int number) {
        if (number == 0 || number == 1) {
            return number;
        }

        int result = 0;
        int result1 = 1;
        int result2 = 1;

        for (int i = 3; i <= number; i++) {
            result = result1 + result2;
            result1 = result2;
            result2 = result;
        }
        return result;
    }

    // Normal Recursion
    public static int fibRecursion1(int number) {
        if (number == 0 || number == 1) {
            return number;
        }
        return fibRecursion1(number - 1) + fibRecursion1(number - 2);
    }

    // Even though, this works, this is not Tail-Recursion. It does not accumulate the result at every step of recursion
    public static int fibRecursion2(int number, int result) {
        if (number == 0 || number == 1) {
            return number;
        }
        result = fibRecursion2(number - 1, result) + fibRecursion2(number - 2, result);
        return result;
    }

    // This is Tail-Recursion
    // Fibonacci using Tail-Recursion is a special case because it calculates result based on two recursive calls.
    // So, You need to handle two results (accumulators) during tail-recursion.
    public static int fibTailRecursion(int number, int result1, int result2) {
        if (number == 0) {
            return 0;
        }
        if (number == 1) {
            int result = result1 + result2;
            System.out.println(number + ", " + result + ", " + result2);
            return result;
        }
        int result = result1 + result2;
        System.out.println(number + ", " + result + ", " + result2);
        return fibTailRecursion(number - 1, result2, result);

    }

    // This doesn't use more than one stack frame because for every method call, it returns an instance of TailCall. It doesn't call the method recursively.
    // When you do TailCall.eval(), then it resolves TailCall's supplier and calls fib method again.
    // It means that you are not calling fib method using direct recursion. Recursion happens when you call TailCall.eval().
    // As after every fib method call, it returns completely back to caller and doesn't recurse until caller wants using eval() method, it uses only one stack frame.
    public static TailCall fibTailRecursionUsingJava8(Integer number, Integer result1, Integer result2) {
        if (number == 0) {
            return TailCall.getFinalValueContainer(1);
        } else if (number == 1) {
            return TailCall.getFinalValueContainer(result1 + result2);
        } else {
            return TailCall.getSupplierContainer(() -> fibTailRecursionUsingJava8(number - 1, result2, result1 + result2));
        }
    }

    /*
        Why Memoization is important?

                    fibRecursion1(4)
                     |
                  fibRecursion1(3)    +       fibRecursion1(2)
                     |               |
                     |              fibRecursion1(1)+fibRecursion1(0)
                 fibRecursion1(2)+fibRecursion1(1)
                    |
                fibRecursion1(1)+fibRecursion1(0)

        If you see, fibRecursion1(2) is called twice. So, unnecessarily why to use stack for that.
        You can store the result of each fibRecursion1(n) call in a Map<2,result of fibRecursion1(2)> and before calling fibRecursion1(2) again check whether map already has its value.

        Memoization helps to save Stack memory. But needs heap memory for a Map which is better than using stack memory.
        Memoization is a solution of Java's Recursion disadvantages, but if you use Java 8's Tail-Recursion, then you don't need Memoization. It uses only one stack frame.

        http://stackoverflow.com/questions/5453376/tail-call-optimization-for-fibonacci-function-in-java
     */
    private static int fibonacciRecursionUsingMemoization(Integer number, Map<Integer, Integer> resultContainer) {
        if (number == 0) {
            return 0;
        }
        if (number == 1) {
            return number;
        }

        Integer num1 = number - 1;
        Integer num2 = number - 2;

        Integer numResult1 = 0;
        Integer numResult2 = 0;

        if (resultContainer.containsKey(num1)) {
            numResult1 = resultContainer.get(num1);
        } else {
            numResult1 = fibonacciRecursionUsingMemoization(num1, resultContainer);
            resultContainer.put(num1, numResult1);
        }

        if (resultContainer.containsKey(num2)) {
            numResult2 = resultContainer.get(num2);
        } else {
            numResult2 = fibonacciRecursionUsingMemoization(num2, resultContainer);
            resultContainer.put(num2, numResult2);
        }

        return numResult1 + numResult2;
    }


    public static void main(String[] args) {
        int number = 9;
        System.out.println("Fibonacci Sequence Imperative: " + FibonacciExample.fibImperative(number));
        System.out.println("Fibonacci Sequence Recursive: " + FibonacciExample.fibRecursion1(number)); // 34
        System.out.println("Fibonacci Sequence Recursive: " + FibonacciExample.fibRecursion2(number, 0)); // 34
        System.out.println("Fibonacci Sequence Tail-Recursive: " + fibTailRecursion(number, 1, 0)); // 34
        System.out.println("Fibonacci Sequence Tail-Recursive using Java 8: " + fibTailRecursionUsingJava8(9, 1, 0).eval()); // 34
        System.out.println("Fibonacci Sequence Recursive using Memoization: " + FibonacciExample.fibonacciRecursionUsingMemoization(9, new HashMap<>()));// 34
    }
}
