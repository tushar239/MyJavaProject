package algorithms.crackingcodinginterviewbook._6sort__search_merge;

// p.g. 399 of Cracking Coding Interview book
/*
Search in Rotated Array:
Given a sorted array of n integers that has been rotated an unknown number of times, write code to find an element in the array.
You may assume that the array was originally sorted in increasing order.

original array = [1,3,4,5,7,10,14,15,16,19,20,21]
It has been rotated unknown number of times.
rotated array = [19,20,21,1,3,4,5,7,10,14,15,16]

You need to find an element 5 in this rotated array without sorting it again.

IMPORTANT:
Whenever you are told that you need to find an element in sorted array, right away you should think of Binary Search.

There is just a small difference between Binary Search code and code for this algorithm.

In normal binary search,
    middle element=14.
    if element to find > 14 then continue search right side of 14, otherwise continue search left side of 14.

In this algorithm,
   let's say element to find = 5
   middle element=4
   if element to find > 4 and <=16 then continue search right side of 4, otherwise continue search left side of 4.

 */
public class _3SearchInRotatedArray {
    public static void main(String[] args) {
        int[] rotatedArray = new int[]{19, 20, 21, 1, 3, 4, 5, 7, 10, 14, 15, 16};
        System.out.println("found 5?: " + binarySearchAlgorithmRecursive(rotatedArray, 0, rotatedArray.length - 1, 5));
        System.out.println("found 21? :" + binarySearchAlgorithmRecursive(rotatedArray, 0, rotatedArray.length - 1, 21));
        System.out.println("found 50? :" + binarySearchAlgorithmRecursive(rotatedArray, 0, rotatedArray.length - 1, 50));
    }

    private static int binarySearchAlgorithmRecursive(int[] A, int start, int end, int numberToSearch) {
        if (A == null || A.length == 0) {
            return -1;
        }
        if (start > end) {
            return -1;
        }

        int mid = (start + end) / 2;

        if (A[mid] == numberToSearch) {
            return mid;
        }
        if (A[start] < A[mid]) { // left side of mid is sorted
            if (numberToSearch >= A[start] && numberToSearch < A[mid]) { // if number falls on left side of mid, then search that number on left side
                return binarySearchAlgorithmRecursive(A, start, mid - 1, numberToSearch);
            }
            return binarySearchAlgorithmRecursive(A, mid + 1, end, numberToSearch);// otherwise, search that number on right side

        }
        if (A[mid] < A[end]) { // right side of mid is sorted
            if (numberToSearch > A[mid] && numberToSearch <= A[end]) { // if number falls on right side of mid, then search that number on right side
                return binarySearchAlgorithmRecursive(A, mid + 1, end, numberToSearch);
            }
            return binarySearchAlgorithmRecursive(A, start, mid - 1, numberToSearch);// otherwise, search that number on left side
        }
        return -1;

    }
}