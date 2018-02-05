package algorithms.crackingcodinginterviewbook._5recursion_and_dynamic_programming;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/*
Permutations without Dups:
Write a method to compute all permutations of a string. String can have duplicate chars.


This is a bit difficult algorithm with a unique approach. It's difficult to understand the code without understanding recursive tree structure.
*/

public class _8StringPermutationsWithDups {
    public static void main(String[] args) {
        String str = "aabc";
        // I couldn't understand this way
        System.out.println("........from cracking coding interview book........");
        {
            List<String> perms = getPerms(str);
            System.out.print("[");
            for (String perm : perms) {
                System.out.print(perm+",");
            }
            System.out.print("]");
        }
        System.out.println();
        System.out.println();
        // I could understand this way
        System.out.println("........from a video 'String Permutation With Dups Algorithm.mp4'........");
        {
            int level = 0;
            char[] auxArray = new char[str.length()];
            List<String> result = new ArrayList<>();


            Map<Character, Integer> charCount = new HashMap<>();
            char[] chars = str.toCharArray();
            for (char c : chars) {
                if (charCount.containsKey(c)) {
                    charCount.put(c, charCount.get(c) + 1);
                } else {
                    charCount.put(c, 1);
                }
            }


            char[] letters = new char[charCount.keySet().size()];
            int[] count = new int[letters.length];
            int c = 0;
            for (Character ch : charCount.keySet()) {
                letters[c] = ch;
                count[c] = charCount.get(ch);
                c++;
            }

            // I could use a Map<Letter, Count> instead of two separate arrays letters and count.
            // It is a bit harder to work with map api compared to array index. So, separating keys(letters) and values(count of letter) in two separate arrays.
            getPerms(str, level, letters, count, auxArray, result);

            System.out.println("Result: "+ result);
        }

    }

    /*
        Watch 'String Permutation With Dups Algorithm.mp4'.

        Unless you draw a recursion tree first, you won't be able code this algorithm.

aux array
-----------------
| a | a | b | c |   - aabc
-----------------
          c   b     - aacb
          and so on...


                m("aabc", 0, count=[2 1 1],letters=[a b c], char[] auxArray, List<String> result)
                |
-----------------------------------------------------------------------------------------------------------------------------------
|                                                                           |
|                                           --------------------- for loop to find a letter having count > 0, a is found --------------------
|                                           |                                                      |                                        |
I/P:
level=0                  reduce the count [1 1 1]     put letter 'a' in auxArray at level=0     m("aabc", 1, [1,1,1], [a,b,c], [a], []   restore the count back [2,1,1]
letters=[a,b,c]                                       if(level=letter.length-1)                  |                                        (IMPORTANT)
count  =[2,1,1]                                         then add auxArray into result list       |
                                                                                                 |
--------------------------------------------------------------------------------------------------
|                                                                                                |
|                                           --------------------- for loop to find a letter having count > 0, a is found --------------------               ---------------------b is found----------------------------------------------------------------------
|                                           |                                               |                                               |               |                                                                                                   |
level=1                             count=[0,1,1]       auxArray=[a,a]                  m("aabc", 2, [0 1 1], [a b c], [a,a], [])      count=[0,1,1]      count[0,0,1]      auxArray=[a,b,c,b]      m("aabc", 2, [0,0,1], [a,b,c], [a,b,c,b], [aabc,aacb])   count[0,1,1]
letters=[a,b,c]                                                                             |                                                                                                                   |
count  =[1,1,1]                                                                             |                                                                                                                   |
                                                                                            |                                                                                                               .........
---------------------------------------------------------------------------------------------
|                                                                                           |
|                                           --------------------- for loop to find a letter having count > 0, b is found ---------------------              ---------------------c is found------------------------------------------------------------------
|                                           |                                               |                                                |              |                                                                                               |
level=2                                 count=[0,0,1]        auxArray=[a,a,b]           m("aabc", 3, [0 0 1], [a b c], [a,a,b], [])     count=[0,1,1]     count[0,1,0]      auxArray=[a,a,c,c]        m("aabc", 3, [0,1,0], [a,b,c], [a,a,b], [aabc])   count[0,1,1]
letters=[a,b,c]                                                                              |                                                                                                                  |
count  =[0,1,1]                                                                              |                                                                                                                  ----------------------------------------------------------------
                                                                                           1 |      2                                                                                                                                                                          |
----------------------------------------------------------------------------------------------                                                                                                                                     ----------------------------------------------
|                                                                                            |                                                                                                                                     |                                           |
|                                           --------------------- for loop to find a letter having count > 0, c is found--------------------------------------------                                                               |
|                                           |                                                      |                                                 |             |                                                             level=3                --------------------- for loop to find a letter having count > 0, b is found-------------------------------------
level=3                                 count=[0,0,0]       auxArray=[a,a,b,c]                m("aabc", 4, [0 0 0], [a b c], [a,a,b,c], [aabc])  count=[0,0,1]  for loop will end so it will return                              letters=[a,b,c]        |                                                      |                                                        |
letters=[a,b,c]                                             As level=letters.size()-1,                     |                                                                                                                     count  =[0,1,0]     count=[0,0,1]       auxArray=[a,a,c,b]                m("aabc", 4, [0 0 0], [a b c], [a,a,b,c], [aabc,aacb])  count=[0,1,1]
count  =[0,0,1]                                                 it auxArray will be returned to result     |                                                                                                                     auxArray=[a,a,b,c]                      As level=letters.size()-1,
                                                                                                           |                                                                                                                                                                it auxArray will be returned to result
------------------------------------------------------------------------------------------------------------
|                       |
level=4                 for loop won't be able to find any letter with count>0
letters=[a,b,c]         so it will return
count  =[0,0,0]


     */
    private static void getPerms(String str, int level, char[] letters, int[] count, char[] auxArray, List<String> result) {

        if (str == null || str.length() == 0) return;

        if (count == null || count.length == 0) return;

        if (letters == null || letters.length == 0) return;


        for (int i = 0; i < letters.length; i++) {
            if (count[i] > 0) {
                count[i]--;

                auxArray[level] = letters[i];
                System.out.println("level-"+level+"  writing letter-"+letters[i]+ " at index-"+level +" in aux array, state of aux array-"+new String(auxArray));

                getPerms(str, level + 1, letters, count, auxArray, result);

                if (level == letters.length-1) {
                    System.out.println("Aux array will be written to result");
                    System.out.println();
                    result.add(new String(auxArray));
                }
                count[i]++; // very important
            }
        }
    }

    private static String convertIntArrayToString(int[] count) {
        String str = "";
        for (int c : count) {
            str += c;
        }
        return str;
    }

    // From Cracking Coding Interview Book
    private static List<String> getPerms(String s) {
        List<String> result = new ArrayList<>();
        Map<Character, Integer> map = buildFreqTable(s);
        createPerms(map, "", s.length(), result);
        return result;
    }

    private static void createPerms(Map<Character, Integer> map, String prefix, int remaining, List<String> result) {
        if (remaining == 0) {
            result.add(prefix);
            return;
        }
        for (Character c : map.keySet()) {
            int count = map.get(c);
            if (count > 0) {
                map.put(c, count - 1);
                createPerms(map, prefix + c, remaining - 1, result);
                map.put(c, count);
            }
        }
    }

    private static Map<Character, Integer> buildFreqTable(String s) {
        Map<Character, Integer> map = new HashMap<>();
        for (char c : s.toCharArray()) {
            if (map.containsKey(c)) {
                map.put(c, map.get(c) + 1);
            } else {
                map.put(c, 1);
            }
        }
        return map;
    }


}
