package algorithms.geeksforgeeks.missing_and_duplicate_elements_algorithms;

/*
Find Two Missing Numbers | Set 2 (XOR based solution)

https://www.geeksforgeeks.org/find-two-missing-numbers-set-2-xor-based-solution/

Given an array of n unique integers where each element in the array is in range [1, n]. The array has all distinct elements and size of array is (n-2). Hence Two numbers from the range are missing from this array. Find the two missing numbers.

Examples:

    Input  : arr[] = {1, 3, 5, 6}, n = 6
    Output : 2 4

    Input : arr[] = {1, 2, 4}, n = 5
    Output : 3 5

    Input : arr[] = {1, 2}, n = 4
    Output : 3 4

I couldn't understand the solution given on
https://www.geeksforgeeks.org/find-two-missing-numbers-set-2-xor-based-solution/

I could understand this one easily
https://www.youtube.com/watch?v=75Jrba2uGFM
"_5Find Two Missing Numbers from an array of 1 to n.mp4"
watch from 12:59 mins

*/
public class _5FindTwoMissingNumbersInArray {

    // https://www.youtube.com/watch?v=75Jrba2uGFM
    //
    private static void findTwoMissingNumbers_Easier_Way(int arr[], int n) {
        int sumOfArray = 0;
        for (int num : arr) {
            sumOfArray += num;
        }

        int sumOfOneToN = n * (n + 1) / 2;

        int diff = sumOfOneToN - sumOfArray; // difference of the sum of 1 to n and array elements

        int divideByTwo = diff / 2;

        // one missing number wii be <= divideByTwo and another one wii be > divideByTwo
        // e.g. arr = {1, 3, 5, 6}, n = 6
        // sumOfOneToN = n(n+1)/2 = 21
        // sumOfArray = 15
        // divideByTwo = (21-15)/2 = 3
        // firstMissingNumber =  1^3  ^  1^2^3 = 2
        // secondMissingNumber = 5^6  ^  4^5^6 = 4

        int firstMissingNumber = 0;
        int secondMissingNumber = 0;
        for (int num : arr) {
            if (num <= divideByTwo) {
                firstMissingNumber = firstMissingNumber ^ num;
            } else {
                secondMissingNumber = secondMissingNumber ^ num;
            }
        }

        for (int i = 1; i <= n; i++) {
            if (i <= divideByTwo) {
                firstMissingNumber = firstMissingNumber ^ i;
            } else {
                secondMissingNumber = secondMissingNumber ^ i;
            }
        }

        System.out.println("First Missing Number: " + firstMissingNumber);
        System.out.println("Second Missing Number: " + secondMissingNumber);
    }

    // https://www.geeksforgeeks.org/find-two-missing-numbers-set-2-xor-based-solution/
    // couldn't understand
    private static void findTwoMissingNumbers(int arr[], int n) {
        /* Get the XOR of all elements in arr[] and
           {1, 2 .. n} */
        int XOR = arr[0];
        for (int i = 1; i < arr.length; i++) {
            XOR ^= arr[i];
        }
        for (int i = 1; i <= n; i++) {
            XOR ^= i;
        }

        //System.out.println("XOR: "+XOR);

        /*int x = XOR & (XOR -1);
        System.out.println("first number: "+x);
        int y = XOR & ~(XOR -1);
        System.out.println("second number: "+y);*/

        // Now XOR has XOR of two missing elements.
        // Any set bit in it must be set in one missing
        // and unset in other missing number

        // Get a set bit of XOR (We get the rightmost set bit)
        int set_bit_no = XOR & ~(XOR - 1);
        //System.out.println("set_bit_no: "+set_bit_no);

        // Now divide elements in two sets by comparing
        // rightmost set bit of XOR with bit at same
        // position in each element.
        int x = 0, y = 0; // Initialize missing numbers
        for (int i = 0; i < arr.length; i++) {
            if ((arr[i] & set_bit_no) > 0)

                //XOR of first set in arr[]
                x = x ^ arr[i];
            else
                //XOR of second set in arr[]
                y = y ^ arr[i];
        }

        for (int i = 1; i <= n; i++) {
            if ((i & set_bit_no) > 0)

                //XOR of first set in arr[] and {1, 2, ...n }
                x = x ^ i;
            else
                //XOR of second set in arr[] and {1, 2, ...n }
                y = y ^ i;
        }

        System.out.println("First Missing Number: " + x);
        System.out.println("Second Missing Number: " + y);
    }

    public static void main(String[] args) {
        //int arr[] = {1, 3, 5, 6};
        int arr[] = {1, 2, 5, 6};

        // Range of numbers is 2 plus size of array
        int n = arr.length + 2;

        System.out.println("Harder way. I couldn't understand this way.....");
        findTwoMissingNumbers(arr, n);

        System.out.println();

        System.out.println("Easier way.....");
        findTwoMissingNumbers_Easier_Way(arr, n);
    }


}
