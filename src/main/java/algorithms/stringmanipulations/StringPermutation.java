package algorithms.stringmanipulations;

import java.util.Arrays;

// p.g. 193 of Cracking Coding Interview book
/*

    Permutation is of different types.

    Permutation between two strings
        - two sorted strings are equal
        OR
        - two string have same number of characters

    Permutation of one string (Palindrom)

        two types of palindroms
        - reversed string equal (palindrom)
        OR
        - string with even number length should have all chars even number of times
        string with odd number of length should have all chars even number of times except a middle char.
 */
public class StringPermutation {
    public static void main(String[] args) {
        System.out.println(checkTwoSortedStringsAreEqual("bcdae", "dcbea"));// true

        System.out.println(checkTwoStringsHaveSameCharsOfSameQuantity("Tushar Chokshi", "Chokshi Tushar"));// true
        System.out.println(checkTwoStringsHaveSameCharsOfSameQuantity("Tushar Chokshi", "Chokshi Tushar Hi"));// false
    }

    private static boolean checkTwoSortedStringsAreEqual(String s1, String s2) {
        if(s1.length() != s2.length()) {
            return false;
        }
        char[] s1Chars = s1.toCharArray();// space O(n)
        char[] s2Chars = s2.toCharArray();// space O(n)

        Arrays.sort(s1Chars);// uses 3way Quick Sort - O(nlog n)
        Arrays.sort(s2Chars);// uses 3way Quick Sort - O(nlog n)

        String newSortedS1 = new String(s1Chars);
        String newSortedS2 = new String(s2Chars);

        return newSortedS1.equals(newSortedS2); // or newSortedS1.equalsIgnoreCase(newSortedS2)
    }

    // space requirement array of 128+array of 128+other variables
    // execution time = O(n)
    private static boolean checkTwoStringsHaveSameCharsOfSameQuantity(String s1, String s2) {
        if(s1.length() != s2.length()) {
            return false;
        }
        int[] s1Chars = new int[128];// space O(128) // There are total 128 characters in ASCII (including numbers, A-Z, a-z, special characters)
        int[] s2Chars = new int[128];// space O(128)

        for (int i = 0; i < s1.length(); i++) { //O(n)
            char c = s1.charAt(i);
            s1Chars[c]++;
        }
        for (int i = 0; i < s2.length(); i++) {//O(n)
            char c = s2.charAt(i);
            s2Chars[c]++;
        }
        for (int i = 0; i < s1Chars.length; i++) {//O(n)
            if(s1Chars[i] != s2Chars[i]) {
                return false;
            }
        }
        return true;
    }
    private static boolean checkTwoStringsHaveSameCharsOfSameQuantity_AnotherWay(String s1, String s2) {
        // don't forget to think about exit conditions
        if (s2.length() != s2.length()) {
            return false;
        }

        final char[] s1Chars = s1.toCharArray();
        final char[] s2Chars = s2.toCharArray();

        int[] s1Ints = new int[128]; // There are total 128 characters in ASCII (including numbers, A-Z, a-z, special characters)
        for (char c : s1Chars) {
            s1Ints[c] = s1Ints[c] + 1; // s1Ints[g's ascii 103] = 1. Every time, you get g, increment array value by 1
        }

        for (char c : s2Chars) {
            s1Ints[c] = s1Ints[c] - 1;
            if (s1Ints[c] < 0) {
                return false;
            }
        }

        for (int s1Int : s1Ints) {
            if (s1Int != 0) {
                return false;
            }
        }
        return true;
    }
}
