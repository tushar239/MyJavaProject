package algorithms._1array_stack_queue.geeksforgeeks.pointers_sorting_stack_queue_string.easy;

/*
Find Least frequent element in an array

https://www.geeksforgeeks.org/least-frequent-element-array/

Input : arr[] = {1, 3, 2, 1, 2, 2, 3, 1}
Output : 3
3 appears minimum number of times in given
array.

Input : arr[] = {10, 20, 30}
Output : 10 or 20 or 30


Solution:
Maintain a Map with array element and its count.
So, O(n) for populating a map by iterating entire array.
+
O(n) to iterate a map and finding an element with min count.

Better solution:
We first sort the array, then linearly traverse the array.


*/
public class _9FindLeastFrequentElementInArray {
}
