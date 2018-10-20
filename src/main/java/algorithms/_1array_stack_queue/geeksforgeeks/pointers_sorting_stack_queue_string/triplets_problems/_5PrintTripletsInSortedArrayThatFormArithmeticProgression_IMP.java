package algorithms._1array_stack_queue.geeksforgeeks.pointers_sorting_stack_queue_string.triplets_problems;

/*
Print all triplets in sorted array that form AP
https://www.geeksforgeeks.org/print-triplets-sorted-array-form-ap/

Given a sorted array of distinct positive integers, print all triplets that form AP (or Arithmetic Progression)

Examples :

    Input : arr[] = { 2, 6, 9, 12, 17, 22, 31, 32, 35, 42 };

        Output :
        6 9 12
        2 12 22
        12 17 22
        2 17 32
        12 22 32
        9 22 35
        2 22 42
        22 32 42

    Input : arr[] = { 3, 5, 6, 7, 8, 10, 12};

        Output :
        3 5 7
        5 6 7
        6 7 8
        6 8 10
        8 10 12

Solution:
    Brute-Force Approach:
        You need 3 pointers (i,j,k). Create 3 loops (one inside another) and compare A[j]-A[i] with A[k]-A[j]. If both are same, then print A[i],A[j],A[k] as eligible triplet.
        otherwise, keep increasing k till end of array. then increase j till end of array then increase i till end of array.
        for(int i=0; i<n; i++) {
            for(int j=i+1; i<n; i++) {
                for(int k=j+1; i<n; i++) {
                    if( (A[j] - A[i]) == (A[k] - A[j]) ) {
                       print  A[i],A[j],A[k] is an eligible triplet
                }
            }
        }

        It is very easy to find a solution in O(n^3) this way.

    Better Approach:
        How can we make it better?
        One thing we know that to convert O(n^3)to O(n^2), a trick is to keep one pointer stable and keep moving other twos together based on certain conditions.
        So, Keep i stable and move j and k together based on certain conditions, so that j and k covers entire array just once for every i.
        To make this trick work, sometimes you need to have sorted array.

        for(int i=0; i<n; i++) {
            j = ....
            k = ...

            while(j... && k...) {
                if(...) j-- / j++
                if(...) k++ / k--
                if(...) print A[i],A[j],A[k] is an eligible triplet
            }
        }

        As you see here, for every i, j and k will traverse the array just once. This will give you O(n^2).

        For this particular example,
        We will keep i in between and j on the left of i and k on the right of i

         2, 6, 9, 12, 17, 22, 31, 32, 35, 42
      <- j  i  k ->

      We will keep moving k and j till we find equal Arithmetic Progression from i.

 */
public class _5PrintTripletsInSortedArrayThatFormArithmeticProgression_IMP {

    public static void main(String[] args) {
        int arr[] = {2, 6, 9, 12, 17, 22, 31, 32, 35, 42};
        print(arr);

        System.out.println("Another way");
        print_another(arr);
    }

    private static void print(int[] A) {
        for (int i = 0; i < A.length; i++) {
            int j = i - 1;
            int k = i + 1;

            while (j >= 0 && k < A.length) {

                int middle = A[i];
                int left = A[j];
                int right = A[k];

                if ((middle - left) == (right - middle)) {
                    System.out.println(left + "," + middle + "," + right);
                    k++;
                    j--;
                } else if ((right - middle) < (middle - left)) {
                    k++;
                } else {
                    j--;
                }
            }
        }
    }

    private static void print_another(int[] A) {

        //int count =0;

        for (int i = 0; i < A.length; i++) {
            int j = i + 1;
            int k = i + 2;

            while (j < k && k < A.length) {

                if ((A[j] - A[i]) > (A[k] - A[j])) k++;
                else if ((A[j] - A[i]) < (A[k] - A[j])) {
                    j++;
                    if (j == k) k++;
                } else {
                    System.out.println(A[i] + "," + A[j] + "," + A[k]);
                    k++;
                }
            }

        }
        //System.out.println(count);
    }

}
