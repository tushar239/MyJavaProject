package algorithms._1array_stack_queue.geeksforgeeks.pointers_sorting_stack_queue_string.triplets_problems;

import java.util.Arrays;

/*
Count triplets with sum smaller than a given value
https://www.geeksforgeeks.org/count-triplets-with-sum-smaller-that-a-given-value/

Given an array of distinct integers and a sum value. Find count of triplets with sum smaller than given sum value. Expected Time Complexity is O(n2).

    Examples:

    Input : arr[] = {-2, 0, 1, 3}
            sum = 2.
    Output : 2
    Explanation :  Below are triplets with sum less than 2
                   (-2, 0, 1) and (-2, 0, 3)

    Input : arr[] = {5, 1, 3, 4, 7}
            sum = 12.
    Output : 4
    Explanation :  Below are triplets with sum less than 4
                   (1, 3, 4), (1, 3, 5), (1, 3, 7) and (1, 4, 5)


Solution:
    It is very easy to solve this problem with Brute-Force Approach that takes O(n^3).

    How can you solve it in O(n^2)?

        One thing we know that that convert O(n^3)to O(n^2), a trick is to keep one pointer stable and keep moving other twos together based on certain conditions.
        So, Keep i stable and move j and k together based on certain conditions, so that j and k covers entire array just once for every i.
        To make this trick work, sometimes you need to have sorted array.


        You need to understand very important concept:

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

    Better Approach: (I liked this approach)

        1) Sort the input array in increasing order.
        2) Initialize result as 0.
        3) Run a loop from i = 0 to n-2.  An iteration of this loop finds all triplets with arr[i] as first element.

             a) Initialize other two elements as corner elements of subarray

                arr[i+1..n-1], i.e., j = i+1 and k = n-1

             b) Move j and k toward each other until they meet, i.e., while (j < k) ------ this is what makes O(n^2) instead of O(n^3)

                    (i) if (arr[i] + arr[j] + arr[k] >= sum), then do k--

                    // Else for current i and j, there can (k-j) possible third elements that satisfy the constraint  --- important
                    (ii) Else count += (k - j) and j++


    int[] arr = {5, 1, 3, 4, 7}

    int[] arr = {1, 3, 4, 5, 7} --- sorted array

    1, 3, 4, 5, 7
    i  j        k


    int k = A.length - 1;

    for (int i = 0; i < A.length; i++) {

        int j = i+1;

        while(k > j)
            if(A[i]+A[j]+A[k] >= expectedSum) {
                k--;
            } else {
                you found k-j combinations of A[i],A[j],A[k] that has sum < expectedSum
                you don't need to move k further down.

                j++;  --- when you increase j, you don't have to start k from the end again because increasing j will increase the sum(or keep it same) with wherever k is.
            }
        }
    }


*/
public class _2CountTripletsWithSumSmallerThanGivenValue_IMP {

    public static void main(String[] args) {
        {
            int A[] = {-2, 0, 1, 3};
            int triplets = countTriplets(A, 2);
            System.out.println(triplets);//2
        }
        {
            int A[] = {5, 1, 3, 4, 7};
            int triplets = countTriplets(A, 12);
            System.out.println(triplets);//4
        }
    }

    private static int countTriplets(int[] A, int expectedSum) {

        // sort the array
        Arrays.sort(A); //O(n log n)

        int count = 0;

        // find triplets
        // O(n^2)
        int k = A.length - 1;

        for (int i = 0; i < A.length; i++) {//O(n)
            int j = i + 1;
            // int k = A.length - 1; ----- actual algorithm online has this line instead of initializing k outside for loop. But I think, this is not needed.

            // move j and k both based on certain conditions till they meet each other. This will convert O(n^2)to O(n)
            while (k > j) {//O(n)
                if ((A[i] + A[j] + A[k]) >= expectedSum) {
                    k--;
                    continue;
                }

                // Assumption: This one assumes that there are no duplicates in an array.
                // e.g. 1 2 3 3 3 4 5
                //      i j     k
                // if this the situation where triplet with < expectedSum is found, then with k-j, there will be same triplets  1,2,3  1,2,3  1,2,3
                count += (k - j);
                j++;
            }
        }
        return count;
    }

}
