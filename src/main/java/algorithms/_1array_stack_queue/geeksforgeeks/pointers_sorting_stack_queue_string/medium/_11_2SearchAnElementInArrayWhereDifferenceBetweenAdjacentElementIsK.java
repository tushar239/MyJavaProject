package algorithms._1array_stack_queue.geeksforgeeks.pointers_sorting_stack_queue_string.medium;

/*

    Searching in an array where adjacent differ by at most k

    https://www.geeksforgeeks.org/searching-array-adjacent-differ-k/

    A step array is an array of integer where each element has a difference of atmost k with its neighbor. Given a key x, we need to find the index value of k if multiple element exist return the first occurrence of key.

    Examples:

    Input : arr[] = {4, 5, 6, 7, 6}
               k = 1 ----- difference between adjacent elements
               x = 6 ----- element to find
    Output : 2
    The first index of 6 is 2.

    Input : arr[] = {20, 40, 50, 70, 70, 60}
              k = 20 ----- difference between adjacent elements
              x = 60 ----- element to find
    Output : 5
    The index of 60 is 5


    This problem is an extension of 'SearchAnElementInArrayWhereDifferenceBetweenAdjacentElementsIs1.java'

    Brute-Force approach:
    A Simple Approach is to traverse the given array one by one and compare every element with given element ‘x’.
    If matches, then return index.

    Better approach:
    The above solution can be Optimized using the fact that difference between all adjacent elements is at most k.
    The idea is to start comparing from the leftmost element and find the difference between current array element and x. Let this difference be ‘diff’. From the given property of array, we always know that x must be at-least ‘diff/k’ away, so instead of searching one by one, we jump ‘diff/k’.
*/
public class _11_2SearchAnElementInArrayWhereDifferenceBetweenAdjacentElementIsK {

    public static void main(String[] args) {
        int[] A = {20, 40, 50, 70, 70, 60};

        search(A, 60, 20);
    }

    private static void search(int[] A, int elementToFind, int diff) {
        if (A == null || A.length == 0) {
            System.out.println("Element can not found because array is either null or empty");
            return;
        }

        for (int i = 0; i < A.length; ) {
            int element = A[i];
            if (element == elementToFind) {
                System.out.println("Element found at index "+i);
                return;
            }

            int jumpingIndex = (elementToFind - element) / diff; // IMPORTANT: whenever you use division, consider a probability of 0 as an answer.
            if (jumpingIndex == 0) jumpingIndex = 1;

            i = i + jumpingIndex;
        }

        System.out.println("Element not found");
    }
}
