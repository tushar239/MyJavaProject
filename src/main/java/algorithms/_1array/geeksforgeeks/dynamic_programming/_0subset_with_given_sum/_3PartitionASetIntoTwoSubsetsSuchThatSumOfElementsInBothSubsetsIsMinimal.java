package algorithms._1array.geeksforgeeks.dynamic_programming._0subset_with_given_sum;

import java.util.HashMap;

/*
Partition a set into two subsets such that the difference of subset sums is minimum
Given a set of integers, the task is to divide it into two sets S1 and S2 such that the absolute difference between their sums is minimum.

If there is a set S with n elements, then if we assume Subset1 has m elements, Subset2 must have n-m elements and the value of abs(sum(Subset1) – sum(Subset2)) should be minimum.

https://www.geeksforgeeks.org/partition-a-set-into-two-subsets-such-that-the-difference-of-subset-sums-is-minimum/

Example:

    Input:  arr[] = {1, 6, 11, 5}
    Output: 1
    Explanation:
    Subset1 = {1, 5, 6}, sum of Subset1 = 12
    Subset2 = {11}, sum of Subset2 = 11


This algorithm is an extension of
    FindIfASubsetWithGivenSumExistsInGivenArray.java
    and
    PartitionASetIntoTwoSubsetsSuchThatSumOfElementsInBothSubsetsAreEqual.java


            max expected sum of one subset
            0   1   2   ....   23/2=11
            -----------------------
   A    0 |
   r    1 |
   r    6 |
   a    11|
   y    5 |                      * ----- min( difference between expectedSum and all prev elements(excluding current element),
                                              difference between expectedSum and sum including this element
                                            )
                                 base conditions condition : if current element == expectedSum, then difference is 0
                                                             if current element is < or >, I still want to find the difference -- so it's not considered in base condition

        When you include current element (5):
            5 + 6 = 11
            so, column 6 and element 11's value will be the difference between 11 and 5.


*/
public class _3PartitionASetIntoTwoSubsetsSuchThatSumOfElementsInBothSubsetsIsMinimal {

    public static void main(String[] args) {
        int arr[] = {1, 6, 7, 5};
        {
            int minDiffBetweenTwoSubSets = findMinDiffBetweenTwoSubSets_Approach1(arr);
            System.out.println(minDiffBetweenTwoSubSets);//1
        }
        {
            int minDiffBetweenTwoSubSets = findMinDiffBetweenTwoSubSets_Approach2(arr);
            System.out.println(minDiffBetweenTwoSubSets);//1
        }
    }

    private static int findMinDiffBetweenTwoSubSets_Approach1(int[] A) {
        int sum = 0;// in interview, use long
        for (int num : A) {
            sum += num;
        }

        int expectedSum = sum / 2;

        boolean canPassedSumBeAchieved = false;
        while (!canPassedSumBeAchieved && expectedSum >= 0) {
            canPassedSumBeAchieved = _1FindIfASubsetWithGivenSumExistsInGivenArray.isSubsetSum_Dynamic_Programming_Top_Down_Approach(A, 0, A.length - 1, expectedSum, new HashMap<>());

            if (!canPassedSumBeAchieved) expectedSum--;
        }

        if (canPassedSumBeAchieved) {
            int sumOfSubset1 = expectedSum;
            int sumOfSubset2 = sum - expectedSum;
            return Math.abs(sumOfSubset1 - sumOfSubset2);
        }

        return -1;
    }


    // Better approach
    // You can improve it using Top-Down Dynamic Programming approach
    private static int findMinDiffBetweenTwoSubSets_Approach2(int[] A) {
        int sum = 0;
        for (int num : A) {
            sum += num;
        }
        int expectedSum = sum / 2; // max expected sum of one of the two subsets
        int sumOfSubset1 = expectedSum - findMinDiff_Approach2(A, 0, A.length - 1, expectedSum);
        int sumOfSubset2 = sum - sumOfSubset1;
        return Math.abs(sumOfSubset1 - sumOfSubset2);

    }
    private static int findMinDiff_Approach2(int[] A, int start, int end, int expectedSum) {
        if (A == null) return expectedSum;

        int element = A[end];

        if (start == end) return expectedSum - element;// start < end check is not necessary because in the algorithm, I am not recursing includingCurrentElement end-2

        if(A[start] == 0) return expectedSum;

        if (expectedSum == 0) return element;

        if (element == expectedSum) {
            return expectedSum - element;// returning difference
        }

        int excludingCurrentElement = findMinDiff_Approach2(A, start, end - 1, expectedSum);
        int includingCurrentElement = findMinDiff_Approach2(A, start, end - 1, expectedSum - element);

        // If expectedSum is closer to the sum that includes current element compared to the sum that doesn't include current element(but includes all prev elements)
        // then you should consider the current element in calculating the
        /*if(Math.abs(includingCurrentElement) <= Math.abs(excludingCurrentElement)) {
            return Math.abs(includingCurrentElement);
        }
        return Math.abs(excludingCurrentElement);*/

        // this is same as above commented code
        return Math.min(Math.abs(includingCurrentElement), Math.abs(excludingCurrentElement));

    }
}
