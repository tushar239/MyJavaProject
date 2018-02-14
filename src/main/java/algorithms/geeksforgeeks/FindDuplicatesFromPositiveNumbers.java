package algorithms.geeksforgeeks;

/*
Given an array of n elements which contains elements from 0 to n-1, with any of these numbers appearing any number of times. Find these repeating numbers in O(n) and using only constant memory space.
It means that if size of the array is 7, then array can have values from 0 to 6. It cannot be more than 6.

For example, let n be 7 and array be {1, 2, 3, 1, 3, 6, 6}, the answer should be 1, 3 and 6.

Below approach won't find duplicate 0s.
    https://www.geeksforgeeks.org/find-duplicates-in-on-time-and-constant-extra-space/
    https://www.youtube.com/watch?v=HuZJqRDOPo0

Below approach will find duplicate 0s also.
    https://www.geeksforgeeks.org/duplicates-array-using-o1-extra-space-set-2/
*/
public class FindDuplicatesFromPositiveNumbers {

    public static void main(String[] args) {
        FindDuplicatesFromPositiveNumbers instance = new FindDuplicatesFromPositiveNumbers();
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

    // This approach won't find duplicate 0s.
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

    // This approach will find duplicate 0s also.
    // https://www.geeksforgeeks.org/duplicates-array-using-o1-extra-space-set-2/
    private void findDuplicatesAnotherApproach(int A[]) {
        if (A == null || A.length == 0) {
            return;
        }

        for (int i = 0; i < A.length; i++) {
            int index = A[i] % A.length;
            A[index] += A.length;
        }

        for (int i = 0; i < A.length; i++) {
            if ((A[i] / A.length) > 1) {
                System.out.println("Duplicate element: " + i);// IMPORTANT: index number is represents duplicate element
            }

        }
    }
}
