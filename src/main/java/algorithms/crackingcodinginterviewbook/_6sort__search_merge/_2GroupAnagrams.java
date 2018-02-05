package algorithms.crackingcodinginterviewbook._6sort__search_merge;

// p.g. 398 of Cracking Coding Interview book.

/*
abc, bac, cab are anagrams because by changing the positions characters in these strings, they can be made identical to each other

You have an array of words/string, you need to put all anagrams next to each other in a array.

A=[abc,def,bac,efd,xyz,cab]
result should be
A=[abc,bac,cab,def,efd,xyz]

Solution:
convert each element to char[] and sort them using Arrays.sort. Make sorted word a key of a hashmap.

Map<String, List<String>> map = new HashMap<>();

step 1
    sort 'abc', which will be 'abc'
    key='abc', value = List['abc']

step 2
    sort 'def', which will be 'def'
    key='def', value = List['def']

step 3
    sort 'bac', which will be 'abc'
    key='abc', value = List['abc','bac']

step 4
    sort 'cab', which will be 'abc'
    key='abc', value = List['abc','bac','cab']

step 4
    sort 'efd', which will be 'def'
    key='def', value = List['def','efd']
and so on

resulting map
map = 'abc' -> List['abc','bac','cab']
      'def' -> List['def','efd']
      'xyz' -> List['xyz']

put these lists to an array
   A=['abc','bac','cab','def','efd','xyz']

 */

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/*

p.g. 398 of Cracking Coding Interview book.

You have an array of words/string, you need to put all anagrams next to each other in a array.

What is Anagram?
boy, yob, oby are called anagrams of each other.

 */
public class _2GroupAnagrams {

    public static void main(String[] args) {
        String[] words = {"boy", "cat", "atc", "oyb", "yob"};
        Map<String, List<String>> anagrams = groupAnagrams(words);

        List<String> result = new LinkedList<>();
        for (String key : anagrams.keySet()) {
            result.addAll(anagrams.get(key));
        }
        System.out.println(result);// [cat, atc, boy, oyb, yob]
    }

    /*
    Sorting each word in an array.
    Sorted word become a key in a map and actual word is a value
    map = boy -> List[boy, oyb, yob]
          act -> List[cat, atc]
     */
    private static Map<String, List<String>> groupAnagrams(String[] words) {

        Map<String, List<String>> anagramsGroup = new HashMap<>();

        for (String word : words) {
            char[] chars = word.toCharArray();
            Arrays.sort(chars); // 3-way quick sort takes O(n log n) for each word
            String key = new String(chars);

            if (anagramsGroup.containsKey(key)) {
                anagramsGroup.get(key).add(word);
            } else {
                List<String> anagrams = new LinkedList<>();
                anagrams.add(word);

                anagramsGroup.put(key, anagrams);
            }
        }

        return anagramsGroup;
    }
}
