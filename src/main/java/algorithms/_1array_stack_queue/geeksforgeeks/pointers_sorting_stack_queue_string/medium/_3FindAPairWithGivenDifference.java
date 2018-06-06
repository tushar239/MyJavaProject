package algorithms._1array_stack_queue.geeksforgeeks.pointers_sorting_stack_queue_string.medium;
/*
Find a pair with the given difference

Given an unsorted array and a number n, find if there exists a pair of elements in the array whose difference is n.

Examples:
Input: arr[] = {5, 20, 3, 2, 50, 80}, n = 78
Output: Pair Found: (2, 80)

Input: arr[] = {90, 70, 20, 80, 50}, n = 45
Output: No Such Pair

https://www.youtube.com/watch?v=qODOVGzk6Zc
https://www.geeksforgeeks.org/find-a-pair-with-the-given-difference/

Solution:
- sort an array
- take first element, add difference to it and try to find that number in remaining array using binary search
  then take second element and repeat until it is successful.

Both first and second steps take O(n log n). So, overall time complexity is O(n log n).
*/
public class _3FindAPairWithGivenDifference {

}
