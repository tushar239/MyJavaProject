package algorithms.stringmanipulations;

// p.g. 192 of Cracking Coding Interview book.
public class StringUniqueChars {
    public static void main(String[] args) {
        String str = "hi I am Tushar";// a is repeating
        // It is worth asking an interviewer whether you should support ascii/extended ascii/unicode chars.
        // ASCII chars are english numbers+letters+special chars = 128. Extended ascii chars (total 256) contains many other special chars.
        // Unicode chars contains numbers+letters+special chars from a lot many languages. nilList is very big and still growing.

        // Assuming that str has ascii chars only. www.asciitable.com
        System.out.println(determineUniquenessOfChars(str));

    }

    protected static boolean determineUniquenessOfChars(String str) {
        char[] chars = new char[128];
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
