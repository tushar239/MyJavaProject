package algorithms.geeksforgeeks;

/*
Find the max diff between any two elements of an array.
Condition is larger element should appear after smaller.

https://www.youtube.com/watch?v=SO0bwMziLlU


e.g. [2,3,10,6,4,8,1]
difference between 2 and 10 will be max. That will be 8.


There is a Brute-Force approach that takes O(n^2).
    You can compare every single element every other and find the max difference by keeping in mind that smaller element appears before bigger one

There is a trickier and better approach that takes O(n).
    Keep track of min_element and find the difference of that with other elements and keep track of max diff.

 */
public class MaximumDifferenceBetweenTwoElementsOfAnArray {

    public static void main(String[] args) {
        int A[] = {2, 3, 10, 6, 4, 8, 1};
        int maxDiff = findMaxDiff(A);
        System.out.println("Answer: " + maxDiff);//8
    }

    private static int findMaxDiff(int A[]) {
        if (A == null || A.length == 0) return 0;

        int maxDiff = 0;
        int min_element = A[0];
        for (int i = 1; i < A.length; i++) {
            if (A[i] < min_element) {
                min_element = A[i];
            } else {
                int diff = A[i] - min_element;
                if (diff > maxDiff) {
                    maxDiff = diff;
                    System.out.println("track of elements: " + min_element + " and " + A[i]);
                }
            }
        }

        return maxDiff;
    }
}
