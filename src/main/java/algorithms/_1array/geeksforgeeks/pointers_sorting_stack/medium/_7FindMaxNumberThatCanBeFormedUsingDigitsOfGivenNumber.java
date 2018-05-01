package algorithms._1array.geeksforgeeks.pointers_sorting_stack.medium;

/*
    Find maximum number that can be formed using digits of a given number

    https://www.geeksforgeeks.org/find-maximum-number-can-formed-digits-number-reviewed/
    https://www.youtube.com/watch?v=bywFHWV0NS0

    Simple Approach:

    - convert a number into array list
    - reverse sort a list (Collections.sort(list, Collections.reverseOrder())

    This approach takes O(n log n)

    Efficient Approach:

    - Create a Map of numbers's digit and count of that digit

        e.g. 343271
        Map -> {3 -> 2,
                4 -> 1
                2 -> 1
                7 -> 1,
                1 -> 1}

    - Now, find elements from 0 to 9 and put that in a list according to their counts.

        123347
*/
public class _7FindMaxNumberThatCanBeFormedUsingDigitsOfGivenNumber {
}
