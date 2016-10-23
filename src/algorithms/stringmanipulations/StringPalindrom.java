package algorithms.stringmanipulations;

/**
 * @author Tushar Chokshi @ 12/30/15.
 */
public class StringPalindrom {
    public static void main(String[] args) {
        System.out.println(isStringPalindrom("abcdcba"));// odd length - O/P: true
        System.out.println(isStringPalindrom("abccba"));// even length - O/P: true

        System.out.println(isStringPalindrom("abcdcbe"));// odd length - O/P: false

        System.out.println(usingOddEvenNumberOfCharactersStrategy("abcdabc"));// odd length - O/P: true
        System.out.println(usingOddEvenNumberOfCharactersStrategy("abcabc"));// even length - O/P: true
        System.out.println(usingOddEvenNumberOfCharactersStrategy("abcabe"));// even length, 'e' appears odd number of times - O/P: false

    }

    private static boolean isStringPalindrom(String str) {
        if(str == null || str.length()<2) {
            return true;
        }
        // compare first half of the string with last half
        // compare str[0] with str[str.length()-1], str[1] with str[str.length()-2] and so on
        // For string with odd length, middle char won't be compared and it is fine.
        for (int i = 0; i<str.length()/2; i++) { // execution time n/2
            if(str.charAt(i) != str.charAt(str.length()-1-i)) {
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

        for (int i = 0; i< chars.length; i++) {
            char c = chars[i];
            charAndTimes[c]++;
        }

        boolean oddFound = false;
        for(int i=0; i<charAndTimes.length; i++) {
            if(charAndTimes[i] == 0) continue;

            if (charAndTimes[i] % 2 == 1) {
                if (oddFound) {
                    return false;// if more than 1 character with odd number found
                }
                oddFound = true; // only one char should be found with odd number
            }
        }
        if (str.length() % 2 == 0 && oddFound) { // if str is of even length and if at least one char with odd number found
            return false;
        }
        return true;


    }

}
