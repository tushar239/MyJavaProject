package algorithms._1array.geeksforgeeks.pointers_sorting_stack.medium;

/*
Count Size of Strictly Increasing Biggest Subarray

https://www.geeksforgeeks.org/count-strictly-increasing-subarrays/

Given an array of integers, count number of subarrays (of size more than one) that are strictly increasing.

Expected Time Complexity : O(n)
Expected Extra Space: O(1)

Examples:

    Input: arr[] = {1, 4, 3}
    Output: 1
    There is only 1 subarray {1, 4}

    Input: arr[] = {1, 2, 3, 4}
    Output: 6
    There are 6 subarrays {1, 2}, {1, 2, 3}, {1, 2, 3, 4}
                          {2, 3}, {2, 3, 4} and {3, 4}


    Input: arr[] = {1, 2, 2, 4}
    Output: 2
    There are 2 subarrays {1, 2} and {2, 4}


Questions for interviewer?
can array have duplicates?

*/
public class _1CountStrictlyIncreasingSubarrays {

    public static void main(String[] args) {
        {
            int A[] = {1, 4, 3};
            int count = count(A);
            System.out.println(count);//1 - {1,4}
        }
        {
            int A[] = {1, 2, 3, 4};
            int count = count(A);
            System.out.println(count);//6
            // {1,2}
            // {2,3}, {1,2,3}
            // {3,4}, {2,3,4}, {1,2,3,4}
        }

        {
            int A[] = {1, 2, 2, 4, 5};
            int count = count(A);
            System.out.println(count);//4
            // {1,2}
            // {2, 4}
            // {4,5}, {2,4,5}
        }
    }

    // O(n)
    private static int count(int[] A) {

        int count = 0;

        int start = 0;
        /*
        for (int j = start + 1; j < A.length; j++) {
            if (A[j] == A[j - 1]) {
                start = j;
            } else if (A[j] < A[j - 1]) {
                start = j;
                count++;
            } else if (A[start] < A[j]) {
                count += (j - start) + 1;
            }
        }
        */

        for (int end = start + 1; end < A.length; end++) {
            if (A[end] <= A[end - 1]) {// if A[end] is not greater than A[end-1], then do not increase the count and reset 'start' also to start from that position
                start = end;
            } else if (A[start] < A[end]) {
                count += (end - start);
            }
        }

        return count;

    }
}
