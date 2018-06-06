package algorithms._1array_stack_queue.geeksforgeeks.pointers_sorting_stack_queue_string.hard;

import java.util.Arrays;

/*
Find minimum difference between any two elements

https://www.youtube.com/watch?v=Cr20pvlhqBU
 */
public class _2MinimumDifferenceBetweenTwoElementsOfAnArray {

    public static void main(String[] args) {
        _2MinimumDifferenceBetweenTwoElementsOfAnArray instance = new _2MinimumDifferenceBetweenTwoElementsOfAnArray();

        //int A[] = {10, 3, 6, 8};//(8-10=2)
        int A[] = {-1, -9, 0, 7, 5, -11, -13, 11, 15};//1

        int minDiff = instance.findMinDiff(A);
        System.out.println(minDiff);

        minDiff = instance.findMinDiffEfficient(A);
        System.out.println(minDiff);
    }

    private int findMinDiff(int A[]) {
        if (A == null || A.length <= 1) return 0;

        Arrays.sort(A);// O(n log n)

        int minAbsDiff = Integer.MAX_VALUE;

        for (int i = 1; i < A.length; i++) {// O(n)
            if ((A[i] - A[i - 1]) < minAbsDiff) {
                minAbsDiff = A[i] - A[i - 1];
            }
        }
        return minAbsDiff;
    }

    /*
    Very tricky solution, but more efficient than above one because it takes O(n).
    This trick is my own invention.

    int A[] = {10, 3, 6, 8, -1};

    num1        num2                    diff
    10          Integer.MAX_VALUE

    3 - 10 = 7
    3 - Integer.MAX_VALUE = some bigger number

    here 7 is lesser, so make 3 as num2. This will reduce the diff between num1 and num2.

    num1        num2                    minDiff
    10          3                        7


    6 - 10 = 4
    6 - 3 = 3

    here if you make num1=6 instead of num2=6, then diff of num1 and num2 will be smaller
    3 is smaller than prev minDiff(7). So, change minDiff=3.

    num1        num2                    minDiff
    6           3                        3

    8 - 6 = 2
    8 - 3 = 5

    here if you make num2=8 instead of num1=6, then diff of num1 and num2 will be smaller, which will be 2.
    2 is smaller than prev minDiff(3). So, change minDiff=2.

    num1        num2                    minDiff
    6           8                        2

    -1 - 6 = 7
    -1 - 8 = 9

    here if you make num2=-1 instead of num1=-1, then diff of num1 and num2 will be smaller, which will be 7.
    But 7 is bigger than prev diff. So, no change in minDiff

     */
    private int findMinDiffEfficient(int A[]) {
        if (A == null || A.length <= 1) return 0;

        int num1 = A[0];
        int num2 = Integer.MAX_VALUE;

        int minAbsDiff = Math.abs(num1 - num2);

        for (int i = 1; i < A.length; i++) {// O(n)
            int withNum1 = Math.abs(A[i] - num1);
            int withNum2 = Math.abs(A[i] - num2);

            if (withNum1 < withNum2) {
                num2 = A[i];
            } else {
                num1 = A[i];
            }

            int diff = Math.abs(num1 - num2);

            if (diff < minAbsDiff) {
                minAbsDiff = diff;
            }
        }
        return minAbsDiff;
    }
}
