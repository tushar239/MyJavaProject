package algorithms._1array_stack_queue.geeksforgeeks.pointers_sorting_stack_queue_string.medium;

/*
Searching an element in an array. In this array, each adjacent element differs by at most k.
https://www.geeksforgeeks.org/searching-array-adjacent-differ-k/

A step array is an array of integer where each element has a difference of at most k with its neighbor. Given a key x, we need to find the index value of k if multiple element exist return the first occurrence of key.

Examples:

Input : arr[] = {4, 5, 6, 7, 6}
        k = 1 (max diff bet two elements in array)
        x = 6 (element to find)
Output : 2
The first index of 6 is 2.


Input : arr[] = {20, 40, 50, 70, 70, 60}
        k = 20 (max diff bet two elements in array)
        x = 60 (element to find)
Output : 5
The index of 60 is 5


A Simple Approach is to traverse the given array one by one and compare every element with given element ‘x’. If matches, then return index.

The above solution can be Optimized using the fact that difference between all adjacent elements is at most k.
The idea is to start comparing from the leftmost element and find the difference between current array element and x. Let this difference be ‘diff’. From the given property of array, we always know that x must be at-least ‘diff/k’ away, so instead of searching one by one, we jump ‘diff/k’.
*/
public class _2SearchingInArrayWhereAdjacentDifferByAtMostK {

    public static void main(String[] args) {
        {
            int arr[] = {20, 40, 50, 70, 70, 60};
            int x = 60;
            int k = 20;
            int index = search(arr, x, k);
            System.out.println(index);//5
        }
        {
            int arr[] = {4, 5, 6, 7, 6};
            int x = 6;
            int k = 1;
            int index = search(arr, x, k);
            System.out.println(index);//2
        }
    }

    private static int search(int[] A, int elementToFind, int K) {
        int index = 0;

        while (index < A.length) {
            int element = A[index];

            if (element == elementToFind) {
                return index;
            }

            index += Math.max(1, Math.abs(element - elementToFind) / K); // Very Important: Math.max(1, ....) because division value can be 0 also (e.g. 10/20=0)
        }
        return -1;
    }
}
