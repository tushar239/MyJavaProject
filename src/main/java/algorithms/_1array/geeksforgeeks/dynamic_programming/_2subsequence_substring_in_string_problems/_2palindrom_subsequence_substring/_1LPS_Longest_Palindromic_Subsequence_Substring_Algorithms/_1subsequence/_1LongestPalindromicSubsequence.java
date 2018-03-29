package algorithms._1array.geeksforgeeks.dynamic_programming._2subsequence_substring_in_string_problems._2palindrom_subsequence_substring._1LPS_Longest_Palindromic_Subsequence_Substring_Algorithms._1subsequence;

/*

Longest Palindromic Substring

https://www.geeksforgeeks.org/longest-palindrome-substring-set-1/

For example, if the given string is “geeksforgeeks”, longest palindromic subsequence is "eekee"

You need to have two strings out of one to compare with each other

S1 = geeksforgeeks
      -
S2 = geeksforgeeks
               -

S1 = geeksforgeeks
       -
S2 = geeksforgeeks
              -

S1 = geeksforgeeks
        -
S2 = geeksforgeeks
        -

S1 = geeksforgeeks
              -
S2 = geeksforgeeks
       -

S1 = geeksforgeeks
               -
S2 = geeksforgeeks
      -

what is the diff between "Longest Common Subsequence" and "Longest Common Palindromic Subsequence" problems?
In later one, traversal of S1 starts from start index and traversal of S2 starts from end index.

*/
public class _1LongestPalindromicSubsequence {

    public static void main(String[] args) {
        {
            String str = "geeksforgeeks";//5 -- "geeks", "eekee", "eesee", "eefee"
            {
                int maxLengthOfPalindromicSubsequence = Brute_Force_Recursive_From_Site(str.toCharArray(), str.toCharArray(), 0, str.toCharArray().length - 1, 0, str.toCharArray().length - 1);
                System.out.println(maxLengthOfPalindromicSubsequence);
            }
            {
                int maxLengthOfPalindromicSubsequence = Brute_Force_Recursive_Another_Way_Better_To_Remember(str.toCharArray(), str.toCharArray(), 0, str.toCharArray().length - 1, 0, str.toCharArray().length - 1);
                System.out.println(maxLengthOfPalindromicSubsequence);
            }
        }
        {
            String str = "BBABCBCAB";//7 -- "BABCBAB"
            {
                int maxLengthOfPalindromicSubsequence = Brute_Force_Recursive_From_Site(str.toCharArray(), str.toCharArray(), 0, str.toCharArray().length - 1, 0, str.toCharArray().length - 1);
                System.out.println(maxLengthOfPalindromicSubsequence);
            }
            {
                int maxLengthOfPalindromicSubsequence = Brute_Force_Recursive_Another_Way_Better_To_Remember(str.toCharArray(), str.toCharArray(), 0, str.toCharArray().length - 1, 0, str.toCharArray().length - 1);
                System.out.println(maxLengthOfPalindromicSubsequence);
            }
        }
        {
            String str = "aba";//3 -- "aba"
            {
                int maxLengthOfPalindromicSubsequence = Brute_Force_Recursive_From_Site(str.toCharArray(), str.toCharArray(), 0, str.toCharArray().length - 1, 0, str.toCharArray().length - 1);
                System.out.println(maxLengthOfPalindromicSubsequence);
            }
            {
                int maxLengthOfPalindromicSubsequence = Brute_Force_Recursive_Another_Way_Better_To_Remember(str.toCharArray(), str.toCharArray(), 0, str.toCharArray().length - 1, 0, str.toCharArray().length - 1);
                System.out.println(maxLengthOfPalindromicSubsequence);
            }
        }
        {
            String str = "aa";//2 -- "aa"
            {
                int maxLengthOfPalindromicSubsequence = Brute_Force_Recursive_From_Site(str.toCharArray(), str.toCharArray(), 0, str.toCharArray().length - 1, 0, str.toCharArray().length - 1);
                System.out.println(maxLengthOfPalindromicSubsequence);
            }
            {
                int maxLengthOfPalindromicSubsequence = Brute_Force_Recursive_Another_Way_Better_To_Remember(str.toCharArray(), str.toCharArray(), 0, str.toCharArray().length - 1, 0, str.toCharArray().length - 1);
                System.out.println(maxLengthOfPalindromicSubsequence);
            }
        }
        {
            String str = "a";//1 -- "a"
            {
                int maxLengthOfPalindromicSubsequence = Brute_Force_Recursive_From_Site(str.toCharArray(), str.toCharArray(), 0, str.toCharArray().length - 1, 0, str.toCharArray().length - 1);
                System.out.println(maxLengthOfPalindromicSubsequence);
            }
            {
                int maxLengthOfPalindromicSubsequence = Brute_Force_Recursive_Another_Way_Better_To_Remember(str.toCharArray(), str.toCharArray(), 0, str.toCharArray().length - 1, 0, str.toCharArray().length - 1);
                System.out.println(maxLengthOfPalindromicSubsequence);
            }
        }
    }

    private static int Brute_Force_Recursive_Another_Way_Better_To_Remember(char[] S1, char[] S2, int s1Start, int s1End, int s2Start, int s2End) {

        if (s1Start > s1End || s2End < s2Start) return 0;

        // reducing the problem by one
        char s1Char = S1[s1Start];
        char s2Char = S2[s2End];

        /*
         "A           B           A"
         s1Start                  s2End

         first and last characters match, so add 1 to result.

         "A           B           A"
                s1Start,s2End

         then s1Start and s2End will move to 'B'. so add 1 to result and let s1Start move ahead to last 'A' and let s2End move ahead to first 'A'.

         "A           B           A"
         s2End                  s1Start

         s1Start and s2End characters match, so add 1 to result.

        */
        if (s1Char == s2Char /*|| s1Start == s2End*/) {

            int includingCurrentChar = 1 + Brute_Force_Recursive_Another_Way_Better_To_Remember(S1, S2, s1Start + 1, s1End, s2Start, s2End - 1);

            int excludingCurrentChar = Math.max(Brute_Force_Recursive_Another_Way_Better_To_Remember(S1, S2, s1Start + 1, s1End, s2Start, s2End),
                    Brute_Force_Recursive_Another_Way_Better_To_Remember(S1, S2, s1Start, s1End, s2Start, s2End - 1));


            return Math.max(includingCurrentChar, excludingCurrentChar);
        }

        // If the first and last characters do not match
        int excludingCurrentChar = Math.max(Brute_Force_Recursive_Another_Way_Better_To_Remember(S1, S2, s1Start + 1, s1End, s2Start, s2End),
                Brute_Force_Recursive_Another_Way_Better_To_Remember(S1, S2, s1Start, s1End, s2Start, s2End - 1));
        return excludingCurrentChar;

    }

    private static int Brute_Force_Recursive_From_Site(char[] S1, char[] S2, int s1Start, int s1End, int s2Start, int s2End) {

        if (s1End > S1.length - 1 || s2End < s2Start) return 0;

        // Base Case 1: If there is only 1 character
        if (s1Start == s2End) return 1;

        // reducing the problem by one
        char s1Char = S1[s1Start];
        char s2Char = S2[s2End];

        // Base Case 2: If there are only 2 characters and both are same
        // "AA" - length of palindromic subsequence will be 2
        if (s1Char == s2Char && s1Start + 1 == s2End) return 2;

        // If the first and last characters match
        // "ABA" - first and last characters match, so both of them are part of palindrome, so +2
        if (s1Char == s2Char) {
            return 2 + Brute_Force_Recursive_From_Site(S1, S2, s1Start + 1, s1End, s2Start, s2End - 1);
        }

        // If the first and last characters do not match
        return Math.max(Brute_Force_Recursive_From_Site(S1, S2, s1Start + 1, s1End, s2Start, s2End),
                Brute_Force_Recursive_From_Site(S1, S2, s1Start, s1End, s2Start, s2End - 1));

    }

}
