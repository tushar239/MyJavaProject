package algorithms.crackingcodinginterviewbook._1stringmanipulations;

import org.apache.commons.lang.StringUtils;

// e.g. p.g. 199 of Cracking Coding Interview book
/*
    Find whether only one char in a String is modified/removed/inserted.
 */
public class _5OneAwayString {
    public static void main(String[] args) {
        String origString = "abc";
        String replacedCharInString = "abe"; // 'c' is replaced with 'e'
        String insertedCharInString = "abdc"; // 'd' is added
        String removedCharFromString = "ac";
        String replacedAndInsertedCharFromString = "xdce"; // 'b' is removed, 'a' is replaced by 'x' and 'e' is added

        System.out.println(isOneAway(origString, replacedCharInString)); // true
        System.out.println(isOneAway(origString, insertedCharInString)); // true
        System.out.println(isOneAway(origString, removedCharFromString)); // true
        System.out.println(isOneAway(origString, replacedAndInsertedCharFromString)); // false

        // easier code
        System.out.println("testing with easier code.......");
        System.out.println(isOneAway_Easier_Way(origString, replacedCharInString)); // true
        System.out.println(isOneAway_Easier_Way(origString, insertedCharInString)); // true
        System.out.println(isOneAway_Easier_Way(origString, removedCharFromString)); // true
        System.out.println(isOneAway_Easier_Way(origString, replacedAndInsertedCharFromString)); // false


    }

    private static boolean isOneAway(String origStr, String updatedStr) {
        if (origStr.equals(updatedStr)) { // both string are same
            return false;
        }

        if (updatedStr.length() - 1 == origStr.length()) {
            return checkCharInsertion(origStr, updatedStr);

        } else if (updatedStr.length() + 1 == origStr.length()) {
            return checkCharRemoval(origStr, updatedStr);

        }
        return checkCharReplacement(origStr, updatedStr);
    }

    private static boolean checkCharInsertion(String s1, String s2) {
        /*
        origString = "abc"
        updatedString = "abdc"

        i=0,j=0 then a==a
        i=1,j=1 then b==b
        i=2,j=2 then c!=d, so i=i-1 and totalMismatch++;
        i=2;j=3 then c==c

        There is only one mismatch, so it is good
         */

        int totalMismatch = 0;

        int i = 0, j =0;
        while(i <= s1.length()-1 && j <= s2.length()-1) {
            if (s1.charAt(i) == s2.charAt(j)) {
                i++; j++;
            } else {
                if (totalMismatch > 0) {
                    return false;
                }
                totalMismatch++;
                j++;
            }
        }
       /* for (int i = 0, j = 0; i < s1.length(); i++, j++) {

            if (s1.charAt(i) == s2.charAt(j)) {
                continue;
            } else {
                totalMismatch++;
                if (totalMismatch > 1) {
                    return false;
                }
                i--;
            }
        }*/

        return true;
    }

    private static boolean checkCharRemoval(String origStr, String updatedStr) {
        int totalMismatch = 0;
        for (int i = 0, j = 0; i < updatedStr.length(); i++, j++) {
            if (updatedStr.charAt(i) == origStr.charAt(j)) {
                continue;
            } else {
                totalMismatch++;
                if (totalMismatch > 1) {
                    return false;
                }
                i--;
            }
        }

        return true;
    }

    private static boolean checkCharReplacement(String origStr, String updatedStr) {
        int totalMismatch = 0;
        for (int i = 0; i < (origStr.length() <= updatedStr.length() ? origStr.length() : updatedStr.length()); i++) {
            if (origStr.charAt(i) == updatedStr.charAt(i)) {
                continue;
            } else {
                totalMismatch++;
                if (totalMismatch > 1) {
                    return false;
                }
            }
        }

        return true;
    }

    private static boolean isOneAway_Easier_Way(String s1, String s2) {
        if (StringUtils.isBlank(s1) && StringUtils.isBlank(s2)) return true;

        char[] s1Chars = s1.toCharArray();
        char[] s2Chars = s2.toCharArray();

        if (Math.abs(s2Chars.length - s1Chars.length) > 1) return false;

        int i = 0;
        int j = 0;
        int diff = 0;

        while (i < s1Chars.length && j < s2Chars.length) {

            if (s1Chars[i] == s2Chars[j]) {
                i++;
                j++;
            } else {
                diff++;
                if (diff > 1) return false;

                if (s1Chars.length > s2Chars.length) {i++;}
                else if (s2Chars.length > s1Chars.length) {j++;}
                else { i++; j++; }
            }

        }

        if (diff > 1) return false;
        return true;
    }
}
