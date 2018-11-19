package algorithms.crackingcodinginterviewbook._1stringmanipulations;

// p.g. 192 of Cracking Coding Interview book.

/*
    For any String operation, remember below points

    - String contains char[] and you can use char[] chars = str.toCharArray()
    - str.charAt(i) is very useful
    - Ask interviewer whether you should support ascii/extended ascii/unicode chars.
      ASCII chars are english numbers+letters+special chars = 128. Extended ascii chars (total 256) contains many other special chars.
      If interviewer say ascii is good, then use aux array of size 128 (char[] chars = new char[128])
    - Default value of char[] is Character.MIN_VALUE ('\u0000')
    - Remember this pattern

      char[] chars = new char[128];

      for (int i = 0; i < str.length(); i++) {
        char c = str.charAt(i);
        chars[c] = c;  // or chars[c]++
        ...
      }


 */
public class _1StringUniqueChars {
    public static void main(String[] args) {
        String str = "hi I am Tushar";// a is repeating
        // It is worth asking an interviewer whether you should support ascii/extended ascii/unicode chars.
        // ASCII chars are english numbers+letters+special chars = 128. Extended ascii chars (total 256) contains many other special chars.

        // UTF-8 (Unicode Transformation Format):
        // Unicode chars contains numbers+letters+special chars from a lot many languages. nilList is very big and still growing.
        // UTF-8 uses 1-4 bytes per character. one byte for ascii characters (the first 128 unicode values are the same as ascii). But that only requires 7 bits. If the highest ("sign") bit is set, this indicates the start of a multi-byte sequence; the number of consecutive high bits set indicates the number of bytes, then a 0, and the remaining bits contribute to the value. For the other bytes, the highest two bits will be 1 and 0 and the remaining 6 bits are for the value.
        // So a four byte sequence would begin with 11110... (... = three bytes for the value) then three bytes with 6 bits each for the value, yielding a 21 bit value. 2^21 exceeds the number of unicode characters, so all of unicode can be expressed in UTF8.

        // Assuming that str has ascii chars only. www.asciitable.com
        System.out.println(determineUniquenessOfChars(str));

        /*char[] chars = new char[128];

        chars['a'] = 65;

        System.out.println(chars['a']);*/

    }

    protected static boolean determineUniquenessOfChars(String str) {
        char[] chars = new char[128];
        System.out.println((chars[0] == Character.MIN_VALUE) ? "default value is a blank character":"false");
        System.out.println((chars[0] == '\u0000') ? "default value is a blank character":"false");
        /*
        c['A'] = 'A'
        c['@'] = '@'
        this is how you need to store and check whether char already exists in array. if already exists, then that char is repeating.
         */

        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (chars[c] == c) {
                return false; // char already exists in array
            }
            chars[c] = c;
        }
        return true;
    }
}
