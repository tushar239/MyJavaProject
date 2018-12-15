package algorithms._1array_stack_queue.geeksforgeeks.dynamic_programming._2subsequence_substring_in_string_problems._1LIS_LCS_LRS_Algorithms._1subsequence;

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
public class _1LCS_LongestCommonSubsequenceInTwoStrings {

    public static void main(String[] args) {
        {
            String s1 = "ACBEA";
            String s2 = "ADCA";
            // O/P: 3

            System.out.println("Brute Force Iterative: " + "S1=" + s1 + ", S2=" + s2);
            int lcsIterative = Brute_Force_Iterative(s1.toCharArray(), s2.toCharArray(), 0, s1.length() - 1, 0, s2.length() - 1);
            System.out.println("Result: " + lcsIterative);

            System.out.println();
            System.out.println();
            System.out.println();

            System.out.println("Brute Force Iterative - Another Way: " + "S1=" + s1 + ", S2=" + s2);
            int lcsIterativeAnotherWay = Brute_Force_Iterative_Another_Way_Outer(s1.toCharArray(), s2.toCharArray(), 0, s1.length() - 1, 0, s2.length() - 1);
            System.out.println("Result: " + lcsIterativeAnotherWay);

            System.out.println();
            System.out.println();
            System.out.println();

            System.out.println("Brute Force Partial Recursive: " + "S1=" + s1 + ", S2=" + s2);
            int lcsPartialRecursive = Brute_Force_Partial_Recursive(s1.toCharArray(), s2.toCharArray(), 0, s1.length() - 1, 0, s2.length() - 1);
            System.out.println("Result: " + lcsPartialRecursive);

            System.out.println();
            System.out.println();
            System.out.println();

            System.out.println("Brute Force Full Recursive: " + "S1=" + s1 + ", S2=" + s2);
            int lcsFullRecursive = Brute_Force_Full_Recursive(s1.toCharArray(), s2.toCharArray(), 0, s1.length() - 1, 0, s2.length() - 1);
            System.out.println("Result: " + lcsFullRecursive);

            System.out.println();
            System.out.println();
            System.out.println();

            System.out.println("Brute Force Full Recursive Improved Code: " + "S1=" + s1 + ", S2=" + s2);
            int lcsFullRecursiveImprovedCode = Brute_Force_Full_Recursive_Improved_Code(s1.toCharArray(), s2.toCharArray(), 0, s1.length() - 1, 0, s2.length() - 1);
            System.out.println("Result: " + lcsFullRecursiveImprovedCode);
            System.out.println("Total comparisons: " + cnt);// approx O(2^(m+n)) = O(2^(5+4))

            System.out.println();
            System.out.println();
            System.out.println();


            System.out.println("Top-Down Dynamic Programming: " + "S1=" + s1 + ", S2=" + s2);
            int[][] memory = new int[s1.length()][s2.length()];
            int lcsTopDown = Top_Down_Dynamic_Programming(s1.toCharArray(), s2.toCharArray(), 0, s1.length() - 1, 0, s2.length() - 1, memory);
            System.out.println("Result: " + lcsTopDown);
        }
        System.out.println();
        System.out.println("------------------------------------------");
        System.out.println();
        {
            String s1 = "ACBEA";
            String s2 = "PQARDSCTA";
            // O/P: 3

            System.out.println("Brute Force Iterative: " + "S1=" + s1 + ", S2=" + s2);
            int lcsIterative = Brute_Force_Iterative(s1.toCharArray(), s2.toCharArray(), 0, s1.length() - 1, 0, s2.length() - 1);
            System.out.println("Result: " + lcsIterative);

            System.out.println();
            System.out.println();
            System.out.println();

            System.out.println("Brute Force Iterative - Another Way: " + "S1=" + s1 + ", S2=" + s2);
            int lcsIterativeAnotherWay = Brute_Force_Iterative_Another_Way_Outer(s1.toCharArray(), s2.toCharArray(), 0, s1.length() - 1, 0, s2.length() - 1);
            System.out.println("Result: " + lcsIterativeAnotherWay);

            System.out.println();
            System.out.println();
            System.out.println();

            System.out.println("Brute Force Partial Recursive: " + "S1=" + s1 + ", S2=" + s2);
            int lcsPartialRecursive = Brute_Force_Partial_Recursive(s1.toCharArray(), s2.toCharArray(), 0, s1.length() - 1, 0, s2.length() - 1);
            System.out.println("Result: " + lcsPartialRecursive);

            System.out.println();
            System.out.println();
            System.out.println();

            System.out.println("Brute Force Full Recursive: " + "S1=" + s1 + ", S2=" + s2);
            int lcsFullRecursive = Brute_Force_Full_Recursive(s1.toCharArray(), s2.toCharArray(), 0, s1.length() - 1, 0, s2.length() - 1);
            System.out.println("Result: " + lcsFullRecursive);

            System.out.println();
            System.out.println();
            System.out.println();

            System.out.println("Brute Force Full Recursive Improved Code: " + "S1=" + s1 + ", S2=" + s2);
            int lcsFullRecursiveImprovedCode = Brute_Force_Full_Recursive_Improved_Code(s1.toCharArray(), s2.toCharArray(), 0, s1.length() - 1, 0, s2.length() - 1);
            System.out.println("Result: " + lcsFullRecursiveImprovedCode);
            System.out.println("Total comparisons: " + cnt);// approx O(2^(m+n)) = O(2^(5+4))

            System.out.println();
            System.out.println();
            System.out.println();


            System.out.println("Top-Down Dynamic Programming: " + "S1=" + s1 + ", S2=" + s2);
            int[][] memory = new int[s1.length()][s2.length()];
            int lcsTopDown = Top_Down_Dynamic_Programming(s1.toCharArray(), s2.toCharArray(), 0, s1.length() - 1, 0, s2.length() - 1, memory);
            System.out.println("Result: " + lcsTopDown);
        }
        System.out.println();
        System.out.println("------------------------------------------");
        System.out.println();
        {
            String s1 = "QRXYZ";
            String s2 = "ABCD";
            //O/P: 0

            System.out.println("Brute Force Iterative: " + "S1=" + s1 + ", S2=" + s2);
            int lcsIterative = Brute_Force_Iterative(s1.toCharArray(), s2.toCharArray(), 0, s1.length() - 1, 0, s2.length() - 1);
            System.out.println("Result: " + lcsIterative);

            System.out.println();
            System.out.println();
            System.out.println();

            System.out.println("Brute Force Iterative - Another Way: " + "S1=" + s1 + ", S2=" + s2);
            int lcsIterativeAnotherWay = Brute_Force_Iterative_Another_Way_Outer(s1.toCharArray(), s2.toCharArray(), 0, s1.length() - 1, 0, s2.length() - 1);
            System.out.println("Result: " + lcsIterativeAnotherWay);

            System.out.println();
            System.out.println();
            System.out.println();

            System.out.println("Brute Force Partial Recursive: " + "S1=" + s1 + ", S2=" + s2);
            int lcsPartialRecursive = Brute_Force_Partial_Recursive(s1.toCharArray(), s2.toCharArray(), 0, s1.length() - 1, 0, s2.length() - 1);
            System.out.println("Result: " + lcsPartialRecursive);

            System.out.println();
            System.out.println();
            System.out.println();

            System.out.println("Brute Force Full Recursive: " + "S1=" + s1 + ", S2=" + s2);
            int lcsFullRecursive = Brute_Force_Full_Recursive(s1.toCharArray(), s2.toCharArray(), 0, s1.length() - 1, 0, s2.length() - 1);
            System.out.println("Result: " + lcsFullRecursive);

            System.out.println();
            System.out.println();
            System.out.println();

            System.out.println("Brute Force Full Recursive Improved Code: " + "S1=" + s1 + ", S2=" + s2);
            int lcsFullRecursiveImprovedCode = Brute_Force_Full_Recursive_Improved_Code(s1.toCharArray(), s2.toCharArray(), 0, s1.length() - 1, 0, s2.length() - 1);
            System.out.println("Result: " + lcsFullRecursiveImprovedCode);
            System.out.println("Total comparisons: " + cnt);// approx O(2^(m+n)) = O(2^(5+4))

            System.out.println();
            System.out.println();
            System.out.println();

            System.out.println("Top-Down Dynamic Programming: " + "S1=" + s1 + ", S2=" + s2);
            int[][] memory = new int[s1.length()][s2.length()];
            int lcsTopDown = Top_Down_Dynamic_Programming(s1.toCharArray(), s2.toCharArray(), 0, s1.length() - 1, 0, s2.length() - 1, memory);
            System.out.println("Result: " + lcsTopDown);
            System.out.println("Total comparisons: " + count);// approx O( (m+n)^2 ) = O( (5+4)^2 )
        }


    }

    /*
          S1 = b d x
          S2 = b x b y d

          O/P: 2  (b,d)

          Time Complexity: O(2 ^ m+n)
          It can be improved to O(mn) using Dynamic Programming

          Try to match S1's first char (b) with S2's first char (b).
          If matches, then
          lscFromFirstChars = 1 + LCS( (d x), (x b y d) )

          There can be other chars in S2 that matches first char of S1. So, you need a loop for above code.

          int maxLcsFromFirstCharOfS1 = 0;

          s1Char = S1[s1Start]
          for(int i=s2Start; i<=s2End; i++) {

            s2Char = S2[i]

            if(s1Char == s2Char) {
                lscFromFirstChars = 1 + LCS( (d x), (x b y d) )

                maxLcsFromFirstCharOfS1 = Math.max(maxLcsFromFirstCharOfS1, lscFromFirstChars)
            }

          }


         Now, you need to repeat this concept for all other chars of S1. So, you need another loop on top of above code.


        int maxLcsFromAllCharsOfS1 = 0;

        for(int j=s1Start; j<=s2End; j++) {

          int maxLcsFromFirstCharOfS1 = 0;

          s1Char = S1[j]

          for(int i=s2Start; i<=s2End; i++) {

            s2Char = S2[i]

            if(s1Char == s2Char) {
                lscFromFirstChars = 1 + LCS( (d x), (x b y d) )

                maxLcsFromFirstCharOfS1 = Math.max(maxLcsFromFirstCharOfS1, lscFromFirstChars)
            }

          }

          maxLcsFromAllCharsOfS1 = Math.max(maxLcsFromAllCharsOfS1, maxLcsFromFirstCharOfS1)
        }

        return maxLcsFromAllCharsOfS1;
     */
    private static int Brute_Force_Iterative(char[] S1, char[] S2, int s1Start, int s1End, int s2Start, int s2End) {

        if (s1End < s1Start || s2End < s2Start) return 0;

        // OR
        /*
        if(s1Start == s1End && s2Start == s2End) {
            // reducing the problem by one
            char s1Char = S1[s1Start];
            char s2Char = S2[s2End];

            if (s1Char == s2Char) {
                return 1;
            }
            return 0;
        }

        if(s1Start == s1End && s2Start < s2End) {
            char s1Char = S1[s1Start];
            char s2Char = S2[s2End];

            if (s1Char == s2Char) {
                return 1;
            }
            return 0;
        }

        if(s1Start < s1End && s2Start == s2End) {
            char s1Char = S1[s1Start];
            char s2Char = S2[s2End];

            if (s1Char == s2Char) {
                return 1;
            }
            return 0;
        }*/

        /*int maxLcsFromAllCharsOfS1 = 0;

        for (int i = s1Start; i <= s1End; i++) {

            char s1Char =S1[i];

            for (int j = s2Start; j <= s2End; j++) {

                char s2Char = S2[j];

                int maxLcsFromFirstCharOfS1 = 0;

                // you can do it a bit different way also.
                // you can find matching char in S2 and start from there. Then again find matching char in S2 and continue that way.
                if ((s1Char == s2Char)) {

                    //System.out.println("    " + S1[i] + " is a part of a common subsequence");// same character will be printed many times because LCS function will be called many times with the same parameters. So, use Dynamic Programming.

                    // you want to continue matching remaining string (i+1 to end) till you find any mismatched char.
                    // when you find any mismatched char, you want to break this loop (so, breakIfNotSame=true)
                    int maxLcsFromFirstCharsOfS1AndS2 = 1 + Brute_Force_Iterative(S1, S2, i + 1, s1End, j + 1, s2End);

                    maxLcsFromFirstCharOfS1 = Math.max(maxLcsFromFirstCharOfS1, maxLcsFromFirstCharsOfS1AndS2);

                }

                maxLcsFromAllCharsOfS1 = Math.max(maxLcsFromAllCharsOfS1, maxLcsFromFirstCharOfS1);
            }

        }

        return maxLcsFromAllCharsOfS1;*/

        // OR

        int finalMax = 0;

        for (int i = s1Start; i <= s1End; i++) {

            for (int j = s2Start; j <= s2End; j++) {

                // you can do it a bit different way also.
                // you can find matching char in S2 and start from there. Then again find matching char in S2 and continue that way.
                if ((S1[i] == S2[j])) {

                    //System.out.println("    " + S1[i] + " is a part of a common subsequence");// same character will be printed many times because LCS function will be called many times with the same parameters. So, use Dynamic Programming.

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

    private static int Brute_Force_Partial_Recursive(char[] S1, char[] S2, int s1Start, int s1End, int s2Start, int s2End) {

        if (s1End < s1Start || s2End < s2Start) return 0;

        int finalMax = 0;

        for (int i = s1Start; i <= s1End; i++) {

            //for (int j = s2Start; j <= s2End; j++) {

                if ((S1[i] == S2[s2Start])) {

                    int max = 1 + Brute_Force_Partial_Recursive(S1, S2, i + 1, s1End, s2Start + 1, s2End);

                    if (finalMax < max) {
                        finalMax = max;
                    }
                }
            //}

            // converted above for loop in recursion
            int maxForCurrentEle = Brute_Force_Partial_Recursive(S1, S2, s1Start, s1End, s2Start+1, s2End);
            if(maxForCurrentEle > finalMax) {
                finalMax = maxForCurrentEle;
            }

        }

        return finalMax;
    }


    private static int Brute_Force_Full_Recursive(char[] S1, char[] S2, int s1Start, int s1End, int s2Start, int s2End) {

        if (s1End < s1Start || s2End < s2Start) return 0;

        int finalMax = 0;

        //for (int i = s1Start; i <= s1End; i++) {

            if ((S1[s1Start] == S2[s2Start])) {

                int max = 1 + Brute_Force_Full_Recursive(S1, S2, s1Start + 1, s1End, s2Start + 1, s2End);

                if (finalMax < max) {
                    finalMax = max;
                }
            }

            int maxForCurrentEle = Brute_Force_Full_Recursive(S1, S2, s1Start, s1End, s2Start+1, s2End);
            if(maxForCurrentEle > finalMax) {
                finalMax = maxForCurrentEle;
            }

        //}
        // converted above for loop in recursion
        int maxForOtherEles = Brute_Force_Full_Recursive(S1, S2, s1Start+1, s1End, s2Start, s2End);
        if(maxForOtherEles > finalMax) {
            finalMax = maxForOtherEles;
        }


        return finalMax;
    }


    /*
        https://www.youtube.com/watch?v=sSno9rV8Rhg
        Time Complexity: O(2 ^m+n)
        It can be improved to O(mn) using Dynamic Programming

        You want total recursive code, if you want to do Bottom-UP. It's very hard to come up with Bottom-Up with iterative code, especially when you have two or more input parameters (here s1 and s2).
     */
    private static int Brute_Force_Full_Recursive_Improved_Code(char[] S1, char[] S2, int s1Start, int s1End, int s2Start, int s2End) {

        if (s1End < s1Start || s2End < s2Start) return 0;

        int finalMax = 0;

        if ((S1[s1Start] == S2[s2Start])) {

            int maxOfStartEleOfS1WithStartEleOfS2 = 1 + Brute_Force_Full_Recursive_Improved_Code(S1, S2, s1Start + 1, s1End, s2Start + 1, s2End);

            if (finalMax < maxOfStartEleOfS1WithStartEleOfS2) {
                finalMax = maxOfStartEleOfS1WithStartEleOfS2;
            }
        }

        int maxForStartEleOfS1WithRestOfElesOfS2 = Brute_Force_Full_Recursive_Improved_Code(S1, S2, s1Start, s1End, s2Start+1, s2End);
        /*if(maxForStartEleOfS1WithRestOfElesOfS2 > finalMax) {
            finalMax = maxForCurrentEle;
        }*/

        int maxForRestOfElesOfS1 = Brute_Force_Full_Recursive_Improved_Code(S1, S2, s1Start+1, s1End, s2Start, s2End);
        /*if(maxForRestOfElesOfS1 > finalMax) {
            finalMax = maxForOtherEles;
        }*/

        finalMax = Math.max(finalMax, Math.max(maxForStartEleOfS1WithRestOfElesOfS2, maxForRestOfElesOfS1));

        return finalMax;
    }

    // ----------------------------------------------------------------------------------------------------------------------

    private static int Brute_Force_Iterative_Another_Way_Outer(char[] S1, char[] S2, int s1Start, int s1End, int s2Start, int s2End) {
        int max = 0;

        for (int i = s1Start; i < S1.length; i++) {
            int lcsFromSpecificElement = Brute_Force_Iterative_Another_Way_Inner(S1, S2, i, S1.length - 1, s2Start, s2End);
            if (lcsFromSpecificElement > max) {
                max = lcsFromSpecificElement;
            }
        }
        return max;
    }

    private static int Brute_Force_Iterative_Another_Way_Inner(char[] S1, char[] S2, int s1Start, int s1End, int s2Start, int s2End) {

        int maxLcsForCurrentElement = 0;
        int currentElement = S1[s1Start];

        for (int j = s2Start; j <= s2End; j++) {

            // you can do it a bit different way also.
            // you can find matching char in S2 and start from there. Then again find matching char in S2 and continue that way.
            if ((currentElement == S2[j])) {

                //System.out.println("    " + currentElement + " is a part of a common subsequence");// same character will be printed many times because LCS function will be called many times with the same parameters. So, use Dynamic Programming.

                // you want to continue matching remaining string (i+1 to end) till you find any mismatched char.
                // when you find any mismatched char, you want to break this loop (so, breakIfNotSame=true)
                int max = 1 + Brute_Force_Iterative_Another_Way_Outer(S1, S2, s1Start + 1, s1End, j + 1, s2End);// IMPORTANT: you have to call outer method only. Result will be wrong, if you call this same method.
                // NOT
                //int max = 1 + Brute_Force_Iterative_Another_Way_Inner(S1, S2, s1Start + 1, s1End, j + 1, s2End);

                if (maxLcsForCurrentElement < max) {
                    maxLcsForCurrentElement = max;
                }
            }
        }
        return maxLcsForCurrentElement;
    }


    static int cnt = 0;

/*    *//*
     https://www.youtube.com/watch?v=sSno9rV8Rhg
     Time Complexity: O(2 ^m+n)
     It can be improved to O(mn) using Dynamic Programming

     IMPORTANT:
     For problems where you have two inputs (here two strings), this is the most effective way of solving the problem.
     You can try to solve the problem as Brute_Force_Iterative(), but there you have to remember that when you recurse, you have to recurse from outer loop. By mistake, you will end up trying recursing like LIS algorithm. It will result in wrong answer.

     S1 = bd
          -
     S2 = bcd
          -

     if letter of both strings match, do 1 + LCS(d, cd)



     S1 = bd
          -
     S2 = abcd
          -

     if letter of both strings do not match, then you want to try to match
     - 'b' with remaining S2 (bcd)
     - try to match remaining S1 (d) with S2(abcd)
    *//*
    private static int Brute_Force_Total_Recursive(char[] S1, char[] S2, int s1Start, int s1End, int s2Start, int s2End) {
        cnt++;
        if (s1Start > s1End || s2Start > s2End) return 0;

        // OR
        *//*
        if(s1Start == s1End && s2Start == s2End) {
            // reducing the problem by one
            char s1Char = S1[s1Start];
            char s2Char = S2[s2End];

            if (s1Char == s2Char) {
                return 1;
            }
            return 0;
        }

        if(s1Start == s1End && s2Start < s2End) {
            char s1Char = S1[s1Start];
            char s2Char = S2[s2End];

            if (s1Char == s2Char) {
                return 1;
            }
            return 0;
        }

        if(s1Start < s1End && s2Start == s2End) {
            char s1Char = S1[s1Start];
            char s2Char = S2[s2End];

            if (s1Char == s2Char) {
                return 1;
            }
            return 0;
        }*//*

        // reducing the problem by one
        char s1Char = S1[s1Start];
        char s2Char = S2[s2Start];

        // if both chars matches, then increase both strings' start index by 1
        if (s1Char == s2Char) {
            //System.out.println("    " + s1Char + " is a part of a common subsequence");// same character will be printed many times because LCS function will be called many times with the same parameters. So, use Dynamic Programming.

            int includingCurrentEle = 1 + Brute_Force_Total_Recursive(S1, S2, s1Start + 1, s1End, s2Start + 1, s2End);

            return includingCurrentEle;
            *//*int excludingCurrentEle = Math.max(Brute_Force_Total_Recursive(S1, S2, s1Start+1, s1End, s2Start, s2End),
                                               Brute_Force_Total_Recursive(S1, S2, s1Start, s1End, s2Start+1, s2End));

            return Math.max(includingCurrentEle, excludingCurrentEle);*//*

        }

        // otherwise, get the max of (increasing first string's start index by 1, increasing second string's start index by 1)
        int excludingCurrentEle = Math.max(Brute_Force_Total_Recursive(S1, S2, s1Start + 1, s1End, s2Start, s2End),
                Brute_Force_Total_Recursive(S1, S2, s1Start, s1End, s2Start + 1, s2End));
        return excludingCurrentEle;
    }*/

    private static int count = 0;
    private static int Top_Down_Dynamic_Programming(char[] S1, char[] S2, int s1Start, int s1End, int s2Start, int s2End, int[][] memory) {
        count++;
        if (s1Start > s1End || s2Start > s2End) return 0;

        // OR
        /*
        if(s1Start == s1End && s2Start == s2End) {
            // reducing the problem by one
            char s1Char = S1[s1Start];
            char s2Char = S2[s2End];

            if (s1Char == s2Char) {
                return 1;
            }
            return 0;
        }

        if(s1Start == s1End && s2Start < s2End) {
            char s1Char = S1[s1Start];
            char s2Char = S2[s2End];

            if (s1Char == s2Char) {
                return 1;
            }
            return 0;
        }

        if(s1Start < s1End && s2Start == s2End) {
            char s1Char = S1[s1Start];
            char s2Char = S2[s2End];

            if (s1Char == s2Char) {
                return 1;
            }
            return 0;
        }*/

        // Dynamic Programming: Returning memoized result
        if(memory[s1Start][s2Start] != 0) {
            return memory[s1Start][s2Start];
        }
        // reducing the problem by one
        char s1Char = S1[s1Start];
        char s2Char = S2[s2Start];

        // if both chars matches, then increase both strings' start index by 1
        if (s1Char == s2Char) {
            //System.out.println("    " + s1Char + " is a part of a common subsequence");// same character will be printed many times because LCS function will be called many times with the same parameters. So, use Dynamic Programming.

            int includingCurrentEle = 1 + Top_Down_Dynamic_Programming(S1, S2, s1Start + 1, s1End, s2Start + 1, s2End, memory);

            // Dynamic Programming: Returning memoized value
            memory[s1Start][s2Start] = includingCurrentEle;

            return includingCurrentEle;
            /*int excludingCurrentEle = Math.max(Top_Down_Dynamic_Programming(S1, S2, s1Start+1, s1End, s2Start, s2End, memory),
                                               Top_Down_Dynamic_Programming(S1, S2, s1Start, s1End, s2Start+1, s2End, memory));

            return Math.max(includingCurrentEle, excludingCurrentEle);*/

        }

        // otherwise, get the max of (increasing first string's start index by 1, increasing second string's start index by 1)
        int excludingCurrentEle = Math.max(Top_Down_Dynamic_Programming(S1, S2, s1Start + 1, s1End, s2Start, s2End, memory),
                Top_Down_Dynamic_Programming(S1, S2, s1Start, s1End, s2Start + 1, s2End, memory));

        // Dynamic Programming: Returning memoized value
        memory[s1Start][s2Start] = excludingCurrentEle;

        return excludingCurrentEle;
    }

    /*

          Bottom-Up Approach
          Based on exit condition of top-down approach (if you start from s1End and s2End and decrease them in recursive call)

                    Q       A       C       B       E       A

            -1      0       1       2       3       4       5

       -1   0       0       0       0       0       0       0

   A   0    0

   D   1    0

   C   2    0

   A   3    0

   P   4    0

   E   5    0


       start filling up from cell (0,0)


       in top-down approach, if you start from s1Start and s2Start and increase them in recursive call,
       exit condition will be s1Start > s1End || s2Start > s2End

            Q       A       C       B       E       A
            0       1       2       3       4       5       6

   A   0                                                    0

   D   1                                                    0

   C   2                                                    0

   A   3                                                    0

   P   4                                                    0

   E   5                                                    0

       6    0      0       0       0        0       0       0

       start filling up from cell (5,5)


       in top-down approach, recursive condition says if(s1Start > s1End || s2Start > s2End) return 0
       exit condition will be s1Start > s1End || s2Start > s2End
       So, you need one more row and column in memory table initialized with 0.

           Q       A       C       B       E       A
           0       1       2       3       4       5       6

   A   0    3      3       2       1       1       1       0

   D   1    2      2       2       1       1       1       0

   C   2    2      2       2       1       1       1       0

   A   3    2      2       1       1       1       1       0

   P   4    1      1       1       1       1       0       0

   E   5    1      1       1       1       1       0       0

       6    0      0       0       0       0       0       0

       start filling up from cell (5,5)

       you answer will be in the first cell (0,0) = 3

       How to find out the elements that makes max lcs?

       Inspect first cell(0,0):
       If s1 and s2 chars do not match, then it means that you have to go to right (0,1) or down (1,0), from where you got the value for first cell.
       (0,1) > (1,0), so you must have got value in (0,0) from (0,1)

       Now inspect (0,1):
       s1 and s2 chars matches. So, 'A' is one of the element in result.
       So, value in (0,1) must have come from 1+(1,2).

       Now inspect (1,2): ..... and so on



     */
    private static void Bottom_Up_Approach(String S1, String S2) {

    }

}
