package algorithms._1array_stack_queue.geeksforgeeks.pointers_sorting_stack_queue_string.string_related_algorithms.medium;

/*
    A Program to check if strings are rotations of each other or not

    https://www.geeksforgeeks.org/a-program-to-check-if-strings-are-rotations-of-each-other/

    Given a string s1 and a string s2, write a snippet to say whether s2 is a rotation of s1 using only one call to strstr routine?
    (eg given s1 = ABCD and s2 = CDAB, return true, given s1 = ABCD, and s2 = ACBD , return false)


    Solution:

    str1="ABCD"
    str2="BCDA"

    Brute-Force approach would take O(n^2).
        BCDA

        as ABCD != BCDA, shift A in BCDA that brings your ABCD.

        like that if you need to compare ABCD with DCBA, you need to shift all characters of DCBA n times.

    Better solution:

        (ABCD+ABCD).contains(BCDA)

*/
public class _2CheckIfStringsAreRotationsOfEachOther {

    public static void main(String[] args) {

        System.out.println(areRotations("ABCD", "BCDA"));//true

        System.out.println(areRotations("ABCD", "BACD"));//false

    }

    private static boolean areRotations(String str1, String str2) {
        if(str1 == null && str2 == null) return true;

        if(str1 != null && str2 == null) return false;

        if(str1 == null && str2 != null) return false;

        if(str1.length() == str2.length()) {

            if((str1+str1).contains(str2)) return true; // Important

        }
        return false;
    }
}
