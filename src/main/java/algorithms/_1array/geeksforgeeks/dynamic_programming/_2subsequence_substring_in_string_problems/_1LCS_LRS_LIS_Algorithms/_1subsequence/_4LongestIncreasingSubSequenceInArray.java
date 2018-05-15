package algorithms._1array.geeksforgeeks.dynamic_programming._2subsequence_substring_in_string_problems._1LCS_LRS_LIS_Algorithms._1subsequence;

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
            int[] A = {1, 2, 3, 4, 5, 6, 7, 8};

            System.out.println("Brute-Force Approach My Way");
            int maxIncreasingSubSequenceSize = LIS_My_Way_Harder_Way_Brute_Force(A, 0, A.length - 1, true);
            System.out.println("Result:" + maxIncreasingSubSequenceSize);//6 - {1,2,3,4,5,6}
            System.out.println("Time Complexity is O(2^n): " + cnt);

            System.out.println();

            System.out.println("Dynamic Programming Top-Down Approach My Way");
            maxIncreasingSubSequenceSize = LIS_My_Way_Harder_Way_With_Dynamic_Top_Down_Approach(A, 0, A.length - 1, true, new HashMap<>());
            System.out.println();
            System.out.println("Result:" + maxIncreasingSubSequenceSize);//6 - {1,2,3,4,5,6}
            System.out.println("Time Complexity is O(n^2): " + count);

            System.out.println();

            System.out.println("Different Way Brute-Force");
            maxIncreasingSubSequenceSize = longestSubsequenceRecursive_Brute_Force(A);
            System.out.println("Result:" + maxIncreasingSubSequenceSize);//6 - {1,2,3,4,5,6}

            System.out.println();

            System.out.println("Different Way Dynamic Programming Top-Down");
            maxIncreasingSubSequenceSize = longestSubsequenceRecursive_top_down(A);
            System.out.println("Result:" + maxIncreasingSubSequenceSize);//6 - {1,2,3,4,5,6}

            System.out.println();

            System.out.println("My Way Brute-Force");
            maxIncreasingSubSequenceSize = max_LIS_Size_My_Way_Brute_Force(A, 0, A.length - 1);
            System.out.println("Result:" + maxIncreasingSubSequenceSize);//6 - {1,2,3,4,5,6}

        }

        co = 0;
        System.out.println();

        {
            int[] A = {1, 12, 3, 4};

            System.out.println("Brute-Force Approach");
            int maxIncreasingSubSequenceSize = LIS_My_Way_Harder_Way_Brute_Force(A, 0, A.length - 1, true);
            System.out.println("Result:" + maxIncreasingSubSequenceSize);//3 - {1,3,4}

            System.out.println("Dynamic Programming Top-Down Approach");
            maxIncreasingSubSequenceSize = LIS_My_Way_Harder_Way_With_Dynamic_Top_Down_Approach(A, 0, A.length - 1, true, new HashMap<>());
            System.out.println();
            System.out.println("Result:" + maxIncreasingSubSequenceSize);//3 - {1,3,4}

            System.out.println("Different Way Brute-Force");
            maxIncreasingSubSequenceSize = longestSubsequenceRecursive_Brute_Force(A);
            System.out.println("Result:" + maxIncreasingSubSequenceSize);//3 - {1,3,4}

            System.out.println("Different Way Dynamic Programming Top-Down");
            maxIncreasingSubSequenceSize = longestSubsequenceRecursive_top_down(A);
            System.out.println("Result:" + maxIncreasingSubSequenceSize);//3 - {1,3,4}

            System.out.println("My Way Brute-Force");
            maxIncreasingSubSequenceSize = max_LIS_Size_My_Way_Brute_Force(A, 0, A.length - 1);
            System.out.println("Result:" + maxIncreasingSubSequenceSize);//3 - {1,3,4}


        }

        co = 0;
        System.out.println();

        {
            int[] A = {1, 0, 4, 5, 2, 7};

            System.out.println("Brute-Force Approach");
            int maxIncreasingSubSequenceSize = LIS_My_Way_Harder_Way_Brute_Force(A, 0, A.length - 1, true);
            System.out.println("Result:" + maxIncreasingSubSequenceSize); //4 - {1,4,5,7} or {0,4,5,7}
//            System.out.println("Time Complexity is O(2^n): " + cnt);

            System.out.println("Dynamic Programming Top-Down Approach");
            maxIncreasingSubSequenceSize = LIS_My_Way_Harder_Way_With_Dynamic_Top_Down_Approach(A, 0, A.length - 1, true, new HashMap<>());
            System.out.println();
            System.out.println("Result:" + maxIncreasingSubSequenceSize); //4 - {1,4,5,7} or {0,4,5,7}
//            System.out.println("Time Complexity is O(n): " + count);

            System.out.println("Different Way Brute-Force");
            maxIncreasingSubSequenceSize = longestSubsequenceRecursive_Brute_Force(A);
            System.out.println("Result:" + maxIncreasingSubSequenceSize); //4 - {1,4,5,7} or {0,4,5,7}

            System.out.println("Different Way Dynamic Programming Top-Down");
            maxIncreasingSubSequenceSize = longestSubsequenceRecursive_top_down(A);
            System.out.println("Result:" + maxIncreasingSubSequenceSize); //4 - {1,4,5,7} or {0,4,5,7}

            System.out.println("My Way Brute-Force");
            maxIncreasingSubSequenceSize = max_LIS_Size_My_Way_Brute_Force(A, 0, A.length - 1);
            System.out.println("Result:" + maxIncreasingSubSequenceSize);//4 - {1,4,5,7} or {0,4,5,7}

        }

        co = 0;
        System.out.println();

        {
            //int[] A = {15, 4, 12, 2, 10};//2 - {4, 12}  or {2, 10}
            //int[] A = {15, 4, 12, 2, 10, 6, 9, 0};//3 - {2,6,9}
            int[] A = {0, 4, 12, 2, 10, 6, 9, 13, 3, 11, 7, 15}; //6 - {0,2,6,9,11,15}

            System.out.println("Brute-Force Approach");
            int maxIncreasingSubSequenceSize = LIS_My_Way_Harder_Way_Brute_Force(A, 0, A.length - 1, true);
            System.out.println("Result:" + maxIncreasingSubSequenceSize);
//            System.out.println("Time Complexity is O(2^n): " + cnt);

            System.out.println("Dynamic Programming Top-Down Approach");
            maxIncreasingSubSequenceSize = LIS_My_Way_Harder_Way_With_Dynamic_Top_Down_Approach(A, 0, A.length - 1, true, new HashMap<>());
            System.out.println();
            System.out.println("Result:" + maxIncreasingSubSequenceSize);
//            System.out.println("Time Complexity is O(n): " + count);

            System.out.println("Different Way Brute-Force");
            maxIncreasingSubSequenceSize = longestSubsequenceRecursive_Brute_Force(A);
            System.out.println("Result:" + maxIncreasingSubSequenceSize);

            System.out.println("Different Way Dynamic Programming Top-Down");
            maxIncreasingSubSequenceSize = longestSubsequenceRecursive_top_down(A);
            System.out.println("Result:" + maxIncreasingSubSequenceSize);

            System.out.println("My Way Brute-Force");
            maxIncreasingSubSequenceSize = max_LIS_Size_My_Way_Brute_Force(A, 0, A.length - 1);
            System.out.println("Result:" + maxIncreasingSubSequenceSize);


        }


    }

    /*

    Solution:
    --------

    You need to find out LIS size for every single element of an array and take the min of their LIS sizes.

    How to find out LIS size for a single element?

        Min LIS for any element is 1 (that element itself).
        Now, you need to LIS sizes of all elements that are bigger than current element and that are after current element. Keep the max one and add 1 to it to add current element in final LIS.


    e.g. int[] A = [1   12  3   4]

    current element = 1
        find max LIS for 1

            (1) (1, 12)  (1,3,4)

            max LIS will be 3

    current element = 12
        find max LIS for 12
            It will be 1 only because there is no bigger element after 12.

    current element = 3
        find max LIS for 3

            (3) (3,4)

            max LIS will be 2

    current element = 4
        find max LIS for 4
            It will be 1 only


    So, MAX LIS size of an array is 3 = (1,3,4)

    ....
    int max_lis_size = Integer.MIN_VALUE;

    for (int i = start; i <= end; i++) {
        int lic_for_single_element = max_LIS_For_Single_Element(A, i, end);
        max_lis_size = Math.max(max_lis_size, max_lis_size_for_single_element);
    }

    return max_lis_size;
    ....


    int max_LIS_For_Single_Element(A, start, end) {

        ....

        int currentElement = A[start];

        int lic_from_remaining_elements = Integer.MIN_VALUE;

        for (int i = start + 1; i <= end; i++) {

            int nextElement = A[i];

            // iterate all elements that are bigger than current element. find their LISes and keep the max one and then finally add current element(1) to max one.
            if (nextElement > currentElement) {

                int lic_of_next_element = max_LIS_Size_For_Single_Element_Brute_Force(A, i, end);

                lic_from_remaining_elements = Math.max(lic_from_remaining_elements, lic_of_next_element);

            }
        }

        return 1 + lic_from_remaining_elements;
    }

    Time Complexity: O(2^n) in Brute-Force approach and O(n^2) in Dynamic Programming approach
    ---------------
    e.g.
    int[] A = {1,2,3,4,5,6}

    For every element you are calling LIC_For_Single_Element method.

    for i=1     time complexity of LIC_For_Single_Element will be 2^5
        i=2                                                       2^4
        i=3                                                       2^3
        ....

    so, total time complexity of LIC_My_Way will be 2^5+2^4+2^3+... = O(2^n)


    Another way of calculating time complexity:

        If you see this algorithm is basically creating combinations of non-repeating sub-arrays as shown below. So, time complexity is O(2^n)

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

    */
    private static int max_LIS_Size_My_Way_Brute_Force(int[] A, int start, int end) {
        if (A == null) return 0;
        if (start == end) return 1;

        int max_lis_size = Integer.MIN_VALUE;

        // Count count_for_all_elements = new Count(0);

        for (int i = start; i <= end; i++) {

            int max_lis_size_for_single_element = max_LIS_Size_For_Single_Element_Brute_Force(A, i, end);

            // Count count_for_single_element = new Count(0);
            // int max_lis_size_for_single_element = max_LIS_Size_For_Single_Element_Brute_Force(A, i, end, count_for_single_element);

            // System.out.println("count for "+A[i]+ " is "+count_for_single_element.getCount());
            // count_for_all_elements.increment(count_for_single_element.getCount());

            max_lis_size = Math.max(max_lis_size, max_lis_size_for_single_element);
        }

        // System.out.println("count for all elements is "+count_for_all_elements.getCount());

        return max_lis_size;
    }

    /*
                                                        LIS(6)
                    LIS(5)                  LIS(4)                  LIS(3)                  LIS(2)                  LIS(1)
    LIS(4)  LIS(3)  LIS(2) LIS(1)   LIS(3)  LIS(2) LIS(1)       LIS(2) LIS(1)               LIS(1)
    ...

    Similar to Fibonacci.java, this kind of recursive tree structure also fall into O(2^n) category of time complexity.
    */
    //    private static int max_LIS_Size_For_Single_Element_Brute_Force(int[] A, int start, int end, Count count) {
    private static int max_LIS_Size_For_Single_Element_Brute_Force(int[] A, int start, int end) {
        // count.incrementByOne();

        if (start == end) {
            return 1;
        }

        int currentElement = A[start];

        int lic_from_remaining_elements = Integer.MIN_VALUE;

        for (int i = start + 1; i <= end; i++) {

            int nextElement = A[i];

            if (nextElement > currentElement) {

                //int lic_of_next_element = max_LIS_Size_For_Single_Element_Brute_Force(A, i, end, count);
                int lic_of_next_element = max_LIS_Size_For_Single_Element_Brute_Force(A, i, end);
                lic_from_remaining_elements = Math.max(lic_from_remaining_elements, lic_of_next_element);

            }
        }

        return 1 + lic_from_remaining_elements;
    }

// ---------------------------------------------------------------------------------------------------------------------------- //

    // https://github.com/mission-peace/interview/blob/master/src/com/interview/dynamic/LongestIncreasingSubsequence.java
    // O(2^n)
    private static int co;

    private static int longestSubsequenceRecursive_Brute_Force(int arr[]) {
        int maxLen = 0;

        // 2^n + 2^n-1 + ...... + 2^0 = 2^n+1 - 1 = O(2^n)
        for (int i = 0; i < arr.length; i++) {

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
//        System.out.println();
        return maxLen + 1;
    }

    // O(n^2)
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


       /* if(t1 > t2){
            System.out.print(arr[currentElementIndex]+",");
        }*/

        int result = Math.max(t1, t2);

        memo.put(currentElementIndex, result);

        return result;
    }

    // ---------------------------------------------------------------------------------------------------------------------------- //


    static int cnt = 0;

    // O(2^n)
    private static int LIS_My_Way_Harder_Way_Brute_Force(int[] A, int start, int end, boolean calculateExcludingCurrentElement) {
        cnt++;

        if (A == null || A.length == 0) return 0;

        //if (start == end) return 1; // not mandatory

        if (end < start) return 0;

        int currentElement = A[start];


        int maxIncludingCurrentElement = 1;

        for (int i = start + 1; i <= end; i++) {

            if (A[i] > currentElement) {

                int includingCurrentElement = 1 + LIS_My_Way_Harder_Way_Brute_Force(A, i, end, false);
//            int excludingCurrentElement = LIS(A, i, end);

                maxIncludingCurrentElement = Math.max(maxIncludingCurrentElement, includingCurrentElement);
//                break;
            }

        }

        if (!calculateExcludingCurrentElement) {// very important
            System.out.print(currentElement + ",");
            return maxIncludingCurrentElement;
        }

        int maxExcludingCurrentElement = LIS_My_Way_Harder_Way_Brute_Force(A, start + 1, end, true);

        if (maxIncludingCurrentElement > maxExcludingCurrentElement) {
            System.out.print(currentElement + ",");
        }
        return Math.max(maxIncludingCurrentElement, maxExcludingCurrentElement);

    }


    static int count = 0;

// O(n^2)
/*

                                    LIS(8)
                                     |
                    LIS(7)                                                                                              LIS(6)          LIS(5)          LIS(4)          LIS(3)          LIS(2)          LIS(1)
                      |
LIS(6)              LIS(5)              LIS(4)              LIS(3)              LIS(2)              LIS(1)
|
LIS(5)      LIS(4)      LIS(3)      LIS(2)      LIS(1)
|
LIS(4)   LIS(3)   LIS(2)   LIS(1)
|
LIS(3)   LIS(2)   LIS(1)
|
LIS(2)   LIS(1)
|
LIS(1)

This many recursions will happen with Dynamic Programming  O(n^2)

*/
    private static int LIS_My_Way_Harder_Way_With_Dynamic_Top_Down_Approach(int[] A, int start, int end, boolean calculateExcludingCurrentElement, Map<Integer, Integer> memo) {

        if (A == null || A.length == 0) return 0;

        //if (start == end) return 1; // not mandatory

        if (start > end) return 0;

        count++;

        if (memo.containsKey(start)) {
            return memo.get(start);
        }

        int currentElement = A[start];


        int maxIncludingCurrentElement = 1;

        for (int i = start + 1; i <= end; i++) {

            if (A[i] > currentElement) {

                int includingCurrentElement = 1 + LIS_My_Way_Harder_Way_With_Dynamic_Top_Down_Approach(A, i, end, false, memo);

                maxIncludingCurrentElement = Math.max(maxIncludingCurrentElement, includingCurrentElement);

//                break;
            }

        }

        if (!calculateExcludingCurrentElement) {
            System.out.print(currentElement + ",");
            memo.put(start, maxIncludingCurrentElement);
            return maxIncludingCurrentElement;
        }

        int maxExcludingCurrentElement = LIS_My_Way_Harder_Way_With_Dynamic_Top_Down_Approach(A, start + 1, end, true, memo);

        if (maxIncludingCurrentElement > maxExcludingCurrentElement) {
            System.out.print(currentElement + ",");
        }

        int result = Math.max(maxIncludingCurrentElement, maxExcludingCurrentElement);
        memo.put(start, result);
        return result;

    }

// ---------------------------------------------------------------------------------------------------------------------------- //
}
