package algorithms._1array.geeksforgeeks.dynamic_programming._2subsequence_substring_in_string_problems._1LCS_Longest_Common_Subsequence_Substring_Algorithms._1subsequence;

import java.util.HashMap;
import java.util.Map;

/*
Longest Increasing Subsequence Size

    https://www.geeksforgeeks.org/longest-monotonically-increasing-subsequence-size-n-log-n/

    https://www.youtube.com/watch?v=E6us4nmXTHs

*/
public class _4LongestIncreasingSubSequenceInArray {

    public static void main(String[] args) {

        {
            int[] A = {1, 2, 3, 4, 5, 6};

            System.out.println("Brute-Force Approach");
            int maxIncreasingSubSequenceSize = LIS_Brute_Force(A, 0, A.length - 1, true);
            System.out.println("Result:" + maxIncreasingSubSequenceSize);//6 - {1,2,3,4,5,6}
//            System.out.println("Time Complexity is O(2^n): " + cnt);

            System.out.println("Dynamic Programming Top-Down Approach");
            maxIncreasingSubSequenceSize = LIS_With_Dynamic_Top_Down_Approach(A, 0, A.length - 1, true, new HashMap<>());
            System.out.println();
            System.out.println("Result:" + maxIncreasingSubSequenceSize);//6 - {1,2,3,4,5,6}
//            System.out.println("Time Complexity is O(n): " + count);

            System.out.println("Different Way Brute-Force");
            maxIncreasingSubSequenceSize = longestSubsequenceRecursive_Brute_Force(A);
            System.out.println("Result:" + maxIncreasingSubSequenceSize);//6 - {1,2,3,4,5,6}

            System.out.println("Different Way Dynamic Programming Top-Down");
            maxIncreasingSubSequenceSize = longestSubsequenceRecursive_top_down(A);
            System.out.println("Result:" + maxIncreasingSubSequenceSize);//6 - {1,2,3,4,5,6}
        }

        co = 0;
        System.out.println();

        {
            int[] A = {1, 12, 3, 4};

            System.out.println("Brute-Force Approach");
            int maxIncreasingSubSequenceSize = LIS_Brute_Force(A, 0, A.length - 1, true);
            System.out.println("Result:" + maxIncreasingSubSequenceSize);//3 - {1,3,4}

            System.out.println("Dynamic Programming Top-Down Approach");
            maxIncreasingSubSequenceSize = LIS_With_Dynamic_Top_Down_Approach(A, 0, A.length - 1, true, new HashMap<>());
            System.out.println();
            System.out.println("Result:" + maxIncreasingSubSequenceSize);//3 - {1,3,4}

            System.out.println("Different Way Brute-Force");
            maxIncreasingSubSequenceSize = longestSubsequenceRecursive_Brute_Force(A);
            System.out.println("Result:" + maxIncreasingSubSequenceSize);//3 - {1,3,4}

            System.out.println("Different Way Dynamic Programming Top-Down");
            maxIncreasingSubSequenceSize = longestSubsequenceRecursive_top_down(A);
            System.out.println("Result:" + maxIncreasingSubSequenceSize);//3 - {1,3,4}

        }

        co = 0;
        System.out.println();

        {
            int[] A = {1, 0, 4, 5, 2, 7};

            System.out.println("Brute-Force Approach");
            int maxIncreasingSubSequenceSize = LIS_Brute_Force(A, 0, A.length - 1, true);
            System.out.println("Result:" + maxIncreasingSubSequenceSize); //4 - {1,4,5,7} or {0,4,5,7}
//            System.out.println("Time Complexity is O(2^n): " + cnt);

            System.out.println("Dynamic Programming Top-Down Approach");
            maxIncreasingSubSequenceSize = LIS_With_Dynamic_Top_Down_Approach(A, 0, A.length - 1, true, new HashMap<>());
            System.out.println();
            System.out.println("Result:" + maxIncreasingSubSequenceSize); //4 - {1,4,5,7} or {0,4,5,7}
//            System.out.println("Time Complexity is O(n): " + count);

            System.out.println("Different Way Brute-Force");
            maxIncreasingSubSequenceSize = longestSubsequenceRecursive_Brute_Force(A);
            System.out.println("Result:" + maxIncreasingSubSequenceSize); //4 - {1,4,5,7} or {0,4,5,7}

            System.out.println("Different Way Dynamic Programming Top-Down");
            maxIncreasingSubSequenceSize = longestSubsequenceRecursive_top_down(A);
            System.out.println("Result:" + maxIncreasingSubSequenceSize); //4 - {1,4,5,7} or {0,4,5,7}
        }

        co = 0;
        System.out.println();

        {
            //int[] A = {15, 4, 12, 2, 10};//2 - {4, 12}  or {2, 10}
            //int[] A = {15, 4, 12, 2, 10, 6, 9, 0};//3 - {2,6,9}
            int[] A = {0, 4, 12, 2, 10, 6, 9, 13, 3, 11, 7, 15}; //6 - {0,2,6,9,11,15}

            System.out.println("Brute-Force Approach");
            int maxIncreasingSubSequenceSize = LIS_Brute_Force(A, 0, A.length - 1, true);
            System.out.println("Result:" + maxIncreasingSubSequenceSize);
//            System.out.println("Time Complexity is O(2^n): " + cnt);

            System.out.println("Dynamic Programming Top-Down Approach");
            maxIncreasingSubSequenceSize = LIS_With_Dynamic_Top_Down_Approach(A, 0, A.length - 1, true, new HashMap<>());
            System.out.println();
            System.out.println("Result:" + maxIncreasingSubSequenceSize);
//            System.out.println("Time Complexity is O(n): " + count);

            System.out.println("Different Way Brute-Force");
            maxIncreasingSubSequenceSize = longestSubsequenceRecursive_Brute_Force(A);
            System.out.println("Result:" + maxIncreasingSubSequenceSize);

            System.out.println("Different Way Dynamic Programming Top-Down");
            maxIncreasingSubSequenceSize = longestSubsequenceRecursive_top_down(A);
            System.out.println("Result:" + maxIncreasingSubSequenceSize);

        }


    }


    // https://github.com/mission-peace/interview/blob/master/src/com/interview/dynamic/LongestIncreasingSubsequence.java
    // O(2^n)
    private static int co;

    private static int longestSubsequenceRecursive_Brute_Force(int arr[]) {
        int maxLen = 0;
        for (int i = 0; i < arr.length - 1; i++) { // O(n-1)

            int currentElementIndex = i;

            int len = longestSubsequenceRecursive_Brute_Force(arr, currentElementIndex, currentElementIndex + 1);
            if (len > maxLen) {
                maxLen = len;
            }
        }
        System.out.println("time complexity: " + co);
        return maxLen + 1;
    }


    /*
                    m(1,2,3,4)
            m(2,3,4)            m(2,3,4)  ---- O(1) operation happens on each node
         m(3,4) m(3,4)      m(3,4) m(3,4)  ---- O(1) operation happens on each node
         ...

         at each level O(1) operation is happening on each node. total number of nodes will be 2^n. So, time complexity = O(2^n)
     */

    private static int longestSubsequenceRecursive_Brute_Force(int[] arr, int currentElementIndex, int nextElementIndex) {
        co++;

        if (nextElementIndex == arr.length) {
            return 0;
        }

        int currentElement = arr[currentElementIndex];

        int t1 = 0;
        if (arr[nextElementIndex] > currentElement) {
            t1 = 1 + longestSubsequenceRecursive_Brute_Force(arr, nextElementIndex, nextElementIndex + 1);
        }
        int t2 = longestSubsequenceRecursive_Brute_Force(arr, currentElementIndex, nextElementIndex + 1);
        return Math.max(t1, t2);
    }


    // O(n^2)
    private static int longestSubsequenceRecursive_top_down(int arr[]) {
        Map<Integer, Integer> memo = new HashMap<>();

        int maxLen = 0;

        for (int i = 0; i < arr.length - 1; i++) { // O(n)

            int currentElementIndex = i;

            // for the first element O(n^2) will happen.
            // from second element it will return already memoized results.
            int len = longestSubsequenceRecursive_top_down(arr, currentElementIndex, currentElementIndex + 1, memo);

            if (len > maxLen) {
                maxLen = len;
            }
        }
        return maxLen + 1;
    }

    private static int longestSubsequenceRecursive_top_down(int[] arr, int currentElementIndex, int nextElementIndex, Map<Integer, Integer> memo) {
        if (memo.containsKey(currentElementIndex)) {
            return memo.get(currentElementIndex);
        }

        if (nextElementIndex == arr.length) {
            return 0;
        }

        int currentElement = arr[currentElementIndex];

        int t1 = 0;
        if (arr[nextElementIndex] > currentElement) {
            t1 = 1 + longestSubsequenceRecursive_top_down(arr, nextElementIndex, nextElementIndex + 1, memo);// you can memoize the result of this recursive call
        }
        int t2 = longestSubsequenceRecursive_top_down(arr, currentElementIndex, nextElementIndex + 1, memo);

        int result = Math.max(t1, t2);

        memo.put(currentElementIndex, result);

        return result;
    }


    /*
        Brute-Force approach - takes O(2^n)

       It has to compare all non-repeating combinations

        {1}
        {1,2}   {1,3}   {1,4}
        {1,2,3} {1,3,4}
        {1,2,3,4}

        {2}
        {2,3} {2,4}
        {2,3,4}

        {3}
        {3,4}

        {4}

        So, O(2^n)

    */
    static int cnt = 0;

    private static int LIS_Brute_Force(int[] A, int start, int end, boolean calculateExcludingCurrentElement) {
        if (A == null || A.length == 0) return 0;

        cnt++;

        //if (start == end) return 1; // not mandatory

        if (end < start) return 0;

        int currentElement = A[start];


        int maxIncludingCurrentElement = 1;

        for (int i = start + 1; i <= end; i++) {

            if (A[i] > currentElement) {

                int includingCurrentElement = 1 + LIS_Brute_Force(A, i, end, false);
//            int excludingCurrentElement = LIS(A, i, end);

                maxIncludingCurrentElement = Math.max(maxIncludingCurrentElement, includingCurrentElement);
//                break;
            }

        }

        if (!calculateExcludingCurrentElement) {// very important
            System.out.print(currentElement + ",");
            return maxIncludingCurrentElement;
        }

        int maxExcludingCurrentElement = LIS_Brute_Force(A, start + 1, end, true);

        if (maxIncludingCurrentElement > maxExcludingCurrentElement) {
            System.out.print(currentElement + ",");
        }
        return Math.max(maxIncludingCurrentElement, maxExcludingCurrentElement);

    }


    static int count = 0;

    private static int LIS_With_Dynamic_Top_Down_Approach(int[] A, int start, int end, boolean calculateExcludingCurrentElement, Map<Integer, Integer> memo) {
        if (A == null || A.length == 0) return 0;

        //if (start == end) return 1; // not mandatory

        if (start > end) return 0;

        if (memo.containsKey(start)) {
            return memo.get(start);
        }
        count++;

        int currentElement = A[start];


        int maxIncludingCurrentElement = 1;

        for (int i = start + 1; i <= end; i++) {

            if (A[i] > currentElement) {

                int includingCurrentElement = 1 + LIS_With_Dynamic_Top_Down_Approach(A, i, end, false, memo);

                maxIncludingCurrentElement = Math.max(maxIncludingCurrentElement, includingCurrentElement);

//                break;
            }

        }

        if (!calculateExcludingCurrentElement) {
            System.out.print(currentElement + ",");
            memo.put(start, maxIncludingCurrentElement);
            return maxIncludingCurrentElement;
        }

        int maxExcludingCurrentElement = LIS_With_Dynamic_Top_Down_Approach(A, start + 1, end, true, memo);

        if (maxIncludingCurrentElement > maxExcludingCurrentElement) {
            System.out.print(currentElement + ",");
        }

        int result = Math.max(maxIncludingCurrentElement, maxExcludingCurrentElement);
        memo.put(start, result);
        return result;

    }


}
