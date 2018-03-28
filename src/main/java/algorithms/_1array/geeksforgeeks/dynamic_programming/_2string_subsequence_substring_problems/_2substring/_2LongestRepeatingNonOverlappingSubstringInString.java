package algorithms._1array.geeksforgeeks.dynamic_programming._2string_subsequence_substring_problems._2substring;

import java.util.HashMap;
import java.util.Map;

/*
Longest repeating and non-overlapping substring:
https://www.geeksforgeeks.org/longest-repeating-and-non-overlapping-substring/

This is a hard problem.

It is same as LongestCommonSubStringInTwoStrings.java with two additional conditions to take care of non-overlapping.


e.g.
str = ABCDxyzAfCD

Longest repeating and non-overlapping substring is "CD" and
Longest repeating and non-overlapping subsequence is "ACD" (LongestRepeatingNonOverlappingSubSequence.java).

*/
public class _2LongestRepeatingNonOverlappingSubstringInString {

    public static void main(String[] args) {
        //String s1 = "geekgeek";//4 - geek
        //String s1 = "gekshforgeksih";// 4- geks
        //String s1 = "gekshforgekih";//3 - gek
        //String s1 = "geeksforgeeks";//5 - geeks
        //String s1 = "gbekgsibek";//3 - bek
        //String s1 = "geekgsieek";//3 - eek
        String s1 = "geekgbeek";//3 - eek

        {
            int substringLength = Brute_Force_Iterative(s1.toCharArray(), s1.toCharArray(), 0, s1.length() - 1, 0, s1.length() - 1, false);
            System.out.println("Brute_Force_Iterative: "+ substringLength);
//            System.out.println(count);
        }
        System.out.println();
        System.out.println();
        System.out.println();
        {
            int substringLength = Brute_Force_Recursive(s1.toCharArray(), s1.toCharArray(), 0, s1.length() - 1, 0, s1.length() - 1, false);
            System.out.println("Brute_Force_Recursive: "+ substringLength);
        }
        System.out.println();
        System.out.println();
        System.out.println();
        {
            int substringLength = Top_Down_Dynamic_Programming(s1.toCharArray(), s1.toCharArray(), 0, s1.length() - 1, 0, s1.length() - 1, false, new HashMap<>());
            System.out.println("Top-Down Dynamic Programming: "+ substringLength);
//            System.out.println(cnt);
        }
        System.out.println();
        System.out.println();
        System.out.println();
        {
            String substringLength = Bottom_Up_Dynamic_Programming_From_Site(s1.toCharArray(), s1.length());
            System.out.println("Bottom-Up Dynamic Programming: "+ substringLength);
        }

    }

    private static int Brute_Force_Recursive(char[] S1, char[] S2, int s1Start, int s1End, int s2Start, int s2End, boolean breakIfNotSame) {

        if (s1End < s1Start || s2End < s2Start) return 0;

        // reducing the problem by one
        char s1Char = S1[s1End];
        char s2Char = S2[s2End];

        if (s1Char == s2Char && s2End > s1End) {

            int includingTheChar = 1;

            int maxSubstringLengthFromRemainingStrings = Brute_Force_Recursive(S1, S2, s1Start, s1End - 1, s2Start, s2End - 1, true);

            // important condition to avoid overlapping
            if( (s2End - s1End) > maxSubstringLengthFromRemainingStrings) {
                includingTheChar = 1 + maxSubstringLengthFromRemainingStrings;
            }

            int excludingTheChar = Math.max(Brute_Force_Recursive(S1, S2, s1Start, s1End - 1, s2Start, s2End, false),
                    Brute_Force_Recursive(S1, S2, s1Start, s1End, s2Start, s2End - 1, false));

            return Math.max(includingTheChar, excludingTheChar);
        }

        if (breakIfNotSame) return 0;

        int excludingTheChar = Math.max(Brute_Force_Recursive(S1, S2, s1Start, s1End - 1, s2Start, s2End, false),
                Brute_Force_Recursive(S1, S2, s1Start, s1End, s2Start, s2End - 1, false));

        return excludingTheChar;

    }


    private static int count = 0;
    // O(n^3).
    // It can be converted close to O(n^2) using Dynamic approach
    private static int Brute_Force_Iterative(char[] S1, char[] S2, int s1Start, int s1End, int s2Start, int s2End, boolean breakIfNotSame) {
        count++;

        if (s1End < s1Start || s2End < s2Start) return 0;

        int finalMax = 0;

        for (int i = s1Start; i <= s1End; i++) {
            int remaining = 0;
            for (int j = s2Start; j <= s2End; j++) {
                if ((S1[i] == S2[j]) &&
                        // important to make sure that comparison in between different indices.
                        (j > i) &&
                        // condition for non-overlapping
                        ((j - i) > (remaining = Brute_Force_Iterative(S1, S2, i + 1, s1End, j + 1, s2End, true)))) {
                    //System.out.println(S1[i] + " duplicate found");

                    int max = 1 + remaining;

                    if (finalMax < max) {
                        finalMax = max;
                    }
                } else {

                    if (breakIfNotSame) {
                        break;
                    }
                }
            }
        }

        return finalMax;
    }

    private static int cnt = 0;
    private static int Top_Down_Dynamic_Programming(char[] S1, char[] S2, int s1Start, int s1End, int s2Start, int s2End, boolean breakIfNotSame, Map<String, Integer> memo) {
        String key = s1Start + ":" + s2Start;

        if (memo.get(key) != null) {
            return memo.get(key);
        }

        cnt++;

        if (s1End < s1Start || s2End < s2Start) return 0;

        int finalMax = 0;
        int startIndexOfSubString = 0;
        int endIndexOfSubString = 0;

        for (int i = s1Start; i <= s1End; i++) {
            for (int j = s2Start; j <= s2End; j++) {
                if ((S1[i] == S2[j]) && (j > i)) {
                    int maxFromRemaining = Top_Down_Dynamic_Programming(S1, S2, i + 1, s1End, j + 1, s2End, true, memo);

                    memo.put(key, maxFromRemaining);

                    if ((j - i) > maxFromRemaining) {

                        int max = 1 + maxFromRemaining;

                        if (finalMax < max) {
                            finalMax = max;
                            startIndexOfSubString = i;
                            endIndexOfSubString = startIndexOfSubString+(finalMax-1);
                        }
                    } else {

                        if (breakIfNotSame) {
                            break;
                        }
                    }
                } else {

                    if (breakIfNotSame) {
                        break;
                    }
                }
            }
        }

        System.out.println("startIndexOfSubString: "+startIndexOfSubString);
        System.out.println("endIndexOfSubString: "+endIndexOfSubString);
        return finalMax;
    }

    private static String Bottom_Up_Dynamic_Programming_From_Site(char[] str, int n) {
        String res = ""; // To store result
        int res_length = 0; // To store length of result
        int LCSRe[][] = new int[n + 1][n + 1];

        // building table in bottom-up manner
        int i, index = 0;
        for (i = 1; i <= n; i++) {
            for (int j = i + 1; j <= n; j++) {
                // (j-i) > LCSRe[i-1][j-1] to remove overlapping
                if (str[i - 1] == str[j - 1] &&
                        LCSRe[i - 1][j - 1] < (j - i)) {
                    LCSRe[i][j] = LCSRe[i - 1][j - 1] + 1;

                    // updating maximum length of the
                    // substring and updating the finishing
                    // index of the suffix
                    if (LCSRe[i][j] > res_length) {
                        res_length = LCSRe[i][j];
                        index = Math.max(i, index);
                    }
                } else
                    LCSRe[i][j] = 0;
            }
        }

        // If we have non-empty result, then insert all
        // characters from first character to last
        // character of string
        if (res_length > 0)
            for (i = index - res_length + 1; i <= index; i++)
                res = res + (str[i - 1]);

        return res;
    }
}
