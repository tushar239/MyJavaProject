package algorithms._1array_stack_queue.geeksforgeeks.pointers_sorting_stack_queue_string.string_related_algorithms.medium;

import java.util.LinkedList;
import java.util.List;

/*
    Reverse String according to the number of words

    https://www.geeksforgeeks.org/reverse-string-according-number-words/


*/
public class _3ReverseStringAccordingToTheNumberOfWords {

    public static void main(String[] args) {
        String str = "Ashish Yadav Abhishek Rajput Sunil Pundir";
        char[] chars = str.toCharArray();
        reverseEvenWords(chars, 0, str.length() - 1);
        System.out.println(new String(chars));// Ashish vadaY Abhishek tupjaR Sunil ridnuP
    }

    private static void reverseEvenWords(char[] chars, int start, int end) {
        List<Integer> spaceIndices = new LinkedList<>();

        for (int i = 0; i < chars.length; i++) {
            if (chars[i] == ' ') {
                spaceIndices.add(i);
            }

        }

        if (spaceIndices.size() % 2 != 0) { // there are odd number of spaces and so even number of words
            for (int i = 0; i < spaceIndices.size(); i = i + 2) {

                int startIndexOfEvenPlacedWord = spaceIndices.get(i) + 1;

                int endIndexOfEvenPlacedWord = chars.length - 1;
                if (i + 1 != spaceIndices.size()) {
                    endIndexOfEvenPlacedWord = spaceIndices.get(i + 1) - 1;
                }

                reverse(chars, startIndexOfEvenPlacedWord, endIndexOfEvenPlacedWord);

            }
        }


    }

    private static void reverse(char str[], int start, int end) {

        // Temporary variable to store character
        char temp;
        while (start <= end) {
            // Swapping the first and last character
            temp = str[start];
            str[start] = str[end];
            str[end] = temp;
            start++;
            end--;
        }
    }


}
