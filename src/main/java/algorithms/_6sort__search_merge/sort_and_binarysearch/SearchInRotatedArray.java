package algorithms._6sort__search_merge.sort_and_binarysearch;

// p.g. 399 of Cracking Coding Interview book
/*
original array = [1,3,4,5,7,10,14,15,16,19,20,21]
It has been rotated unknown number of times.
rotated array = [19,20,21,1,3,4,5,7,10,14,15,16]

You need to find an element 5 in this rotated array without sorting it again.

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
public class SearchInRotatedArray {
    public static void main(String[] args) {
        int[] rotatedArray = new int[]{19, 20, 21, 1, 3, 4, 5, 7, 10, 14, 15, 16};
        System.out.println("found 5?: " + binarySearchAlgorithmRecursive(rotatedArray, 0, rotatedArray.length - 1, 5));
        System.out.println("found 21? :" + binarySearchAlgorithmRecursive(rotatedArray, 0, rotatedArray.length - 1, 21));
        System.out.println("found 50? :" + binarySearchAlgorithmRecursive(rotatedArray, 0, rotatedArray.length - 1, 50));
    }

    private static boolean binarySearchAlgorithmRecursive(int[] array, int start, int end, int numberToSearch) {
        if (end < start) {
            return false;
        }

        int middle = (start + end) == 0 ? 0 : (start + end) / 2;
        if (array[middle] == numberToSearch) {
            return true;
        } else if (numberToSearch > array[middle] &&
                //extra condition compared to normal binary search algorithm
                numberToSearch <= array[end]) {
            return binarySearchAlgorithmRecursive(array, middle + 1, end, numberToSearch);
        }
        return binarySearchAlgorithmRecursive(array, start, middle - 1, numberToSearch);

    }
}