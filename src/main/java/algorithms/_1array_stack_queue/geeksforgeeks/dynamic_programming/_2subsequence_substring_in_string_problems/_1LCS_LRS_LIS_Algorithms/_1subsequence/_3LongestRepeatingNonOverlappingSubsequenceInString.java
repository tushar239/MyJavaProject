package algorithms._1array_stack_queue.geeksforgeeks.dynamic_programming._2subsequence_substring_in_string_problems._1LCS_LRS_LIS_Algorithms._1subsequence;

/*
    Longest repeating and non-overlapping subsequence

    e.g.
    str = ABCDxyzAfCkD

    Longest repeating and non-overlapping subsequence is "ACD" and
    Longest repeating and non-overlapping substring is "CD" (LongestRepeatingNonOverlappingSubString.java)


    Solution of this problem is same as LongestCommonSubSequence.java with TWO extra conditions.

        A   B   C   D   x   y   z   A   f   C  k    D
                  s1End

        A   B   C   D   x   y   z   A   f   C  k    D
                                                   s2End

        You need to find two same chars at different positions (s2End > s1End).

        After that, you need to make sure that result that you get from remaining strings by reducing s2End and s1End(maxSubsequenceLengthFromRemainingStrings) is less than s2End-s1End

*/
public class _3LongestRepeatingNonOverlappingSubsequenceInString {

    public static void main(String[] args) {
        //String str = "gekshforgeksih";// 4- geks
        //String str = "gekshforgekih";//3 - gek
        //String str = "geeksforgeeks";//5 - geeks
//        String str = "geegkeek";//3 - gee
//        String str = "gbekgsibek";//4 - gbek
        String str = "gbekgsibekg";//5 - gbekg
//        String str = "geekgsieek";//4 - geek
//        String str = "geekgbeek";//4 - geek

        char[] strChars = str.toCharArray();
        int strStart = 0;
        int strEnd = str.length() - 1;

        int maxNonOverlappingSubstringLength = LCS_Brute_Force(strChars, strChars, strStart, strEnd, strStart, strEnd);

        System.out.println("Maximum Non-Overlapping Substring Length: "+ maxNonOverlappingSubstringLength);
    }

    private static int LCS_Brute_Force(char[] S1, char[] S2, int s1Start, int s1End, int s2Start, int s2End) {
        if (s1End < s1Start || s2End < s2Start) return 0;

        // reducing the problem by one
        char s1Char = S1[s1End];
        char s2Char = S2[s2End];

        // if both chars matches, then reduce both strings' end index by 1
        if ((s1Char == s2Char) &&
                // Important condition-1 for making sure that subsequences are not overlapping
                (s2End > s1End)) {

            int includingCurrentChar = 1;

            int maxSubsequenceLengthFromRemainingStrings = LCS_Brute_Force(S1, S2, s1Start, s1End - 1, s2Start, s2End - 1);

            // Important condition-2 for making sure that subsequences are not overlapping
            if ((s2End - s1End) > maxSubsequenceLengthFromRemainingStrings) {
                includingCurrentChar = 1 + LCS_Brute_Force(S1, S2, s1Start, s1End - 1, s2Start, s2End - 1);
            }

            int excludingCurrentChar = Math.max(LCS_Brute_Force(S1, S2, s1Start, s1End - 1, s2Start, s2End),
                    LCS_Brute_Force(S1, S2, s1Start, s1End, s2Start, s2End - 1));

            return Math.max(includingCurrentChar, excludingCurrentChar);

        }


        // otherwise, get the max of (reducing first string's end index by 1, reducing second string's end index by 1)
        int excludingCurrentChar = Math.max(LCS_Brute_Force(S1, S2, s1Start, s1End - 1, s2Start, s2End),
                LCS_Brute_Force(S1, S2, s1Start, s1End, s2Start, s2End - 1));
        return excludingCurrentChar;
    }

}
