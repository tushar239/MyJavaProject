package algorithms.crackingcodinginterviewbook._5recursion_and_dynamic_programming;

import java.util.HashMap;
import java.util.Map;

/*
Triple Steps:
A child is running up a staircase with n steps and can hop either 1 step, 2 steps, or 3 steps at a time.
Implement a method to count how many possible ways the child can run up the stairs.

Watch 'Recurrence Relations Part2.mp4' video first
and then
Watch 'Staircase Problem same as Fibonacci Series problem.mp4' video.


I have implemented this algorithm in multiple ways
- Brute Force way
- Top-Bottom Dynamic Programming (Memoization) way
- Bottom-Up Dynamic Programming way
- Tail-Recursion way (may be not so important for interview)


Time Complexity:
This problem is same as Fibonacci Series

                                            f(4)

                f(3)						f(2)                    f(1)

	    f(2)    f(1)	 f(0)		    f(1)  f(0) f(-1)

    f(1)   f(0)

At each node constant time operation is happening.
number of nodes = 3^0 + 3^1 + 3^2 + 3^3 + .... + 3^n ~= 3^(n+1) - 1 ~= 3^n
At each node constant time operation is happening, so time complexity = O(3^n)


Using Back Substitution:

    T(n) = T(n-1) + T(n-2) + T(n-3) + C
    This formula is a bit different than T(n) = 2T(n/2) + something --- you see this in tree/array recursion where number of nodes/elements are becoming half at each recursion


    T(n) = T(n-1) + T(n-2) + T(n-3) + C

    Assuming that T(n-2) ~= T(n-1)  ---- in reality T(n-2) is little less than T(n-1)
              and T(n-3) ~= T(n-2)
    This assumption will find upper bound of time complexity

    T(n) = 3T(n-1) + C
    T(n-1) = 3T(n-2) + C
    T(n-2) = 3T(n-3) + C

    T(n) = 3T(n-1) + C
         = 3(3T(n-2) + C) + C
         = 9T(n-2) + 4C
         = 9(3T(n-3) + C) + 4C
         = 27T(n-3) + 13C

    replacing the value that is changing with k. here, it is 3.

         = (3^k) T(n-k) + (3 ^ k-1)C

    applying base condition, n-k=0. So, k=n.

        = 3^n T(0) + (3^k-1)C

        T(0) = 0

        = 0 + (3^k-1)C
        ~= 3^k = 3^n        --------- upper bound


    Assuming that T(n-1) ~= T(n-2) ---- in reality T(n-1) is little more than T(n-2)
             and T(n-2) ~= T(n-3)
    This assumption will find lower bound of time complexity

    T(n) = 3T(n-3) + C
    T(n-3) = 3T(n-6) + C
    T(n-6) = 3T(n-9) + C

    T(n) = 3T(n-3) + C
         = 3(3T(n-6) + C) + C
         = 9T(n-6) + 4C
         = 9(3T(n-9) + C) + 4C
         = 27T(n-9) + 10C
         = 3^k T(n-(k*3)) + (3^k - 1)C

    n-(k*3) = 0
    n = k*3
    k = n/3

         = 3^(n/3) T(0) + (3^(n/3) - 1)C
         ~=O(3 ^ n/3)        --------- lower bound

 */
public class _1TripleSteps {
    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        int stairs = 10;
        System.out.println("Total Steps using Normal Recursion: " + totalSteps_BruteForce(stairs) + ", time taken: " + (System.currentTimeMillis() - start));

        start = System.currentTimeMillis();
        System.out.println("Total Steps using Tail Recursion: " + totalSteps_TailRecursion(stairs) + ", time taken: " + (System.currentTimeMillis() - start));

        start = System.currentTimeMillis();
        System.out.println("Total Steps using Top-Bottom Dynamic Programming (Memoization): " + totalSteps_Memoization(stairs, new HashMap<>()) + ", time taken: " + (System.currentTimeMillis() - start));

        start = System.currentTimeMillis();
        System.out.println("Total Steps using Bottom-Up Dynamic Programming (Memoization): " + totalSteps_Memoization1(stairs) + ", time taken: " + (System.currentTimeMillis() - start));

        // best approach from better time complexity
        start = System.currentTimeMillis();
        System.out.println("Total Steps using Bottom-Up Dynamic Programming: " + totalSteps_BottomUpDynamicProgramming(stairs, new int[stairs + 1]) + ", time taken: " + (System.currentTimeMillis() - start));

        /*// best approach from better time complexity
        start = System.currentTimeMillis();
        int[] steps = {1, 2, 3};
        System.out.println("Total Steps using Bottom-Up Dynamic Programming: " + totalSteps_BottomUpDynamicProgramming1(steps, stairs) + ", time taken: " + (System.currentTimeMillis() - start));
*/
        start = System.currentTimeMillis();
        System.out.println("Total Steps using Brute-Force (as per book): " + totalSteps_book_way_BruteForce(stairs) + ", time taken: " + (System.currentTimeMillis() - start));
        System.out.println(cnt);
        //System.out.println(totalSteps_wrong(9));
    }


    /*private static int totalSteps_wrong(int stairs) {
        int rootSteps = 0;
        if (stairs < 0) {
            return rootSteps;
        }
        if (stairs == 0) {
            return rootSteps += 0;
        }
        if (stairs == 1) {
            return rootSteps += 1;
        }
        if (stairs == 2) {
            return rootSteps += 2;
        }
        if (stairs == 3) {
            return rootSteps += 4;
        }
        // if you think c(4) = rootSteps + c(3), then result=3. It is a wrong result.
        // If there are 4 stairs, there will be 7 possibilities = c(3)+c(2)+c(1)
        int minusOne = totalSteps_wrong(stairs - 1);
        return rootSteps + minusOne;
    }*/

    /*
    Watch 'Recurrence Relations Part2.mp4' video first.

    T(n) = base condition result + T(n-1) + T(n-2) + T(n-3)
    Here, you need 3 base conditions T(1), T(2) and T(3) and one additional one for T(0).

    For this algorithm, T(0)=0, T(1)=1, T(2)=2, T(3)=4
    If there are 0 steps, then there are 0 possibilities to reach to destination.
    If there is 1 step, then there is just 1 possibility to reach to destination.
    If there are 2 steps, then there are 2 possibilities to reach to destination.
    If there are 3 steps, then there are 3 possibilities to reach to destination.

    From above T(n) formula,

        T(1)=T(0)
        T(2)=T(1)+T(0)
        T(3)=T(2)+T(1)+T(0)

    If you keep T(0)=1 instead of 0, you don't need to keep base conditions for T(1), T(2), T(3) because these conditions will automatically give right results.
    This is what "Cracking Coding Interview book" does.
    Just one base condition
        if(stairs == 0) return 1;
    is enough.

     */
    private static int totalSteps_BruteForce(int stairs) {
        // if there are < 0 stairs, total number of different steps are 0
        if (stairs < 0) {
            return 0;
        }
        // if there are 0 stairs, total number of different steps are 0
        if (stairs == 0) {
            return 0;
        }
        // if there is 1 stair, total number of different combination of steps are 1
        if (stairs == 1) {
            return 1;
        }
        // if there are 2 stair, total number of different combination of steps are 2
        // reserve 2 steps together or reserve one-one steps
        if (stairs == 2) {
            return 2;
        }

        // if there are 3 stair, total number of different combination of steps are 4
        // reserve 3 stairs together or 2+1 stairs or 1+2 stairs or reserve 1+1+1 stairs
        if (stairs == 3) {
            return 4;
        }

        // in reality, if you are finding ways to climb n stairs with 1 step
        // then you need to find ways to climb n-1 stairs and add 1 to each of those ways
        // it doesn't change total number of ways. so just finding number of ways to climb n-1 stairs is enough.
        int minusOne = totalSteps_BruteForce(stairs - 1);

        // in reality, if you are finding ways to climb n stairs with 2 steps
        // then you need to find ways to climb n-2 stairs and add 2 to each of those ways
        // it doesn't change total number of ways. so just finding number of ways to climb n-2 stairs is enough.

        // you might be thinking that don't you need to add 1s first in all those ways and then add 2 to all those ways.
        // so, number of ways will be doubled
        // well, that's not true because ways added 1s will be same as some of the ways that you covered with ways to climb n-1 stairs and adding 1 step to them.
        // so, in this case, you just add step 2 to all ways found from n-2 stairs.
        int minusTwo = totalSteps_BruteForce(stairs - 2);

        // in reality, if you are finding ways to climb n stairs with 3 steps
        // then you need to find ways to climb n-3 stairs and add 3 to each of those ways
        // it doesn't change total number of ways. so just finding number of ways to climb n-1 stairs is enough.
        int minusThree = totalSteps_BruteForce(stairs - 3);
        return minusOne + minusTwo + minusThree;
    }

    static int cnt = 0;
    private static int totalSteps_book_way_BruteForce(int stairs) {
        cnt++;
        if (stairs < 0) {
            return 0;
        }
        if (stairs == 0) {
            return 1;
        }

        int minusOne = totalSteps_book_way_BruteForce(stairs - 1);
        int minusTwo = totalSteps_book_way_BruteForce(stairs - 2);
        int minusThree = totalSteps_book_way_BruteForce(stairs - 3);
        return minusOne + minusTwo + minusThree;
    }

    /*
       Followed 'totalSteps_BruteForce' method to to convert it into Bottom-Up Dynamic approach.

       You can use 1-D array for Bottom-Up Dynamic approach here. You can figure that out by seeing the base conditions of Brute-Force approach.
       You can use 2-D array also just like CoinsChange.java, but it will over complicate the logic.

       In CoinsChange.java, there are no straight forward base conditions that can be converted into 1-D array.
       And in-fact, it is harder to think brute-force in CoinsChange case. It is easier to thing Bottom-Up Dynamic approach to begin with.
    */
    private static int totalSteps_BottomUpDynamicProgramming(int stairs, int[] memo) {
        memo[0] = 0;
        memo[1] = 1;
        memo[2] = 2;
        memo[3] = 4;

        if (stairs < 0) return memo[0];

        for (int i = 4; i <= stairs; i++) {
            memo[i] = memo[i - 1] + memo[i - 2] + memo[i - 3];
        }

        return memo[stairs];
    }
    /*

     */
/*
    This is how you should think about Bottom-Up Dynamic Programming approach.
    I took this approach from

       stairs
           0       1       2       3       4       5       6       7       8       9       10
       ---------------------------------------------------------------------------------------
s    0 |   1       0       0       0       0       0       0       0       0       0       0
t      |
e    1 |   1       1       1       1       1       1       1       1       1       1       1
p      |
s    2 |   1       1       1+1     2+1    3+2     5+3     8+5     13+8    21+13   34+21   55+34
       |   |       ||      ^ ^
       |   |       ||      | |
       |   |       ||      | |
       |   |       ||      | |
       |   |       |-------- |
       |   --------|----------
       |           v
     3 |   1       1       2       2+1+1   4+2+1 7+4+2     13+7+4  24+13+7 44+24+13 81+44+24 149+81+44=274
           
           |       |       |       ^ ^ ^
           |       |       |       | | |
           |       |       |       | | |
           |       |       --------- | |
           |       ------------------- |
           |----------------------------

     I haven't coded, but if you want you can code 2-D array as shown above for Bottom-Up Dynamic approach.

     if(stair < step) memo[step][stair]=memo[step-1][stair]
     else
        for(int i=0;i<step;i++) memo[step][stair] += memo[step-(i+1)][stair]
        so, for step=2, memo[2][2]=memo[2][1]+memo[2][0]  and so on
            for step=3, memo[3][3]=memo[3][2]+memo[3][1]+memo[3][0] and so on

     *//*

    private static int totalSteps_BottomUpDynamicProgramming1(int[] steps, int stairs) {

    }
*/


    private static int totalSteps_TailRecursion(int stairs) {

        // send the result of base conditions as input parameters
        return totalSteps_TailRecursion(stairs, 0, 1, 2, 4);
    }

    // I just followed the steps of Fibonacci
    private static int totalSteps_TailRecursion(int stairs, int stepsForZeroStairs, int stepsForOneStair, int stepsForTwoStairs, int stepsForThreeStairs) {
        if (stairs == 0) {
            return stepsForZeroStairs;
        }
        if (stairs == 1) {
            return stepsForZeroStairs + stepsForOneStair;
        }
        if (stairs == 2) {
            return stepsForZeroStairs + stepsForOneStair + stepsForTwoStairs;
        }
        if (stairs == 3) {
            return stepsForZeroStairs + stepsForOneStair + stepsForTwoStairs;
        }
        int stepsForFour = stepsForOneStair + stepsForTwoStairs + stepsForThreeStairs;

        return totalSteps_TailRecursion(stairs - 1, stepsForOneStair, stepsForTwoStairs, stepsForThreeStairs, stepsForFour);
    }

    private static int totalSteps_Memoization(int stairs, Map<Integer, Integer> result) {
        int steps = 0;
        if (stairs < 0) {
            return steps;
        }
        if (stairs == 0) {
            return steps + 0;
        }
        if (stairs == 1) {
            return steps + 1;
        }
        if (stairs == 2) {
            return steps + 2;
        }
        if (stairs == 3) {
            return steps + 4;
        }

        int minusOne;
        if (result.containsKey(stairs - 1)) minusOne = result.get(stairs - 1);
        else {
            minusOne = totalSteps_Memoization(stairs - 1, result);
            result.put(stairs - 1, minusOne);
        }

        int minusTwo;
        if (result.containsKey(stairs - 2)) minusTwo = result.get(stairs - 2);
        else {
            minusTwo = totalSteps_Memoization(stairs - 2, result);
            result.put(stairs - 2, minusTwo);
        }
        int minusThree;
        if (result.containsKey(stairs - 3)) minusThree = result.get(stairs - 3);
        else {
            minusThree = totalSteps_Memoization(stairs - 3, result);
            result.put(stairs - 3, minusThree);
        }
        return steps + minusOne + minusTwo + minusThree;
    }


    private static int totalSteps_Memoization1(int stairs) {
        if (stairs < 0) return 0;

        int[] memo = new int[stairs + 1];
        memo[0] = 0;
        memo[1] = 1;
        memo[2] = 2;
        memo[3] = 4;

        if (stairs > 3) {
            totalSteps_Memoization1(stairs, memo);
        }
        return memo[memo.length - 1];
    }

    private static void totalSteps_Memoization1(int stairs, int[] memo) {
        for (int i = 4; i <= stairs; i++) {
            memo[i] = memo[i - 1] + memo[i - 2] + memo[i - 3];
        }
    }
}
