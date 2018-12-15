package algorithms._1array_stack_queue.geeksforgeeks.dynamic_programming._2subsequence_substring_in_string_problems._1LIS_LCS_LRS_Algorithms._1subsequence;

/*
    Longest Increasing Odd Even Subsequence

    https://www.geeksforgeeks.org/longest-increasing-odd-even-subsequence/

    Input : arr[] = {5, 6, 9, 4, 7, 8}
    Output : 4
    {5, 6, 7, 8} is the required longest increasing odd even subsequence.

    This algorithm is an extension to LongestIncreasingSubSequenceInArray.java
*/
public class _0_1LIS_LongestIncreasingOddEvenSubSequenceInArray {

    public static void main(String[] args) {

        int[] A = {5, 6, 9, 4, 7, 8}; // O/P: 4 {5,6,7,8}
        int maxLis = maxOddEvenLIS(A);
        System.out.println(maxLis);

        /*maxLis = maxOddEvenLIS_Using_Include_Exclude(A, 0, A.length-1);
        System.out.println(maxLis);*/
    }

    private static int maxOddEvenLIS(int[] A) {
        int maxLis = 0;
        for (int i = 0; i < A.length; i++) {
            maxLis = Math.max(maxLis, maxOddEvenLIS(A, i, A.length - 1));
        }
        return maxLis;
    }

    private static int maxOddEvenLIS(int[] A, int start, int end) {
        if (start == end) {
            return 1;
        }

        int currentEle = A[start];

        int currentElementIndex = start;


        int maxLis = 0;

        for (int i = currentElementIndex + 1; i <= end; i++) {

            int nextElementIndex = i;
            int nextElement = A[nextElementIndex];


            // this is the only extra condition that is required compared to LongestIncreasingSubSequenceInArray.java
            boolean isItNextPossibleElement = isItNextPossibleElement(A, currentEle, nextElement, end);

            int lisIncludingCurrentEle = 1;

            if (isItNextPossibleElement) {
                lisIncludingCurrentEle = 1 + maxOddEvenLIS(A, nextElementIndex, end);
            }

            if (lisIncludingCurrentEle > maxLis) {
                maxLis = lisIncludingCurrentEle;
            }

        }

        return maxLis;
    }

    /*private static int maxOddEvenLIS_Using_Include_Exclude(int[] A, int start, int end) {
        if (start == end) {
            return 1;
        }

        int currentEle = A[start];

        int currentElementIndex = start;


        //for (int i = currentElementIndex + 1; i <= end; i++) {

        int nextElementIndex = currentElementIndex + 1;
        int nextElement = A[nextElementIndex];


        // this is the only extra condition that is required compared to LongestIncreasingSubSequenceInArray.java
        boolean isItNextPossibleElement = isItNextPossibleElement(A, currentEle, nextElement, end);

        int lisIncludingCurrentEle = 1;

        if (isItNextPossibleElement) {
            lisIncludingCurrentEle = 1 + maxOddEvenLIS_Using_Include_Exclude(A, nextElementIndex, end);
        }

        int lisExcludingCurrentEle = maxOddEvenLIS_Using_Include_Exclude(A, currentElementIndex + 1, end);

        int maxLis = Math.max(lisIncludingCurrentEle, lisExcludingCurrentEle);


        //}

        return maxLis;
    }*/


    private static boolean isItNextPossibleElement(int[] A, int currentEle, int nextElement, int end) {
        if (nextElement > currentEle) {
            if (currentEle % 2 == 0) { // if current element is even, then next element has to be odd and greater than current element
                return (nextElement % 2 != 0);
            } else { // if current element is odd, then next element has to be even and greater than current element
                return (nextElement % 2 == 0);
            }
        }
        return false;
    }

}
