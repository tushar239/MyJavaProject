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



                                            fib(5)
                   fib(4)                                       fib(3)
         fib(3)              fib(2)                 fib(2)                  fib(1)
    fib(2)   fib(1)     fib(1)  fib(0)          fib(1)  fib(0)
fib(1) fib(0)

Total number of nodes in the tree will represent the runtime, since each call only doesn O(1) work outside of its recursive calls. Therefore, the number of calls is the runtime.
How many nodes are there in the tree?
The root node has two children. Each of those children has two children (so four children total in the "grand children" level).
Each of those grandchildren has to children, and so on.
If we do this n times, we will have reoughly o(2^n) nodes. This gives us runtime of roughly O(2^n).

Actually, it's slightly better than O(2^n) because if you see the right subtree is slightly smaller than the left subtree.

This issue can be solved using Dynamic Programming techniques.
- Top-Down Dynamic Programming (or Memoization)
- Bottom-Up Dynamic Programming

Top-Down Dynamic Programming (or Memoization)
---------------------------------------------
    e.g.fibonacciRecursion_Memoization method


                                                fib(5)
                       fib(4)                                       fib(3)
             fib(3)              fib(2)
        fib(2)   fib(1)
    fib(1) fib(0)

    Result of fib(1), fib(0), fib(2), fib(3), fib(4) and fib(5) will be memoized. Repetitive calls to these methods will be avoided. and number of call will be reduced as shown above.
    This will improve runtime to approx O(n).

    - Basically, if you see above example, it's a Brute Force approach because you are calling the method with same parameter multiple times.
    Memoization can solve this problem (read RecursionAndMemoizationConcepts.java)
    - It can also help to convert Normal Recursive method to Tail-Recursive method (read RecursionAndMemoizationConcepts.java)

Bottom-Up Dynamic Programming
-----------------------------
    This avoids using Recursion.
    It stores base condition (exit condition) result (fib(1) and fib(0)) in some external variable like array or map etc just like Memoization
    and then it works bottom-up
        to calculate fib(2), it uses stored values of fib(1) and fib(0) and then it stores fib(2) value also.
        to calculate fib(3), it uses stored values of fib(2) and fib(1) and then it stores fib(3) value also.
        and so on.
    e.g. fibonacci_BottomUpDynamicProgramming method

*/
@SuppressWarnings("Duplicates")
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
       Top-Bottom Dynamic Programming (Memoization)
       --------------------------------------------

       Why Memoization is important?


                   fibRecursion1(4)
                    |
                 fibRecursion1(3)    +       fibRecursion1(2)
                    |                              |
                    |              fibRecursion1(1)+fibRecursion1(0)
                    |
                fibRecursion1(2)+fibRecursion1(1)
                   |
               fibRecursion1(1)+fibRecursion1(0)

       If you see, fibRecursion1(2) is called twice. So, unnecessarily why to use stack for that.
       You can store the result of each fibRecursion1(n) call in a Map<2,result of fibRecursion1(2)> and before calling fibRecursion1(2) again check whether map already has its value.

       Memoization helps to save Stack memory. But needs heap memory for a Map which is better than using stack memory.
       Memoization is a solution of Java's Recursion disadvantages, but if you use Java 8's Tail-Recursion, then you don't need Memoization. It uses only one stack frame.

       http://stackoverflow.com/questions/5453376/tail-call-optimization-for-fibonacci-function-in-java
    */
    private static int fibonacciRecursion_Memoization(Integer number, Map<Integer, Integer> resultContainer) {
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
            numResult1 = fibonacciRecursion_Memoization(num1, resultContainer);
            resultContainer.put(num1, numResult1);
        }

        if (resultContainer.containsKey(num2)) {
            numResult2 = resultContainer.get(num2);
        } else {
            numResult2 = fibonacciRecursion_Memoization(num2, resultContainer);
            resultContainer.put(num2, numResult2);
        }

        return numResult1 + numResult2;
    }

    /*
    Bottom-Up Approach of Dynamic Programming
    -----------------------------------------

    It doesn't use recursion like Top-Bottom Approach of Dynamic Programmin(Memoization).

    This avoids using Recursion.
    It stores base condition (exit condition) result (fib(1) and fib(0)) in some external variable like array or map etc just like Memoization
    and then it works bottom-up
        to calculate fib(2), it uses stored values of fib(1) and fib(0) and then it stores fib(2) value also.
        to calculate fib(3), it uses stored values of fib(2) and fib(1) and then it stores fib(3) value also.
        and so on.
     */
    private static int fibonacci_BottomUpDynamicProgramming(Integer number, Integer[] memo) {
        if (number == 0) return 0;
        if (number == 1) return 1;

        memo[0] = 0;
        memo[1] = 1;

        for (int i = 2; i <= number; i++) {
            memo[i] = memo[i - 1] + memo[i - 2];
        }

        return fibonacci_BottomUpDynamicProgramming(number - 1, memo) + fibonacci_BottomUpDynamicProgramming(number - 2, memo);
    }

    public static void main(String[] args) {
        int number = 9;
        System.out.println("Fibonacci Sequence Imperative: " + fibImperative(number));
        System.out.println("Fibonacci Sequence Recursive: " + fibRecursion1(number)); // 34
        System.out.println("Fibonacci Sequence Tail-Recursive: " + fibTailRecursion(number, 1, 0)); // 34
        System.out.println("Fibonacci Sequence Tail-Recursive using Java 8: " + fibTailRecursionUsingJava8(9, 1, 0).eval()); // 34
        System.out.println("Fibonacci Sequence Recursive using Top-Bottom Dynamic Approach (Memoization): " + fibonacciRecursion_Memoization(9, new HashMap<>()));// 34
        System.out.println("Fibonacci Sequence Recursive using Bottom-Up Dynamic Approach: " + fibonacci_BottomUpDynamicProgramming(9, new Integer[10]));// 34
    }
}
