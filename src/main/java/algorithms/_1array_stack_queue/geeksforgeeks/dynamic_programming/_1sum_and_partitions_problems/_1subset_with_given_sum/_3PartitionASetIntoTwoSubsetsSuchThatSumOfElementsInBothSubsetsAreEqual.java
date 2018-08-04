package algorithms._1array_stack_queue.geeksforgeeks.dynamic_programming._1sum_and_partitions_problems._1subset_with_given_sum;

import java.util.HashMap;

/*
Partition problem is to determine whether a given set can be partitioned into two subsets such that
the sum of elements in both subsets is same.

https://www.geeksforgeeks.org/dynamic-programming-set-18-partition-problem/

Examples

    arr[] = {1, 5, 11, 5}
    Output: true
    The array can be partitioned as {1, 5, 5} and {11}

    arr[] = {1, 5, 3}
    Output: false
    The array cannot be partitioned into equal sum sets.

This algorithm is an extension of FindIfASubsetWithGivenSumExistsInGivenArray.java
*/
public class _3PartitionASetIntoTwoSubsetsSuchThatSumOfElementsInBothSubsetsAreEqual {

    public static void main(String[] args) {
        {
            int A[] = {1, 5, 11, 5};
            boolean result = areThereTwoSubsetsSuchThatSumOfTheirElementsAreEqual(A);
            System.out.println(result);
        }

        // FindIfASubsetWithGivenSumExistsInGivenArray.java works only for array with +ve numbers. It is very root_to_leaf_problems_hard to make it work for -ve numbers.
        /*{
            int A[] = {-1, -5, 11, -5};
            boolean result = areThereTwoSubsetsSuchThatSumOfTheirElementsAreEqual(A);
            System.out.println(result);
        }*/
    }
    private static boolean areThereTwoSubsetsSuchThatSumOfTheirElementsAreEqual(int[] A) {
        if (A == null) return false;

        int sum = 0;// in interview, use long
        for (int num : A) {
            sum += num;
        }

        int halfSum = 0; // in interview, use long
        if (sum % 2 == 0) {
            halfSum = sum / 2;

            boolean result = _2FindIfASubsetWithGivenSumExistsInGivenArray.isSubsetSum_Dynamic_Programming_Top_Down_Approach(A, 0, A.length - 1, halfSum, new HashMap<>());
            return result;

        } else {
            System.out.println("Because the sum of all numbers is odd, there cannot be two subsets with same sum");
        }

        return false;

    }
}
