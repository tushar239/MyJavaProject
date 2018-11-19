package algorithms.crackingcodinginterviewbook._1stringmanipulations;

import org.apache.commons.lang.StringUtils;

/*
p.g. 201 of Cracking Coding Interview book

String Compression:
Implement a method to perform basic string compression using the counts of repeated characters.
For example, the string aabcccccaaa would be a2b1c5a3.
If the compressed string would not become smaller than the original string, your method should return the original string.
You can assume the string has only uppercase and lowercase letter (A-Z, a-z).
*/

public class _6StringCompression {
    public static void main(String[] args) {
        //System.out.println(getCompressedString("aabccccaaa"));//a4b4c4a3 --- this is wrong

        System.out.println(compressStringEasiestApproach("aabccccaaa"));//a2b1c4a3

        System.out.println(compressStringEasiestApproach("abcdabcd"));//a1b1c1d1a1b1c1d1
        // if compressed string length is same as original string length, then return original string
        System.out.println(compressStringEasiestApproach("aabbcc"));//aabbcc


//        System.out.println(compressStringEasierApproach("aabccccaaa"));//a4b1c4a3
//        System.out.println(compressStringEasierApproach("abcdabcd"));//a1b1c1d1a1b1c1d1
//        // if compressed string length is same as original string length, then return original string
//        System.out.println(compressStringEasierApproach("aabbcc"));//aabbcc


    }

    // This algorithm creates many string objects, but there is no other alternative. It won't make sense to convert str to char[] in this case.
    private static String getCompressedString(String str) {
        String compressedStr = "";
        for (int i = 0; i < str.length(); i++) {
            char currentChar = str.charAt(i);
            if (i == 0) {
                compressedStr += currentChar + "1";
            } else {
                char prevChar = str.charAt(i - 1);
                // compare current char with prev char. If both are same then increase that char's total match number (last char of compressedStr) by one.
                // otherwise, it is a new char, so append currentChar+"1" with compressedStr
                if (prevChar == currentChar) {
                    compressedStr = compressedStr.replace("" + compressedStr.charAt(compressedStr.length() - 1), String.valueOf(Integer.valueOf("" + compressedStr.charAt(compressedStr.length() - 1)) + 1));
                } else {
                    compressedStr += currentChar + "1";
                }
            }

        }

        // if compressed string length is same as original string length, then return original string
        if (str.length() == compressedStr.length()) {
            return str;
        }
        return compressedStr;
    }

    public static String compressStringEasiestApproach(String origStr) {
        StringBuilder sb = new StringBuilder();

        if (origStr.length() == 0) return sb.toString();

        //for(int i=0; i<origStr.length(); i++) {
        int i = 0;
        while (i <= origStr.length() - 1) {

            int count = 0;
            int j = i;

            while (j <= origStr.length() - 1 && origStr.charAt(i) == origStr.charAt(j)) {
                count++;
                j++;
            }

            sb.append(origStr.charAt(i)).append(count);

            i = j; // when you do this, please make sure that you don't use for loop for i, otherwise it will do i++ also.
        }

        String compressed = sb.toString();

        if (compressed.length() == origStr.length()) {
            return origStr;
        }

        return compressed;
    }

    public static String compressStringEasierApproach(String origStr) {
        if (StringUtils.isBlank(origStr)) return origStr;

        char[] chars = origStr.toCharArray();

        // size complexity will be max O(2n), if every character in origStr is different
        StringBuilder sb = new StringBuilder(); // contains resizable array of characters. so, runtime complexity of resizing an array is Amortized time O(1).
        char prevChar = Character.MIN_VALUE; // same as '' or '/u0000', but '' is not allowed.
        int count = 0;

        for (int i = 0; i < chars.length; i++) { // Runtime Complexity = O(n)
            if (prevChar == Character.MIN_VALUE) {
                sb.append(chars[i]);
                count++;
            } else if (prevChar != chars[i]) {
                sb.append(count);
                count = 1;
                sb.append(chars[i]);
            } else {
                count++;
            }

            prevChar = chars[i];

            if (i == chars.length - 1) {
                sb.append(count);
            }
        }

        String compressedStr = sb.toString();
        if (compressedStr.length() < origStr.length()) {
            return compressedStr;
        }
        return origStr;

    }
}
