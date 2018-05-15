package algorithms.crackingcodinginterviewbook._5recursion_and_dynamic_programming;

import java8.functionalprograming.functionalprogramminginjavabook.chapter4.TailCall;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("Duplicates")
/*
F0	F1	F2	F3	F4	F5	F6	F7	F8	F9	F10	F11	F12	F13	F14	F15	F16	F17	  F18	F19	  F20
0	1	1	2	3	5	8	13	21	34	55	89	144	233	377	610	987	1597  2584	4181  6765

Below code shows how can you write Fibonacci sequence code using
- Normal Recursion          - uses multiple stack frames
- Tail Recursion            - uses multiple stack frames in Java, but just one in Scala
- Tail Recursion using Java8 - uses one stack frame
- Memoized Recursion


Recursive Tree method to calculate time complexity

                                                fib(5)
                       fib(4)                                       fib(3)
             fib(3)              fib(2)                 fib(2)                  fib(1)
        fib(2)   fib(1)     fib(1)  fib(0)          fib(1)  fib(0)
    fib(1) fib(0)

    Total number of nodes in the tree will represent the runtime, since each call only does O(1) work outside of its recursive calls. Therefore, the number of calls is the runtime.
    How many nodes are there in the tree?
    The root node has two children. Each of those children has two children (so four children total in the "grand children" level).
    Each of those grandchildren has to children, and so on.
    If we do this n times, we will have roughly o(2^n) nodes. This gives us runtime of roughly O(2^n).

    Actually, it's slightly better than O(2^n) because if you see the right subtree is slightly smaller than the left subtree.


Back Substitution method to calculate time complexity

        T(n) = T(n-1) + T(n-2)

        T(n-2) will be < T(n-1), so it is safe to assume that T(n-2) takes same time as T(n-1)

        T(n) = 2 T(n-1)
        T(n-1) = 2 T(n-2)
        T(n-2) = 2 T(n-3)

        T(n-1) = 4 T(n-2)
        T(n) = 8 T(n-3)
             = 2^k T(n-k)

        if n-k=0 then answer is 0
        if n-k=1 then answer is 1

        To calculate upper bound, we will take n-k=1
        So, k=n

        So, T(n)= 2^n * 1 = 2^n


There is a similar problem that also has time complexity O(2^n)

        T(n) = T(n-1) + T(n-2) + T(n-3) +.......
        e.g. LongestIncreasingSubSequenceInArray.java


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
    This will improve runtime to approx O(2n).

    - Basically, if you see above example, it's a Brute Force approach because you are calling the method with same parameter multiple times.
    Memoization can solve this problem (read RecursionConcepts.java)
    - It can also help to convert Normal Recursive method to Tail-Recursive method (read RecursionConcepts.java)

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
    // Even though, this works, this is not Tail-Recursion. It does not accumulate the result at every step of recursion
    public static int fibRecursion(int number) {
        if (number == 0 || number == 1) {
            return number;
        }
        int minusOne = fibRecursion(number - 1);
        int minusTwo = fibRecursion(number - 2);
        return minusOne + minusTwo;
    }

    // This is Tail-Recursion
    // Tail recursion doesn't do any computation with previous element.
    // It stores the result in a variable and pass it as a parameter during recursive call.
    // when last method call is kept in the stack, it already has final result in it all method calls return with that return. It doesn't do anymore computation.
    // This approach is very useful in functional programming where Recursion is inherent and java has problem with size of stack. In Functional programming, if you use tail-recursion, then using some trick, you can get away from using so many stack slots. you can using only one stack slot for entire recursion

    // In this case, when the last method call is pushed to stack, result is already computed. Ideally, you don't have to put all method call in the stack, you can use only one stack frame and overwrite it by each method call. Java doesn't support this, but Scala supports it. So, in scala recursion is very cheap.
    // Fibonacci using Tail-Recursion is a special case because it calculates result based on two recursive calls.
    // So, You need to handle two results (accumulators) during tail-recursion.
    public static int fibTailRecursion(int number, int result1, int result2) {
        if (number == 0) {
            return 0;
        }
        if (number == 1) {
            int result = result1 + result2;
            //System.out.println(number + ", " + result + ", " + result2);
            return result;
        }
        int result = result1 + result2;
        //System.out.println(number + ", " + result + ", " + result2);
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


                          fib(4)
                            |
                  fib(3)    +       fib(2)
                     |                  |
                     |            fib(1)+fib(0)
                     |
               fib(2)+fib(1)
                |
          fib(1)+fib(0)

        If you see, fib(2) is called twice. So, unnecessarily why to use stack for that.
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
        System.out.println("Fibonacci Sequence Recursive: " + fibRecursion(number)); // 34
        System.out.println("Fibonacci Sequence Tail-Recursive: " + fibTailRecursion(number, 1, 0)); // 34
        System.out.println("Fibonacci Sequence Tail-Recursive using Java 8: " + fibTailRecursionUsingJava8(9, 1, 0).eval()); // 34
        System.out.println("Fibonacci Sequence Recursive using Top-Bottom Dynamic Approach (Memoization): " + fibonacciRecursion_Memoization(9, new HashMap<>()));// 34
        System.out.println("Fibonacci Sequence Recursive using Bottom-Up Dynamic Approach: " + fibonacci_BottomUpDynamicProgramming(9, new Integer[10]));// 34
    }
}
