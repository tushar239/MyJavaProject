package algorithms._1array_stack_queue.geeksforgeeks.pointers_sorting_stack_queue_string.medium;

/*
    k-th missing element in sorted array

    https://www.geeksforgeeks.org/k-th-missing-element-in-sorted-array/

    Given an increasing sequence a[], we need to find the K-th missing contiguous element in the increasing sequence which is not present in the sequence. If no k-th missing element is there output -1.

    Examples:

    Input :   a[] = {2, 3, 5, 9, 10};
                k = 1;
    Output : 4
    Explanation: Missing Element in the increasing sequence are {4 and 6, 7, 8}. So k-th missing element is 4

    Input : a[] = {2, 3, 5, 9, 10, 11, 12};
              k = 4;
    Output : 8
    Explanation: missing element in the increasing
    sequence are {4, 6, 7, 8}  so k-th missing
    element is 8
*/
public class _12KthMissingElementInSortedArray {

    public static void main(String[] args) {

        int A[] = {2, 3, 6, 7, 11, 12};
        findKthMissing(A, 4);//9  --- missing elements between 3 and 6 =(4,5). missing elements between 7 and 11=(8,9,10). So, 4th missing element is 9.
    }

    private static void findKthMissing(int[] A, int k) {

        int prevDiffs = 0;
        int totalDiffs = 0;

        for (int i = 0; i < A.length - 1; i++) {

            int diffs = (A[i + 1] - A[i])-1;// e.g. 3,5  diffs=1

            prevDiffs = totalDiffs;

            totalDiffs += diffs;

            if (totalDiffs < k) {
                continue;
            }

            if (totalDiffs == k) {
                System.out.println(A[i + 1] - 1);
                return;
            }

            int posOfKthMissingNumber = k - prevDiffs;
            System.out.println(A[i] + posOfKthMissingNumber);

            break;

        }
    }
}
