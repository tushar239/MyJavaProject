package algorithms._1array.geeksforgeeks.dynamic_programming;

/*

Maximum sum such that no two elements are adjacent

https://www.geeksforgeeks.org/maximum-sum-such-that-no-two-elements-are-adjacent/

Given an array of positive numbers, find the maximum sum of a subsequence with the constraint that no 2 numbers in the sequence should be adjacent in the array. So 3 2 7 10 should return 13 (sum of 3 and 10) or 3 2 5 10 7 should return 15 (sum of 3, 5 and 7).Answer the question in most efficient way.

Examples :

    Input : arr[] = {5, 5, 10, 100, 10, 5}
    Output : 110

    Input : arr[] = {1, 2, 3}
    Output : 4

    Input : arr[] = {1, 20, 3}
    Output : 20


Solution:

    I would say that this algorithm is PARTIALLY using  Dynamic Programming approach because
    it looks like NP-complete problem
    +
    it needs to MEMORIZE the previous values to calculate current value .

    But it is not either using the recursion or not trying to avoid it.

    When to use Dynamic Programming?
    - You are trying to figure out n! kind of problem
    - If you use previously calculated result to calculate current result, you are using dynamic programming.

        Dynamic Programming can be done in two ways

        - Top-Bottom approach (it memoizes previously calculated values, but doesn't avoid recursion)
        - Bottom-Up approach (it memoizes previously calculated values and also avoids recursion)
          This requires you to build a memoization table.
          You can create memoization table only if you know the max value (max weight that your knapsack can handle/max sum that you need to achieve etc.)

    Watch this video to understand the algorithm
    https://www.youtube.com/watch?v=UtGtF6nc35g
*/

public class MaximumSumSubSequenceNonAdjacent {

    public static void main(String[] args) {
        int A[] = {4, 1, 1, 4, 2, 1};
        int maxSum = findMaxSum(A);
        System.out.println(maxSum);//9 = 4+4+1

        Use maxSum_recursion = findMaxSum_recursion(A, 0, A.length - 1);
        System.out.println(maxSum_recursion.maxSum);
    }

    private static int findMaxSum(int[] A) {
        if (A == null || A.length == 0) return 0;
        if (A.length == 1) return A[0];
        if (A.length == 2) return Math.max(A[0], A[1]);

        // Now the actual algorithm starts

        int include = A[0]; // consider first element to be part of max sum
        int exclude = 0; // if you consider first element to be a part of max sum, previous element cannot be a part of max sum.

        for (int i = 1; i < A.length; i++) {
            // Now let's think how the values of include and exclude will change, if you want to include current element in max sum.
            int temp = include;
            include = Math.max(include, exclude + A[i]); // you can consider current element to include in max sum, if previously calculated 'include' is less than 'exclude+current element'
            exclude = temp; // if you consider current element to include in max sum, then you need to 'exclude' previous 'include'.
        }

        return Math.max(include, exclude);
    }

    private static class Use {
        private boolean firstElementUsed;
        private int maxSum;

        public Use(boolean firstElementUsed, int maxSum) {
            this.firstElementUsed = firstElementUsed;
            this.maxSum = maxSum;
        }

        public boolean isFirstElementUsed() {
            return firstElementUsed;
        }

        public int getMaxSum() {
            return maxSum;
        }
    }

    private static Use findMaxSum_recursion(int[] A, int start, int end) {
        //if (A == null || (start > end)) return new Use(false, 0);
        if (start == end) return new Use(true, 0);
        if ((end - start) == 1) {
            int max = Math.max(A[start], A[end]);
            if(max== A[start]) {
                return new Use(true, A[start]);
            }
            return new Use(false, A[end]);
        }
        if ((end - start) == 2) {
            if(A[start]+A[end] >= A[start+1]) {
                return new Use(true, A[start]+A[end]);
            }
            return new Use(false, A[start+1]);
        }

        int first = A[start];
        Use used = findMaxSum_recursion(A, start + 1, end);
        int maxSumFromRemainingArray = used.maxSum;
        if(!used.firstElementUsed) {
            if(maxSumFromRemainingArray+first > maxSumFromRemainingArray) {
                return new Use(true, maxSumFromRemainingArray+first);
            }
            return new Use(false, maxSumFromRemainingArray);
        }
        return new Use(false, maxSumFromRemainingArray);
    }
}
