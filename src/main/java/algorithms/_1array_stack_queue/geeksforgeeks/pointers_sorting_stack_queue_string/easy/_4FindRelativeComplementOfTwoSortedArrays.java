package algorithms._1array_stack_queue.geeksforgeeks.pointers_sorting_stack_queue_string.easy;

/*
Find relative complement of two sorted arrays

https://www.geeksforgeeks.org/find-relative-complement-two-sorted-arrays/

Given two sorted arrays arr1 and arr2 of size m and n respectively. We need to find relative complement of two array i.e, arr1 – arr2 which means that we need to find all those elements which are present in arr1 but not in arr2.

Examples:

Input : arr1[] = {3, 6, 10, 12, 15}
        arr2[] = {1, 3, 5, 10, 16}

Output : 6 12 15

The elements 6, 12 and 15 are present

in arr[], but not present in arr2[]


Input : arr1[] = {10, 20, 36, 59}
        arr2[] = {5, 10, 15, 59}
Output : 20 36

Solution:
1. Take two pointers i and j which traverse through arr1 and arr2 respectively.
2. If arr1[i] element is smaller than arr2[j] element print this element and increment i.
3. If arr1 element is greater than arr2[j] element then increment j.
4. otherwise increment i and j.

*/
public class _4FindRelativeComplementOfTwoSortedArrays {
}
