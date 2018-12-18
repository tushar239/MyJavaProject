package algorithms.crackingcodinginterviewbook._6sort__search_merge;

import java.util.Arrays;

// p.g. 397 of Cracking Coding Interview book
/*
Two sorted arrays
A = [1,3,5,7]
B = [0,2,4,6,8,9]

Assume A is big enough to accommodate B also.
A = [1,3,5,7, , , , , , ]

Solution 1 (not good)
 If you try to compare two arrays from their beginnings, then before you insert B's element into A, you need move all A's elements first.
 So, it doesn't seem like a good idea.

Solution 2
 Merge sort's concur method.

 Start comparing A and B from their last positions.
 If B's element is bigger then insert it at the end of A and reduce B's index by 1
 You need 3 pointers - one starts from the actual last index of A (a=3)
                       second starts from last index of B (b=5)
                       third starts from last index of A (c=9)

   a=3, b=5, c=9

   step 1

    A = [1,3,5,7, , , , , ,9]
    b=4, c=8

   step 2
    A = [1,3,5,7, , , , ,8,9]
    b=3, c=7

   step 3
   A = [1,3,5, , , , ,7,8,9]
   a=2,b=3, c=6

   and so on till a>=0 or b>=0


    Time Complexity= O(m+n)

 */
public class _1MergeTwoSortedArrays {

    public static void main(String[] args) {
        Integer[] A = {1, 3, 5, 7, null, null, null, null, null, null};
        Integer[] B = {0, 2, 4, 6, 8, 9};

        merge(A, B, 3, B.length - 1, A.length - 1);
        System.out.println(Arrays.asList(A));
        System.out.println(count);
    }

    static int count = 0;

    private static void merge(Integer[] A, Integer[] B, int lastIndexOfA, int lastIndexOfB, int lastIndexOfAWithMerge) {
        int a = lastIndexOfA;
        int b = lastIndexOfB;
        int m = lastIndexOfAWithMerge;

        while (a >= 0 && b >= 0) {
            count++;

            if (A[a] > B[b]) {
                A[m] = A[a];
                a--;
                m--;
            } else if (A[a] < B[b]) {
                A[m] = B[b];
                b--;
                m--;
            } else {
                A[m] = A[a];
                a--;
                m--;

                A[m] = B[b];
                b--;
                m--;
            }

        }

        // important
        while (a >= 0) {
            count++;

            A[m] = A[a];
            a--;
            m--;
        }

        while (b >= 0) {
            count++;

            A[m] = B[b];
            b--;
            m--;
        }

    }

}
