package algorithms._1array_stack_queue.interviewbit.pointers;

/*
Maximum Absolute Difference:
You are given an array of N integers, A1, A2 ,…, AN. Return maximum value of f(i, j) for all 1 ≤ i, j ≤ N.
f(i, j) is defined as |A[i] - A[j]| + |i - j|, where |x| denotes absolute value of x.

For example,

A=[1, 3, -1]

f(1, 1) = f(2, 2) = f(3, 3) = 0
f(1, 2) = f(2, 1) = |1 - 3| + |1 - 2| = 3
f(1, 3) = f(3, 1) = |1 - (-1)| + |1 - 3| = 4
f(2, 3) = f(3, 2) = |3 - (-1)| + |2 - 3| = 5

So, we return 5.

Brute-Force approach is easier, but it takes O(n^2).
Efficient approach is trickier and it takes O(n)

It's a very tricky problem. You have to memorize the solution.
I read the solution from https://www.geeksforgeeks.org/maximum-absolute-difference-value-index-sums/
*/
public class MaximumAbsoluteDifference {

    public static void main(String[] args) {
        MaximumAbsoluteDifference instance = new MaximumAbsoluteDifference();
        //int A[] = {1, 3, -1}; // 5
        //int A[] = {2, 3, 10, 6, 4, 8, 1};//13
        //int A[] = { 89, -83, 38, 58, 79, -80, 8, 19, 31, -95 };//193
        //int A[] = {2, 2, 2};//  wrong solution gives (expected value is 2)
        int A[] = {55, -8, 43, 52, 8, 59, -91, -79, -18, -94};// wrong solution gives 157 (expected result=158)
        int maxSum = instance.maxAbsoluteDifferenceWrongSolution(A);
        System.out.println(maxSum);
        maxSum = instance.maxAbsoluteDifferenceRightSolution(A);
        System.out.println(maxSum);
    }

    /*
    I thought that I can apply the same logic as MaximumDifferenceBetweenTwoElementsOfAnArray.java here
    But that won't work because absolute_diff_between_indices(i-j) does not necessarily give max value when absolute_diff_between_elements(A[i]-A[j]) gives max value.
    */
    private int maxAbsoluteDifferenceWrongSolution(int A[]) {
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

    /*
    https://www.geeksforgeeks.org/maximum-absolute-difference-value-index-sums/

    f(i, j) = |A[i] – A[j]| + |i – j| can be written in 4 ways (Since we are looking at max value, we don’t even care if the value becomes negative as long as we are also covering the max value in some way).

    Case 1:
        A[i] > A[j] and i > j
        |A[i] - A[j]| = A[i] - A[j]
        |i -j| = i - j

        hence, f(i, j) = (A[i] + i) - (A[j] + j)

    Case 2:
        A[i] < A[j] and i < j
        |A[i] - A[j]| = -(A[i]) + A[j]
        |i -j| = -(i) + j

        hence, f(i, j) = -(A[i] + i) + (A[j] + j)  = (A[j] + j) - (A[i] + i)

    Case 3:
        A[i] > A[j] and i < j
        |A[i] - A[j]| = A[i] - A[j]
        |i -j| = -(i) + j

        hence, f(i, j) = (A[i] - i) - (A[j] - j)

    Case 4:
        A[i] < A[j] and i > j
        |A[i] - A[j]| = -(A[i]) + A[j]
        |i -j| = i - j

        hence, f(i, j) = -(A[i] - i) + (A[j] - j) = (A[j] - j) - (A[i] - i)


    Note that case 1 and 2 are equivalent and so are case 3 and 4
    and hence we can design our algorithm only for two cases as it will cover all the possible cases.

    So, now we have two cases

    f(i, j) = (A[i] + i) - (A[j] + j)
    f(i, j) = (A[i] - i) - (A[j] - j)

    i and j are just indices in an array.
    Basically, it means that (A[j] + j) is same as min value of (A[i] + i)
    similarly, (A[j] - j) is same as min value of (A[i] - i)

    So, keep track of min and max values of (A[i] + i) and (A[i] - i)
    and get the values of above two cases
    and find max of above two cases' results.

     */
    private int maxAbsoluteDifferenceRightSolution(int A[]) {
        if (A == null || A.length == 0) return 0;

        int ans = 0;

        int max1 = Integer.MIN_VALUE, max2 = Integer.MIN_VALUE;
        int min1 = Integer.MAX_VALUE, min2 = Integer.MAX_VALUE;

        for (int i = 0; i < A.length; i++) {
            max1 = Math.max(max1, A[i] + i);
            min1 = Math.min(min1, A[i] + i);

            max2 = Math.max(max2, A[i] - i);
            min2 = Math.min(min2, A[i] - i);
        }
        int case1Result = max1 - min1;
        int case3Result = max2 - min2;

        ans = Math.max(ans, case1Result);
        ans = Math.max(ans, case3Result);

        return ans;

    }
}
