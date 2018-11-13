package algorithms._1array_stack_queue.geeksforgeeks.pointers_sorting_stack_queue_string.medium;

import java.util.Arrays;

/*
    Maximum possible difference of two subsets of an array

    https://www.geeksforgeeks.org/maximum-possible-difference-two-subsets-array/


    Given an array of n-integers. Array may contain repetitive elements but the highest frequency of any elements must not exceed two.
    You have to make two subsets such that difference of their elements sum is maximum and both of them jointly contains all of elements of given array along with the most important condition, no subset should contain repetitive elements.


    Question for interviewer:
    I am assuming that there won't be more than two duplicate elements. If that assumption is not right, you have to check for more than two duplicate elements as well and throw an exception if needed.

    e.g. {5, 8, -1, 4}

    two subsets can be created in following way such that their difference can be maximum.

    subset1 = -1
    subset2 = 5,8,4

    {5, 8, 5, 4};

    two subsets can be created in following way such that their difference can be maximum and there are no duplicate elements considered in each on of them.

    subset1 = 5
    subset2 = 5,8,4


    Solution:
    - sort an array, so that you can track duplicate elements easily
    - check next element of each element
        if next element is a duplicate one, then
            use element for subset1 and next element for subset2
            move pointer after next element
        else
            use element for subset1
            move pointer to next element

       If there were no duplicate elements, subset2 will be empty. You need at least one element in it to find max diff between two subsets.
       So, use lowest element of input array for subset2 and take that element out from subset1.
*/
public class _10MaxPossibleDiffOfTwoSubsetsOfAnArray {

    public static void main(String[] args) {
        {
            int arr[] = {5, 8, -1, 4};
            find(arr);
        }
        System.out.println();
        {
            int arr[] = {5, 8, 5, 4};
            find(arr);
        }

    }

    private static void find(int[] A) {

        if (A == null || A.length == 0) {
            System.out.println("Difference : " + 0);
            return;
        }

        if (A.length == 1) {
            System.out.println("Difference : " + A[0]);
        }

        Arrays.sort(A);// sort an array O(n log n)

        int sumOfElementsInSubSet1 = 0;
        int sumOfElementsInSubSet2 = 0;

        // O(n)
        for (int i = 0; i < A.length; ) {
            int element = A[i];//5

            if ((i + 1) < A.length) {// to avoid ArrayIndexOutOfBoundException

                int nextElement = A[i + 1];

                if (element == nextElement) {// duplicate element found, so keep one element in one subset and another one in another subset.
                    sumOfElementsInSubSet1 += element;
                    sumOfElementsInSubSet2 += nextElement;
                    i = i + 2;
                } else {// no duplicate
                    sumOfElementsInSubSet1 += element;
                    i = i + 1;
                }
            } else {
                sumOfElementsInSubSet1 += element;
                i = i + 1;
            }
        }

        // If there were no duplicate elements, subset2 will be empty. You need at least one element in it to find max diff between two subsets.
        // So, use lowest element of input array for subset2 and take that element out from subset1.
        if (sumOfElementsInSubSet2 == 0) {
            sumOfElementsInSubSet2 = A[0];
            sumOfElementsInSubSet1 = sumOfElementsInSubSet1 - A[0];
        }

        System.out.println("sum of first subset : " + sumOfElementsInSubSet1);
        System.out.println("sum of second subset : " + sumOfElementsInSubSet2);
        System.out.println("Difference : " + Math.abs(sumOfElementsInSubSet1 - sumOfElementsInSubSet2));
    }

}
