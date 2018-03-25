package algorithms._1array.geeksforgeeks.dynamic_programming.string_subsequence_substring_problems.subsequence;

import java.util.HashMap;
import java.util.Map;

/*
Longest Common SubSequence:

https://www.youtube.com/watch?v=7KcR7fN4-CA
https://www.geeksforgeeks.org/longest-common-subsequence/

Difference between SubString and SubSequence

    S1= A    C   B   E   A
        -    -           -

    "ACA" is not a substring in S1, but it is a subsequence.
    "CBE" is a substring as well as subsequence in S1.

Find Longest Common SubSequence in two strings

    S1= A    C   B   E   A
    S2= A    D   C   A

LCS(S1[], S2[], s1Start, s1End, s2Start, s2End) {


    if(S1[s1End] == S2[s2End]) {
        return 1 + LCS(S1, S2, s1Start, s1End-1, s2Start, s2End-1);
    }

    return Math.max(LCS(S1, S2, s1Start, s1End-1, s2Start, s2End),
                    LCS(S1, S2, s1Start, s1End, s2Start, s2End-1))

}
                                        LCS(ACBEA, ADCA)
                                                |
                                        LCS(ACBE, ADC)
                                                |
            ---------------------------------------------------------------------------
            |                                                                         |
           LCS(ACB, ADC)                                                            LCS(ACBE, AD)
            |                                                                         |
      -------------------------------                                   -------------------------------
      |                             |                                   |                             |
     LCS(AC, ADC)               LCS(ACB, AD)                           LCS(ACB, AD)               LCS(ACBE, A)
      |                             |
     LCS(A,AD)              -----------------
      |                     |               |
    -------------          LCS(ACB,A)       LCS(AC,AD)
    |           |
 LCS(A,A)   LCS( ,D)
    |
 LCS( , )

 and so on........

 Time Complexity: O(2^(m+n)). Similar to Fibonacci.java

 Using Dynamic Programming, it can be reduced a lot

*/
public class _1LongestCommonSubSequence {

    public static void main(String[] args) {
        {
            String s1 = "ACBEA";
            String s2 = "ADCA";

            System.out.println("Brute Force: " + "S1=" + s1 + ", S2=" + s2);
            System.out.println();
            int lcs = LCS_Brute_Force(s1.toCharArray(), s2.toCharArray(), 0, s1.length() - 1, 0, s2.length() - 1);
            System.out.println();
            System.out.println("Max common subsequence: " + lcs);//3

            System.out.println();
            System.out.println();
            System.out.println();

            System.out.println("Top-Down Dynamic Programming: " + "S1=" + s1 + ", S2=" + s2);
            System.out.println();
            lcs = LCS_Top_Down_Approach(s1.toCharArray(), s2.toCharArray(), 0, s1.length() - 1, 0, s2.length() - 1, new HashMap<>());
            System.out.println();
            System.out.println("Max common subsequence: " + lcs);//3
        }
        System.out.println();
        System.out.println();
        System.out.println();
        {
            String s1 = "QRXYZ";
            String s2 = "ABCD";

            System.out.println("Brute Force: " + "S1=" + s1 + ", S2=" + s2);
            System.out.println();
            int LCS = LCS_Brute_Force(s1.toCharArray(), s2.toCharArray(), 0, s1.length() - 1, 0, s2.length() - 1);
            System.out.println("Max common subsequence: " + LCS);//0
            System.out.println();
            System.out.println("Total comparisons: " + cnt);// approx O(2^(m+n)) = O(2^(5+4))

            System.out.println();
            System.out.println();
            System.out.println();

            System.out.println("Top-Down Dynamic Programming: " + "S1=" + s1 + ", S2=" + s2);
            System.out.println();
            LCS = LCS_Top_Down_Approach(s1.toCharArray(), s2.toCharArray(), 0, s1.length() - 1, 0, s2.length() - 1, new HashMap<>());
            System.out.println("Max common subsequence: " + LCS);//0
            System.out.println();
            System.out.println("Total comparisons: " + count);// approx O( (m+n)^2 ) = O( (5+4)^2 )
        }
    }

    static int cnt = 0;

    private static int LCS_Brute_Force(char[] S1, char[] S2, int s1Start, int s1End, int s2Start, int s2End) {
        cnt++;
        if (s1End < s1Start || s2End < s2Start) return 0;

        // reducing the problem by one
        char s1Char = S1[s1End];
        char s2Char = S2[s2End];

        // if both chars matches, then reduce both strings' end index by 1
        if (s1Char == s2Char) {
            System.out.println("    " + s1Char + " is a part of a common subsequence");// same character will be printed many times because LCS function will be called many times with the same parameters. So, use Dynamic Programming.
            return 1 + LCS_Brute_Force(S1, S2, s1Start, s1End - 1, s2Start, s2End - 1);
        }

        // otherwise, get the max of (reducing first string's end index by 1, reducing second string's end index by 1)
        return Math.max(LCS_Brute_Force(S1, S2, s1Start, s1End - 1, s2Start, s2End),
                LCS_Brute_Force(S1, S2, s1Start, s1End, s2Start, s2End - 1));
    }

    private static int count = 0;

    private static int LCS_Top_Down_Approach(char[] S1, char[] S2, int s1Start, int s1End, int s2Start, int s2End, Map<String, Integer> memo) {
        count++;

        String key = s1End + ":" + s2End;
        if (memo.get(key) != null) return memo.get(key);

        if (s1End < s1Start || s2End < s2Start) return 0;

        // reducing the problem by one
        char s1Char = S1[s1End];
        char s2Char = S2[s2End];

        // if both chars matches, then reduce both strings' end index by 1
        if (s1Char == s2Char) {
            System.out.println("    " + s1Char + " is a part of a common subsequence");// same character will be printed many times because LCS function will be called many times with the same parameters. So, use Dynamic Programming.
            int resultFromRemaining = LCS_Top_Down_Approach(S1, S2, s1Start, s1End - 1, s2Start, s2End - 1, memo);
            memo.put((s1End - 1) + ":" + (s2End - 1), resultFromRemaining);
            return 1 + resultFromRemaining;
        }

        // otherwise, get the max of (reducing first string's end index by 1, reducing second string's end index by 1)
        int resultFromRemainingS1 = LCS_Top_Down_Approach(S1, S2, s1Start, s1End - 1, s2Start, s2End, memo);
        memo.put((s1End - 1) + ":" + s2End, resultFromRemainingS1);

        int resultFromRemainingS2 = LCS_Top_Down_Approach(S1, S2, s1Start, s1End, s2Start, s2End - 1, memo);
        memo.put(s1End + ":" + (s2End - 1), resultFromRemainingS2);

        return Math.max(resultFromRemainingS1, resultFromRemainingS2);
    }
}
