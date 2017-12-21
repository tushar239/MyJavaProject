package algorithms._5recursion_and_memoization;

import java8.functionalprograming.functionalprogramminginjavabook.chapter4.TailCall;

import java.util.HashMap;
import java.util.Map;

/*
What is Fibonacci Sequence?
https://www.mathsisfun.com/numbers/fibonacci-sequence.html


F0	F1	F2	F3	F4	F5	F6	F7	F8	F9	F10	F11	F12	F13	F14	F15	F16	F17	  F18	F19	  F20
0	1	1	2	3	5	8	13	21	34	55	89	144	233	377	610	987	1597  2584	4181  6765

Below code shows how can you write Fibonacci sequence code using
- Normal Recursion          - uses multiple stack frames
- Tail Recursion            - uses multiple stack frames in Java, but just one in Scala
- Tail Recursion using Java8 - uses one stack frame
- Memoized Recursion

Memoization Advantages:
Read RecursionAndMemoizationConcepts.java
*/
public class _0Fibonacci {

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

    // Normal Recursion (not a Tail-Recursion)
    public static int fibRecursion1(int number) {
        if (number == 0 || number == 1) {
            return number;
        }
        int minusOne = fibRecursion1(number - 1);
        int minusTwo = fibRecursion1(number - 2);
        return minusOne + minusTwo;
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
    // Tail recursion doesn't do any computation with previous element.
    // It stores the result in a variable and pass it as a parameter during recursive call.
    // when last method call is kept in the stack, it already has final result in it all method calls return with that return. It doesn't do anymore computation.
    // This approach is very useful in functional programming where Recursion is inherant and java has problem with size of stack. In Functional programming, if you use tail-recursion, then using some trick, you can get away from using so many stack slots. you can using only one stack slot for entire recursion

    // In this case, when the last method call is pushed to stack, result is already computed. Ideally, you don't have to put all method call in the stack, you can use only one stack frame and overwrite it by each method call. Java doesn't support this, but Scala supports it. So, in scala recursion is very cheap.
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
        System.out.println("Fibonacci Sequence Imperative: " + fibImperative(number));
        System.out.println("Fibonacci Sequence Recursive: " + fibRecursion1(number)); // 34
        System.out.println("Fibonacci Sequence Recursive: " + fibRecursion2(number, 0)); // 34
        System.out.println("Fibonacci Sequence Tail-Recursive: " + fibTailRecursion(number, 1, 0)); // 34
        System.out.println("Fibonacci Sequence Tail-Recursive using Java 8: " + fibTailRecursionUsingJava8(9, 1, 0).eval()); // 34
        System.out.println("Fibonacci Sequence Recursive using Memoization: " + fibonacciRecursionUsingMemoization(9, new HashMap<>()));// 34
    }
}
