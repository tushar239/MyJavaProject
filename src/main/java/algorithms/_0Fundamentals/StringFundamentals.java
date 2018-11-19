package algorithms._0Fundamentals;

import algorithms.utils.ArrayUtils;

import java.util.Arrays;
import java.util.Comparator;

/*

For any String operation, remember below points

-   String contains char[] and you can use char[] chars = str.toCharArray()

-   str.charAt(i) is very useful

-   Ask interviewer whether you should support ascii/extended ascii/unicode chars.
    ASCII chars are english numbers+letters+special chars = 128. Extended ascii chars (total 256) contains many other special chars.
    If interviewer say ascii is good, then use aux array of size 128 (char[] chars = new char[128])

-   Default value of char[] is Character.MIN_VALUE ('\u0000')

-   Remember this patterns

      char[] result = new char[128]

      for (int i = 0; i < str.length(); i++) {
        char c = str.charAt(i);
        result[c] = c;  // or 65 - 65 means 'A' - every character has some int value attached to it in ASCII
        ...
      }


     int[] result = new int[128]

     for (int i = 0; i < str.length(); i++) {
        char c = str.charAt(i);
        result[c]++; // this will keep the count of characters in a string

     }

-   Ascii values:

    http://www.asciitable.com/

	A to Z = 65-90
              	a to z = 97-122
              	0 to 9 = 48-57

    a-A=32

-   Important methods of Character class:

    Character.isLetter
    Character.isDigit
    Character.isLowerCase
    Character.isUpperCase
    Character.toLowerCase
    Character.toUpperCase
    Character.charAt(index)
    Character.compare(char1, char2)

-   Character.MIN_VALUE ('\u0000')


*/
public class StringFundamentals {

    public static void main(String[] args) {
        String[] strs = new String[] {"a", "abc", "ab", "b"};

        Arrays.sort(strs, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                if(o1.length() ==o2.length()) {
                    return 0;
                }
                if (o1.length() < o2.length()) {// remember that -1 goes with <
                 return -1;
                }
                return 1;
            }
        });

        ArrayUtils.printArray(strs);
    }
}
