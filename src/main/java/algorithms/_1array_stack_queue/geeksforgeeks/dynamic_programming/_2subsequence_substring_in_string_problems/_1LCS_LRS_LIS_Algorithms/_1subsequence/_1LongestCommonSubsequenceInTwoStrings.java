package algorithms._1array_stack_queue.geeksforgeeks.dynamic_programming._2subsequence_substring_in_string_problems._1LCS_LRS_LIS_Algorithms._1subsequence;

import java.util.HashMap;
import java.util.Map;

/*
Longest Common Subsequence in two strings:

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


    A and A are same, so result = max( includingCurrentChar = 1 + LCS from remaining strings LCS("ACBE","ADC"),
                                       excludingCurrentChar = max(LCS(S1,"ACBE","ADCA"),
                                                                  LCS(S2,"ACBEA","ADC")
                                     )

        S1= A    C   B   E   A
                             -
        S2= A    D   C   A
                         -

    E and C are not same

        S1= A    C   B   E   A
                         -
        S2= A    D   C   A
                     -
        so, result = excludingCurrentChar = max(LCS(S1,"ACB","ADC"), LCS(S2,"ACBE","AD")

                    S1= A    C   B   E   A      A    C   B   E   A
                                     -                   -
                    S2= A    D   C   A          A    D   C   A
                             -                           -


    int LCSubsequence(S1[], S2[], s1Start, s1End, s2Start, s2End) {


        if(S1[s1End] == S2[s2End]) {

            int includingCurrentChar = 1 + LCSubsequence(S1, S2, s1Start, s1End-1, s2Start, s2End-1);

            int excludingCurrentChar = Math.max(LCSubsequence(S1, S2, s1Start, s1End-1, s2Start, s2End),
                                                LCSubsequence(S1, S2, s1Start, s1End, s2Start, s2End-1));

            return Math.max(includingCurrentChar, excludingCurrentChar);
        }

        int excludingCurrentChar =  Math.max(LCSubsequence(S1, S2, s1Start, s1End-1, s2Start, s2End),
                                             LCSubsequence(S1, S2, s1Start, s1End, s2Start, s2End-1));
        return excludingCurrentChar;

    }

    There is a small difference between this algorithm and "Longest Common Substring" algorithm.
    Read LongestCommonSubstringInTwoStrings.java



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

 Using Dynamic Programming, it can be reduced a lot to O(m*n).

*/
public class _1LongestCommonSubsequenceInTwoStrings {

    public static void main(String[] args) {
        {
            String s1 = "ACBEA";
            String s2 = "ADCA";

            System.out.println("Brute Force: " + "S1=" + s1 + ", S2=" + s2);
            System.out.println();
            int lcs = Brute_Force_Recursive(s1.toCharArray(), s2.toCharArray(), 0, s1.length() - 1, 0, s2.length() - 1);
            System.out.println();
            System.out.println("Max common subsequence: " + lcs);//3

            System.out.println();
            System.out.println();
            System.out.println();

            System.out.println("Brute Force Iterative: " + "S1=" + s1 + ", S2=" + s2);
            System.out.println();
            int lcsIterative = Brute_Force_Iterative(s1.toCharArray(), s2.toCharArray(), 0, s1.length() - 1, 0, s2.length() - 1);
            System.out.println();
            System.out.println("Max common subsequence: " + lcsIterative);//3

            System.out.println();
            System.out.println();
            System.out.println();


            System.out.println("Top-Down Dynamic Programming: " + "S1=" + s1 + ", S2=" + s2);
            System.out.println();
            lcs = Top_Down_Dynamic_Programming(s1.toCharArray(), s2.toCharArray(), 0, s1.length() - 1, 0, s2.length() - 1, new HashMap<>());
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
            int LCS = Brute_Force_Recursive(s1.toCharArray(), s2.toCharArray(), 0, s1.length() - 1, 0, s2.length() - 1);
            System.out.println("Max common subsequence: " + LCS);//0
            System.out.println();
            System.out.println("Total comparisons: " + cnt);// approx O(2^(m+n)) = O(2^(5+4))

            System.out.println();
            System.out.println();
            System.out.println();


            System.out.println("Brute Force Iterative: " + "S1=" + s1 + ", S2=" + s2);
            System.out.println();
            int lcsIterative = Brute_Force_Iterative(s1.toCharArray(), s2.toCharArray(), 0, s1.length() - 1, 0, s2.length() - 1);
            System.out.println();
            System.out.println("Max common subsequence: " + lcsIterative);//3

            System.out.println();
            System.out.println();
            System.out.println();

            System.out.println("Top-Down Dynamic Programming: " + "S1=" + s1 + ", S2=" + s2);
            System.out.println();
            LCS = Top_Down_Dynamic_Programming(s1.toCharArray(), s2.toCharArray(), 0, s1.length() - 1, 0, s2.length() - 1, new HashMap<>());
            System.out.println("Max common subsequence: " + LCS);//0
            System.out.println();
            System.out.println("Total comparisons: " + count);// approx O( (m+n)^2 ) = O( (5+4)^2 )
        }
    }

    static int cnt = 0;

    // Time Complexity: O(2 ^ m+n)
    // It can be improved to O(mn) using Dynamic Programming
    private static int Brute_Force_Recursive(char[] S1, char[] S2, int s1Start, int s1End, int s2Start, int s2End) {
        cnt++;
        if (s1End < s1Start || s2End < s2Start) return 0;

        // reducing the problem by one
        char s1Char = S1[s1End];
        char s2Char = S2[s2End];

        // if both chars matches, then reduce both strings' end index by 1
        if (s1Char == s2Char) {
            System.out.println("    " + s1Char + " is a part of a common subsequence");// same character will be printed many times because LCS function will be called many times with the same parameters. So, use Dynamic Programming.

            int includingTheChar = 1 + Brute_Force_Recursive(S1, S2, s1Start, s1End - 1, s2Start, s2End - 1);

            int excludingTheChar = Math.max(Brute_Force_Recursive(S1, S2, s1Start, s1End - 1, s2Start, s2End),
                    Brute_Force_Recursive(S1, S2, s1Start, s1End, s2Start, s2End - 1));

            return Math.max(includingTheChar, excludingTheChar);
        }

        // otherwise, get the max of (reducing first string's end index by 1, reducing second string's end index by 1)
        int excludingTheChar = Math.max(Brute_Force_Recursive(S1, S2, s1Start, s1End - 1, s2Start, s2End),
                Brute_Force_Recursive(S1, S2, s1Start, s1End, s2Start, s2End - 1));
        return excludingTheChar;
    }


    private static int Brute_Force_Iterative(char[] S1, char[] S2, int s1Start, int s1End, int s2Start, int s2End) {

        if (s1End < s1Start || s2End < s2Start) return 0;

        int finalMax = 0;

        for (int i = s1Start; i <= s1End; i++) {

            for (int j = s2Start; j <= s2End; j++) {

                if ((S1[i] == S2[j])) {

                    System.out.println("    " + S1[i] + " is a part of a common subsequence");// same character will be printed many times because LCS function will be called many times with the same parameters. So, use Dynamic Programming.

                    // you want to continue matching remaining string (i+1 to end) till you find any mismatched char.
                    // when you find any mismatched char, you want to break this loop (so, breakIfNotSame=true)
                    int max = 1 + Brute_Force_Iterative(S1, S2, i + 1, s1End, j + 1, s2End);

                    if (finalMax < max) {
                        finalMax = max;
                    }
                }
            }
        }

        return finalMax;
    }

    private static int count = 0;

    private static int Top_Down_Dynamic_Programming(char[] S1, char[] S2, int s1Start, int s1End, int s2Start, int s2End, Map<String, Integer> memo) {
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

            int includingTheChar = 1 + Top_Down_Dynamic_Programming(S1, S2, s1Start, s1End - 1, s2Start, s2End - 1, memo);


            int resultFromRemainingS1 = Top_Down_Dynamic_Programming(S1, S2, s1Start, s1End - 1, s2Start, s2End, memo);
            memo.put((s1End - 1) + ":" + s2End, resultFromRemainingS1);

            int resultFromRemainingS2 = Top_Down_Dynamic_Programming(S1, S2, s1Start, s1End, s2Start, s2End - 1, memo);
            memo.put(s1End + ":" + (s2End - 1), resultFromRemainingS2);

            int excludingTheChar = Math.max(resultFromRemainingS1, resultFromRemainingS2);

            return Math.max(includingTheChar, excludingTheChar);
        }

        // otherwise, get the max of (reducing first string's end index by 1, reducing second string's end index by 1)
        int resultFromRemainingS1 = Top_Down_Dynamic_Programming(S1, S2, s1Start, s1End - 1, s2Start, s2End, memo);
        memo.put((s1End - 1) + ":" + s2End, resultFromRemainingS1);

        int resultFromRemainingS2 = Top_Down_Dynamic_Programming(S1, S2, s1Start, s1End, s2Start, s2End - 1, memo);
        memo.put(s1End + ":" + (s2End - 1), resultFromRemainingS2);

        int excludingTheChar = Math.max(resultFromRemainingS1, resultFromRemainingS2);
        return excludingTheChar;
    }
}
