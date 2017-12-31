package algorithms._5recursion_and_dynamic_programming;

/*
Find minimum number of coins required to get specified amount.
e.g. you have infinite flow of coins 1,5,6,8 and you need to get amount 11 by using min number of these coins.


This algorithm is an extension of CoinsChange.java

Do CoinsChange.java first
and then
Watch 'Coin Changing Minimum Number of Coins Dynamic programming.mp4'
*/

public class _11CoinsChangeMin {

    private static int minCoins(int[] coins, int coinsStartIndex, int coinsEndIndex, int startAmount, int endAmount) {

        int minCoins = 0;

        if(coins == null || coins.length == 0) {
            return minCoins;
        }

        if(coinsStartIndex > coinsEndIndex) {
            return minCoins;
        }

        if(startAmount > endAmount) {
            return minCoins;
        }

        if(startAmount == 0) {
            return 0; // important. It cannot be determined without drawing a memoization matrix for bottom-up dynamic programming.
        }

        if(coinsStartIndex == 0) {
            return startAmount;// important. It cannot be determined without drawing a memoization matrix for bottom-up dynamic programming.
        }

return 0;
    }
}
