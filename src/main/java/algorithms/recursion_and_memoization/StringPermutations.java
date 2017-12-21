package algorithms.recursion_and_memoization;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * @author Tushar Chokshi @ 1/30/16.
 */
// p.g. 358 of Cracking Coding Interview book --- not able to understand this approach
public class StringPermutations {
    public static void main(String[] args) {
        String str = "AABB";

        System.out.println("All Permutations");
        {
            List<String> permutation = permutation(str.toCharArray(), 0, str.length() - 1);
            System.out.println("Contains duplicates: "+permutation);
            System.out.println("Without duplicates (not so good approach): "+new HashSet<>(permutation)); // not so good approach.
        }

        System.out.println();
        System.out.println("All Permutations Without Duplicates - Better Approach");
        // I am not able to understand this ?????????????????
        // http://codereview.stackexchange.com/questions/41618/permutation-of-a-string-eliminating-duplicates
        {
            final List<String> stringPermutations = new ArrayList<String>();
            permuteWithoutDuplicates(str, 0, stringPermutations);
            System.out.println(stringPermutations);
        }

        // Cracking coding interview book also has some another approach, but not able to understand it ???????????


    }


    // Total permutations = n! (n factorialRecursive) where n is a length of string

    // Recursion - reducing problem by 1
    // separate prefix (first element(start) of string) and run recursion on start+1 to end of string.
    // then think how to deleteRootAndMergeItsLeftAndRight prefix with generated output of permutations from start+1 to end
    // you just need to insert prefix element at each place in generated permutations from start+1 to end

    private static List<String> permutation(final char[] str, final int start, int end) {
        if (start == end) {
            List<String> list = new ArrayList<>();
            list.add("" + str[start]);
            return list;
        }
        char prefix = str[start];
        List<String> list = permutation(str, start + 1, end);

        return treatPrefixAndListOfString(prefix, list);

    }

    private static List<String> treatPrefixAndListOfString(char prefix, List<String> strs) {
        List<String> newList = new ArrayList<>();
        for (int i = 0; i < strs.size(); i++) {
            String s = strs.get(i);
            for (int j = 0; j < s.length(); j++) {
                newList.add(s.substring(0, j) + prefix + s.substring(j));
            }
            newList.add(s + prefix);
        }
        return newList;

    }


    private static void permuteWithoutDuplicates(String s, int currIndex, List<String> stringPermutations) {
        if (currIndex == s.length() - 1) {
            stringPermutations.add(s);
            return;
        }
        int prefixIndex = currIndex;
        // prints the string without permuting characters from currIndex onwards.
        permuteWithoutDuplicates(s, prefixIndex + 1, stringPermutations);

        // prints the strings on permuting the characters from currIndex onwards.
        for (int i = prefixIndex + 1; i < s.length(); i++) {
            if (s.charAt(prefixIndex) == s.charAt(i)) continue;
            s = swap(s, prefixIndex, i);
            permuteWithoutDuplicates(s, prefixIndex + 1, stringPermutations);
        }
    }

    private static String swap(String s, int i, int j) {
        char[] ch = s.toCharArray();
        char tmp = ch[i];
        ch[i] = ch[j];
        ch[j] = tmp;
        return new String(ch);
    }


    /*private static List<String> permAntoherWay(String s, int prefixIndex) {
        if (prefixIndex == s.length() - 1) {
            List<String> perms = new ArrayList<>();
            perms.add(s);
            return perms;
        }

        List<String> newList = new ArrayList<>();
        newList.add(s);
        for (int j = prefixIndex + 1; j < s.length(); j++) {
            if (s.charAt(j) == s.charAt(prefixIndex)) continue;
            newList.add(swap(s, j, prefixIndex));
        }

        for (String str : newList) {
            permAntoherWay(str, prefixIndex + 1);
        }
        return newList;
    }

    static void permutateHelperDuplicate(String prefix, String rest, int N) {

        if (N == 0) {
            System.out.println(prefix);
        } else {
            for (int i = 0; i < rest.length(); i++) {


                //test if rest[i] is unique.
                boolean found = false;
                for (int j = 0; j < i; j++) {
                    if (rest.charAt(j) == rest.charAt(i)) //rest[j]==rest[i]
                        found = true;
                }
                if (found)
                    continue;
                String newPrefix = prefix + rest.charAt(i);
                String newRest = rest.substring(0, i) + rest.substring(i + 1, N);
                permutateHelperDuplicate(newPrefix, newRest, newRest.length());
            }
        }
    }*/

}
