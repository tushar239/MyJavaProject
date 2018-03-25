package algorithms._1array.geeksforgeeks.dynamic_programming.string_subsequence_substring_problems.substring;

/*
Given two string sequences write an algorithm to find, find the length of longest substring present in both of them.

https://algorithms.tutorialhorizon.com/dynamic-programming-longest-common-substring/

This is a hard problem.

LongestRepeatingNonOverlappingSubString.java is a harder version of this.
*/
public class _1LongestCommonSubStringInTwoStrings {

    public static void main(String[] args) {
        String s1 = "XYZABC";
        String s2 = "ABCXYZA";
        int longestCommonSubStringSize = Brute_Force(s1.toCharArray(), s2.toCharArray(), 0, s1.toCharArray().length - 1, 0, s2.toCharArray().length - 1, false);
        System.out.println(longestCommonSubStringSize);//4
    }

    // O(n^3)
    // It can be improved to O(n^2) using Dynamic Approach.

    // 'breakIfNotSame' is an important flag
    private static int Brute_Force(char[] S1, char[] S2, int s1Start, int s1End, int s2Start, int s2End, boolean breakIfNotSame) {

        if (s1End < s1Start || s2End < s2Start) return 0;

        int finalMax = 0;

        int startIndexOfSubString = 0;
        int endIndexOfSubString = 0;

        for (int i = s1Start; i <= s1End; i++) {

            for (int j = s2Start; j <= s2End; j++) {

                if ((S1[i] == S2[j])) {

                    // you want to continue matching remaining string (i+1 to end) till you find any mismatched char.
                    // when you find any mismatched char, you want to break this loop (so, breakIfNotSame=true)
                    int max = 1 + Brute_Force(S1, S2, i + 1, s1End, j + 1, s2End, true);

                    if (finalMax < max) {
                        finalMax = max;
                        startIndexOfSubString = i;
                        endIndexOfSubString = i+ (finalMax-1);
                    }
                } else {

                    if (breakIfNotSame) {
                        break;
                    }
                }
            }
        }

        System.out.println("startIndexOfSubString: "+startIndexOfSubString);//0
        System.out.println("endIndexOfSubString: "+endIndexOfSubString);//3
        return finalMax;
    }
}
