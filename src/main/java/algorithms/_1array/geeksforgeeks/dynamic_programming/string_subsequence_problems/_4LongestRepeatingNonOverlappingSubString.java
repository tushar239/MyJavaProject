package algorithms._1array.geeksforgeeks.dynamic_programming.string_subsequence_problems;

/*
Longest repeating and non-overlapping substring:
https://www.geeksforgeeks.org/longest-repeating-and-non-overlapping-substring/

*/
public class _4LongestRepeatingNonOverlappingSubString {

    public static void main(String[] args) {
        //String s1 = "geekgeek";//4 - geek
        //String s1 = "gekshforgeksih";// 4- geks
        //String s1 = "gekshforgekih";//3 - gek
        //String s1 = "geeksforgeeks";//5 - geeks
        //String s1 = "gbekgsibek";//3 - bek
        //String s1 = "geekgsieek";//3 - eek
        String s1 = "geekgbeek";//3 - eek

        {
            int substringLength = Brute_Force(s1.toCharArray(), s1.toCharArray(), 0, s1.length() - 1, 0, s1.length() - 1, false);
            System.out.println(substringLength);
        }

        {
            String subString = Bottom_Up_From_Site(s1.toCharArray(), s1.length());
            System.out.println(subString);
        }

    }

    private static int Brute_Force(char[] S1, char[] S2, int s1Start, int s1End, int s2Start, int s2End, boolean breakIfNotSame) {
        if (s1End < s1Start || s2End < s2Start) return 0;

        int finalMax = 0;

        for (int i = s1Start; i <= s1End; i++) {
            for (int j = s2Start; j <= s2End; j++) {
                if ((S1[i] == S2[j]) &&
                        (j > i) &&
                        // condition for non-overlapping
                        ((j - i) > Brute_Force(S1, S2, i + 1, s1End, j + 1, s2End, true))) {
                    System.out.println(S1[i] + " duplicate found");

                    int max = 1 + Brute_Force(S1, S2, i + 1, s1End, j + 1, s2End, true);

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

    private static String Bottom_Up_From_Site(char[] str, int n) {
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
