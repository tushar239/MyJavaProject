package algorithms._1array_stack_queue.geeksforgeeks.dynamic_programming._2subsequence_substring_in_string_problems._1LIS_LCS_LRS_Algorithms._2substring;

/*
Given two string sequences write an algorithm to find, find the length of longest substring present in both of them.

https://algorithms.tutorialhorizon.com/dynamic-programming-longest-common-substring/

This is a root_to_leaf_problems_hard problem.

This problem is similar to "Longest Common Subsequence in two strings" (LongestCommonSubSequenceInTwoStrings.java) with a slight difference.

    S1= gXeeks
    S2= geeks

    Longest common subsequence = geeks
    Longest common substring = eeks


    To take care of this difference,
        When two chars are same, find the result from remaining strings till their characters matches. As soon as, there is a mismatch, break the loop.

     private static int LCSubstring(char[] S1, char[] S2, int s1Start, int s1End, int s2Start, int s2End, boolean breakIfNotSame) {

        char s1Char = S1[s1End];
        char s2Char = S2[s2End];

        // When character matches in two strings, get the max from remaining strings and add 1 to it
        // When you are finding max from remaining strings, make sure that you stop when characters do not match --- This condition is applicable for "Longest Common Substring", not for "Longest Common Subsequence"
        if (s1Char == s2Char) {
            int includingCurrentChar = 1 + LCSubstring(S1, S2, s1Start, s1End-1, s2Start, s2End-1, true)

            int excludingCurrentChar = Math.max(LCSubstring(S1, S2, s1Start, s1End-1, s2Start, s2End, false),
                                                LCSubstring(S1, S2, s1Start, s1End, s2Start, s2End-1, false));

            return Math.max(includingCurrentChar, excludingCurrentChar);

        } else {

            if(breakIfNotSame) return 0; ---------- You don't need this condition for LCSubsequence

            int excludingCurrentChar = Math.max(LCSubstring(S1, S2, s1Start, s1End-1, s2Start, s2End, false),
                                                LCSubstring(S1, S2, s1Start, s1End, s2Start, s2End-1, false));
            return excludingCurrentChar;
        }
     }


LongestRepeatingNonOverlappingSubString.java is an extension of this problem.
*/
public class _1LongestCommonSubstringInTwoStrings {

    public static void main(String[] args) {
        {
            String s1 = "XYZABC";
            String s2 = "ABCXYZA";

            {
                int longestCommonSubStringSize = Brute_Force_Iterative(s1.toCharArray(), s2.toCharArray(), 0, s1.toCharArray().length - 1, 0, s2.toCharArray().length - 1, false);
                System.out.println(longestCommonSubStringSize);//4 - XYZA
            }

            {
                int longestCommonSubStringSize = Brute_Force_Recursive(s1.toCharArray(), s2.toCharArray(), 0, s1.toCharArray().length - 1, 0, s2.toCharArray().length - 1, false);
                System.out.println(longestCommonSubStringSize);//4 - XYZA
            }
        }
        {
            String s1 = "XgeeksY";
            String s2 = "AgeeksABC";
            {
                int longestCommonSubStringSize = Brute_Force_Iterative(s1.toCharArray(), s2.toCharArray(), 0, s1.toCharArray().length - 1, 0, s2.toCharArray().length - 1, false);
                System.out.println(longestCommonSubStringSize);//5 - geeks
            }

            {
                int longestCommonSubStringSize = Brute_Force_Recursive(s1.toCharArray(), s2.toCharArray(), 0, s1.toCharArray().length - 1, 0, s2.toCharArray().length - 1, false);
                System.out.println(longestCommonSubStringSize);//5 - geeks
            }

        }
    }

    // Time Complexity: O(2 ^ m+n)
    // It can be improved to O(mn) using Dynamic Programming
    // 'breakIfNotSame' is an important flag that differentiates this algorithm from "Longest Common Subsequence" algorithm.
    private static int Brute_Force_Recursive(char[] S1, char[] S2, int s1Start, int s1End, int s2Start, int s2End, boolean breakIfNotSame) {

        if (s1End < s1Start || s2End < s2Start) return 0;

        // reducing the problem by one
        char s1Char = S1[s1End];
        char s2Char = S2[s2End];

        if (s1Char == s2Char) {
            int includingTheChar = 1 + Brute_Force_Recursive(S1, S2, s1Start, s1End - 1, s2Start, s2End - 1, true); // ------ important to set breakIfNotSame=true

            int excludingTheChar = Math.max(Brute_Force_Recursive(S1, S2, s1Start, s1End - 1, s2Start, s2End, false),
                    Brute_Force_Recursive(S1, S2, s1Start, s1End, s2Start, s2End - 1, false));

            return Math.max(includingTheChar, excludingTheChar);
        }

        if (breakIfNotSame) return 0;

        int excludingTheChar = Math.max(Brute_Force_Recursive(S1, S2, s1Start, s1End - 1, s2Start, s2End, false),
                Brute_Force_Recursive(S1, S2, s1Start, s1End, s2Start, s2End - 1, false));

        return excludingTheChar;

    }

    private static int Brute_Force_Iterative(char[] S1, char[] S2, int s1Start, int s1End, int s2Start, int s2End, boolean breakIfNotSame) {

        if (s1End < s1Start || s2End < s2Start) return 0;

        int finalMax = 0;

        int startIndexOfSubString = 0;
        int endIndexOfSubString = 0;

        for (int i = s1Start; i <= s1End; i++) {

            for (int j = s2Start; j <= s2End; j++) {

                if ((S1[i] == S2[j])) {

                    // you want to continue matching remaining string (i+1 to end) till you find any mismatched char.
                    // when you find any mismatched char, you want to break this loop (so, breakIfNotSame=true)
                    int max = 1 + Brute_Force_Iterative(S1, S2, i + 1, s1End, j + 1, s2End, true);

                    if (finalMax < max) {
                        finalMax = max;
                        startIndexOfSubString = i;
                        endIndexOfSubString = i + (finalMax - 1);
                    }
                } else {

                    if (breakIfNotSame) {
                        break;
                    }
                }
            }
        }

        System.out.println("startIndexOfSubString: " + startIndexOfSubString);//0
        System.out.println("endIndexOfSubString: " + endIndexOfSubString);//3
        return finalMax;
    }

}
