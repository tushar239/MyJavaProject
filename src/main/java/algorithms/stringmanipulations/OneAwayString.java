package algorithms.stringmanipulations;

// e.g. p.g. 199 of Cracking Coding Interview book
/*
    Find whether only one char in a String is modified/removed/inserted.
 */
public class OneAwayString {
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

    }

    private static boolean isOneAway(String origStr, String updatedStr) {
        if (origStr.equals(updatedStr)) { // both string are same
            return false;
        }

        if (updatedStr.length() - 1 == origStr.length()) {
            return checkCharInsertion(origStr, updatedStr);

        }
        else if (updatedStr.length() + 1 == origStr.length()) {
            return checkCharRemoval(origStr, updatedStr);

        }
        return checkCharReplacement(origStr, updatedStr);
    }

    private static boolean checkCharInsertion(String origStr, String updatedStr) {
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

        for (int i=0,j=0; i<origStr.length(); i++,j++) {

            if(origStr.charAt(i) == updatedStr.charAt(j)) {
                continue;
            }
            else {
                totalMismatch++;
                if(totalMismatch > 1) {
                    return false;
                }
                i--;
            }
        }

        return true;
    }

    private static boolean checkCharRemoval(String origStr, String updatedStr) {
        int totalMismatch = 0;
        for (int i=0,j=0; i<updatedStr.length(); i++,j++) {
            if(updatedStr.charAt(i) == origStr.charAt(j)) {
                continue;
            } else {
                totalMismatch++;
                if(totalMismatch > 1) {
                    return false;
                }
                i--;
            }
        }

        return true;
    }

    private static boolean checkCharReplacement(String origStr, String updatedStr) {
        int totalMismatch = 0;
        for (int i=0; i<(origStr.length() <= updatedStr.length()?origStr.length():updatedStr.length()); i++) {
            if(origStr.charAt(i) == updatedStr.charAt(i)) {
                continue;
            }
            else {
                totalMismatch++;
                if(totalMismatch > 1) {
                    return false;
                }
            }
        }

        return true;
    }
}
