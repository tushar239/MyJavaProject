package algorithms._1array_stack_queue.geeksforgeeks.pointers_sorting_stack_queue_string.string_related_algorithms.medium;

import java.util.HashSet;
import java.util.Set;

/*

Find out all substrings and subsequences of a given string.

https://www.geeksforgeeks.org/subarraysubstring-vs-subsequence-and-programs-to-generate-them/

Memorize these algorithms.


Subarray/Substring

    A subbarray is a contiguous part of array. An array that is inside another array.
    For example, consider the array [1, 2, 3, 4], There are 10 non-empty sub-arrays.
    The subbarays are (1), (2), (3), (4), (1,2), (2,3), (3,4), (1,2,3), (2,3,4) and (1,2,3,4).
    In general, for an array/string of size n, there are n*(n+1)/2 subarrays/subsrings are possible.

    1
    12
    123
    1234

    2
    23
    234

    3
    34

    4


Subsequence

    A subsequence is a sequence that can be derived from another sequence by zero or more elements, without changing the order of the remaining elements.
    For the same example, there are 15 sub-sequences.
    They are (1), (2), (3), (4), (1,2), (1,3),(1,4), (2,3), (2,4), (3,4), (1,2,3), (1,2,4), (1,3,4), (2,3,4), (1,2,3,4).
    More generally, we can say that for a sequence of size n, we can have (2^n - 1) sub-sequences in total.

    1
    12,13,14
    123
    1234

    2
    23,24
    234

    3
    34

    4
*/
public class _1PrintAllSubSequencesAndSubArrays {

    public static void main(String[] args) {
        String str = "12345";
        System.out.println("SubStrings");
        printSubStringsIteratively(str.toCharArray());

        //System.out.println("SubSequences");
        //printSubSequencesIteratively(str.toCharArray());


        System.out.println("SubSequences Iteratively.....");
        Set<String> subseqs = getSubSequences(str.toCharArray(), 0, str.length() - 1);
        System.out.println(subseqs);

        System.out.println("SubSequences Recursively.....");
        Set<String> subSeqs = getSubSequencesRecursively(str.toCharArray(), 0, str.length() - 1);
        System.out.println(subSeqs);


        //System.out.println("SubSequences Recursively-1");
        //printSubSequencesRecursively_1(str.toCharArray(), 0, str.length() - 1);

        //System.out.println("SubSequences Recursively-2");
        //printSubSequencesRecursively_2(str.toCharArray(), 0, 1, str.length() - 1);

    }

    /*

        REMEMBER: Total number of possible SubStrings/SubArrays are n(n+1)/2.
        REMEMBER: You need 2 for loops for n(n+1)/2 possibilities.

        for(int i=0; i<chars.length; i++)
            for(int j=i+1; j<chars.length; j++)


        String s = "ABCD"

        O/P:
            A
            AB
            ABC
            ABCD

            B
            BC
            BCD

            C
            CD

            D
    */
    private static void printSubStringsIteratively(char[] chars) {

        for (int i = 0; i < chars.length; i++) {

            System.out.println(chars[i]);
            String s = "" + chars[i];

            for (int j = i + 1; j < chars.length; j++) {
                s = s + chars[j];
                System.out.println(s);
            }
        }

    }


    // working
    /*

        REMEMBER: Total number of possible SubSequences are 2^n - 1.
        REMEMBER: You need 3 for loops for 2^n possibilities.


        i   j
        1   2   3   4

        i       j
        1   2   3   4

        i           j
        1   2   3   4

        add 1 to setUpper
        add 1,4 to setUpper
        find all subsequences in between i and j  (2    2,3     3). add them to setUpper and add 1 at beginning and 4 at the end of each of these subseqs (1,2,4    1,2,3,4   1,3,4) and add to to setUpper.


        find subseqs(char[] chars, int start, int end) {

            Set<String> setUpper = new HashSet<>();

            for (int i = start; i <= end; i++) {

                add A[i] to 'setUpper'

                for (int j = i + 1; j <= end; j++) {

                    add A[i]+","+A[j] to setUpper

                    Set<String> set = getSubSequences(chars, i + 1, j - 1);

                    add 'set' to 'setUpper'
                    add A[i] as prefix and A[j] as suffix to all items in 'set' and add them to 'setUpper'
                }
            }

        }

     */
    private static Set<String> getSubSequences(char[] chars, int start, int end) {
        if (start > end) return new HashSet<>();

        Set<String> setupper = new HashSet<>();

        for (int i = start; i <= end; i++) {

            setupper.add(chars[i] + "");

            for (int j = i + 1; j <= end; j++) {

                setupper.add(chars[i] + "" + chars[j]);

                Set<String> set = getSubSequences(chars, i + 1, j - 1);

                for (String s : set) {
                    setupper.add(chars[i] + s + chars[j]);
                }
            }
        }
        return setupper;

    }


    /*
       1    2   3   4

       reduce the problem by 1.

       startEle = 1;

       add startEle to mainSubSeqSet.

       find subSeqSet from remaining array (2,3,4) add it to mainSubSeqSet.
       add startEle=1 as prefix to all subSeqSet and add them to mainSubSeqSet.

     */
    private static Set<String> getSubSequencesRecursively(char[] chars, int start, int end) {
        if (chars.length == 0) return new HashSet<>();

        if (start == end) {
            Set<String> set = new HashSet<>();
            set.add(chars[start] + "");
            return set;
        }

        char startEle = chars[start];

        Set<String> mainSubSeqSet = new HashSet<>();

        // add startEle to mainSubSeqSet.
        mainSubSeqSet.add("" + startEle);

        // find subSeqSet from remaining array and add it to mainSubSeqSet
        Set<String> remainingArraySubSeqSet = getSubSequencesRecursively(chars, start + 1, end);
        mainSubSeqSet.addAll(remainingArraySubSeqSet);

        // add startEle as prefix to all subSeqSet and add them to mainSubSeqSet.
        for (String subseq : remainingArraySubSeqSet) {
            mainSubSeqSet.add(startEle + subseq);
        }

        return mainSubSeqSet;
    }

    /*

     REMEMBER: Total number of possible SubSequences are 2^n - 1.
     REMEMBER: You need 3 for loops for 2^n possibilities.

               for(int i=0; i<chars.length; i++)
                 print chars[i]

                 for(int j=i+1; j<chars.length; j++)
                     print chars[i]+""+chars[j]

                     for(int k=i; k<=j; k++)
                         print concatenated string of all chars[k] ---- this will print some duplicates. e.g. for str=AB, it will print "AB" twice because first "AB" is printed by second for loop already and second one will be printed by third for loop.




    String str = "ABCD"

    O/P:
     A
     AB
     AC
     ABC
     AD
     ABCD

     B
     BC
     BD
     BCD

     C
     CD

     D


     You can use this method for to use iterative approach for LongestIncreasingSubSequenceInArray.java (LIS)
 */
    @Deprecated // doesn't give all results
    private static void printSubSequencesIteratively(char[] chars) {

        for (int i = 0; i < chars.length; i++) {

            System.out.println(chars[i]);

            for (int j = i + 1; j < chars.length; j++) {

                System.out.println(chars[i] + "" + chars[j]);

                String s = "";
                for (int k = i + 1; k < j; k++) {// we are not using for(int k=i; k<=j; k++) to avoid duplicates.
                    s = s + chars[k];
                }

                if (!s.isEmpty()) {
                    System.out.println(chars[i] + s + chars[j]);
                }
            }
        }

    }

    @Deprecated // doesn't give right result
    private static void printSubSequencesRecursively_1(char[] chars, int i, int end) {

        if (i == end) {
            System.out.println(chars[i]);
            return;
        } else {

        /*
            for (int i = 0; i < chars.length; i++)

            To convert it into recursive method,
                0 and chars.length becomes method parameters
                ++i becomes recursive method's parameters
        */

            System.out.println(chars[i]);

            for (int j = i + 1; j <= end; j++) {

                System.out.println(chars[i] + "" + chars[j]);

                String s = "";
                for (int k = i + 1; k < j; k++) {// we are not using for(int k=i; k<=j; k++) to avoid duplicates.
                    s = s + chars[k];
                }

                if (!s.isEmpty()) {
                    System.out.println(chars[i] + s + chars[j]);
                }
            }

            printSubSequencesRecursively_1(chars, ++i, end);// first for loop is replaced by this recursion
        }
    }

    @Deprecated// couldn't make it work
    private static void printSubSequencesRecursively_2(char[] chars, int i, int j, int end) {


        if (i == end) {
            System.out.println(chars[i]);
        } else if (j == end) {
            System.out.println(chars[i] + "" + chars[j]);
        } else {

            System.out.println(chars[i]);

            System.out.println(chars[i] + "" + chars[j]);

            String s = "";
            for (int k = i + 1; k < j; k++) {// we are not using for(int k=i; k<=j; k++) to avoid duplicates.
                s = s + chars[k];
            }

            if (!s.isEmpty()) {
                System.out.println(chars[i] + s + chars[j]);
            }


            printSubSequencesRecursively_2(chars, i, ++j, end); // second for loop is replaced by this recursion

            printSubSequencesRecursively_2(chars, ++i, i + 1, end);// first for loop is replaced by this recursion

        }
    }


}
