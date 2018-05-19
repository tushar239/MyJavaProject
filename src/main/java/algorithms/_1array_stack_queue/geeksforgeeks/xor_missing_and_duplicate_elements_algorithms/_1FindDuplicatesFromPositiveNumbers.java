package algorithms._1array_stack_queue.geeksforgeeks.xor_missing_and_duplicate_elements_algorithms;

/*
Find Duplicates when there are more than one duplicates:

    https://www.geeksforgeeks.org/find-duplicates-in-on-time-and-constant-extra-space/

    Given an array of n elements which contains elements from 0 to n-1, with any of these numbers appearing any number of times. Find these repeating numbers in O(n) and using only constant memory space.
    It means that if size of the array is 7, then array can have values from 0 to 6. It cannot be more than 6.

    For example,
    let n be 7 and array be {1, 2, 3, 1, 3, 6, 6}, the answer should be 1, 3 and 6.

Solution:

    Below approach won't find duplicate 0s.
        https://www.geeksforgeeks.org/find-duplicates-in-on-time-and-constant-extra-space/
        https://www.youtube.com/watch?v=HuZJqRDOPo0

    Below approach will find duplicate 0s also.
        https://www.geeksforgeeks.org/duplicates-array-using-o1-extra-space-set-2/

Find Duplicate in a sorted array when there is just one duplicate:
    FindTheOnlyRepeatingElementInSortedArrayOfSizeN.java
*/
public class _1FindDuplicatesFromPositiveNumbers {

    public static void main(String[] args) {
        _1FindDuplicatesFromPositiveNumbers instance = new _1FindDuplicatesFromPositiveNumbers();
        {
            int A[] = {0, 1, 4, 3, 1, 7, 6, 5, 7, 0, 3}; // this approach won't be able to find duplicate 0s.
            //int A[] = {1, 2, 3, 1, 3, 6, 6};
            //int A[] = {1, 2, 33, 1, 33, 16, 16};// won't work because there are element greater than size of an array
            instance.findDuplicates(A);
        }
        System.out.println();
        {
            int A[] = {0, 1, 4, 3, 1, 7, 6, 5, 7, 0, 3}; // this approach will be able to find duplicate 0s.
            //int A[] = {1, 2, 3, 1, 3, 6, 6};
            //int A[] = {1, 2, 33, 1, 33, 16, 16};// won't work because there are element greater than size of an array
            instance.findDuplicatesAnotherApproach(A);
        }
        /*System.out.println(0 % 11);//0
        System.out.println(1 % 11);//1
        System.out.println(2 % 11);//2
        System.out.println(10 % 11);//10
        System.out.println(11 % 11);//0


        System.out.println(0 / 11);//0
        System.out.println(1 / 11);//0
        System.out.println(2 / 11);//0
        System.out.println(10 / 11);//0
        System.out.println(11 / 11);//1
        System.out.println(12 / 11);//1

        double d = 29 / 11;
        System.out.println(d);//2.0*/

    }

    // This approach will find duplicate 0s also.
    // https://www.geeksforgeeks.org/duplicates-array-using-o1-extra-space-set-2/
    // You can use this approach when array can have 0 also in an array.
    private void findDuplicatesAnotherApproach(int A[]) {
        if (A == null || A.length == 0) {
            return;
        }

        for (int i = 0; i < A.length; i++) { // O(n)
            int element = A[i];

            int index = element % A.length;
            A[index] += A.length;
        }

        for (int i = 0; i < A.length; i++) { // O(n)
            int element = A[i];

            if ((element / A.length) > 1) { // IMPORTANT
                System.out.println("Duplicate element: " + i);// IMPORTANT: index number is represents duplicate element
            }

        }
    }

    // This approach won't find duplicate 0s.
    // You can use this approach when array has 1 to n numbers (no 0)
    private void findDuplicates(int A[]) {
        if (A == null || A.length == 0) {
            return;
        }

        for (int i = 0; i < A.length; i++) {
            int element = Math.abs(A[i]); // important

            // visit A[element]. If it is -ve, then that element is duplicate. Otherwise make it -ve.

            if (element < A.length && A[element] < 0) {
                System.out.println("Duplicate element: " + element);
                continue;
            }

            if (element < A.length && element >= 0) {
                A[element] = -A[element];
                continue;
            }
        }
    }
}
