package algorithms.crackingcodinginterviewbook._1stringmanipulations;

/**
 * @author Tushar Chokshi @ 8/19/15.
 */
public class _4ReplaceSpaceByOtherString {
    public static void main(String[] args) {
        // Here actual String is of length 13="Mr John Smith", but extra 4 spaces at the end are given to fill up accommodate %20 for spaces between words.
        // This algorithm teaches you how to use pointers in an array
        replaceSpaceWithOtherString("Mr John Smith    ", "%20");

        // practice of above
        test2("Mr John Smith    ", "%20");


        // Replace  sub strings with another string
        // This algorithm is harder than "replacing just a space with other string" (above algorithm)
        replaceSubStringWithOtherString("Mr hi oki hello hi  ", "%20", "hi");
    }

    /*
    Replace space by %20

    ---------------------------------------------------------------
    | M | r |  | J | O | H | N |  | S | M | I | T | H |  |  |  |  |
    ---------------------------------------------------------------
                                                    m           k

    length of string with extra spaces to accommodate new characters = 17
    find the length of actual string = 13

    keep moving i'th element to k's position till Space is found.
    When space is found put %20 in kth positions.
     */
    private static void replaceSpaceWithOtherString(String origStr, String strToReplace) {
        // rule of thumb - any string manipulate - either use char[] array or StringBuilder
        final char[] chars = origStr.toCharArray();

        int m = 0;
        for (int i = chars.length - 1; i >= 0; i--) {
            if (chars[i] == ' ') {
                continue;
            }
            m = i;// m will be at 'H'
            break;
        }

        int k = chars.length - 1; // k will be at the end of array

        while (m >= 0) {
            System.out.println(new String(chars));
            if (chars[m] != ' ') {
                chars[k--] = chars[m--];
            } else {
                chars[k--] = '0';
                chars[k--] = '2';
                chars[k--] = '%';
            }
        }
        System.out.println(new String(chars));
    }


    private static void test2(String orig, String replacement) {
        if (orig == null || orig.length() == 0) return;

        char[] origChars = orig.toCharArray();
        char[] replacementChars = replacement.toCharArray();

        int actualOrigLength = origChars.length;

        for (int i = origChars.length - 1; i >= 0; i--) {
            if (origChars[i] == ' ') {
                actualOrigLength--;
            } else {
                break;
            }
        }

        int k = origChars.length - 1;
        for (int i = actualOrigLength - 1; i >= 0; i--) {
            if (origChars[i] != ' ') {
                origChars[k] = origChars[i];
                k--;
                continue;
            }

            for (int j = replacementChars.length - 1; j >= 0; j--) {
                origChars[k] = replacementChars[j];
                k--;
            }
        }

        System.out.println("test2: " + String.valueOf(origChars));
        //System.out.println(String.valueOf(null));
    }

    /*
       Replace 'hi' by %20


       This algorithm is a complex form of above algorithm 'Replace Space by another String'
       This is very tricky because you need to use 3 pointers.

           --------------------------------------------------------------------------------------------
    C[] =  | M | r |  | J | O | H | N |  | S | M | I | T | H |  | H  | I |  | T | U | S | H | A | R |  |
           ---------------------------------------------------------------------------------------------
                                                                                              i   k
                                                                                              m

                                                                                     ---------
                                                                             R[] =   | h | i |
                                                                                     ---------
                                                                                           j

       keep moving i'th element to k's position till i's element matches last char of string that needs to be replace (here 'i' of 'hi').

           --------------------------------------------------------------------------------------------
    C[] =  | M | r |  | J | O | H | N |  | S | M | I | T | H |  | H  | I |  |  | T | U | S | H | A | R |
           ---------------------------------------------------------------------------------------------
                                                                       i   k
                                                                       m


        Compare m with j, then m-- to j-- . Keep doing this until j<0 or C[m] != R[j]

            --------------------------------------------------------------------------------------------
   C[] =   | M | r |  | J | O | H | N |  | S | M | I | T | H |  | H  | I |  |  | T | U | S | H | A | R |
            ---------------------------------------------------------------------------------------------
                                                                       i   k
                                                               m


    if j<0, it means match is found.
    So, put %20 at kth position.
    set i = k


            --------------------------------------------------------------------------------------------
   C[] =   | M | r |  | J | O | H | N |  | S | M | I | T | H |  | %  | 2 | 0 |  | T | U | S | H | A | R |
            ---------------------------------------------------------------------------------------------
                                                               k
                                                               i
                                                               m

    */
    public static void replaceSubStringWithOtherString(String orig, String replacement, String strToReplace) {
        if (orig == null || orig.length() == 0) return;

        char[] origChars = orig.toCharArray();
        char[] replacementChars = replacement.toCharArray();
        char[] strToReplaceChars = strToReplace.toCharArray();

        int actualOrigLength = origChars.length;

        for (int i = origChars.length - 1; i >= 0; i--) {
            if (origChars[i] == ' ') {
                actualOrigLength--;
            } else {
                break;
            }
        }

        int k = origChars.length - 1;

        for (int i = actualOrigLength - 1; i >= 0; i--) {

            int m = i;
            int j = strToReplaceChars.length - 1;


            while (j >= 0 && origChars[m] == strToReplaceChars[j]) {
                m--;
                j--;
            }

            if (j < 0) {
                for (int q = replacementChars.length - 1; q >= 0; q--) {
                    origChars[k] = replacementChars[q];// %20
                    k--;
                }
                i = k;
            } else {
                origChars[k] = origChars[i];
                k--;
            }


        }

        System.out.println("test3: " + String.valueOf(origChars));
    }

}
