package algorithms._1array_stack_queue.geeksforgeeks.divide_and_concur.binary_search_way.medium;

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

Important:

    - If you need to find ALL peak elements, you can do it in Brute-Force O(n) way and that's the best way.
    You can't reduce it to O(log n) because you need to find peaks on both sides of middle element (not just one side), if you use divide and conquer.

    - This algorithm teaches you some important concepts to remember when you are doing Divide and Conquer.

     It is very important to remember that when you do divide and conquer, you cannot pass 'mid' as an 'end' index in recursive call e.g. findPeak(A,start,mid)
     If you do that, it will end up in infinite recursion.
     Because at some point mid=1 will come. At this point, when you do findPeak(A,start=0,mid=1), in this call again mid=(0+1)/2=1. So, you will infinitely end up calling findPeak(A,0,1).
     So, always use findPeak(A,start,mid-1 / mid+1) in recursive call.

    Read ArrayFundamentals.java Divide and Concur section.
*/
public class _2_1FindPeakInGivenArray {

    public static void main(String[] args) {
        int[] A = {5, 10, 20, 15};//2
//        int[] A = {10, 90, 15, 2, 23, 20, 67}; // 1
//        int[] A = {5, 10, 15, 20};//3
//        int[] A = {20, 15, 10, 5};//0
//        int[] A = {1,2,3};//2
//        int[] A = {1};//-1
//        int[] A = {2, 1};//0

        int pickIndex = findPeakElementIndex_Better_Way(A, 0, A.length - 1);
        System.out.println(pickIndex);

//        int pick = findPeakElement(A, 0, A.length - 1);
//        System.out.println(pick);

    }

    /*

     Before you do this algorithm, read BitonicPoint.java

     CAUTION:
     - It is very important to remember that when you do divide and conquer, you CANNOT pass 'mid' as an 'end' index in recursive call e.g. findPeak(A,start,mid)
     If you do that, it will end up in infinite recursion.
     Because at some point mid=1 will come. At this point, when you do findPeak(A,start=0,mid=1), in this call again mid=(0+1)/2=1. So, you will infinitely end up calling findPeak(A,0,1).
     So, always use findPeak(A,start,mid-1 / mid+1) in recursive call.

     - When you need to to access
        A[mid-1], it is must to check whether mid < 0
        A[mid+1], it is must to check whether mid > A.length-1

     - When you have an exit condition to check start==end, then you need to have a sense that
     if your array is 1,2,3 and start and end pointers are at 3 (at the end) or at 1 (at the beginning), what should you do?

     - In below algorithm, you are checking for start==end because you know that recursion is increasing/decreasing the index by one only. so, at some point start can become end.
     In Binary Search related algorithm, you can remember that you can avoid start==end check and keep start>end check
    */

    private static int findPeakElementIndex_Better_Way(int[] A, int start, int end) {
        if (A == null || A.length == 0) return -1;

        /*
            You are checking for start==end because you know that recursion is increasing/decreasing the index by one only. so, at some point start can become end.
            In BinarySearch related algorithms, it is challenging to decide whether to return A[start] or -1, when start==end.
            Then think, if your array is 1,2,3 and start and end pointers are at 3 (at the end) or at 1 (at the beginning), what should you do?
        */
        /*
        if (start == end) {

            // return -1 or A[start]  won't work because start and end can point to beginning or end of the array. In both cases, it has element after or before it. So, it can be either leftCornerElement or rightCornerElement.

            if (isLeftCornerElement(A, start) && isRightCornerElement(A, start)) {
                return -1;
            } else if (isLeftCornerElement(A, start)) {
                if (A[start + 1] < A[start]) {
                    return start;
                }
                return -1;
            } else {
                if (A[start - 1] < A[start]) {
                    return start;
                }
                return -1;
            }
        }
        */

        //OR

        /*
            for start==end exit condition, if you need to repeat the logic same as rest of the algorithm,
            checking start>end may also work fine.
        */
        if (start > end) {
            return -1;
        }

        int mid = (start + end) / 2;

        if (!isLeftCornerElement(A, mid) && !isRightCornerElement(A, mid)) {
            if (A[mid] > A[mid + 1] && A[mid] > A[mid - 1]) {
                return mid;
            } else if (A[mid] < A[mid + 1]) {
                return findPeakElementIndex_Better_Way(A, mid + 1, end);
            }
            return findPeakElementIndex_Better_Way(A, start, mid - 1);
        } else if (isLeftCornerElement(A, mid)) {
            if (A[mid] > A[mid + 1]) {
                return mid;
            }
            return -1;
        } else if (isRightCornerElement(A, mid)) {
            if (A[mid] > A[mid - 1]) {
                return mid;
            }
            return -1;
        }

        return -1;

    }

    private static boolean isLeftCornerElement(int[] a, int index) {
        return index == 0;
    }

    private static boolean isRightCornerElement(int[] a, int index) {
        return index == a.length - 1;
    }

    // CAUTION:
    // It is very important to remember that when you do divide and conquer, you CANNOT pass 'mid' as an 'end' index in recursive call e.g. findPeak(A,start,mid)
    // If you do that, it will end up in infinite recursion.
    // Because at some point mid=1 will come. At this point, when you do findPeak(A,start=0,mid=1), in this call again mid=(0+1)/2=1. So, you will infinitely end up calling findPeak(A,0,1).
    // So, always use findPeak(A,start,mid-1 / mid+1) in recursive call.
    static int findPeakElement(int A[], int start, int end) {

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

        // IMPORTANT:
        // Don't compare mid-1 >= start. Always do mid > start.
        // Similarly, don't compare mid+1 <= end. Always do mid < end.

        // IMPORTANT:
        // It is an important exit condition.
        // you are using arr[mid+1] and arr[mid-1] later in the code. It can throw ArrayIndexOutOfBoundException.
        // To avoid that, mid has to be < end and > start. So, you must have an exit condition checking mid == end and mid == start.
        if (mid == start && mid == end) {
            return midElement;
        }
        if (mid == start && mid < end) {
            if (midElement < A[mid + 1]) {
                return midElement;
            }
            return -1;
        }
        if (mid == end && mid > start) {
            if (midElement > A[mid - 1]) {
                return midElement;
            }
            return -1;
        }

        if (mid > start && mid < end) {
            if (A[mid - 1] <= midElement && A[mid + 1] <= midElement) {
                return midElement;
            }
            if (A[mid - 1] > midElement) {
                return findPeakElement(A, start, mid - 1);// important: you cannot pass mid
            }
            return findPeakElement(A, mid + 1, end);// important: you cannot pass mid
        }

        return -1;

        /*
        // Important:
        // Don't compare mid-1 >= start. Always do mid > start.
        // Similarly, don't compare mid+1 <= end. Always do mid < end.
        if (mid > start && mid < end && A[mid - 1] <= midElement && A[mid + 1] <= midElement) {
            return midElement;
        } else if (mid > start && mid == end && A[mid - 1] <= midElement) {
            return midElement;
        } else if (mid < end && mid == start && A[mid + 1] <= midElement) {
            return midElement;
        }
        // If middle element is not peak and its left neighbor is
        // greater than it,then left half must have a peak element
        else if (mid > start && A[mid - 1] > midElement) {
            return findPeak(A, start, mid - 1);// important: you cannot pass mid
        }

        // If middle element is not peak and its right neighbor
        // is greater than it, then right half must have a peak element
        else return findPeak(A, mid + 1, end);// important: you cannot pass mid
        */
    }

}
