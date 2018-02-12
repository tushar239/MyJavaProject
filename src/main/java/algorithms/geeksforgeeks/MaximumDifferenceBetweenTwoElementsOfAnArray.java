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

        // keep track of min_element and maxDiff
        int min_element = A[0];
        int maxDiff = 0;

        /* this one also works
        for (int i = 1; i < A.length; i++) {
            if (A[i] < min_element) {
                min_element = A[i];
            } //else {

                int diff = A[i] - min_element;

                if (diff > maxDiff) {
                    maxDiff = diff;
                    System.out.println("track of elements: " + min_element + " and " + A[i]);
                }
            }
        //}*/

        int max_element = A[0];
        for (int i = 1; i < A.length; i++) {
            int element = A[i];

            boolean isMinElementChanged = false;

            if (element < min_element) {
                min_element = element;
                isMinElementChanged = true;
            }

            boolean isMaxElementChanged = false;

            // if min_element changes, then it means that max_element might have been set before this new min_element. As per the requirement, we cannot have max_element before min_element.
            // so we have to reset max_element to min_element.
            // This requirement is not there for MaximumAbsoluteDifference.java
            if(isMinElementChanged) {
                max_element = min_element;
                isMaxElementChanged = true;
            } else {
                if (element > max_element) {
                    max_element = element;
                    isMaxElementChanged = true;
                }
            }

            // if min_element and/or max_element is changed, then only it makes sense to find next maxDiff
            if (isMinElementChanged || isMaxElementChanged) {
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
