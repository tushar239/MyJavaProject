package algorithms._1array_stack_queue.geeksforgeeks.pointers_sorting_stack_queue_string.sort.hard;

/*

    Check whether a given array is a k sorted array or not

    A k sorted array is an array where each element is at most k distance away from its target position in the sorted array.

    https://www.geeksforgeeks.org/check-whether-given-array-k-sorted-array-not/


    Input : arr[] = {3, 2, 1, 5, 6, 4}, k = 2
    Output : Yes
    Every element is at most 2 distance away
    from its target position in the sorted array.

    Input : arr[] = {13, 8, 10, 7, 15, 14, 12}, k = 3
    Output : No
    13 is more than k = 3 distance away
    from its target position in the sorted array.

    Solution:

    Copy elements elements of original array arr[] to an auxiliary array aux[].
    Sort aux[].
    Now, for each element at index i in arr[], find its index j in aux[] using Binary Search.
    If for any element k < abs(i-j), then arr[] is not a k sorted array. Else it is a k sorted array.

 */
public class _3CheckWhetherGivenArrayIsAKSortedArrayOrNot {
}
