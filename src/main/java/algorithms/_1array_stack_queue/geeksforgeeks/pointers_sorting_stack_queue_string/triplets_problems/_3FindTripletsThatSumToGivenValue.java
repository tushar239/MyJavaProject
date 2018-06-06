package algorithms._1array_stack_queue.geeksforgeeks.pointers_sorting_stack_queue_string.triplets_problems;

import java.util.Arrays;

/*
Find a triplet that sum to a given value

https://www.geeksforgeeks.org/find-a-triplet-that-sum-to-a-given-value/

This problem is very similar to "CountTripletsWithSumSmallerThanGivenValue.java" with slight change.

int[] arr = {5, 1, 3, 4, 7}, expectedSum=15

int[] arr = {1, 3, 4, 5, 7} --- sorted array

1, 3, 4, 5, 7
i  j        k

for (int i = 0; i < A.length; i++) {

    int j = i+1;
    int k = A.length - 1;

    while(k > j)
        if(A[i]+A[j]+A[k] == expectedSum) {
            print a triplet A[i],A[j],A[k]
            break;
        } else if(A[i]+A[j]+A[k] < expectedSum) {
            j++;
        } else {
            k--;
        }
    }
}

*/
public class _3FindTripletsThatSumToGivenValue {

    public static void main(String[] args) {
        {
            int A[] = {12, 3, 4, 1, 6, 9};
            printTriplets(A, 24);
        }
        {
            int A[] = {12, 3, 4, 1, 6, 9};
            printTriplets(A, 12);
        }
    }

    private static void printTriplets(int[] A, int expectedSum) {

        // sort the array
        Arrays.sort(A); //O(n log n)

        int count = 0;

        // find triplets
        // O(n^2)
        int k = A.length - 1;

        for (int i = 0; i < A.length; i++) {

            int j = i + 1;
            // int k = A.length - 1; ----- actual algorithm online has this line instead of initializing k outside for loop. But I think, this is not needed.

            while (k > j) {
                int sum = A[i] + A[j] + A[k];

                if (sum == expectedSum) {
                    System.out.println(A[i] + "," + A[j] + "," + A[k]);
                    break;
                } else if (sum < expectedSum) {
                    j++;
                } else {
                    k--;
                }
            }
        }
    }

}
