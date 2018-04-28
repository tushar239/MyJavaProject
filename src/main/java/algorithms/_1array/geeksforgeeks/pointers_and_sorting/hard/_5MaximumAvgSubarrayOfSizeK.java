package algorithms._1array.geeksforgeeks.pointers_and_sorting.hard;
/*
Maximum average subarray of size k is a subarray (sequence of contiguous elements in the array).
https://www.youtube.com/watch?v=fCGLjlsEsNA

Solution:
 F
 S
[11, -8, 16, -7, 24, -2, 3]


      F
 S
[11, -8, 16, -7, 24, -2, 3]


          F
 S
[11, -8, 16, -7, 24, -2, 3]


    11 + -8 + 16 = 19
    avg = 19/3 = 6. maxAvg=6.

              F
      S
[11, -8, 16, -7, 24, -2, 3]

    sum-A[S]+A[F]   ----- important
    19 - 11 + -7 = 1
    avg = 1/3 = 0

                 F
         S
[11, -8, 16, -7, 24, -2, 3]

    sum-A[S]+A[F]
    1 - -8 + 24 = 33
    avg = 33/3 = 11. maxAvg=11.

                      F
              S
[11, -8, 16, -7, 24, -2, 3]

    sum-A[S]+A[F]
    33 - 16 + -2 = 15
    avg = 15/3 = 5.

                          F
                  S
[11, -8, 16, -7, 24, -2, 3]

    sum-A[S]+A[F]
    15 - -7 + 3 = 25
    avg = 25/3 = 8
*/

public class _5MaximumAvgSubarrayOfSizeK {

    public static void main(String[] args) {
        _5MaximumAvgSubarrayOfSizeK instance = new _5MaximumAvgSubarrayOfSizeK();

        int[] A = {11, -8, 16, -7, 24, -2, 3};// with K=3, O/P=11 (indices=2,3,4)
        int K = 3; //size of subarray

        int avg = instance.maxAvgContiguousSubArray(A, K);
        System.out.println(avg);
    }

    private int maxAvgContiguousSubArray(int[] A, int K) {
        if (A == null || A.length == 0 || K <= 0 || A.length < K) return 0;

        int slow = 0;

        int maxAvg = 0;
        int sum = 0;

        int count = K;

        for (int fast = 0; fast < A.length; fast++) {

            sum = sum + A[fast];

            count--;

            if (count == 0) {

                int avg = sum / K;

                if (avg > maxAvg) {
                    System.out.println(slow);
                    maxAvg = avg;
                }

                sum = sum - A[slow];
                slow++;
                count = 1;
            }
        }

        return maxAvg;
    }
}
