package algorithms._1array.geeksforgeeks.divide_and_concur.medium;

/*
Find a peak element

https://www.geeksforgeeks.org/find-a-peak-in-a-given-array/

Given an array of integers. Find a peak element in it.
An array element is peak if it is NOT smaller than its neighbors.
For corner elements, we need to consider only one neighbor.
For example,
    For input array {5, 10, 20, 15}, 20 is the only peak element.
    For input array {10, 20, 15, 2, 23, 90, 67}, there are two peak elements: 20 and 90.

Note that we need to return any one peak element.


Solution:
    Brute-Force: Compare every element with its neighbours and find a pick element. This takes O(n).
    Better Approach: Use Binary Search kind of solution. This takes O(log n)


    A = {10, 20, 15, 40, 23, 90, 67}
    middle=40, so it's a peak element

    A = {10, 40, 30, 2, 15, 90, 67}
    middle=2 and 2 is not a peak
    but 30 is >= 2, so one peak element must be found on left side of middle element

    A = {10, 20, 15, 2, 23, 90, 67}
    middle=2 and 2 is not a peak
    but 23 is >= 2, so one peak element must be found on right side of middle element


If you need to find all peak elements, you can do either in Brute-Force O(n) way and that's the best way.
You can't reduce it to O(log n) because you need to find peaks on both sides of middle element (not just one side).
*/
public class _3FindPickInGivenArray {

    public static void main(String[] args) {
        int[] A = {5, 10, 20, 15};//20
//        int[] A = {10, 90, 15, 2, 23, 20, 67}; // 90
//        int[] A = {5, 10, 15, 20};//20
//        int[] A = {20, 15, 10, 5};//20
        int pick = findPeak(A, 0, A.length - 1);
        System.out.println(pick);

    }

    static int findPeak(int A[], int start, int end) {

        if (A == null || A.length == 0) return Integer.MIN_VALUE;

        // not mandatory
        if (start > end) return Integer.MIN_VALUE;

        // not mandatory
        if (start == end) return A[start];

        // not mandatory
        if ((end - start) == 1) {
            return Math.max(A[start], A[end]);
        }
        // not mandatory
        if (end - start == 2) {
            if (A[start] >= A[start + 1]) return A[start];
            if (A[start + 1] >= A[start] && A[start + 1] >= A[start + 2]) return A[start + 1];
            return A[start + 2];
        }

        // Find index of middle element
        int mid = (start + end) / 2;

        int midElement = A[mid];

        // Compare middle element with its neighbours (if neighbours exist)
        if (mid == start && mid == end) {
            return midElement;
        } else if (mid > start && A[mid - 1] <= midElement && mid < end && A[mid + 1] <= midElement) {
            return midElement;
        } else if (mid > start && A[mid - 1] <= midElement && mid == end) {
            return midElement;
        } else if (mid < end && A[mid + 1] <= midElement && mid == start) {
            return midElement;
        }
        // If middle element is not peak and its left neighbor is
        // greater than it,then left half must have a peak element
        else if (mid > start && A[mid - 1] > midElement) {
            return findPeak(A, start, mid - 1);
        }

        // If middle element is not peak and its right neighbor
        // is greater than it, then right half must have a peak element
        else return findPeak(A, mid + 1, end);
    }
}
