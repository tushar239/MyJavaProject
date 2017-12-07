package algorithms.stringmanipulations;

/**
 * @author Tushar Chokshi @ 8/19/15.
 */
public class _4ReplaceSpaceByOtherString {
    public static void main(String[] args) {
        // Here actual String is of length 13="Mr John Smith", but extra 4 spaces at the end are given to fill up accommodate %20 for spaces between words.
        replaceSpaceWithOtherString("Mr John Smith    ", "%20");
    }

    private static void replaceSpaceWithOtherString(String origStr, String strToReplace) {
        // rule of thumb - any string manipulate - either use char[] array or StringBuilder
        final char[] chars = origStr.toCharArray();

        int actualSizeOfString = 0;
        for (int j = chars.length - 1; j >= 0; j--) {
            if (chars[j] == ' ') {
                continue;
            }
            actualSizeOfString = j;//"Mr John Smith"=13
            break;
        }

        int k = chars.length - 1;
        for (int i = actualSizeOfString; i >= 0; i--) {
            System.out.println(new String(chars));
            if(chars[i] != ' ') {
                chars[k--] = chars[i];
            } else {
                chars[k--] = '0';
                chars[k--] = '2';
                chars[k--] = '%';
            }
        }
        System.out.println(new String(chars));
    }
}
