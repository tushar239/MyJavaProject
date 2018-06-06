package algorithms._1array_stack_queue.geeksforgeeks.pointers_sorting_stack_queue_string.string_related_algorithms.easy;

/*
    Convert characters of a string to opposite case

    https://www.geeksforgeeks.org/convert-alternate-characters-string-upper-case/

    https://www.youtube.com/watch?v=zOD0lG8E8BU

    Input : geeksForgEeks
    Output : GEEKSfORGeEKS

    Input : hello every one
    Output : HELLO EVERY ONE


    IMPORTANT:
    Ascii values: A to Z = 65-90
                  a to z = 97-122
                  0 to 9 = 48-57

    a-A=32

    Important methods of Character class:
    Character.isLetter
    Character.isDigit
    Character.isLowerCase
    Character.isUpperCase
    Character.toLowerCase
    Character.toUpperCase
    Character.charAt(index)
    Character.compare(char1, char2)

    Character.MIN_VALUE ('\u0000')

 */
public class ConvertCaseOfCharactersInString {

    public static void main(String[] args) {
        String str = "geeks123 $ ForgEeks";
        String newStr = convert(str);
        System.out.println(newStr);// GEEKS123 $ fORGeEKS
    }

    private static String convert(String str) {
        if (str == null || str.length() == 0) return str;

        char[] chars = str.toCharArray();

        for (int i = 0; i < chars.length; i++) {

            // it's a capital letter
            if (chars[i] >= 65 && chars[i] <= 90) {
                chars[i] = (char) (chars[i] + 32);
            } else if (chars[i] >= 90 && chars[i] <= 122) {
                chars[i] = (char) (chars[i] - 32);
            }
        }

        return new String(chars);
    }
}
