package algorithms._1array_stack_queue.interviewbit.pointers;

import java.util.LinkedList;
import java.util.List;

/*
Find out the maximum sub-array of non negative numbers from an array.
The sub-array should be continuous. That is, a sub-array created by choosing the second and fourth element and skipping the third element is invalid.

Maximum sub-array is defined in terms of the sum of the elements in the sub-array. Sub-array A is greater than sub-array B if sum(A) > sum(B).

Example:

A : [1, 2, 5, -7, 2, 3]
The two sub-arrays are [1, 2, 5] [2, 3].
The answer is [1, 2, 5] as its sum is larger than [2, 3]

NOTE 1: If there is a tie, then compare with segment's length and return segment which has maximum length
NOTE 2: If there is still a tie, then return the segment with minimum starting index

 */
public class MaxSet {

    public static void main(String[] args) {
        MaxSet maxSet = new MaxSet();

        //int[] A = {1, 10, 5, -7, 8, 7, 1, 0, -9, 5, 11};

        //int[] A = null;
        //int[] A = new int[]{0, 0, -1, 0};
        int[] A = {1967513926, 1540383426, -1303455736, -521595368};

        int[] nums = maxSet.maxset(A);

        if (nums != null) {
            for (int num : nums) {
                System.out.print(num + ",");
            }
        }
    }

    public int[] maxset(int[] A) {
        // remember to do this before you do A.length
        if (A == null) return null;
        if (A.length == 0) return A;
        return maxset(A, 0, A.length - 1);
    }

    public int[] maxset(int[] A, int start, int end) {
        if (start > end) return null;


        // creating super list of lists with positive numbers.
        // e.g. [ [1, 10, 5], [8, 7, 1, 0], [5, 11] ]
        List<List<Integer>> lists = new LinkedList<>();

        lists.add(new LinkedList<>());

        for (int i = 0; i < A.length; i++) {
            if (A[i] < 0) {
                lists.add(new LinkedList<>());
            } else {
                lists.get(lists.size() - 1).add(A[i]);
            }
        }

        // do not initialize it with null and put a null check in if condition inside for loop
        List<Integer> listWithMaxSum = new LinkedList<>();
        // you should always think what if sum of two integer numbers can be a long.
        // so do not create 'int maxSum'. Always create 'long maxSum'
        long maxSum = 0;

        for (List<Integer> list : lists) {
            // you should always think what if sum of two integer numbers can be a long.
            // so do not create 'int maxSum'. Always create 'long maxSum'
            long sum = 0;

            for (Integer number : list) {
                sum += number;
            }

            if (sum > maxSum) {
                listWithMaxSum = list;
                maxSum = sum;
            } else if (sum == maxSum) {
                if (list.size() > listWithMaxSum.size()) {
                    listWithMaxSum = list;
                    maxSum = sum;
                }
            }
        }

        if (listWithMaxSum != null) {
            int[] nums = new int[listWithMaxSum.size()];
            int count = 0;
            for (Integer num : listWithMaxSum) {
                nums[count] = num;
                count++;
            }
            return nums;
        }

        return null;
    }

}
