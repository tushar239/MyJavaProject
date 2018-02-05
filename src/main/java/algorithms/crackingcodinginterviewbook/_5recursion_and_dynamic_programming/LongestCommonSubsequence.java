package algorithms.crackingcodinginterviewbook._5recursion_and_dynamic_programming;

/*
Find a length of LCS (Longest Common Subsequence) between two strings:


P = a b c d e f g
       -   - - -   -
Q = p q a r c d e g f
           -   - - -   -

O/P: a c d e f


There are many possible common subsequencens in two strings. You need to find length of the longest one.

Watch 'Longest Common Subsequence (Brute-Force and Dynamic Programming).mp4'


LCS Brute-Force Approach

    P    =   A   B   A   C   D
    Q    =   B   A   T   D

    Recursion - reduce the problem by 1.
                Take first char of both strings.
                If they match find result is LCS(P[n-1], Q[m-1]) + 1
                If they do not match then result is max(LCS(P[n-1], Q[m]), LCS(P[n], Q[m-1]))

                int LCS(char[] Pchars, char[] Qchars, startIndexOfP, endIndexOfP, startIndexOfQ, endIndexOfQ) {

                    if(Pchars == null || Pchar.size() == 0 || Qchars == null || Qchars.size() == 0) return 0;
                    if(startIndexOfP > endIndexOfP) return 0;
                    if(startIndexOfQ > endIndexOfQ) return 0;

                    char firstCharFromP = Pchars[startIndexOfP];
                    char firstCharFromQ = Qchars[startIndexOfQ];

                    if(firstCharFromP == firstCharFromQ) {
                        return 1+ LCS(Pchars, Qchars, startIndexOfP + 1, endIndexOfP, startIndexOfQ + 1, endIndexOfQ);
                    }
                    return Math.max(LCS(Pchars, Qchars, startIndexOfP + 1, endIndexOfP, startIndexOfQ, endIndexOfQ),
                                    LCS(Pchars, Qchars, startIndexOfP, endIndexOfP, startIndexOfQ + 1, endIndexOfQ))
                }

        Worst case:

        P = A   A
        Q = B   B   B

         LCS([A,A], [B,B,B])
                |
------------------------------------------------------------------------------------------------
|                                                                   |                         |
L = LCS([A], [B,B,B])                                           R = LCS([A, A], [B,B])      return max(L, R)
         |                                                                  |
------------------------------------------                  ---------------------------------------------
|                   |                    |                  |                       |                   |
L=LCS([], [B,B,B])  R=LCS([A], [B,B])   return max(L, R)  L=LCS([A], [B,B])  R=LCS([A,A], [B])   return max(L, R)
                            |                                     |
                            |                                     |
                            |                                     |
                            ----------------------------------------
                                            |
                                            v
                                        called more than once

Dynamic Programming Approach:

    This problem can be solved using Dynamic Programming.

    One way is to use Top-Bottom approach of Dynamic Programming that is easy.

    Now, let's think how to convert Brute-Force into Bottom-Up Dynamic Programming. How to come up with a formula to calculate the value in 2-D matrix cells?

    Rule of Thumb:
    When you try to convert Brute-Force into Bottom-Up Dynamic Programming approach, you think recursive approach little bit differently.
    Instead of thinking of taking out first element to reduce the problem by 1, you think of taking out the last element.

    This problem can be solved using Bottom-Up Approach Dynamic Approach.


    Initial matrix

            A   B   A   C   D

        0   0   0   0   0   0

    B   0

    A   0

    T   0

    D   0


    Think of reducing the problem by 1 by taking out last elements of both strings in below matrix cell(4,5)

    Here, last chars of both strings are matching, so cell(4,5)'s value should be cell(3,4)'s value + 1
                                                                                  cell(n-1,m-1) + 1
    If last chars of both strings do not match, then cell(4,5)'s value should be max(cell(3,5), cell(4,4))
                                                                                 max(cell(n-1,m), cell(n,m-1))
                  <------- Q ------>
                  A   B   A   C   D
                  0   1   2   3   4

                 0   1   2   3   4   5
                ------------------------
             0 | 0   0   0   0   0   0
    ^          |
    |  B  0  1 | 0
    |          |
    P  A  1  2 | 0
    |          |
    |  T  2  3 | 0                \
    |          |                   \
    |          |                   v
    v  D  3  4 | 0                  1+


*/
public class LongestCommonSubsequence {

    public static void main(String[] args) {
        LongestCommonSubsequence lcs = new LongestCommonSubsequence();
        String s1 = "BATD";
        String s2 = "ABACD";

        char[] P = s1.toCharArray();
        char[] Q = s2.toCharArray();

        System.out.println("Length of LCS is" + " " +
                lcs.lcs(P, Q, P.length, Q.length));
    }


    int lcs(char[] P, char[] Q, int sizeOfP, int sizeOfQ) {
        int L[][] = new int[P.length + 1][Q.length + 1]; // matrix


        // initialize first row with 0s
        for (int col = 0; col <= sizeOfQ; col++) {
            L[0][col] = 0;
        }

        // initialize first col with 0s
        for (int row = 0; row <= sizeOfP; row++) {
            L[row][0] = 0;
        }

        for (int i = 1; i <= sizeOfP; i++) {
            for (int j = 1; j <= sizeOfQ; j++) {
                //System.out.println("["+i+","+j+"]");

                if (P[i - 1] == Q[j - 1]) {
                    L[i][j] = L[i - 1][j - 1] + 1;
                } else {
                    L[i][j] = Math.max(L[i - 1][j], L[i][j - 1]);
                }
            }
        }
        return L[L.length-1][L[0].length-1];
    }

}
