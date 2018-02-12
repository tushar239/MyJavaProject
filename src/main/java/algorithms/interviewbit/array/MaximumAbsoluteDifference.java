package algorithms.interviewbit.array;

/*
Maximum Absolute Difference
You are given an array of N integers, A1, A2 ,…, AN. Return maximum value of f(i, j) for all 1 ≤ i, j ≤ N.
f(i, j) is defined as |A[i] - A[j]| + |i - j|, where |x| denotes absolute value of x.

For example,

A=[1, 3, -1]

f(1, 1) = f(2, 2) = f(3, 3) = 0
f(1, 2) = f(2, 1) = |1 - 3| + |1 - 2| = 3
f(1, 3) = f(3, 1) = |1 - (-1)| + |1 - 3| = 4
f(2, 3) = f(3, 2) = |3 - (-1)| + |2 - 3| = 5

So, we return 5.


To solve this problem, you need to first learn MaximumDifferenceBetweenTwoElementsOfAnArray.java
*/
public class MaximumAbsoluteDifference {

    public static void main(String[] args) {
        MaximumAbsoluteDifference instance = new MaximumAbsoluteDifference();
        int A[] = {1, 3, -1};
        //int A[] = {2, 3, 10, 6, 4, 8, 1};
        int maxSum = instance.maxAbsoluteDifference(A);
        System.out.println(maxSum);
    }

    private int maxAbsoluteDifference(int A[]) {
        if (A == null || A.length == 0) return 0;

        // keep track of min_element, max_element, diff of them, index1, index2, diff of them
        int min_element_index = 0;
        int max_element_index = 0;

        int min_element = A[min_element_index];
        int max_element = A[max_element_index];

        int max_sum_of_two_diffs = 0;

        for (int i = 1; i < A.length; i++) {
            int element = A[i];// 3, -1

            boolean isMinElementChanged = false;

            if (element < min_element) {
                min_element = element;//1, -1
                min_element_index = i;//0, 2
                isMinElementChanged = true;
            }

            boolean isMaxElementChanged = false;

            if (element > max_element) {
                max_element = element;//3
                max_element_index = i;//1
                isMaxElementChanged = true;
            }

            if (isMinElementChanged || isMaxElementChanged) {
                int absolute_diff_between_elements = Math.abs(min_element - max_element);//1-3=2, -1-3=4
                int absolute_diff_between_indices = Math.abs(min_element_index - max_element_index);//0-1=1, 2-1=1

                int sum = absolute_diff_between_elements + absolute_diff_between_indices;//2+1=3, 4+1=5

                if (sum > max_sum_of_two_diffs) {
                    max_sum_of_two_diffs = sum;//3, 5
                }
            }

        }

        System.out.println("min_element_index: " + min_element_index);
        System.out.println("max_element_index: " + max_element_index);

        System.out.println("min_element: " + min_element);
        System.out.println("max_element: " + max_element);

        return max_sum_of_two_diffs;

    }
}
