package algorithms.crackingcodinginterviewbook._5recursion_and_dynamic_programming;

/*
Find minimum number of coins required to get specified amount.
e.g. you have infinite flow of coins 1,5,6,8 and you need to get amount 11 by using min number of these coins.


This algorithm is an extension of CoinsChange.java

Do CoinsChange.java first
and then
Watch 'Coin Changing Minimum Number of Coins Bottom-Up Dynamic Programming approach.mp4'
*/

import org.apache.commons.lang.StringUtils;

public class _11CoinsChangeMin {

    public static void main(String[] args) {
        int amount = 17;
        int[] coins = {2,6,7};

        /*int minCoins = minCoins(coins, 0, coins.length - 1, amount);
        System.out.println(minCoins);*/
        {
            int minRequiredCoins = minCoins_bottom_up_dynamic_approach(coins, amount);
            System.out.println();
            System.out.println(minRequiredCoins);
        }
        System.out.println();
        {
            int minRequiredCoins = minCoinDynamic(amount, coins);
            System.out.println(minRequiredCoins);
        }
        System.out.println();
        {
            int minRequiredCoins = minCoins_brute_force(coins, coins.length - 1, amount);
            System.out.println(minRequiredCoins);
        }
        System.out.println();
    }

    /*
          amount
               0       1        2            3          4           5
           ----------------------------------------------------------------
    c    0 |   0       0        0            0          0           0
    o      |
    i    1 |   0       1        2            3          4           5
    n      |           |        |
    s                  v        v
         2 |   0       1    min(2,0)+1   min(3,1)+1  min(4,1)+1  min(5,2)+1
           |   ^                  |          |
               |                  |          |
               --------------------          |
           |                                 v
         3 |   0       1        2        min(2,0)+1  min(2,1)+1  min(3,2)+1
               |                               ^
               |                               |
               ---------------------------------

        If you understand CoinsChange.java, you will be able to understand above matrix also.

     */
    private static int minCoins_bottom_up_dynamic_approach(int[] coins, int amount) {
        if (coins == null || coins.length == 0) return 0;
        if (amount < 0) return 0;

        int memo[][] = new int[coins.length + 1][amount + 1];

        // populate first row
        for (int col = 0; col <= amount; col++) {
            memo[0][col] = 0;// Important. always initialize 1st row with 1s
        }

        // populate first col
        for (int row = 0; row < coins.length; row++) {
            memo[row][0] = 0;// Important. initialize 1st col with 1s or 0s as per your need as explained in method comment
        }

        /*
        populate second row

         if amount(col) <= coin value, then one coin is required with that value to make that amount
         if amount(col) > coin value, then
         total coins required = amount/coin_value, if remainder=0, otherwise amount/coin_value+1
         */
        for (int col = 1; col <= amount; col++) {
            if(col <= coins[0]) {
                memo[1][col] = 1;
            } else {
                int quotient = col / coins[0];

                memo[1][col] = quotient;

                int remainder = col % coins[0];
                if(remainder > 0) {
                    memo[1][col] += 1;
                }
            }
        }

        // start iterating from second row
        for (int row = 2; row < memo.length; row++) {
            // start iterating from second col
            for (int col = 1; col < memo[row].length; col++) {

                int coin = coins[row - 1];

                // formula to find min required coins for an amount at memo[row][col]
                if (coin > col) { // if coin_value > amount
                    memo[row][col] = memo[row - 1][col]; // reserve prev row's value (value determined for prev coins)
                } else {
                    memo[row][col] = Math.min(memo[row - 1][col], memo[row][col - coin]) + 1; // min(value determined by prev coins - prev row, value at amount=current_amount-coin_value) + 1
                }
            }
        }

        printMemo(coins, memo);

        return memo[memo.length - 1][memo[0].length - 1]; // final value is stored in last cell
    }

    private static void printMemo(int[] coins, int[][] memo) {
        String str1 = StringUtils.rightPad(StringUtils.leftPad("", 5), 5);
        System.out.print(str1);
        for (int col = 0; col < memo[0].length; col++) {
            String str = StringUtils.rightPad(StringUtils.leftPad(""+col, 5), 5);
            System.out.print(str);
        }
        System.out.println();
        System.out.print(str1);
        for (int col = 0; col < memo[0].length; col++) {
            String str = StringUtils.rightPad(StringUtils.leftPad("-", 5), 5);
            System.out.print(str);
        }
        System.out.println();
        for (int row = 0; row < memo.length; row++) {
            if(row-1 < 0) {
                String str = StringUtils.rightPad(StringUtils.leftPad("0 |", 5), 5);
                System.out.print(str);
            } else {
                String str = StringUtils.rightPad(StringUtils.leftPad(""+coins[row - 1]+" |", 5), 5);
                System.out.print(str);
            }

            for (int col = 0; col < memo[row].length; col++) {
                String str = StringUtils.rightPad(StringUtils.leftPad(""+memo[row][col], 5), 5);
                System.out.print(str);
            }
            System.out.println();
        }
    }

    // https://github.com/mission-peace/interview/blob/master/src/com/interview/dynamic/CoinChangingMinimumCoin.java
    public static int minCoinDynamic(int amount, int[] coins) {
        int[] coinReq = new int[amount+1]; // this will store the optimal solution
        // for all the values -- from 0 to
        // given amount.
        int[] CC = new int[coins.length]; // resets for every smaller problems
        // and minimum in it is the optimal
        // solution for the smaller problem.
        coinReq[0] = 0; // 0 coins are required to make the change for 0
        // now solve for all the amounts
        for (int amt = 1; amt <= amount; amt++) {
            for (int j = 0; j < CC.length; j++) {
                CC[j] = -1;
            }
            // Now try taking every coin one at a time and fill the solution in
            // the CC[]
            for (int j = 0; j < coins.length; j++) {
                if (coins[j] <= amt) { // check if coin value is less than
                    // amount
                    CC[j] = coinReq[amt - coins[j]] + 1; // if available,
                    // select the
                    // coin and add
                    // 1 to solution
                    // of
                    // (amount-coin
                    // value)
                }
            }
            //Now solutions for amt using all the coins is stored in CC[]
//			reserve out the minimum (optimal) and store in coinReq[amt]
            coinReq[amt]=-1;
            for(int j=1;j<CC.length;j++){
                if(CC[j]>0 && (coinReq[amt]==-1 || coinReq[amt]>CC[j])){
                    coinReq[amt]=CC[j];
                }
            }
        }
        //return the optimal solution for amount
        return coinReq[amount];

    }

    // https://www.youtube.com/watch?v=F28xN-S1SmI
    // https://www.geeksforgeeks.org/find-minimum-number-of-coins-that-make-a-change/
    // coinsEndIndex is size of coins array (number of different coins)
    // I am not able to understand this
    private static int minCoins_brute_force(int coins[], int coinsEndIndex, int amount)
    {
        // base case
        if (amount == 0) return 0;

        // Initialize result
        int res = Integer.MAX_VALUE;

        // Try every coin that has smaller value than amount
        for (int i=0; i<coinsEndIndex; i++)
        {
            if (coins[i] <= amount)
            {
                int sub_res = minCoins_brute_force(coins, coinsEndIndex, amount-coins[i]);

                // Check for INT_MAX to avoid overflow and see if
                // result can minimized
                if (sub_res != Integer.MAX_VALUE && sub_res + 1 < res)
                    res = sub_res + 1;
            }
        }
        return res;
    }
}
