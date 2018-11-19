package algorithms._1array_stack_queue.geeksforgeeks.pointers_sorting_stack_queue_string.segregation_algorithms;

import algorithms.utils.ArrayUtils;

import static algorithms.utils.ArrayUtils.exchange;

/*
    Segregate Even and Odd numbers (bring all even numbers together and all odd numbers together)

    https://www.geeksforgeeks.org/segregate-even-and-odd-numbers/

    Given an array A[], write a function that segregates even and odd numbers. The functions should put all even numbers first, and then odd numbers.

    Example

    Input  = {12, 34, 45, 9, 8, 90, 3}
    Output = {12, 34, 8, 90, 45, 9, 3}

    Solutions:

    1) Iterate entire array and keep track of which element is even and which one is odd.
       Then put all even numbers first and then odd numbers in the array.

    2) Preferred Solution:

       keep i at first odd number found from left.
       keep j at first even number found from right.

                 i               j
        12, 34, 45, 22, 9, 8, 7, 90, 3

        if(i < j)
            exchange A[i] and A[j]

       Move i to next odd number
       Move j to next even number

                        i  j
        12, 34, 90, 22, 9, 8, 7, 45, 3

        if(i < j)
            exchange A[i] and A[j]

        12, 34, 90, 22, 8, 9, 7, 45, 3

*/
public class _1SegregateEvenAndOddNumbers {

    public static void main(String[] args) {
        int[] A = {12, 34, 45, 9, 8, 90, 3};
        rearrange(A);

        ArrayUtils.printArray(A);// {12,34,90,8,9,45,3}
    }

    private static void rearrange(int[] arr) {
        if (arr == null || arr.length <= 1) return;

        // find first odd number from left
        int oddIndex = findIndexOfOddNumber(arr, 0);

        // find first even number from right
        int evenIndex = findIndexOfEvenNumber(arr, arr.length - 1);

        while ((oddIndex < evenIndex) && (oddIndex != -1 && evenIndex != -1)) {
            exchange(arr, oddIndex, evenIndex);

            oddIndex = findIndexOfOddNumber(arr, oddIndex + 1);
            evenIndex = findIndexOfEvenNumber(arr, evenIndex - 1);
        }
    }

    private static int findIndexOfEvenNumber(int[] arr, int start) {
        while (start >= 0) {
            if (arr[start] % 2 == 0) {
                return start;
            }
            start--;
        }
        return -1;
    }

    private static int findIndexOfOddNumber(int[] arr, int start) {

        while (start < arr.length) {

            if (arr[start] % 2 != 0) {
                return start;
            }
            start++;
        }
        return -1;
    }
}
