package algorithms._1array_stack_queue.geeksforgeeks.pointers_sorting_stack_queue_string.triplets_problems;

/*
    Find triplets with max sum.

    https://www.geeksforgeeks.org/find-maximum-sum-triplets-array-j-k-ai-aj-ak/

    There are two ways to solve this problem.
    - O(n^3)     --- Brute-Force approach
    - O(n log n) --- sort an array and then take last 3 elements. It will form max sum.
    - O(n)       --- keep int[] maxNums = new int[3] and keep track of max numbers.

    Below code shows first approach, but it is easy to write third one also, just by keeping track of 3 max numbers by iterating an array just once.

    Important:
    Use this code to understand how to convert iterative code to recursive code.

    Time Complexity:

         n!
       -------
       k!(n-k)!

    When you need to find combinations of k=3 elements in such a way that there are no duplicate combinations, then use above formula.

*/
public class _1FindTripletWithMaxSum {
    public static void main(String[] args) {
        int[] A = {1, 2, 3, -5, 6, 7, -7, 9, 5, -8, 3, 10, 14, 15};
        {
            int tripletMaxSum = findTripletWithMaxSum_Iteratively(A);
            System.out.println(tripletMaxSum);
        }
        {
            int tripletMaxSum = findTripletWithMaxSum_Partial_Recursively(A, 0, A.length - 1);
            System.out.println(tripletMaxSum);
        }
        {
            int tripletMaxSum = findTripletWithMaxSum_Recursively(A, 0, 1, 2, A.length - 1);
            System.out.println(tripletMaxSum);
        }

    }

    private static int findTripletWithMaxSum_Iteratively(int[] A) {

        int length = A.length - 1;
        int maxSum = 0;

        for (int i = 0; i <= length; i++) {
            for (int j = i + 1; j <= length; j++) {
                for (int k = j + 1; k <= length; k++) {
                    int sum = A[i] + A[j] + A[k];
                    if (sum > maxSum) {
                        System.out.println(A[i] + "," + A[j] + "," + A[k]);
                        maxSum = sum;
                    }
                }

            }
        }
        return maxSum;
    }

    private static int findTripletWithMaxSum_Partial_Recursively(int[] A, int start, int end) {
        if (start == end) return 0;
        if (start > end - 2) return 0;
/*
        if(end < start+2) return 0;
        if(end == start+3) return A[start]+A[start+1]+A[start+2];
*/

        int element = A[start];

        int maxSum = 0;
        for (int j = start + 1; j <= end; j++) {
            for (int k = j + 1; k <= end; k++) {
                int sum = element + A[j] + A[k];
                if (sum > maxSum) {
                    maxSum = sum;
                }
            }
        }

        return Math.max(maxSum, findTripletWithMaxSum_Partial_Recursively(A, start + 1, end));
    }

    private static int findTripletWithMaxSum_Recursively(int[] A, int start, int start2, int start3, int end) {

        if (start > end - 2 || start2 > end - 1 || start3 > end) {
            return 0;
        }

        int sum = A[start] + A[start2] + A[start3];


        return Math.max(Math.max(Math.max(sum, findTripletWithMaxSum_Recursively(A, start, start2, start3 + 1, end)),
                findTripletWithMaxSum_Recursively(A, start, start2 + 1, start3 + 1, end)),
                findTripletWithMaxSum_Recursively(A, start + 1, start2 + 1, start3 + 1, end)
        );

    }
}
