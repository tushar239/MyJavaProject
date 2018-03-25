package algorithms._1array.geeksforgeeks.dynamic_programming;

/*
Longest Repeating Subsequence:

In AABEBCDD, repeating subsequence is ABD. It appears twice.

Input: str = "abc"
Output: 0
There is no repeating subsequence

Input: str = "aab"
Output: 1
The two subssequence are 'a'(first) and 'a'(second).
Note that 'b' cannot be considered as part of subsequence
as it would be at same index in both.

Input: str = "aabb"
Output: 2

Input: str = "axxxy"
Output: 2


Solution of this problem is same as LongestCommonSubSequence.java with a tiny difference.

    The idea is to find the LCS(str, str)where str is the input string with the restriction that when both the characters are same, they shouldn't be on the same index in the two strings.

    int LCS(str,str) {

        if(S1[s1End] == S2[s2End] && s1End != s2End) { // s1End != s2End is an additional condition
            return 1 + LCS(S1, S2, s1Start, s1End-1, s2Start, s2End-1);
        }

        return Math.max(LCS(S1, S2, s1Start, s1End-1, s2Start, s2End),
                        LCS(S1, S2, s1Start, s1End, s2Start, s2End-1))

    }


 */
public class _5RepeatingSubSequence {
}
