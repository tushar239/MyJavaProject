package algorithms._1array_stack_queue.geeksforgeeks.xor_missing_and_duplicate_elements_algorithms;

/*
Find the first repeating element in an array of integers
Given an array of integers, find the first repeating element in it. We need to find the element that occurs more than once and whose index of first occurrence is smallest.

https://www.geeksforgeeks.org/find-first-repeating-element-array-integers/

Examples:

Input:  arr[] = {10, 5, 3, 4, 3, 5, 6}
Output: 5 [5 is the first element that repeats]

Input:  arr[] = {6, 10, 5, 4, 9, 120, 4, 6, 10}
Output: 6 [6 is the first element that repeats]

IMPORTANT:
See FindDuplicatesFromPositiveNumbers.java
It clearly says that array of size n has 0 to n-1 numbers only.

Here, for this algorithm, we don't have that benefit here.
We need to use some other technique to solve this problem in O(n).

Solution 1:
    The idea is to traverse the given array from right to left and update the minimum index whenever we find an element that has been visited on right side.
    It uses a Set for additional memory.

Solution 2:
    Solution 1 is better, but you can use this solution too.
    It is O(n) only, but it needs an array to be traversed twice and use HashMap for additional memory.

Set internally uses Map anyways.

*/

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class _1FindFirstDuplicateNumberInAnArrayOfIntegers {

    public static void main(String[] args) {
        int A[] = {10, 5, 3, 4, 3, 5, 6}; // first repeating element=5
        _1FindFirstDuplicateNumberInAnArrayOfIntegers instance = new _1FindFirstDuplicateNumberInAnArrayOfIntegers();
        instance.goodSolution(A);
        instance.betterSolution(A);
    }

    private void goodSolution(int[] A) {
        if (A == null || A.length == 0) return;

        Map<Integer, Integer> map = new HashMap<>();

        for (int i = 0; i < A.length; i++) { // O(n)
            int element = A[i];
            if (map.containsKey(element)) {
                map.put(element, map.get(element) + 1);
            } else {
                map.put(element, 1);
            }
        }

        for (int i = 0; i < A.length; i++) { // O(n)
            int element = A[i];
            if (map.get(element) > 1) {
                System.out.println("First repeating element: " + element);
                break;
            }
        }

    }

    private void betterSolution(int[] A) {
        if (A == null || A.length == 0) return;

        Set<Integer> set = new HashSet<>();

        int repeating = Integer.MIN_VALUE;

        for (int i = A.length - 1; i >= 0; i--) { // O(n)
            int element = A[i];

            if (set.contains(element)) {
                repeating = element;
            } else {
                set.add(element);
            }
        }
        System.out.println("First repeating element: " + repeating);
    }
}
