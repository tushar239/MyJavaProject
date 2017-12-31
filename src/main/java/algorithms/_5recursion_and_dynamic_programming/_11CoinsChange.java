package algorithms._5recursion_and_dynamic_programming;

/*
Coins:
Given an infinite number of quarters(25 cents), dimes (10 cents), nickels (5 cents), and pennies (1 cent),
Write code to calculate the number of wayhs of representing n cents;
 */
public class _11CoinsChange {
    public static void main(String[] args) {
        int amount = 5;
        int[] coins = {1, 2, 3, 4};

        {
            int count = count_bottom_up_dynamic_approach(coins, amount);
            System.out.println(count);
        }
        {
            System.out.println(numberOfSolutions(coins, amount));
        }
        {
            // i couldn't correct this algorithm. answer is not matching with above ones.
            int count = count_recursion(coins, coins.length - 1, amount);
            System.out.println(count);
        }
    }

/*

    This algorithm is a good example of understanding how to start thinking Bottom-Up Dynamic programming right away instead of thinking about Recursion first.
    This problem can be solved easily by thinking about Bottom-Up Dynamic approach rather than Recursion.


    Read Dynamic Programming from 'README_grokking_algorithms_book_notes.docx'

    Watch 'Coin change problem(Dynamic Programming).mp4
    and
    'Coin Changing Number of ways to get total dynamic programming.mp4'.

    This is the perfect example of how to think the solution using bottom-up dynamic programming.

    When can you use dynamic programming?
        'Optimal Substructure and Overlapping Problem - Prerequisite for Dynamic Programming.mp4'
        when you have
        - optimal substructure - if problem can be divided into subproblems and the solutions of subproblems can be used to construct the solution of a bigger problem (basically you can use recursion)
        and
        - overlapping problems (like fibonacci.java) - function with same input is called multiple times.

    When can you use Greedy programming?
        When you have
        - optimal substructure
        but not
        - overlapping problem


    Challenging part of below way of bottom-up dynamic approach is to find out a formula to calculate the result in each cell of the matrix.
    Watch above mentioned videos to understand that.

        amount
           0       1       2       3       4       5 
c      ----------------------------------------------
o      |
i    1 |   1       1       1       1       1       1
n      |
s    2 |   1       1       1+1     1+1    1+2      1+2
       |                    |      |      |
       |                    v      v      v
     3 |   1       1        2      2+1    3+1      3+2
       |   |       |                 ^      ^
           |       |                 |      |
           --------|------------------      |
                   |                        |
                   --------------------------
     4 |   1       1        2       3       4       5+1=6


    - Always remember to start 1st col with 0 (one additional col is required).

    - At each row, you have current row coin and prev rows coins available for calculation.
      e.g. when you are on row 3, you have coins 1,2,3 available for calculation.

    - Don't initialize the value for 1st col right now. you start filling up the values from coins=1 and amount=1 cell (memo[1][1]) in above matrix.
      When you will try to determine the value of memo[2][2] (number of ways to get amount 2 with coins 1 and 2), you will figure out the whether the value of 1st col should be 1 or 0.

    - How to determine number of ways to get amount 4 with for with coins 1,2,3 (value for memo[3][4])?

        including number of ways with to get amount 4 with coins 1 and 2        +      number of ways to get amount 4 using coin 2

        ways with coins 1,2 for amount 4 = memo[2][4]                           +      to get amount=4, you need to include amount 1 to coin 3. so, 3+| 1 | = 4. So, to find out number of ways to get amount 1 with coins 1,2,3, you should get value from memo[3][1]

        So, memo[3][4] = memo[2][4] + memo[3][1]

    - How to decide the value of 1st col with amount 0, should it be 0 or 1?

        You will be able to determine it when you try to find out the value of memo[2][2] or memo[3][3] etc.
        e.g. number of ways to get amount 2 with coins 1 and 2 (memo[2][2]) are
        number of ways to get amount 2 with coins 1 (memo[1][2])
        (1+1)
                    +
        number of ways you can get amount 2 with coin 2. You know that this value has to be 1 to determine that a possible way is 2+0.
        what amount should you add to coin 2 to get amount 2?
        (2+0=2)
        You should find out number of ways to get amount 0 using coins 1 and 2 (memo[2][0)). Now, you know that to get expected result value of memo[2][0] should be 1. It cannot be 0.

 */
    private static int count_bottom_up_dynamic_approach(int coins[], int amount) {
        if (coins == null || coins.length == 0) return 0;
        if (amount < 0) return 0;

        int memo[][] = new int[coins.length + 1][amount + 1];

        for (int i = 0; i <= coins.length; i++) {
            memo[i][0] = 1;// Important. always initialize 1st col with 1s
        }

        for (int i = 0; i <= amount; i++) {
            memo[0][i] = 0;// Important. always initialize 1st row with 0s
        }

        for (int i = 1; i <= coins.length; i++) {
            for (int j = 1; j <= amount; j++) {

                int coin = coins[i - 1];

                if (coin > j) {
                    memo[i][j] = memo[i - 1][j];
                } else {
                    memo[i][j] = memo[i - 1][j] + memo[i][j - coin];
                }
            }
        }
        return memo[memo.length - 1][memo[0].length - 1];// last value of memo is the answer
    }

    // https://github.com/mission-peace/interview/blob/master/src/com/interview/dynamic/CoinChanging.java
    // i took this code just to check my code's output with this code's output
    public static int numberOfSolutions(int[] coins, int total) {
        int temp[][] = new int[coins.length + 1][total + 1];
        for (int i = 0; i <= coins.length; i++) {
            temp[i][0] = 1;
        }
        for (int i = 1; i <= coins.length; i++) {
            for (int j = 1; j <= total; j++) {
                if (coins[i - 1] > j) {
                    temp[i][j] = temp[i - 1][j];
                } else {
                    temp[i][j] = temp[i][j - coins[i - 1]] + temp[i - 1][j];
                }
            }
        }
        return temp[coins.length][total];
    }

    /*private static int count_recursion1(int coins[], int coinsStartIndex, int coinsEndIndex, int amount) {
        int count = 0;

        if (coins == null || coins.length == 0) return count;
        if (amount < 0) return count;
        if(amount == 0) return count++;
        if (coinsStartIndex < 0) return count;
        if (coinsStartIndex > coinsEndIndex) return count;

        if (amount < coins[coinsStartIndex]) {
            count += count_recursion1(coins, coinsStartIndex - 1, coinsEndIndex, amount);
        } else {
            count += count_recursion1(coins, coinsStartIndex - 1, coinsEndIndex, amount)+count_recursion1(coins, coinsStartIndex, coinsEndIndex, amount- coins[coinsStartIndex]);
        }
        return count;
    }*/

    // https://www.geeksforgeeks.org/dynamic-programming-set-7-coin-change/
    // Recursion
    private static int count_recursion(int coins[], int coinsIndex, int amount) {
        // If amount is 0 then there is 1 solution (do not include any coin)
        if (amount == 0)
            return 1;

        // If amount is less than 0 then no solution exists
        if (amount < 0)
            return 0;

        // If there are no coins and amount is greater than 0, then no solution exist
        if (coinsIndex <= 0 && amount >= 1)
            return 0;

        // count is sum of solutions (i) including coins[coinsIndex-1]
        //                           (ii) excluding coins[coinsIndex-1]
        return count_recursion(coins, coinsIndex - 1, amount) + count_recursion(coins, coinsIndex, /*amount-coinsIndex*/amount - coins[coinsIndex - 1]);
    }
}
