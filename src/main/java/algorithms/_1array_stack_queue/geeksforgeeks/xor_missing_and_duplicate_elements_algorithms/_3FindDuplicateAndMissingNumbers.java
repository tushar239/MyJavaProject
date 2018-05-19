package algorithms._1array_stack_queue.geeksforgeeks.xor_missing_and_duplicate_elements_algorithms;

/*
Find the repeating and the missing:
Given an unsorted array of size n. Array elements are in range from 1 to n. One number from set {1, 2, …n} is missing and one number occurs twice in array. Find these two numbers.

https://www.geeksforgeeks.org/find-a-repeating-and-a-missing-number/

See FindDuplicatesFromPositiveNumbers.java and FindMissingElementInDuplicateArray.java first.

Solution:
    n=size of an array

    To find a duplicate number (D),
        you can use exactly same technique as FindDuplicatesFromPositiveNumbers.java
        or
        use XORing technique (But you cannot use this technique when there is a missing number)
        duplicate number = (XOR of all elements of an array) XOR (XOR of 1 to n)

    To find missing number,
        Summation of 1 to n numbers is same as n(n+1)/2.
        missing number = (n(n+1)/2) - (sum of all elements of an array - D).

*/
public class _3FindDuplicateAndMissingNumbers {

    public static void main(String[] args) {
        //int A[] = {5, 3, 2, 4, 2, 1};
        int A[] = {5, 3, 2, 4, 2, 1};

        findDuplicateAndMissingNumber(A);
    }

    private static void findDuplicateAndMissingNumber(int A[]) {
        if (A == null || A.length == 0) {
            return;
        }

        int sum = 0;
        int n = A.length;

        // take sum of all elements. It will be used later on to find missing number
        for (int i = 0; i < n; i++) { // O(n)
            int element = A[i];

            sum += element;
        }

        // find duplicate number
        // This logic is same as FindDuplicatesFromPositiveNumbers.java
        for (int i = 0; i < n; i++) { // O(n)
            int element = A[i];

            int index = element % n;
            A[index] += n;
        }

        int duplicateElement = -1;

        for (int i = 0; i < n; i++) { // O(n)
            int element = A[i];
            if ((element / n) > 1) {
                duplicateElement = i;// IMPORTANT: index number is represents duplicate element
                System.out.println("Duplicate Element: " + duplicateElement);//2
            }
        }


        if (duplicateElement == -1) {
            duplicateElement = 0;
        }

        // Find missing number
        // This logic is same as FindMissingNumberInArray.java
        int missingElement = (((n * (n + 1)) / 2)) - (sum - duplicateElement);
        if (missingElement != 0) {
            System.out.println("Missing Element: " + missingElement);//6
        }
    }

}
