package algorithms._1array_stack_queue.geeksforgeeks.dynamic_programming._1sum_and_partitions_problems._1subset_with_given_sum;

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
public class _4PartitionASetIntoTwoSubsetsSuchThatSumOfElementsInBothSubsetsIsMinimal {

    public static void main(String[] args) {
//        int arr[] = {1, 6, 7, 5, 7};
//        int arr[] = {1,6,11,5};
//        int arr[] = {1,6,11,5,2,3,9};
        int arr[] = {1, 6, 11, 5, 7, 76}; // answer : 46
        {
            // from geeksforgeeks site
            int minDiffBetweenTwoSubSets = findMinDiffBetweenTwoSeubSets_GeeksForGeeksWay(arr);
            System.out.println(minDiffBetweenTwoSubSets);
        }
        {
            int minDiffBetweenTwoSubSets = findMinDiffBetweenTwoSubSets_Approach2(arr);
            System.out.println(minDiffBetweenTwoSubSets);
        }
        {
            int minDiffBetweenTwoSubSets = findMinDiffBetweenTwoSubSets_Approach1(arr);
            System.out.println(minDiffBetweenTwoSubSets);
        }
        // not working
        /*{
            int minDiffBetweenTwoSubSets = findMinDiffBetweenTwoSubSets_Approach3(arr);
            System.out.println(minDiffBetweenTwoSubSets);
        }*/

    }

    // Returns minimum possible difference between
    // sums of two subsets
    public static int findMinDiffBetweenTwoSeubSets_GeeksForGeeksWay(int arr[]) {
        // Compute total sum of elements
        int sumTotal = 0;
        for (int i = 0; i < arr.length; i++)
            sumTotal += arr[i];

        // Compute result using recursive function
        return findMinRec(arr, 0, arr.length - 1, 0, sumTotal);
//        return findMinRec(arr, 0, arr.length - 1, sumTotal);
    }

    public static int findMinRec(int arr[], int start, int end,
                                 int sumCalculated,
                                 int sumTotal) {
        // If we have reached last element.
        // Sum of one subset is sumCalculated, sum of other subset is sumTotal-sumCalculated.
        // Return absolute difference of two sums.
        if (start == end)
            return Math.abs((sumTotal - sumCalculated) - sumCalculated);


        int currentElement = arr[end];

        // For every item arr[i], we have two choices
        // (1) We do not include it first set
        // (2) We include it in first set
        // We return minimum of two choices
        return Math.min(
                findMinRec(arr, start, end - 1, sumCalculated + currentElement, sumTotal),
                findMinRec(arr, start, end - 1, sumCalculated, sumTotal));
    }

    public static int findMinRec(int arr[], int start, int end, int sumTotal) {
        // If we have reached last element.
        // Sum of one subset is sumCalculated,
        // sum of other subset is sumTotal-
        // sumCalculated.  Return absolute
        // difference of two sums.
        if (start == end)
//            return Math.abs((sumTotal - sumCalculated) - sumCalculated);
            return sumTotal - arr[end];


        int currentElement = arr[end];

        // For every item arr[i], we have two choices
        // (1) We do not include it first set
        // (2) We include it in first set
        // We return minimum of two choices

        int includingCurrentElement = currentElement + findMinRec(arr, start, end - 1, sumTotal);
        int excludingCurrentElement = findMinRec(arr, start, end - 1, sumTotal - currentElement);

        return Math.min(includingCurrentElement, excludingCurrentElement);
//        return sumTotal - min;
    }


    private static int findMinDiffBetweenTwoSubSets_Approach3(int[] A) {
        int sum = 0;
        for (int num : A) {
            sum += num;
        }
        int expectedSum = sum / 2; // max expected sum of one of the two subsets
        int sumOfSubset1 = expectedSum - findMinDiff_Approach3(A, 0, A.length - 1, expectedSum);
        int sumOfSubset2 = sum - sumOfSubset1;
        return Math.abs(sumOfSubset2 - sumOfSubset1);
    }

    private static int findMinDiff_Approach3(int[] A, int start, int end, int expectedSum) {
        if (A == null) return expectedSum;

        if (start == end) return A[end];

        int element = A[end];

        if (element == expectedSum) return element;

        int excludingCurrentElement = findMinDiff_Approach3(A, start, end - 1, expectedSum);
        int includingCurrentElement = element + findMinDiff_Approach3(A, start, end - 1, expectedSum - element);

        return Math.min(Math.abs(expectedSum - excludingCurrentElement), Math.abs(expectedSum - includingCurrentElement));

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

    // returns difference between expectedSum and  sum of elements for one subset that is closer to expectedSum
    private static int findMinDiff_Approach2(int[] A, int start, int end, int expectedSum) {
        if (A == null) return expectedSum;

        int element = A[end];

        if (start == end)// start < end check is not necessary because in the algorithm, I am not recursing includingCurrentElement end-2
            return expectedSum - element;

//        if (A[start] == 0) return expectedSum;

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

    // This approach basically uses a classic 'Subset Sum' algorithm to find out whether given elements can form so and so sum
    private static int findMinDiffBetweenTwoSubSets_Approach1(int[] A) {
        int sum = 0;// in interview, use long
        for (int num : A) {
            sum += num;
        }

        // one of the two subsets can have max sum = totalSum/2
        int expectedSum = sum / 2;

        // starting from max possible sum for one subset, keep finding what sum can be formed by given elements (expectedSum, expectedSum-1, expectedSum-2, etc...).
        boolean canPassedSumBeAchieved = false;
        while (!canPassedSumBeAchieved && expectedSum >= 0) {
            // Using classic 'Subset Sum' algorithm for expectedSum, expectedSum-1, expectedSum-2 etc.
            // e.g. if expectedSum-2 can be formed by given elements, then you don't go further down to expectedSum-3
            // it means that one of the subset will have sum of its elements=expectedSum-2
            canPassedSumBeAchieved = _2FindIfASubsetWithGivenSumExistsInGivenArray.isSubsetSum_Dynamic_Programming_Top_Down_Approach(A, 0, A.length - 1, expectedSum, new HashMap<>());

            if (!canPassedSumBeAchieved) expectedSum--;
        }

        if (canPassedSumBeAchieved) {
            int sumOfSubset1 = expectedSum;
            int sumOfSubset2 = sum - expectedSum;
            return Math.abs(sumOfSubset1 - sumOfSubset2);
        }

        return -1;
    }

}
