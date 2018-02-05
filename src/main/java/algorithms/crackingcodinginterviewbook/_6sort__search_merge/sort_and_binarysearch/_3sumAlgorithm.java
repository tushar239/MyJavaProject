package algorithms.crackingcodinginterviewbook._6sort__search_merge.sort_and_binarysearch;

import java.util.Arrays;

/*
Find all combinations of elements that sums to 0 from array.

Solution 1: takes O(n^3) execution time

        for (int i = 0; i < a.length; i++) {
            for (int j = i + 1; j < a.length; j++) {
                for (int k = j + 1; k < a.length; k++) {
                    if a[i]+a[j]+a[k] == 0, then that combination is eligible
                }
            }
        }

Solution 2: takes O(N^2 log n) time
        for (int i = 0; i < a.length; i++) {
            for (int j = i + 1; j < a.length; j++) {
                firstNumber = a[i]
                secondNumber = a[j]
                expectedThirdNumber = -(a[i]+a[j])
                if(firstNumber<secondNumber<expectedThirdNumber) {
                    do a binary search for expectedThirdNumber.
                    if found then combination of first,second and expected numbers is eligible
                }

            }
        }

 */
public class _3sumAlgorithm {
    public static void main(String[] args) {
        int array[] = {1, -10, 9, -1, 11};
        // 3-sumIteratively algorithm with O(N^3) execution time
        System.out.println("Testing 3 sumIteratively algorithm with O(N^3) execution time");
        _3SumAlgorithmTaking_N3_ExecutionTime(array);
        System.out.println();

        // 3-sumIteratively algorithm with O(N^2 logN) execution time
        // sort an array that takes N^2 and then N^2 for making groups of two elements and apply binary sort to find third element
        // total execution time = N^2 + (N^2 logN). As per big O notation, if we consider highest number then execution time becomes O(N^2 logN)
        System.out.println("Testing 3 sumIteratively algorithm with O(N^2 logN) execution time");
        _3SumAlgorithmTaking_N2logN_ExecutionTime(array);
    }

    // Solution 2: takes O(N^2 log n) time
    private static void _3SumAlgorithmTaking_N2logN_ExecutionTime(int[] array) {
        // Do Quick Sort that might reserve N^2 in worst condition
        Arrays.sort(array); // array = [-10,-1,1,9,11]

        // Two for loops takes N^2 and binary search takes logN. So total execution time for below block is N^2 * logN
        int count = 0;
        for (int i = 0; i < array.length; i++) {
            for (int j = i + 1; j < array.length; j++) {
                int firstNumber = array[i];
                int secondNumber = array[j];
                int expectedThirdNumber = -(array[i] + array[j]);
                if ((firstNumber < secondNumber) && (secondNumber < expectedThirdNumber)) { // firstNumber < secondNumber < expectedThirdNumber. This condition is to remove duplicates
                    if (BinarySearch.binarySearchAlgorithmIterative(array, expectedThirdNumber)) {
                        System.out.println("combinations that sums zero: " + array[i] + "," + array[j] + "," + expectedThirdNumber);
                    }
                }
            }
        }
    }

    // Solution 1: takes O(n^3) execution time
    private static void _3SumAlgorithmTaking_N3_ExecutionTime(int[] a) {
        int count = 0;
        for (int i = 0; i < a.length; i++) {
            for (int j = i + 1; j < a.length; j++) {
                for (int k = j + 1; k < a.length; k++) {
                    if (a[i] + a[j] + a[k] == 0) {
                        System.out.println("combinations that sums zero: " + a[i] + "," + a[j] + "," + a[k]);
                        count++;
                    }
                }
            }
        }
    }
}
