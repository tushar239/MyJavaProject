package algorithms.geeksforgeeks;

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
                   (1, 3, 4), (1, 3, 5), (1, 3, 7) and
                   (1, 4, 5)



It is very easy to solve this problem with O(n^3).

How can you solve it in O(n^2)?

    You still need 3 pointers to figure out the sum of triplets, but out of 3 pointers, if two pointer can traverse an array together (one from starting and another from ending), then you can achieve the solution in O(n^2).

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

Solution:

1) Sort the input array in increasing order.
2) Initialize result as 0.
3) Run a loop from i = 0 to n-2.  An iteration of this loop finds all
   triplets with arr[i] as first element.

     a) Initialize other two elements as corner elements of subarray

        arr[i+1..n-1], i.e., j = i+1 and k = n-1

     b) Move j and k toward each other until they meet, i.e., while (j < k) ------ this is what makes O(n^2) instead of O(n^3)

            (i) if (arr[i] + arr[j] + arr[k] >= sum), then do k--

            // Else for current i and j, there can (k-j) possible third elements that satisfy the constraint  --- important
            (ii) Else count += (k - j) and j++

*/
public class _10CountTripletsWithSumSmallerThanGivenValue {

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
        for (int i = 0; i < A.length; i++) {//O(n)
            int j = i + 1;
            int k = A.length - 1;

            // move j and k both based on certain conditions till they meet each other. This will convert O(n^2)to O(n)
            while (k > j) {//O(n)
                if ((A[i] + A[j] + A[k]) >= expectedSum) {
                    k--;
                    continue;
                }
                count += (k - j);
                j++;
            }
        }
        return count;
    }

}
