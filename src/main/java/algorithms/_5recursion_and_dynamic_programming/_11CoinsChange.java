package algorithms._5recursion_and_dynamic_programming;

import java.util.HashMap;
import java.util.Map;

/*
Coins:
Given an infinite number of quarters(25 cents), dimes (10 cents), nickels (5 cents), and pennies (1 cent),
Write code to calculate the number of ways of representing n cents;
 */
public class _11CoinsChange {
    public static void main(String[] args) {
        int amount = 5;
        int[] coins = {1, 2, 3, 4};

        {
            System.out.println(numberOfSolutions(coins, amount));
        }
        {
            int count = count_brute_force(coins, 0, coins.length - 1, amount);
            System.out.println(count);
        }
        {
            // i couldn't correct this algorithm. answer is not matching with above ones.
            int count = count_top_down_dynamic_approach(coins, 0, coins.length - 1, amount, new HashMap<>());
            System.out.println(count);
        }
        {
            int count = count_bottom_up_dynamic_approach(coins, amount);
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
           ----------------------------------------------
    c    0 |   1       0       0       0       0       0
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


        - Always remember to start 1st row with 0 (one additional row is required).
          If you have 0 coins, there are 0 ways to get to any amount.

        - Always remember to start 1st col with 0 (one additional col is required).
          Later on, we will see how to determine the values in col 0.

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

        int memo[][] = new int[coins.length+1][amount + 1];

        // populate first row
        for (int col = 0; col <= amount; col++) {
            memo[0][col] = 0;// Important.  initialize 1st row with 0s
        }

        // populate first col
        for (int row = 0; row < coins.length; row++) {
            memo[row][0] = 1;// Important. initialize 1st col with 1s or 0s as per your need as explained in method comment
        }

        // start iterating from second row
        for (int row = 1; row < memo.length; row++) {
            // start iterating from second col
            for (int col = 1; col < memo[row].length; col++) {

                int coin = coins[row - 1];

                if (coin > col) {
                    memo[row][col] = memo[row - 1][col];
                } else {
                    memo[row][col] = memo[row - 1][col] + memo[row][col - coin];
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

    // Watch 'Coin Change Using Top-Down Dynamic Approach.mp4'
    // Recursion
    private static int count_brute_force(int coins[], int coinsStartIndex, int coinsEndIndex, int amount) {

        int ways = 0;
        // if you don't have any coins, you cannot make any amount
        if (coins == null || coins.length == 0) {
            return ways;
        }

        // if you have coins, but target amount is 0, then there is only one way to make amount 0;
        if (amount == 0) {
            return ways + 1;
        }

        if (coinsStartIndex > coinsEndIndex) {
            return ways;
        }

        //System.out.println(amount+"-"+coins[coinsStartIndex]);
        /*
        if you have coins {1, 2, 3, 4} of cents to make 5 cents

        reducing the problem by one:
            think you have only {1} coin to make 0 cents. find out the ways to make 0 cents from {1} coin.
            recurse the logic to make remaining amount (5-0) with remaining {2,3,4} coins and add their ways to make 5 cents to the ways to make 5 cents using {1}.

            now increase the amount by coin value

        Steps with coin 1

             amountForCoin1 = 0

             ways({1}, amountForCoin1)
             |
             | remainingAmountForOtherCoins = amount-amountForCoin1 = 5
             |
             --- ways({2,3,4}, remainingAmountForOtherCoins)


             amountForCoin1 = 0 + coin value(1) = 1

             ways({1}, amountForCoin1)
             |
             | remainingAmountForOtherCoins = amount-amountForCoin1 = 4
             |
             --- ways({2,3,4}, remainingAmountForOtherCoins)


             amountForCoin1 = 1 + coin value(1) = 2

             ways({1}, amountForCoin1)
             |
             | remainingAmountForOtherCoins = amount-amountForCoin1 = 3
             |
             --- ways({2,3,4}, remainingAmountForOtherCoins)

             amountForCoin1 = 2 + coin value(1) = 3

             ways({1}, amountForCoin1)
             |
             | remainingAmountForOtherCoins = amount-amountForCoin1 = 2
             |
             --- ways({2,3,4}, remainingAmountForOtherCoins)

             amountForCoin1 = 3 + coin value(1) = 4

             ways({1}, amountForCoin1)
             |
             | remainingAmountForOtherCoins = amount-amountForCoin1 = 1
             |
             --- ways({2,3,4}, remainingAmountForOtherCoins)

             amountForCoin1 = 4 + coin value(1) = 5

             ways({1}, amountForCoin1)
             |
             | remainingAmountForOtherCoins = amount-amountForCoin1 = 0
             |
             --- ways({2,3,4}, remainingAmountForOtherCoins)


            So, while loop for coin {1} till amountForCoin1 <= amount
            With different combinations of {1} and amountForCoin1, determine different combinations of remaining coins with remaining amount (amount-amountForCon1).

        */



        int amountRemainingWithCoin = 0;
        while (amountRemainingWithCoin <= amount) {
            int amountRemainingForOtherCoins = amount - amountRemainingWithCoin;

            int waysUsingRemainingCoins = count_brute_force(coins, coinsStartIndex + 1, coinsEndIndex, amountRemainingForOtherCoins);
            ways += waysUsingRemainingCoins;

            amountRemainingWithCoin += coins[coinsStartIndex];
        }

        return ways;

    }

    /*
        As you see in above recursive call, changing elements are coinsStartIndex and amount.
        So, you can memoize the result of recursive call with key containing coinsStartIndex and amount.
     */
    private static int count_top_down_dynamic_approach(int coins[], int coinsStartIndex, int coinsEndIndex, int amount, Map<String, Integer> coinsStartIndexAmountAndWaysMap) {

        String key = coinsStartIndex + "-" + amount;

        if (coinsStartIndexAmountAndWaysMap.containsKey(key)) {

            return coinsStartIndexAmountAndWaysMap.get(key);

        } else {

            int ways = 0;
            // if you don't have any coins, you cannot make any amount
            if (coins == null || coins.length == 0) {
                return ways;
            }

            // if you have coins, but target amount is 0, then there is only one way to make amount 0;
            if (amount == 0) {
                return ways + 1;
            }

            if (coinsStartIndex > coinsEndIndex) {
                return ways;
            }

            int amountRemainingWithCoin = 0;
            while (amountRemainingWithCoin <= amount) {
                int amountRemainingForOtherCoins = amount - amountRemainingWithCoin;


                int waysUsingRemainingCoins = count_top_down_dynamic_approach(coins, coinsStartIndex + 1, coinsEndIndex, amountRemainingForOtherCoins, coinsStartIndexAmountAndWaysMap);
                ways += waysUsingRemainingCoins;

                amountRemainingWithCoin += coins[coinsStartIndex];
            }

            coinsStartIndexAmountAndWaysMap.put(key, ways);

            return ways;
        }
    }
}
