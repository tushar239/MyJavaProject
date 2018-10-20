package algorithms._1array_stack_queue.geeksforgeeks.pointers_sorting_stack_queue_string.hard;
/*
Find a subarray of size k whose average is max. (sequence of contiguous elements in the array).
https://www.youtube.com/watch?v=fCGLjlsEsNA

Brute-Force: you can use an algorithm to find all subarrays of size k and then find one from them with max avg. This will take O(n^3) just like PrintAllSubSequencesAndSubArrays.java
Better approach:
Hint - Find sum of first k elements and then look through remaining array in O(n).

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
        System.out.println(avg);//11

        avg = instance.maxAvgContiguousSubArray_better(A, K);
        System.out.println(avg);//11
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
                    //System.out.println(slow);
                    maxAvg = avg;
                }

                sum = sum - A[slow];
                slow++;
                count = 1;
            }
        }

        return maxAvg;
    }

    /*

        1   5   3   2   7   4       K=3



        get the sum of first K elements

                i
        1   5   3   2   7   4

        sum = 1 + 5 + 3 = 9


        avg = 9/K = 3
        maxAvg = 3




                    i
        1   5   3   2   7   4

        sum = sum - A[i-K] = 9-1 = 8
        sum = sum + A[i] = 8+2 = 10

        avg = 10/K = 3
        maxAvg = 3

                        i
        1   5   3   2   7   4

        sum = sum - A[i-K] = 10-5 =5
        sum = sum + A[i] = 5+7 = 12

        avg = 12/K = 4
        maxAvg = 4


                            i
        1   5   3   2   7   4

        sum = sum - A[i-K] = 12-3 =9

        avg = 9/K = 3
        maxAvg is still 4



        Answer = maxAvg=4

     */
    private int maxAvgContiguousSubArray_better(int[] A, int K) {

        if (A == null || A.length == 0 || K <= 0 || A.length < K) return 0;

        int sum = 0;
        int maxAvg = 0;

        // find sum and maxAvg from first k elements
        for (int i = 0; i < K; i++) {

            sum += A[i];

            int avg = sum/K;

            if(avg > maxAvg) {
                maxAvg = avg;
            }
        }

        // At this point, you will have sum from first K elements.

        // now iterate remaining array and take out i-Kth element of an array from sum and add ith element
        // and keep track of maxAvg
        for (int i = K; i < A.length; i++) {

            sum = sum - A[i-K];

            sum = sum + A[i];

            int avg = sum/K;

            if(avg > maxAvg) {
                maxAvg = avg;
            }

        }

        return maxAvg;

    }

}
