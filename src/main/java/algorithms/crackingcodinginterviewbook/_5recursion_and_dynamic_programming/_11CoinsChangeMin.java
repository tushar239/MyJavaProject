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

        // I have covered all test cases needed to debug the code
        // First all tests that goes through exit conditions
        // Then all test cases that goes through conditions related to currentCoin in the code
/*
        // test cases related to exit conditions in the code

        int amount=8;
        int[] coins = {8}; // O/P: 1


        int amount=8;
        int[] coins = {9};//O/P:0


        int amount=8;
        int[] coins = {1};//O/P:8 when you can use multiple coins of same value. O/P: 0 when you can use only one coin of same value.


        int amount=8;
        int[] coins = {3};//O/P:0

        // test cases related to currentCoin in the code

        int amount=8;
        int[] coins = {8,6};//O/P:1


        int amount=8;
        int[] coins = {9,6};//O/P:0


        int amount=8;
        int[] coins = {9,8};//O/P:1
*/

        int amount = 8;
        int[] coins = {1, 6}; //O/P:3 (1,1,6) when you can use multiple coins of same value. O/P: 0 when you can use only one coin of same value.

/*
        int amount=8;
        int[] coins = {3,6}; //O/P:0

        int amount=8;
        int[] coins = {3,8}; //O/P:1
*/

/*
        //int amount = 17;
        //int[] coins = {2,6,7}; // O/P: 0

        //int amount = 15;
        //int[] coins = {2, 6, 7}; // O/P:3

        //int amount = 13;
        //int[] coins = {2, 6, 7}; // O/P:2
*/
        {
            int minRequiredCoins = minCoins_my_way_infinite_coins_of_one_value_case(coins, 0, coins.length - 1, amount);
            System.out.println(minRequiredCoins);
        }

        {

            int minRequiredCoins = minCoins_my_way_one_coin_of_one_value_case(coins, 0, coins.length - 1, amount);
            System.out.println(minRequiredCoins);
        }

        /*System.out.println();

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
        System.out.println();*/
    }


    /*
        This algorithm is for the situation when only one coin of the same value can be used.

        Bottom-Up:

        IMPORTANT:
        As you see in Brute-Force approach, you are increasing start index of coins and reducing amount in recursive call.
        So, Bottom-Up matrix should do the same. Increase coins and reduce amount

        coins=(1,7) amount=8

                                        amount
             8       7        6            5          4           3         2       1
         ----------------------------------------------------------------------------
  c    1 |   2       0        0            0          0           0         0       1
  o      |
  i    7 |   0       1        0            0          0           0         0       0
  n      |
  s

        Answer = 2 in first cell
        You need min 2 coins from (1,7) to make amount-8



        coins=(1,6) amount=8

                                        amount
             8       7        6            5          4           3         2       1
         ----------------------------------------------------------------------------
  c    1 |   0       2        0            0          0           0         0       1
  o      |
  i    6 |   0       0        1            0          0           0         0       0
  n      |
  s

        Answer = 0 in first cell
        You cannot make amount=8 using coins (1,6)

     */
    private static int minCoins_my_way_one_coin_of_one_value_case(int[] coins, int startIndex, int endIndex, int amount) {

        if (coins.length == 0) return 0;

        if (startIndex == endIndex) {
            int currentCoin = coins[startIndex];

            if (currentCoin == amount) { // coins=(8) amount=8
                return 1;
            }
            if (currentCoin > amount) { // coins=(9) amount=8
                return 0;
            }
            if (currentCoin < amount) { // coins=(1) amount=8
                return 0; // this code changes when you can use infinite number of same coin
            }
        }

        // IMPORTANT:
        // Even though, amount is changing in recursive call, you don't need an exit condition for amount because amount will never become 0 or less than 0
        // amount is changing in recursive call when currentCoin < amount and it is doing amount-currentCoin. It means that amount will never become 0 or less than 0
        /*
        if (amount == 0) {
            return 0;
        }
        */

        int currentCoin = coins[startIndex];

        int minUsingCurrentCoin = 0;

        if (currentCoin == amount) {// coins=(8,9) amount=8
            // WRONG - if you see recursive call, you are trying to use remaining array (8) to make amount (8)
            // You are trying to make full amount using remaining array. It means that you will not be including currentCoin in coins that can make full amount.
            // Here, code should be all about including currentCoin in computation.
            /*
            minUsingCurrentCoin = 1
            int minUsingRemainingCoinsToMakeFullAmount = minCoins_my_way_one_coin_of_one_value_case(coins,startIndex+1, endIndex, amount)
            if(minUsingRemainingCoinsToMakeFullAmount > 0) {
                minUsingCurrentCoin = 1 + minUsingRemainingCoinsToMakeFullAmount
            }*/

            minUsingCurrentCoin = 1;
        } else if (currentCoin > amount) {// coins=(9,8) amount=8
            minUsingCurrentCoin = 0;
            // WRONG - if you see recursive call, you are trying to use remaining array (8) to make amount (8)
            // You are trying to make full amount using remaining array. It means that you will not be including currentCoin in coins that can make full amount.
            // Here, code should be all about including currentCoin in computation.
            // minUsingCurrentCoin = 0 + minCoins_my_way_one_coin_of_one_value_case(coins,startIndex+1, endIndex, amount);
        } else {
            /*
             case 1:
                 coins = (1,6) amount=8
                 currentCoin=1
                 1<8
                 coinsRequiredUsingRemainingCoins=(6) to make amount (8-1=7) = 0
                 coinsRequiredUsingRemainingCoinsAndAmount is 0, so by including current coin (1), you will not be able to make amount=8
                 so, you cannot use (1) to make amount=8
             case 2:
                coins = (1,7) amount=8
                currentCoin=1
                1<6
                coinsRequiredUsingRemainingCoins (7) And Amount (8-1=7) = 1
                so, coinsRequiredUsingCurrentCoin = 1 + coinsRequiredUsingRemainingCoinsAndAmount = 2

            */
            // this code changes when you can use infinite number of same coin
            int minUsingRestOfCoinsAndRestOfAmount = minCoins_my_way_one_coin_of_one_value_case(coins, startIndex + 1, endIndex, amount - currentCoin);
            if (minUsingRestOfCoinsAndRestOfAmount == 0) {
                minUsingCurrentCoin = 0;
            } else {
                minUsingCurrentCoin = 1 + minUsingRestOfCoinsAndRestOfAmount;
            }

        }

        // coins = (8,6) amount=8
        // minUsingCurrentCoin 8 to make amount 8 = 1
        // minWaysUsingRestOfTheCoins (6) to make amount 8 = 0
        // so, you cannot just blindly return Math.min(minUsingCurrentCoin, minWaysUsingRestOfTheCoins)
        // it will return 0 instead of 1
        int minWaysUsingRestOfTheCoins = minCoins_my_way_one_coin_of_one_value_case(coins, startIndex + 1, endIndex, amount);

        if (minWaysUsingRestOfTheCoins == 0 && minUsingCurrentCoin > 0) {
            return minUsingCurrentCoin;
        } else if (minWaysUsingRestOfTheCoins > 0 && minUsingCurrentCoin == 0) {
            return minWaysUsingRestOfTheCoins;
        }
        return Math.min(minUsingCurrentCoin, minWaysUsingRestOfTheCoins);

    }

    /*
    This algorithm is for the situation when infinite number of coins of same value can be used
 */
    private static int minCoins_my_way_infinite_coins_of_one_value_case(int[] coins, int startIndex, int endIndex, int amount) {

        if (coins.length == 0) return 0;

        if (startIndex == endIndex) {
            int currentCoin = coins[startIndex];

            if (currentCoin == amount) {// coins=(8) amount=8
                return 1;
            }
            if (currentCoin > amount) {// coins=(9) amount=8
                return 0;
            }
            if (currentCoin < amount) {
                // IMPORTANT: This code assumes that you can use only 1 coin of the same value (you can't use infinite number of coins of the same value).
                //return 0;

                // This code assumes that you can use infinite number of coins of the same value.
                // e.g. currentCoin=1 and amount=8, then 8 coins of 1s can be used
                if (amount % currentCoin == 0) { // coins=(1) amount=8 should return 8 because you can use 8 coins of value 1 to make amount=8
                    return amount / currentCoin;
                }
                return 0; // coins=(3) amount=8 should return 0 because you can't use 1 or more coins of value 3 to make amount=8
            }
        }

        // IMPORTANT
        // You don't need any coin to make amount=0
        // By seeing recursive call, you will figure out that amount can reach to 0. So, you need an exit condition for amount==0
        if (amount == 0) {
            return 0;
        }

        int currentCoin = coins[startIndex];

        int minUsingCurrentCoin = 0;

        if (currentCoin == amount) {
            minUsingCurrentCoin = 1;
        } else if (currentCoin > amount) {
            minUsingCurrentCoin = 0;
        } else {
            /*
             if currentCoin=1 and amount=8, then maxNumberOfCoinsThatCanBeUsedToMakeAmount will be 8.
             if currentCoin=3 and amount=8, then maxNumberOfCoinsThatCanBeUsedToMakeAmount be 2
             you need to consider both these situations for coming up with the correct code

             Case 1:
             coins = (1,6) amount=8
             currentCoin=1. So, maxNumberOfCoinsThatCanBeUsedToMakeAmount = 8

             coin   numberOfCurrentCoins(i)   amountConsumedByCurrentCoin      coinsUsedFromRemaining           totalCoinsUsed
                                                                              remaining coins    amount
             1          1                               1                          (6)             7    =  0            0 (when you cannot make remaining amount using remaining coins, then you cannot make total amount including current coin also)
             1          2                               2                          (6)             6    =  1        2+1=3
             1          3                               3                          (6)             5    =  0            0
             1          4                               4                          (6)             4    =  0            0
             1          5                               5                          (6)             3    =  0            0
             1          6                               6                          (6)             2    =  0            0
             1          7                               7                          (6)             1    =  0            0
             1          8                               8                          (6)             0    =  0            8 (special condition - even though you could not make remaining amount using remaining coins, you could make total amount using current coin)

             Case 2:
             coins = (3,6) amount=8
             currentCoin=3. So, maxNumberOfCoinsThatCanBeUsedToMakeAmount = 2

             coin   numberOfCurrentCoins(i)   amountConsumedByCurrentCoin      coinsUsedFromRemaining           totalCoinsUsed
                                                                              remaining coins    amount
             3          1                               3                          (6)             3    =  0            0 (when you cannot make remaining amount using remaining coins, then you cannot make total amount including current coin also)
             3          2                               6                          (6)             2    =  0            0

           */
            int maxNumberOfCoinsThatCanBeUsedToMakeAmount = amount / currentCoin;

            int min = 0;

            for (int i = 1; i <= maxNumberOfCoinsThatCanBeUsedToMakeAmount; i++) {
                int amountConsumedByCurrentCoin = i * currentCoin;

                int minFromRemaining = minCoins_my_way_infinite_coins_of_one_value_case(coins, startIndex + 1, endIndex, amount - amountConsumedByCurrentCoin);

                if (minFromRemaining == 0) {// If I could not make remaining amount using remaining coins, then I cannot make total amount including current coin (except one special condition)
                    if (amountConsumedByCurrentCoin == amount) {// special condition - even though you could not make remaining amount using remaining coins, you could make total amount using current coin
                        min = i;
                    } else {
                        min = 0;
                    }
                } else {
                    min = i + minFromRemaining;
                }

                if (minUsingCurrentCoin == 0 && min > 0) {
                    minUsingCurrentCoin = min;
                } else if (minUsingCurrentCoin > 0 && min == 0) {
                    // don't change minUsingCurrentCoin
                } else {
                    if (min < minUsingCurrentCoin) {
                        minUsingCurrentCoin = min;
                    }
                }
            }
        }

        int minWaysUsingRestOfTheCoins = minCoins_my_way_infinite_coins_of_one_value_case(coins, startIndex + 1, endIndex, amount);

        if (minWaysUsingRestOfTheCoins == 0 && minUsingCurrentCoin > 0) {
            return minUsingCurrentCoin;
        } else if (minWaysUsingRestOfTheCoins > 0 && minUsingCurrentCoin == 0) {
            return minWaysUsingRestOfTheCoins;
        }
        return Math.min(minUsingCurrentCoin, minWaysUsingRestOfTheCoins);

    }

    // ------------------------------------------------------------------------------------------------------------

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
            if (col <= coins[0]) {
                memo[1][col] = 1;
            } else {
                int quotient = col / coins[0];

                memo[1][col] = quotient;

                int remainder = col % coins[0];
                if (remainder > 0) {
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
            String str = StringUtils.rightPad(StringUtils.leftPad("" + col, 5), 5);
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
            if (row - 1 < 0) {
                String str = StringUtils.rightPad(StringUtils.leftPad("0 |", 5), 5);
                System.out.print(str);
            } else {
                String str = StringUtils.rightPad(StringUtils.leftPad("" + coins[row - 1] + " |", 5), 5);
                System.out.print(str);
            }

            for (int col = 0; col < memo[row].length; col++) {
                String str = StringUtils.rightPad(StringUtils.leftPad("" + memo[row][col], 5), 5);
                System.out.print(str);
            }
            System.out.println();
        }
    }

    // https://github.com/mission-peace/interview/blob/master/src/com/interview/dynamic/CoinChangingMinimumCoin.java
    public static int minCoinDynamic(int amount, int[] coins) {
        int[] coinReq = new int[amount + 1]; // this will store the optimal solution
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
            coinReq[amt] = -1;
            for (int j = 1; j < CC.length; j++) {
                if (CC[j] > 0 && (coinReq[amt] == -1 || coinReq[amt] > CC[j])) {
                    coinReq[amt] = CC[j];
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
    private static int minCoins_brute_force(int coins[], int coinsEndIndex, int amount) {
        // base case
        if (amount == 0) return 0;

        // Initialize result
        int res = Integer.MAX_VALUE;

        // Try every coin that has smaller value than amount
        for (int i = 0; i < coinsEndIndex; i++) {
            if (coins[i] <= amount) {
                int sub_res = minCoins_brute_force(coins, coinsEndIndex, amount - coins[i]);

                // Check for INT_MAX to avoid overflow and see if
                // result can minimized
                if (sub_res != Integer.MAX_VALUE && sub_res + 1 < res)
                    res = sub_res + 1;
            }
        }
        return res;
    }

}
