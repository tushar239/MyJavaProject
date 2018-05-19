package algorithms._1array_stack_queue.geeksforgeeks.divide_and_concur.traversing_on_both_sides_of_mid.medium;

/*
    Collect all coins in minimum number of steps

    https://www.geeksforgeeks.org/collect-coins-minimum-number-steps/

    Given many stacks of coins which are arranged adjacently. We need to collect all these coins in the minimum number of steps where in one step we can collect one horizontal line of coins or vertical line of coins and collected coins should be continuous.

    e.g.

        heightOfStacksOfCoins[] = [2 3 2 5 4]

       [2   3   2   5   4]

                    O
                    O   O
            O       O   O
        O   O   O   O   O
        O   O   O   O   O

        There are two ways to collect the coins from the stacks.

        - vertically collect all the coins. It will require 5 steps because there are 5 elements in array.
        - find min height stack and collect that many coins horizontally (this will make at least one stack empty)

        [0  1  0    3   2]

                    O
                    O   O
            O       O   O

        you can recursively find min number of steps from start to mid(stack index that is empty now)
                                                 and from mid+1 to end


        Time Complexity:
                      size of input array
                    m(  7       )           --- 7*7 operations to find min height stack
                        |
            --------------------------
            |                         |
           m(3)                      m(3)   --- 3*3 + 3*3 operations to find min height stack
            |                         |
       -------------            -------------
       |            |           |           |
      m(1)          m(1)       m(1)          m(1)


    So, on each level of the recursive step, n^2 operations are happening. So, time complexity is O(n^2 * height of recursive tree) = O(n^2 log2 n)=O(n^2)
*/

public class _1CollectAllStacksOfCoinsInMinimumNumberOfSteps {

    public static void main(String[] args) {
        {
            int[] heightOfStacksOfCoins = {2, 1, 2, 5, 1};
            int minSteps = minSteps(heightOfStacksOfCoins, 0, heightOfStacksOfCoins.length - 1);
            System.out.println(minSteps);//4
        }

        {
            int[] heightOfStacksOfCoins = {2, 1, 2, 5, 1};
            int minSteps_without_manipulating_input_array = minSteps_without_manipulating_input_array(heightOfStacksOfCoins, 0, heightOfStacksOfCoins.length - 1, 0);
            System.out.println(minSteps_without_manipulating_input_array);//4
        }
    }

    private static int minSteps(int[] A, int start, int end) {
        if (start > end) return 0;

        if (start == end) {
            if (A[start] > 0) {// important
                return 1;
            }
            return 0;
        }

        int stepsToCollectAllCoinsVertically = (end - start) + 1; // if there are 5 stacks of coins, if you collect every stack one by one (vertically), it takes 5 steps.

        int minHeight = Integer.MAX_VALUE;
        int minHeightIndex = 0;
        for (int i = start; i <= end; i++) {
            if (A[i] < minHeight) {
                minHeight = A[i];
                minHeightIndex = i;
            }
        }

        int stepsToCollectAllPossibleCoinsHorizontally = minHeight;

        // collecting coins horizontally will reduce the height of each stack by number of coins in min height stack
        for (int i = start; i <= end; i++) {
            A[i] = A[i] - stepsToCollectAllPossibleCoinsHorizontally;
        }

        stepsToCollectAllPossibleCoinsHorizontally = stepsToCollectAllPossibleCoinsHorizontally +
                minSteps(A, start, minHeightIndex - 1) +
                minSteps(A, minHeightIndex + 1, end);

        return Math.min(stepsToCollectAllCoinsVertically, stepsToCollectAllPossibleCoinsHorizontally);
    }


    private static int minSteps_without_manipulating_input_array(int[] A, int start, int end, int collectedCoinsHorizontally) {
        if (start > end) return 0;

        if (start == end) {
            if (A[start] - collectedCoinsHorizontally > 0) {// important
                return 1;
            }
            return 0;
        }

        int stepsToCollectAllCoinsVertically = (end - start) + 1; // if there are 5 stacks of coins, if you collect every stack one by one (vertically), it takes 5 steps.

        int minHeight = Integer.MAX_VALUE;
        int minHeightIndex = 0;
        for (int i = start; i <= end; i++) {
            int height = A[i] - collectedCoinsHorizontally;
            if (height < minHeight) {
                minHeight = height;
                minHeightIndex = i;
            }
        }

        collectedCoinsHorizontally = minHeight;

        /*// collecting coins horizontally will reduce the height of each stack by number of coins in min height stack
        for (int i = start; i <= end; i++) {
            A[i] = A[i] - stepsToCollectAllPossibleCoinsHorizontally;
        }*/

        int stepsToCollectAllPossibleCoinsHorizontally = collectedCoinsHorizontally +
                minSteps_without_manipulating_input_array(A, start, minHeightIndex - 1, collectedCoinsHorizontally) +
                minSteps_without_manipulating_input_array(A, minHeightIndex + 1, end, collectedCoinsHorizontally);

        return Math.min(stepsToCollectAllCoinsVertically, stepsToCollectAllPossibleCoinsHorizontally);
    }

}
