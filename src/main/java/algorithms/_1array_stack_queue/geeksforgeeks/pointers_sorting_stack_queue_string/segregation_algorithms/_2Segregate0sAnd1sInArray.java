package algorithms._1array_stack_queue.geeksforgeeks.pointers_sorting_stack_queue_string.segregation_algorithms;
/*
    Segregate 0s and 1s in an array

    https://www.geeksforgeeks.org/segregate-0s-and-1s-in-an-array-by-traversing-array-once/

    You are given an array of 0s and 1s in random order. Segregate 0s on left side and 1s on right side of the array. Traverse array only once.

    Input array   =  [0, 1, 0, 1, 0, 0, 1, 1, 1, 0]
    Output array =  [0, 0, 0, 0, 0, 1, 1, 1, 1, 1]

    Solutions:
    1) count all 0s and keep them at the beginning before all 1s - O(n)
    2) find first 1 from left and keep index i at it.
       find first 0 from right and keep index j at it.
       if i<j, swap them

       keep repeating above steps till i<j
*/
public class _2Segregate0sAnd1sInArray {
}
