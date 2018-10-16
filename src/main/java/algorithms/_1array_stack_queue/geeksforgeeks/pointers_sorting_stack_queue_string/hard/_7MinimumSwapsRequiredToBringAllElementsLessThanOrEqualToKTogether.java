package algorithms._1array_stack_queue.geeksforgeeks.pointers_sorting_stack_queue_string.hard;

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
public class _7MinimumSwapsRequiredToBringAllElementsLessThanOrEqualToKTogether {

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

        Normally, when there are two pointers and one is moving while another one is stagnant, there will be O(n^2) scenario.
        But when both are moving from opposite directions and stop when both of them are at the same position, there will be O(n) scenario.

        As you see in below example, both j and k are moving in opposite direction when i is stagnant. so, i will be incremented for every n traversals.
        So, this algorithm will take O(n^2) instead of O(n^3), even though there are 3 pointers.

        for(int i=0; i<n; i++) {
            int j=i+1;
            int k=n-1;

            while(j<k) {
                if(....) { k--; ..... }
                if(...) { j++; .....}
            }
        }

        Similar thing is there in CountTripletsWithSumSmallerThanGivenValue.java


        keep Start at the element that is <=k

        5, 1, 3, 6, 4, 7, 2, 9
           S

        move Start till element is > k

        5, 1, 3, 6, 4, 7, 2, 9
                 S

        keep End at the position that is <=k

        5, 1, 3, 6, 4, 7, 2, 9
                 S        E

        Now, start counting the number of swaps required and keep moving both S and E together.

        cnt=1 because 2 can be put in place of 6

        5, 1, 3, 6, 4, 7, 2, 9
                    S  E

        4 is already <= k. so no need to swap with 7.


        Answer = 1 swap required

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
