package algorithms._1array_stack_queue.geeksforgeeks.pointers_sorting_stack_queue_string.string_related_algorithms.hard;

import java.util.LinkedList;
import java.util.List;

/*
    Permute a string by changing case

    https://www.geeksforgeeks.org/permute-string-changing-case/

    Input : ab
    Output : AB Ab ab aB

    Input : ABC
    Output : abc Abc aBc ABc abC AbC aBC ABC

    Solution:

    abc

    reducing the problem by one.

    changing first character to LOWER CASE and figuring out permutations with rest of the string
    changing first character to UPPER CASE and figuring out permutations with rest of the string
*/
public class _2PermuteStringByChangingCase {

    public static void main(String[] args) {
        permutation("ABC");
    }

    private static void permutation(String str) {

        int length = str.length();

        String lowerCaseStr = str.toLowerCase();

//        System.out.println(lowerCaseStr);

        List<String> result = new LinkedList<>();
        //for (int i = 0; i < length; i++) {
            result.addAll(permutation(lowerCaseStr.toCharArray(), 0, length - 1));
        //}

        for (String s : result) {
            System.out.println(s);
        }
    }

    private static List<String> permutation(char[] chars, int start, int end) {

        // if there is just one character
        if (start == end) {
            List<String> set = new LinkedList<>();

            char startChar = chars[start];

            chars[start] = Character.toLowerCase(startChar);

            set.add(new String(chars));

            chars[start] = Character.toUpperCase(startChar);

            set.add(new String(chars));

            return set;
        }

        char startChar = chars[start];

        //changing first character to LOWER CASE and figuring out permutations with rest of the string

        chars[start] = Character.toLowerCase(startChar);

        List<String> set1 = permutation(chars, start + 1, end);// index is increased by 1. So, start will reach to end at some point. So, you need an exit condition for start==end.

        // changing first character to UPPER CASE and figuring out permutations with rest of the string
        chars[start] = Character.toUpperCase(startChar);

        List<String> set2 = permutation(chars, start + 1, end);

        List<String> result = new LinkedList<>();

        result.addAll(set1);
        result.addAll(set2);

        return result;

    }
}
