package algorithms._1array_stack_queue.geeksforgeeks.divide_and_concur.binary_search_way.easy;

/*
Find the only repeating element in a sorted array of size n

https://www.geeksforgeeks.org/find-repeating-element-sorted-array-size-n/

Given a sorted array of n elements containing elements in range from 1 to n-1 i.e. one element occurs twice, the task is to find the repeating element in an array.

Examples:

    Input :  arr[] = { 1, 2 , 3 , 4 , 4}
    Output :  4

    Input :  arr[] = { 1 , 1 , 2 , 3 , 4}
    Output :  1


Solution:

    This is a simple Binary Search based algorithm.

    if(A[mid] == A[mid-1] || A[mid] == A[mid+1]), then it is a repeating element.
    if(A[mid] <= mid) return binarySearch(A,start,mid-1)
    else return binarySearch(A,mid+1,end);

Find Duplicates when there are more than one duplicates:
    FindDuplicatesFromPositiveNumbers.java
*/
public class _3FindTheOnlyRepeatingElementInSortedArrayOfSizeN {

    public static void main(String[] args) {
        {
            int[] A = {1, 1, 2, 3, 4};
            int repeatingElement = binarySearch(A, 0, A.length - 1);
            System.out.println(repeatingElement);//1
        }
        {
            int[] A = {1, 2, 3, 4, 4};
            int repeatingElement = binarySearch(A, 0, A.length - 1);
            System.out.println(repeatingElement);//4
        }
        {
            int[] A = {1, 2, 3, 4, 5};
            int repeatingElement = binarySearch(A, 0, A.length - 1);
            System.out.println(repeatingElement);//Integer.MIN_VALUE
        }
    }

    private static int binarySearch(int[] A, int start, int end) {
        if (A == null) return Integer.MIN_VALUE;

        if (start == end) return Integer.MIN_VALUE;

        int mid = (start + end) / 2;

        int midElement = A[mid];

        if (mid < A.length-1) {
            if (midElement == A[mid + 1]) {
//                System.out.println("Repeating element is " + midElement);
                return midElement;
            }
        }
        if (mid > 0) {
            if (midElement == A[mid - 1]) {
//                System.out.println("Repeating element is " + midElement);
                return midElement;
            }
        }

        if (midElement <= mid) return binarySearch(A, start, mid - 1);

        return binarySearch(A, mid + 1, end);
    }

    // This code will result in infinite loop
    private static void binarySearch_Infinite_Loop(int[] A, int start, int end) {
        if (A == null) return;

        if (start == end) return;

        int mid = (start + end) / 2;

        int midElement = A[mid];

        if (mid < end) {
            if (midElement == A[mid + 1]) {
                System.out.println("Repeating element is " + midElement);
                return;
            }
        }
        if (mid > start) {
            if (midElement == A[mid - 1]) {
                System.out.println("Repeating element is " + midElement);
                return;
            }
        }

        // IMPORTANT:
        // Even though result is found from this recursion, it will go to next line and so there will be infinite loop
        // To avoid that, you need to remember that when recursive method do not return anything, every recursive call has to be surrounded by a condition.
        if (midElement <= mid) binarySearch_Infinite_Loop(A, start, mid - 1);

        //else ----- uncomment this to avoid infinite loop.
        binarySearch_Infinite_Loop(A, mid + 1, end);
    }
}
