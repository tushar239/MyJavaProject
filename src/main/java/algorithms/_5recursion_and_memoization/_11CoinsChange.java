package algorithms._5recursion_and_memoization;

public class _11CoinsChange {
    public static void main(String[] args) {
        int[] coins = {1,2,3,4};
        int amount = 4;
        int count = count(coins, coins.length-1, amount);
        System.out.println(count);
    }
    // https://www.geeksforgeeks.org/dynamic-programming-set-7-coin-change/
    private static int count( int coins[], int coinsIndex, int amount )
    {
        // If amount is 0 then there is 1 solution (do not include any coin)
        if (amount == 0)
            return 1;

        // If amount is less than 0 then no solution exists
        if (amount < 0)
            return 0;

        // If there are no coins and amount is greater than 0, then no solution exist
        if (coinsIndex <=0 && amount >= 1)
            return 0;

        // count is sum of solutions (i) including coins[coinsIndex-1]
        //                           (ii) excluding coins[coinsIndex-1]
        return count( coins, coinsIndex - 1, amount ) + count( coins, coinsIndex, /*amount-coinsIndex*/amount-coins[coinsIndex-1] );
    }
}
