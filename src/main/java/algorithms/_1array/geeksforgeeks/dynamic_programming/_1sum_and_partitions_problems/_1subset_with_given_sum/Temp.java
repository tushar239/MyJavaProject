package algorithms._1array.geeksforgeeks.dynamic_programming._1sum_and_partitions_problems._1subset_with_given_sum;

public class Temp {
    public static void main(String[] args) {
        int[] A = {1, 2, 3, -5, 6, 7, -7, 9, 5, -8, 3, 10, 14, 15};
        {
            int tripletMaxSum = triplet(A);
            System.out.println(tripletMaxSum);
        }
        {
            int tripletMaxSum = tripletRecursively(A, 0, A.length - 1);
            System.out.println(tripletMaxSum);
        }
        {
            int tripletMaxSum = tripletRecursively_2(A, 0, 1, 2, A.length - 1);
            System.out.println(tripletMaxSum);
        }

    }

    private static int triplet(int[] A) {

        int length = A.length - 1;
        int maxSum = 0;

        for (int i = 0; i <= length; i++) {
            for (int j = i + 1; j <= length; j++) {
                for (int k = j + 1; k <= length; k++) {
                    int sum = A[i] + A[j] + A[k];
                    if (sum > maxSum) {
                        System.out.println(A[i] + "," + A[j] + "," + A[k]);
                        maxSum = sum;
                    }
                }

            }
        }
        return maxSum;
    }

    private static int tripletRecursively(int[] A, int start, int end) {
        if (start == end) return 0;
        if (start > end - 2) return 0;
/*
        if(end < start+2) return 0;
        if(end == start+3) return A[start]+A[start+1]+A[start+2];
*/

        int element = A[start];

        int maxSum = 0;
        for (int j = start + 1; j <= end; j++) {
            for (int k = j + 1; k <= end; k++) {
                int sum = element + A[j] + A[k];
                if (sum > maxSum) {
                    maxSum = sum;
                }
            }
        }

        return Math.max(maxSum, tripletRecursively(A, start + 1, end));
    }

    private static int tripletRecursively_2(int[] A, int start, int start2, int start3, int end) {

        if (start > end - 2 || start2 > end - 1 || start3 > end) {
            return 0;
        }

        int sum = A[start] + A[start2] + A[start3];


        return Math.max(Math.max(Math.max(sum, tripletRecursively_2(A, start, start2, start3 + 1, end)),
                tripletRecursively_2(A, start, start2 + 1, start3 + 1, end)),
                tripletRecursively_2(A, start + 1, start2 + 1, start3 + 1, end)
        );

    }
}
