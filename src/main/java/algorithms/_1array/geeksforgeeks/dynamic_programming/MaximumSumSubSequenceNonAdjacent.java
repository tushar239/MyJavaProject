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

    This algorithm is based on Dynamic Programming and you need to MEMORIZE the approach.

    What is Dynamic Programming?
    If you use previously calculated result to calculate current result, you are using dynamic programming.

    Watch this video to understand the algorithm
    https://www.youtube.com/watch?v=UtGtF6nc35g
*/

public class MaximumSumSubSequenceNonAdjacent {

    public static void main(String[] args) {
        int A[] = {4, 1, 1, 4, 2, 1};
        int maxSum = findMaxSum(A);
        System.out.println(maxSum);//9 = 4+4+1
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
}
