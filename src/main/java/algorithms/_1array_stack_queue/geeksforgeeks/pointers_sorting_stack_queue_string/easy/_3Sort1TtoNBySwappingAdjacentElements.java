package algorithms._1array_stack_queue.geeksforgeeks.pointers_sorting_stack_queue_string.easy;
/*
Sort 1 to N by swapping adjacent elements

https://www.geeksforgeeks.org/sort-1-n-swapping-adjacent-elements/

Given an array A of size N consisting of elements 1 to N. A boolean array B consisting of N-1 elements indicates that if B[i] is 1 then A[i] can be swapped with A[i+1]. Find if A can be sorted by swapping elements.

Examples:

Input : A[] = {1, 2, 5, 3, 4, 6}
        B[] = {0, 1, 1, 1, 0}
Output : A can be sorted
We can swap a[3] with a[4] and then a[4] with a[5].

Input : A[] = {2, 3, 1, 4, 5, 6}
        B[] = {0, 1, 1, 1, 1}
Output : A can not be sorted
We can not sort A by swapping elements.

*/
public class _3Sort1TtoNBySwappingAdjacentElements {

    public static void main(String[] args) {
        {
            int A[] = {1, 2, 5, 3, 4, 6};
            int B[] = {0, 1, 1, 1, 0};
            boolean canBeSorted = canBeSorted(A, B);
            System.out.println(canBeSorted);//true
        }
        {
            int A[] = {2, 3, 1, 4, 5, 6};
            int B[] = {0, 1, 1, 1, 1};
            boolean canBeSorted = canBeSorted(A, B);
            System.out.println(canBeSorted);//false
        }
    }
    private static boolean canBeSorted(int[] A, int[] B) {

        for (int i = 0; i < A.length-1; i++) {
            if(B[i] == 1 && A[i] > A[i+1]) {
                // Swapping logic using XOR
                A[i]    = A[i] ^ A[i+1];
                A[i+1]  = A[i] ^ A[i+1];
                A[i]    = A[i] ^ A[i+1];
            }
        }
/*
        for (int i = 0; i < A.length; i++) {
            System.out.println(A[i]);
        }
*/

        for (int i = 0; i < A.length; i++) {
            if(A[i] != (i+1)) {
                return false;
            }
        }
        return true;
    }
}
