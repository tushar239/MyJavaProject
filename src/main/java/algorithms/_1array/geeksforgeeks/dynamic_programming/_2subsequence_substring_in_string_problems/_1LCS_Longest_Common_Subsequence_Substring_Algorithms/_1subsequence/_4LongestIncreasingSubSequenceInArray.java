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
            int[] A = {1,2,3,4,5,6};

            System.out.println("Brute-Force Approach");
            int maxIncreasingSubSequenceSize = LIS_Brute_Force(A, 0, A.length - 1, true);
            System.out.println("Result:"+ maxIncreasingSubSequenceSize);
            System.out.println("Time Complexity is O(2^n): "+cnt);//64 = O(2^n)

            System.out.println("Dynamic Programming Top-Down Approach");
            maxIncreasingSubSequenceSize = LIS_With_Dynamic_Top_Down_Approach(A, 0, A.length - 1, true, new HashMap<>());
            System.out.println();
            System.out.println("Result:"+ maxIncreasingSubSequenceSize);
            System.out.println("Time Complexity is O(n): "+count);//6 = O(n)

        }
        System.out.println();
        {
            int[] A = {1, 0, 4, 5, 2, 7};

            System.out.println("Brute-Force Approach");
            int maxIncreasingSubSequenceSize = LIS_Brute_Force(A, 0, A.length - 1, true);
            System.out.println("Result:"+ maxIncreasingSubSequenceSize); //4 - {1,4,5,7} or {0,4,5,7}

            System.out.println("Dynamic Programming Top-Down Approach");
            maxIncreasingSubSequenceSize = LIS_With_Dynamic_Top_Down_Approach(A, 0, A.length - 1, true, new HashMap<>());
            System.out.println();
            System.out.println("Result:"+ maxIncreasingSubSequenceSize);

        }
        System.out.println();
        {
            //int[] A = {15, 4, 12, 2, 10};//2 - {4, 12}  or {2, 10}
            //int[] A = {15, 4, 12, 2, 10, 6, 9, 0};//3 - {2,6,9}
            int[] A = {0, 4, 12, 2, 10, 6, 9, 13, 3, 11, 7, 15}; //6 - {0,2,6,9,11,15}

            System.out.println("Brute-Force Approach");
            int maxIncreasingSubSequenceSize = LIS_Brute_Force(A, 0, A.length - 1, true);
            System.out.println("Result:"+ maxIncreasingSubSequenceSize);

            System.out.println("Dynamic Programming Top-Down Approach");
            maxIncreasingSubSequenceSize = LIS_With_Dynamic_Top_Down_Approach(A, 0, A.length - 1, true, new HashMap<>());
            System.out.println();
            System.out.println("Result:"+ maxIncreasingSubSequenceSize);
        }


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
    static int cnt=0;
    private static int LIS_Brute_Force(int[] A, int start, int end, boolean calculateExcludingCurrentElement) {
        if (A == null || A.length == 0) return 0;

        cnt++;

        //if (start == end) return 1; // not mandatory

        if (end < start) return 0;

        int currentElement = A[start];


        int maxIncludingCurrentElement = 1;

        for (int i = start + 1; i <= end; i++) {

            if (A[i] <= currentElement) continue;

            int includingCurrentElement = 1 + LIS_Brute_Force(A, i, end, false);
//            int excludingCurrentElement = LIS(A, i, end);

            maxIncludingCurrentElement = Math.max(maxIncludingCurrentElement, includingCurrentElement);

        }

        if (!calculateExcludingCurrentElement) {// very important
            return maxIncludingCurrentElement;
        }

        int maxExcludingCurrentElement = LIS_Brute_Force(A, start + 1, end, true);

        return Math.max(maxIncludingCurrentElement, maxExcludingCurrentElement);

    }


    static int count=0;
    private static int LIS_With_Dynamic_Top_Down_Approach(int[] A, int start, int end, boolean calculateExcludingCurrentElement, Map<Integer, Integer> memo) {
        if (A == null || A.length == 0) return 0;

        //if (start == end) return 1; // not mandatory

        if (end < start) return 0;

        if(memo.containsKey(end)) {
            return memo.get(end);
        }
        count++;

        int currentElement = A[start];


        int maxIncludingCurrentElement = 1;

        for (int i = start + 1; i <= end; i++) {

            if (A[i] <= currentElement) continue;

            int includingCurrentElement = 1 + LIS_With_Dynamic_Top_Down_Approach(A, i, end, false, memo);

            maxIncludingCurrentElement = Math.max(maxIncludingCurrentElement, includingCurrentElement);

        }

        if (!calculateExcludingCurrentElement) {
            System.out.print(currentElement+",");
            memo.put(end, maxIncludingCurrentElement);
            return maxIncludingCurrentElement;
        }

        int maxExcludingCurrentElement = LIS_With_Dynamic_Top_Down_Approach(A, start + 1, end, true, memo);

        int result = Math.max(maxIncludingCurrentElement, maxExcludingCurrentElement);
        memo.put(end, result);
        return result;

    }

    // not working
    private static int LIS_2(int[] A, int start, int end, int prev) {
        if (A == null || A.length == 0) return 0;

        if (end < start) return 0;

        int currentElement = A[start];

        int includingCurrentElement = 1;
        // include current element, if it is greater than prev one
        if(currentElement > prev) {
            includingCurrentElement = 1 + LIS_2(A, start+1, end, currentElement);
        }
        int excludingCurrentElement = LIS_2(A, start+1, end, currentElement);

        return Math.max(includingCurrentElement, excludingCurrentElement);

    }


}
