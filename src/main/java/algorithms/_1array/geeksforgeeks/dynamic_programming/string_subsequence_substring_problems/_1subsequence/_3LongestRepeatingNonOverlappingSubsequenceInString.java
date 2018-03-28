package algorithms._1array.geeksforgeeks.dynamic_programming.string_subsequence_substring_problems._1subsequence;

/*
    Longest repeating and non-overlapping subsequence

    e.g.
    str = ABCDxyzAfCD

    Longest repeating and non-overlapping subsequence is "ACD" and
    Longest repeating and non-overlapping substring is "CD" (LongestRepeatingNonOverlappingSubString.java)


    Solution of this problem is same as LongestCommonSubSequence.java with two extra conditions.
*/
public class _3LongestRepeatingNonOverlappingSubsequenceInString {

    public static void main(String[] args) {
        //String s1 = "gekshforgeksih";// 4- geks
        //String s1 = "gekshforgekih";//3 - gek
        //String s1 = "geeksforgeeks";//5 - geeks
//        String s1 = "geegkeek";//3 - gee
//        String s1 = "gbekgsibek";//4 - gbek
        String s1 = "gbekgsibekg";//5 - gbekg
//        String s1 = "geekgsieek";//4 - geek
//        String s1 = "geekgbeek";//4 - geek
        int substringLength = LCS_Brute_Force(s1.toCharArray(), s1.toCharArray(), 0, s1.length() - 1, 0, s1.length() - 1);
        System.out.println(substringLength);

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
