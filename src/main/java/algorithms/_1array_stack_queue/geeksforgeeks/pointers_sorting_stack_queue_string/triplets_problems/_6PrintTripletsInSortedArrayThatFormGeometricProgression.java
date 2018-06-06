package algorithms._1array_stack_queue.geeksforgeeks.pointers_sorting_stack_queue_string.triplets_problems;

/*
Find all triplets in a sorted array that forms Geometric Progression:
https://www.geeksforgeeks.org/find-all-triplets-in-a-sorted-array-that-forms-geometric-progression/


This problem is similar to "PrintTripletsInSortedArrayThatFormArithmeticProgression.java", just formula to calculate Geometric progression is different.

arr[j] / arr[i] = r and arr[k] / arr[j] = r
where r is an positive integer and 0 <= i < j and j < k <= n - 1

A geometric progression is a sequence of numbers where each term after the first is found by multiplying the previous one by a fixed, non-zero number called the common ratio. For example, the sequence 2, 6, 18, 54,… is a geometric progression with common ratio 3.

    Examples:

    Input:
    arr = [1, 2, 6, 10, 18, 54]
    Output:
    2 6 18
    6 18 54

    Input:
    arr = [2, 8, 10, 15, 16, 30, 32, 64]
    Output:
    2 8 32
    8 16 32
    16 32 64

    Input:
    arr = [ 1, 2, 6, 18, 36, 54]
    Output:
    2 6 18
    1 6 36
    6 18 54

*/
public class _6PrintTripletsInSortedArrayThatFormGeometricProgression {
}
