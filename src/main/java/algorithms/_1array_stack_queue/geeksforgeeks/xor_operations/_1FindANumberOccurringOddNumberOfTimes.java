package algorithms._1array_stack_queue.geeksforgeeks.xor_operations;

/*
Find the Numbers Occurring Even Number of Times in O(n) without using any extra space:

Example:
I/P = [1, 2, 3, 2, 3, 1, 3]
O/P = 3

https://www.geeksforgeeks.org/find-the-number-occurring-odd-number-of-times/
https://www.youtube.com/watch?v=2Bn5uBnmptU

Solution:
If you XOR a number with itself, it gives 0.
So, if you XOR all numbers in an array, numbers that occur even number of times will cancel each other and the final result will the a number that is occurring odd number of times.

1^2^3^2^3^1^3 = 1^1^2^2^3^3^3 = 3


XORing solution works only when there is only one number that occurs odd number of times.

If you have more than elements occurring odd number of times, then you have no other choice, but you need to use hash table approach
to store
1->2 times
2->2 times
3->3 times
*/
public class _1FindANumberOccurringOddNumberOfTimes {

    public static void main(String[] args) {
        //int A[] = {0, 0, 0, 1, 2, 3, 2, 3, 1, 3, 3};//0
        int A[] = {1, 2, 3, 2, 3, 1, 3};//3
        int oddNumber = findOddNumber(A);
        System.out.println(oddNumber);
    }

    private static int findOddNumber(int[] A) {
        if (A == null || A.length == 0) return Integer.MIN_VALUE;

        int oddNumber = A[0];

        for (int i = 1; i < A.length; i++) {
            oddNumber ^= A[i];
        }
        return oddNumber;
    }
}
