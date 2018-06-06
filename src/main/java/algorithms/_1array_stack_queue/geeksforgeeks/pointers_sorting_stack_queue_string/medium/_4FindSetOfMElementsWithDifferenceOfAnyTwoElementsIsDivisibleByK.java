package algorithms._1array_stack_queue.geeksforgeeks.pointers_sorting_stack_queue_string.medium;

/*
Find set of m-elements with difference of any two elements is divisible by k

https://www.geeksforgeeks.org/find-set-m-element-whose-difference-two-element-divisible-k/

Given an array of n-positive integers and a positive integer k, find a set of exactly m-elements such that difference of any two element is equal to k.

Examples :

    Input : arr[] = {4, 7, 10, 6, 9},
            k = 3, m = 3
    Output : Yes, 4 7 10

    Input : arr[] = {4, 7, 10, 6, 9},
            k = 12, m = 4
    Output : No

    Input : arr[] = {4, 7, 10, 6, 9},
            k = 3, m = 4
    Output : No


Solution:
Make the group of array elements whose remainders are same when you divide those numbers by k.
e.g. 4,7,10 are the numbers that gives same remainder when dividing them by 3 and so diff bet any of those elements will be 3.
*/

import java.util.LinkedList;
import java.util.List;

public class _4FindSetOfMElementsWithDifferenceOfAnyTwoElementsIsDivisibleByK {

    public static void main(String[] args) {
        int[] A = {4, 7, 10, 6, 9};
        {
            int k = 3;//diff bet two elements should be divisible by k
            int m = 3;//total number of elements to find

            findElements(A, k, m);// Yes Found, [4, 7, 10]
        }
        {
            int k = 3;
            int m = 4;

            findElements(A, k, m);// Not Found
        }
        {
            int k = 4;
            int m = 3;

            findElements(A, k, m);// Not Found
        }
    }

    private static void findElements(int[] A, int k, int m) {

        List<Integer>[] result = new List[k];

        for (int i = 0; i < result.length; i++) {
            result[i] = new LinkedList<>();
        }

        for (int i = 0; i < A.length; i++) {
            int element = A[i];
            result[element % k].add(element);
        }

        for (List<Integer> list : result) {
            if (list.size() >= m) {
                System.out.println("Yes Found, " + list);
                return;
            }
        }
        System.out.println("Not Found");
    }
}
