package algorithms._5recursion_and_memoization;

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
 */
public class _1TripleSteps {
    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        int stairs = 4;
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

    For this algorithm, T(0)=0, T(1)=1, T(2)=2, T(3)=3
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
        int steps = 0;
        // if there are < 0 stairs, total number of different steps are 0
        if (stairs < 0) {
            return steps;
        }
        // if there are 0 stairs, total number of different steps are 0
        if (stairs == 0) {
            return steps + 0;
        }
        // if there is 1 stair, total number of different combination of steps are 1
        if (stairs == 1) {
            return steps + 1;
        }
        // if there are 2 stair, total number of different combination of steps are 2
        // take 2 steps together or take one-one steps
        if (stairs == 2) {
            return steps + 2;
        }

        // if there are 3 stair, total number of different combination of steps are 4
        // take 3 stairs together or 2+1 stairs or 1+2 stairs or take 1+1+1 stairs
        if (stairs == 3) {
            return steps + 4;
        }

        int minusOne = totalSteps_BruteForce(stairs - 1);
        int minusTwo = totalSteps_BruteForce(stairs - 2);
        int minusThree = totalSteps_BruteForce(stairs - 3);
        return steps + minusOne + minusTwo + minusThree;
    }

    private static int totalSteps_book_way_BruteForce(int stairs) {
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
s      ---------------------------------------------------------------------------------------
t      |
e    1 |   1       1       1       1       1       1       1       1       1       1       1
p      |
s    2 |   1       1       1+1     2+1   3+1   4+3       7+4     11+7    18+11   29+18   47+29
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
        if(stairs < 0) return 0;

        int[] memo = new int[stairs+1];
        memo[0] = 0;
        memo[1] = 1;
        memo[2] = 2;
        memo[3] = 4;

        if(stairs > 3) {
            totalSteps_Memoization1(stairs, memo);
        }
        return memo[memo.length-1];
    }
    private static void totalSteps_Memoization1(int stairs, int[] memo) {
       for(int i=4; i<=stairs; i++) {
           memo[i] = memo[i-1]+memo[i-2]+memo[i-3];
       }
    }
}
