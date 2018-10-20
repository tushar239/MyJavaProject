package algorithms._1array_stack_queue.geeksforgeeks.pointers_sorting_stack_queue_string.string_related_algorithms.medium;

import java.util.HashSet;
import java.util.Set;

/*

Find out all substrings and subsequences of a given string.

https://www.geeksforgeeks.org/subarraysubstring-vs-subsequence-and-programs-to-generate-them/

Memorize these algorithms.


SubSequence/Subset is a set of any elements in an array. There can be total 2^n - 1  Subsets/SubSequences.
SubString/Subarray is a subset of continuous elements in an array. There can be total n(n+1)/2 SubArrays/SubStrings.


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

        testIterations();

        String str = "123456";
//        String str = "1234";

        char[] chars = str.toCharArray();
        int start = 0;
        int end = str.length() - 1;

        System.out.println("...................Substrings....................");
        {
            System.out.println("SubStrings Iteratively.....");
            //printSubStringsIteratively(chars);

            printSubStringsIteratively_better(chars);

            System.out.println("SubStrings Recursively.....");
            Set<String> subStrings = getSubStringsRecursively(chars, start, end);
            System.out.println("Total number of substrings: " + subStrings.size());
            System.out.println(subStrings);
            System.out.println("Time Complexity: " + count);

        }

        System.out.println();

        System.out.println("...................Subsequences...................");
        {
            //System.out.println("SubSequences");
            //printSubSequencesIteratively(str.toCharArray());


            System.out.println("SubSequences Iteratively.....");
            Set<String> subseqs = getSubSequencesIteratively(chars, start, end);
            System.out.println("Total number of subseqs: " + subseqs.size());
            System.out.println(subseqs);
            System.out.println("Time Complexity: " + cnt1);

            Set<String> subArrays = getSubSequencesRecursively_Better(chars, start, end);
            System.out.println("Total number of substrings: " + subArrays.size());
            System.out.println(subArrays);


            System.out.println("SubSequences Recursively.....");
            Set<String> subSeqs = getSubSequencesRecursively(chars, start, end);
            System.out.println("Total number of subseqs: " + subSeqs.size());
            System.out.println(subSeqs);
            System.out.println("Time Complexity: " + cnt);


            //System.out.println("SubSequences Recursively-1");
            //printSubSequencesRecursively_1(str.toCharArray(), 0, str.length() - 1);

            //System.out.println("SubSequences Recursively-2");
            //printSubSequencesRecursively_2(str.toCharArray(), 0, 1, str.length() - 1);
        }
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
    private static void printSubStringsIteratively_better(char[] chars) {

        for (int i = 0; i < chars.length; i++) {

            System.out.println(chars[i]);

            for (int j = i+1; j < chars.length; j++) {

                String s = "";
                for(int k=i; k<=j ; k++) {// IMPORTANT:
                    s+=chars[k];
                }
                System.out.println(s);
            }

        }

    }


    /*

        String str = "1234";

        Reduce the problem by 1.

        char startEle = 1

        Set<String> allSubSeqs = new HashSet<>();
        allSubSeqs.add(startEle); // (1) is your one subseq


        Set<String> subSeqsFromRemaining = findSubSeqs of remaining elements ("234")

        allSubSeqs.addAll(subSeqsFromRemaining);

        for(String subSeq : subSeqsFromRemaining) {
            allSubSeqs.add(firstEle + subSeq);
        }

     */
    private static Set<String> getSubSequencesRecursively_Better(char[] chars, int start, int end) { //1,2,3,4
        if(start == end) {
            Set<String> set = new HashSet<>();
            set.add(chars[start]+"");
            return set;
        }

        char firstEle = chars[start];//1

        Set<String> main = new HashSet<>();

        main.add(firstEle+"");

        Set<String> remaining = getSubSequencesRecursively_Better(chars, start + 1, end);

        main.addAll(remaining);

        for (String s : remaining) {
            main.add(firstEle+s);
        }

        return main;
    }



    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------

    @Deprecated// not sure whether this is correct. See recursive algorithm
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



    /*
           char[] A = "1    2   3   4   5"

           Reducing problem by one:

          start
           1    2   3   4   5

           add A[start] as one substring

          start i
           1    2   3   4   5

           add "12" as a substring

          start     i
           1    2   3   4   5

           add "123" as a substring

          start         i
           1    2   3   4   5

           add "1234" as a substring

          start             i
           1    2   3   4   5

           add "12345" as a substring


          move start to next position


               start
           1    2   3   4   5

           add "2" as a substring

               start i
           1    2    3   4   5

           add "23" as a substring

               start     i
           1    2    3   4   5

           add "234" as a substring

               start         i
           1    2    3   4   5

           add "2345" as a substring


           move start to next position.... and so on



           Set<String> recurse(char[] A, int start, int end) {

                ... exit conditions ...


               char firstEle = 1;

               create a mainSet and add 1 to it.

               for(int i=start+1 (2) to end (5)) {

                    StringBuffer sb = new StringBuffer();

                    for(int j=start(1) to i) { // collect all A[j] in one string buffer. (12, 123, 1234, 12345)
                        sb.add(A[j]);
                    }

                    add stringbuffer to mainSet
               }

               Set<String> subStringsFromRemainingChars = recurse(A, start+1, end);

               mainSet.addAll(subStringsFromRemainingChars);

               return mainSet;
           }


        Time Complexity O(n ^ 3)
     */
    private static int count = 0;

    private static Set<String> getSubStringsRecursively(char[] chars, int start, int end) {
        if (chars == null) return new HashSet<>();

        // how to decide exit conditions?
        // if you see recursion is incrementing start by one only. So, there is a possibility of start==end
        // if start is incrementing by two, then there is a possibility of start > end
        // if there are two recursive calls and both start+1 and start+2 are happening, then there are both possibilities (start==end and start>end)
        if (start == end) {
            count++;
            Set<String> set = new HashSet<>();
            set.add(chars[start] + "");
            return set;
        }

        char firstEle = chars[start];

        Set<String> mainSet = new HashSet<>();
        mainSet.add(firstEle + "");
        count++;

        for (int i = start + 1; i <= end; i++) {

            StringBuffer sb = new StringBuffer();

            // above one for loop and one recursive call takes O(n^2). Below for loop adds additional time.
            // so total time complexity is O(n^3)
            for (int j = start; j <= i; j++) {
                count++;
                sb.append(chars[j]);
            }
            mainSet.add(sb.toString());
        }

        Set<String> setFromRemainingArray = getSubStringsRecursively(chars, start + 1, end);

        mainSet.addAll(setFromRemainingArray);

        return mainSet;
    }




    // working
    /*

        REMEMBER: Total number of possible SubSequences are 2^n - 1.
        REMEMBER: You need 3 for loops for 2^n possibilities.
        Time Complexity O(n * 2^n)


        i   j
        1   2   3   4

        i       j
        1   2   3   4

        i           j
        1   2   3   4

        add 1 to setUpper
        add 1,4 to setUpper
        find all subsequences in between i and j  ({2}, {2,3}, {3}). add them to setUpper and add 1 at beginning and 4 at the end of each of these subseqs (1,2,4    1,2,3,4   1,3,4) and add to to setUpper.


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
    private static int cnt1 = 0;
    private static Set<String> getSubSequencesIteratively(char[] chars, int start, int end) {

        // how to decide exit conditions?
        // if you see recursion is incrementing start by one only. So, there is a possibility of start==end
        // if start is incrementing by two, then there is a possibility of start > end
        // if there are two recursive calls and both start+1 and start+2 are happening, then there are both possibilities (start==end and start>end)

        //if (start > end) return new HashSet<>();
        if(start == end) {
            Set<String> setupper = new HashSet<>();
            setupper.add(""+chars[start]);
            return setupper;
        }

        Set<String> setupper = new HashSet<>();

        for (int i = start; i <= end; i++) {

            setupper.add(chars[i] + "");

            for (int j = i + 1; j <= end; j++) {

                setupper.add(chars[i] + "" + chars[j]);

                Set<String> set = getSubSequencesIteratively(chars, i + 1, j - 1);

                // above 2 for loops and recursive call takes O(2^n). Below for loop adds additional time.
                // so total time complexity is O(n * 2^n)
                for (String s : set) {
                    cnt1++;
                    setupper.add(chars[i] + s + chars[j]);
                }
            }
        }
        return setupper;

    }


    private static void testIterations() {
        int[] A = {1,2,3,4,5};
        testIterations(A, 0, A.length-1);
        System.out.println(cnt3); // should be n(n+1)/2
    }

    private static int cnt3 = 0;
    private static void testIterations(int[] A, int start, int end) {
        if(start > end) return;

        for(int i=start; i<=end; i++) {

            cnt3++;
        }

        testIterations(A, start+1, end);
    }

    /*
       1    2   3   4

       reduce the problem by 1.

       startEle = 1;

       add startEle to mainSubSeqSet.

       find subSeqSet from remaining array (2,3,4) add it to mainSubSeqSet.
       add startEle=1 as prefix to all subSeqSet and add them to mainSubSeqSet.


       Time Complexity O(2^n). It is not O(n * 2^n).

       there are 4 elements and it takes 2^n-1 + 2^n-2 + 2^n-3 +...+2^0 = 8+4+2+1 = 15 = 2^n

       recurse(1234) - 8
       |
       recurse(234) - 4
       |
       recurse(34) - 2
       |
       recurse(4) - 1
       |
       exit cond - 1

     */
    private static int cnt = 0;

    private static Set<String> getSubSequencesRecursively(char[] chars, int start, int end) {
        if (chars.length == 0) return new HashSet<>();

        // how to decide exit conditions?
        // if you see recursion is incrementing start by one only. So, there is a possibility of start==end
        // if start is incrementing by two, then there is a possibility of start > end
        // if there are two recursive calls and both start+1 and start+2 are happening, then there are both possibilities (start==end and start>end)
        if (start == end) {
            cnt++;
            System.out.print(cnt+",");
            Set<String> set = new HashSet<>();
            set.add(chars[start] + "");
            return set;
        }

        char startEle = chars[start];

        Set<String> mainSubSeqSet = new HashSet<>();

        // add startEle to mainSubSeqSet.
        mainSubSeqSet.add("" + startEle);
        cnt++;

        // find subSeqSet from remaining array and add it to mainSubSeqSet
        Set<String> remainingArraySubSeqSet = getSubSequencesRecursively(chars, start + 1, end);
        mainSubSeqSet.addAll(remainingArraySubSeqSet);

        // add startEle as prefix to all subSeqSet and add them to mainSubSeqSet.
        for (String subseq : remainingArraySubSeqSet) {
            cnt++;
            mainSubSeqSet.add(startEle + subseq);
        }

        System.out.print(cnt+",");

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
