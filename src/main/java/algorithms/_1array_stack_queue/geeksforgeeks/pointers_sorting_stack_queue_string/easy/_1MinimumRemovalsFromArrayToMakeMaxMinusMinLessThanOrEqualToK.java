package algorithms._1array_stack_queue.geeksforgeeks.pointers_sorting_stack_queue_string.easy;

import java.util.Arrays;

/*
Minimum removals from array to make max – min <= K

https://www.geeksforgeeks.org/minimum-removals-array-make-max-min-k/

Given N integers and K, find the minimum number of elements that should be removed such that Amax-Amin<=K. After removal of elements, Amax and Amin is considered among the remaining elements.
Examples:

Input : a[] = {1, 3, 4, 9, 10, 11, 12, 17, 20}      k = 4
Output : 5
Explanation: Remove 1, 3, 4 from beginning
and 17, 20 from the end.

Input : a[] = {1, 5, 6, 2, 8}       K=2
Output : 3
Explanation: There are multiple ways to
remove elements in this case.
One among them is to remove 5, 6, 8.
The other is to remove 1, 2, 5


0  1  2  3  4   5   6   7   8
1, 3, 4, 9, 10, 11, 12, 17, 20      k=4
                        E   S

    20-17 = 3 <= k

    FS=8
    FE=7

1, 3, 4, 9, 10, 11, 12, 17, 20
                     E      S

    20-12 = 8 > k, so next time change S


1, 3, 4, 9, 10, 11, 12, 17, 20
                     E   S

    17-12 = 5 > k, so next time change S

1, 3, 4, 9, 10, 11, 12, 17, 20
                 E   S

    12-11 = 1 <= k, but S-E = FS-FE, so no change in FS and FE

1, 3, 4, 9, 10, 11, 12, 17, 20
             E      S

    12-10 = 2 <= k, but S-E > FS-FE

    FS=6
    FE=4

1, 3, 4, 9, 10, 11, 12, 17, 20
         E          S

    12-9 = 3 <= k, but S-E > FS-FE

    FS=6
    FE=3

1, 3, 4, 9, 10, 11, 12, 17, 20
      E              S

    12-4 = 8 > k, so next time change S

1, 3, 4, 9, 10, 11, 12, 17, 20
             E   S

and so on.....

*/
public class _1MinimumRemovalsFromArrayToMakeMaxMinusMinLessThanOrEqualToK {

    public static void main(String[] args) {
        {
            int A[] = {1, 3, 4, 9, 10, 11, 12, 17, 20};
            int k = 4;

            int totalMinRemovalsRequired = minRemovals(A, k);
            System.out.println(totalMinRemovalsRequired);//5
        }
        {
            int A[] = {1, 5, 6, 2, 8};
            int k = 2;

            int totalMinRemovalsRequired = minRemovals(A, k);
            System.out.println(totalMinRemovalsRequired);//3
        }
    }

    private static int minRemovals(int[] A, int k) {
        Arrays.sort(A); // O(n log n)

        int finalStart = A.length - 1;
        int finalEnd = A.length - 1;

        // O(n^2)
        for (int start = A.length - 1; start >= 0; start--) {

            for (int end = start - 1; end >= 0; end--) {

                if ((A[start] - A[end]) <= k) {
                    if ((finalStart - finalEnd) < (start - end)) {
                        finalStart = start;
                        finalEnd = end;
                    }
                } else {
                    break;
                }

            }
        }

        if (finalStart > finalEnd) {
            return ((A.length - 1) - finalStart) + finalEnd;
        }
        return 0;
    }
}
