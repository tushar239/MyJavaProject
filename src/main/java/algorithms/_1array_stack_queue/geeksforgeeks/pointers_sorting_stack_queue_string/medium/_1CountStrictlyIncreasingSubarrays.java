package algorithms._1array_stack_queue.geeksforgeeks.pointers_sorting_stack_queue_string.medium;

/*
Count Size of Strictly Increasing Biggest Subarray

https://www.geeksforgeeks.org/count-strictly-increasing-subarrays/

Given an array of integers, count number of subarrays (of size more than one) that are strictly increasing.

Expected Time Complexity : O(n)
Expected Extra Space: O(1)

Hint:
    for increasing subarray problem, you have to compare an element with its previous element.

    0 1 2 3 4 5 6
    1,4,6,2,3,4,5
    S E

    4 > 1, so count += (end-start) = 1

    0 1 2 3 4 5 6
    1,4,6,2,3,4,5
    S   E

    6 > 4, so count += (end-start) = 3

    0 1 2 3 4 5 6
    1,4,6,2,3,4,5
    S     E

    2 is not > 6, so

    0 1 2 3 4 5 6
    1,4,6,2,3,4,5
            E
          S

    and so on.

    if(element > prev element) then count += (end-start)
    else start=end

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
            int count = findSizeOfBiggestSubarray(A);
            System.out.println(count);//1 - {1,4}
        }
        {
            int A[] = {1, 2, 3, 4};
            int count = findSizeOfBiggestSubarray(A);
            System.out.println(count);//6
            // {1,2}
            // {2,3}, {1,2,3}
            // {3,4}, {2,3,4}, {1,2,3,4}
        }

        {
            int A[] = {1, 2, 2, 4, 5};
            int count = findSizeOfBiggestSubarray(A);
            System.out.println(count);//4
            // {1,2}
            // {2, 4}
            // {4,5}, {2,4,5}
        }

        {
            int A[] = {1, 4, 6, 2, 3, 4, 5};
            int count = findSizeOfBiggestSubarray(A);
            System.out.println(count);//9
            // {1,4}, {1,4,6}
            // {4,6}
            // {2,3}, {2,3,4}, {2,3,4,5}
            // {3,4}, {3,4,5}
            // {4,5}
        }
    }

    // O(n)
    private static int findSizeOfBiggestSubarray(int[] A) {

        int start = 0;

        int size = 0;

        /*
        for (int j = start + 1; j < A.length; j++) {
            if (A[j] == A[j - 1]) {
                start = j;
            } else if (A[j] < A[j - 1]) {
                start = j;
                size++;
            } else if (A[start] < A[j]) {
                size += (j - start) + 1;
            }
        }
        */

        for (int end = start + 1; end < A.length; end++) {
            if (A[end] <= A[end - 1]) {// if A[end] is not greater than A[end-1], then do not increase the size and reset 'start' also to start from that position
                start = end;
            } else if (A[start] < A[end]) {
                size += (end - start);
            }
        }

        return size;

    }

    // This is another algorithm:
    // Find size of Biggest strictly increasing subarray.
    // This algorithm is a slight modification of above algorithm.
    private static int sizeOfStrictlyIncreasingBiggestSubArray_another_way(int[] A) {

        int start = 0;

        int maxSize = 0;

        for (int end = start + 1; end < A.length; end++) {

            if (A[end] > A[end - 1]) {

                int size = (end - start) + 1;

                if (size > maxSize) {
                    maxSize = size;
                }
            } else {
                start = end;
            }
        }

        return maxSize;

    }
}
