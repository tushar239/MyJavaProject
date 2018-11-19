package algorithms.crackingcodinginterviewbook._1stringmanipulations;

/*

    Meaning of Permutation:
    one of several possible variations in which a set or number of things can be ordered or arranged.

    Permutation is of different types. See StringPermutation.java

    Two types of palindromes:

    - reversed string equal (Palindrome)
    OR
    - string with even number length should have all chars even number of times
    string with odd number of length should have all chars even number of times except a middle char.
*/
public class _3StringPalindrome {
    public static void main(String[] args) {
        System.out.println(isStringPalindrome("abcdcba"));// odd length - O/P: true
        System.out.println(isStringPalindrome_another_way("abcdcba"));// odd length - O/P: true

        System.out.println(isStringPalindrome("abccba"));// even length - O/P: true
        System.out.println(isStringPalindrome_another_way("abccba"));// even length - O/P: true

        System.out.println(isStringPalindrome("abcdcbe"));// odd length - O/P: false
        System.out.println(isStringPalindrome_another_way("abcdcbe"));// odd length - O/P: false

        System.out.println(usingOddEvenNumberOfCharactersStrategy("abcdabc"));// odd length - O/P: true
        System.out.println(usingOddEvenNumberOfCharactersStrategy("abcabc"));// even length - O/P: true
        System.out.println(usingOddEvenNumberOfCharactersStrategy("abcabe"));// even length, 'e' appears odd number of times - O/P: false

    }

    private static boolean isStringPalindrome_another_way(String str) {
        if (str == null || str.length() == 0 || str.length() == 1) return true;

        char[] chars = str.toCharArray();

        if (str.length() == 2 && chars[0] == chars[1]) {
            return true;
        }

        int start = 0;
        int end = chars.length - 1;

        int mid = (start + end) / 2;

        int i = mid;
        int j = mid;// for odd length string

        if (str.length() % 2 == 0) {// for even length string
            j = mid + 1;
        }

        // you can do this recursively also
        while (i >= 0) {
            if (chars[i] == chars[j]) {
                i--;
                j++;
            } else {
                return false;
            }
        }

        return true;
    }


    private static boolean isStringPalindrome(String str) {
        if (str == null || str.length() < 2) {
            return true;
        }
        // compare first half of the string with last half
        // compare str[0] with str[str.length()-1], str[1] with str[str.length()-2] and so on
        // For string with odd length, middle char won't be compared and it is fine.
        for (int i = 0; i < str.length() / 2; i++) { // execution time n/2
            if (str.charAt(i) != str.charAt(str.length() - 1 - i)) {
                return false;
            }
        }
        return true;
    }

    /*
    If string length is even then all chars should appear even number of times.
    If String is odd, then only one char should appear odd number of times, other should appear even number of times.
     */
    private static boolean usingOddEvenNumberOfCharactersStrategy(String str) {
        char[] chars = str.toCharArray();

        int[] charAndTimes = new int[128];

        for (int i = 0; i < chars.length; i++) {
            char c = chars[i];
            charAndTimes[c]++; // important operation. you can do ++ in char array.
        }

        boolean oddFound = false;
        for (int i = 0; i < charAndTimes.length; i++) {
            if (charAndTimes[i] == 0) continue;

            if (charAndTimes[i] % 2 == 1) {
                if (oddFound) {
                    return false;// if more than 1 character with odd number found
                }
                oddFound = true; // only one char should be found with odd number
            }
        }
        if (str.length() % 2 == 0 && oddFound) { // if str is of even length and if only one char with odd number found
            return false;
        }
        return true;


    }

}
