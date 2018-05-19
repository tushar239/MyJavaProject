package algorithms._1array_stack_queue.interviewbit.kadane_algorithm;

/*
    Maximum Subarray Sum Excluding Certain Elements

    https://www.geeksforgeeks.org/maximum-subarray-sum-excluding-certain-elements/


    Given an array of A of n integers and an array B of m integers find the Maximum Contiguous Subarray Sum of array A such that any element of array B is not present in that subarray.

    Input : A = {1, 7, -10, 6, 2}, B = {5, 6, 7, 1}
    Output : 2
    Explanation Since the Maximum Sum Subarray of A is not allowed to have any element that is present in array B.
    The Maximum Sum Subarray satisfying this is {2} as the only allowed subarrays are:{-10} and {2}. The Maximum Sum Subarray being {2} which sums to 2

    Input : A = {3, 4, 5, -4, 6}, B = {1, 8, 5}
    Output : 7

    Explanation
    The Maximum Sum Subarray satisfying this is {3, 4} as the only allowed subarrays are {3}, {4}, {3, 4}, {-4}, {6}, {-4, 6}. The Maximum Sum subarray being {3, 4} which sums to 7


    Method-1 O(n * m)

        We can solve this problem using the Kadane’s Algorithm. Since we don’t want any of the elements of array B to be part of any subarray of A, we need to modify the classical Kadane’s Algorithm a little.

    Method 2 (O((n+m)*log(m)) approach)

        The main idea behind this approach is exactly the same as that of method 1. This approach just makes the searching of an element of array A, in array B, faster by using Binary Search
        Note: We need to sort the Array B to apply Binary Search on it.


*/
public class _3MaximumSubarraySumExcludingCertainElements {
}
