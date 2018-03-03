package algorithms.geeksforgeeks;

/*
Minimum swaps required to bring all elements less than or equal to k together:
https://www.geeksforgeeks.org/minimum-swaps-required-bring-elements-less-equal-k-together/

Given an array of n positive integers and a number k. Find the minimum number of swaps required to bring all the numbers less than or equal to k together.

Input:  arr[] = {2, 1, 5, 6, 3}, k = 3
Output: 1

Explanation:
To bring elements 2, 1, 3 together, swap
element '5' with '3' such that final array
will be-
arr[] = {2, 1, 3, 6, 5}

Input:  arr[] = {2, 7, 9, 5, 8, 7, 4}, k = 5
Output: 2
*/
public class _9MinimumSwapsRequiredToBringAllElementsLessThanOrEqualToKTogether {

    public static void main(String[] args) {
        int k = 4;
        int A[] = {5, 1, 3, 6, 4, 7, 2, 9}; //1
        // int A[] = {5,1,3,6,4,7,9}; //1
        // int A[] = {5,1,3,6,4,4,2,1,2};//1
        // int A[] = {5,1,3,6,7,9,4,2,1,2};//3
        //int A[] = {5, 1, 3, 2, 4, 7, 9}; //0
        int swaps = minSwaps(A, k);
        System.out.println(swaps);
    }

    /*
        This algorithm runs in O(n).
        You need to maintain two pointers.
    */
    private static int minSwaps(int[] A, int k) {
        if (A == null || A.length == 0) {
            return 0;
        }

        int start = 0;
        int end = A.length - 1;

        // find the starting point of 'start' pointer.
        // starting point is the place where element <= k (e.g. i=1)
        for (int i = 0; i < A.length; i++) {
            if (A[i] > k) {
                start++;
                continue;
            }
            break;
        }

        // move 'start' pointer till you find element > k (e.g. i=3)
        for (int i = start; i < A.length; i++) {
            if (A[i] <= k) {
                start++;
                continue;
            }
            break;
        }

        // find the starting point of 'end' pointer.
        // starting point is the place where element <= k (e.g. i=6)
        for (int i = A.length - 1; i >= start; i--) {
            if (A[i] > k) {
                end--;
                continue;
            }
            break;
        }

        // At this point 'start' pointer is at the position where element > k
        // and 'end' pointer is at the position where element <= k
        // {5,1,3,6,4,7,2,9}
        //        |     |
        //      start  end
        // now you can start counting number of possible swaps
        // you can swap 'start' and 'end' elements, increment start and decrement end. continue this process till end <= start
        int swaps = 0;
        while (end > start) {
            if (A[start] <= k) {
                start++;
                continue;
            }
            swaps++;
            start++;
            end--;
        }

        return swaps;
    }
}
