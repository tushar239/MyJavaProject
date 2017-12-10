package algorithms.stringmanipulations;

import org.apache.commons.lang.StringUtils;

// p.g. 201 of Cracking Coding Interview book
public class _6StringCompression {
    public static void main(String[] args) {
        System.out.println(getCompressedString("aabccccaaa"));//a4b4c4a3 --- thi is wrong
        System.out.println(getCompressedString("abcdabcd"));//a1b1c1d1a1b1c1d1
        // if compressed string length is same as original string length, then return original string
        System.out.println(getCompressedString("aabbcc"));//aabbcc


        System.out.println(compressStringEasierApproach("aabccccaaa"));//a4b1c4a3
        System.out.println(compressStringEasierApproach("abcdabcd"));//a1b1c1d1a1b1c1d1
        // if compressed string length is same as original string length, then return original string
        System.out.println(compressStringEasierApproach("aabbcc"));//aabbcc

    }

    // This algorithm creates many string objects, but there is no other alternative. It won't make sense to convert str to char[] in this case.
    private static String getCompressedString(String str) {
        String compressedStr = "";
        for (int i = 0; i < str.length(); i++) {
            char currentChar = str.charAt(i);
            if(i==0) {
                compressedStr+=currentChar+"1";
            } else {
                char prevChar = str.charAt(i-1);
                // compare current char with prev char. If both are same then increase that char's total match number (last char of compressedStr) by one.
                // otherwise, it is a new char, so append currentChar+"1" with compressedStr
                if(prevChar == currentChar) {
                    compressedStr = compressedStr.replace(""+compressedStr.charAt(compressedStr.length() - 1), String.valueOf(Integer.valueOf(""+compressedStr.charAt(compressedStr.length() - 1))+1));
                } else {
                    compressedStr+=currentChar+"1";
                }
            }

        }

        // if compressed string length is same as original string length, then return original string
        if(str.length() == compressedStr.length()) {
            return str;
        }
        return compressedStr;
    }

    public static String compressStringEasierApproach(String origStr) {
        if(StringUtils.isBlank(origStr)) return origStr;

        char[] chars = origStr.toCharArray();

        // size complexity will be max O(2n), if every character in origStr is different
        StringBuilder sb = new StringBuilder(); // contains resizable array of characters. so, runtime complexity of resizing an array is Amortized time O(1).
        char prevChar = Character.MIN_VALUE; // same as '' or '/u0000', but '' is not allowed.
        int count = 0;

        for (int i = 0; i < chars.length; i++) { // Runtime Complexity = O(n)
            if(prevChar == Character.MIN_VALUE) {
                sb.append(chars[i]);
                count++;
            } else if(prevChar != chars[i]) {
                sb.append(count);
                count=1;
                sb.append(chars[i]);
            } else {
                count++;
            }

            prevChar = chars[i];

            if(i == chars.length - 1) {
                sb.append(count);
            }
        }

        String compressedStr = sb.toString();
        if(compressedStr.length() < origStr.length()) {
            return compressedStr;
        }
        return origStr;

    }
}
