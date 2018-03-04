package algorithms._1array;

/*
Tricky:
    PlusOne.java

Sorting:
    FindKthLargestElement.java ----- based on value of k, you decide whether to use aux array approach or quick sort approach.

Binary Search:
    FindAPairWithGivenDifference.java

More than one pointers:

    Very Very .... Important concepts.

    - MinimumSwapsRequiredToBringAllElementsLessThanOrEqualToKTogether.java
        This algorithm teaches you how to reduce O(n^2)to O(n).

    - CountTripletsWithSumSmallerThanGivenValue.java
      PrintTripletsInSortedArrayThatFormArithmeticProgression.java
        These algorithms teaches you how to reduce O(n^3)to O(n^2).


    - Tricky algorithms
        You need to memorize the solutions.

        MaximumDifferenceBetweenTwoElementsOfAnArray.java
        MaximumAbsoluteDifference.java
            This algorithms teaches you how to reduce O(n^2)to O(n).

        MaximumAvgSubarrayOfSizeK.java

        FindMajorityElementInArray.java

        FindKthLargestElement.java ----- based on value of k, you decide whether to use aux array approach or quick sort approach.


Kadane's Algorithm

    MaxSumContiguousSubarray.java, FlipElementsInArrayToGetMaximum1s.java

        This algorithm is mainly used to find contiguous subarray that gives max sum in O(n). But similar concept can be applied to other algorithms also.

        This algorithm assumes that there is at least one +ve number. So, you start your pointer from first +ve number in an array because you cannot have a subarray smaller than that first +ve number.

        In this algorithm, you keep 2 variables (sum and maxSum).


        int startIndex = -1;
        int endIndex = -1;

        int finalStartIndex = startIndex;
        int finalEndIndex = endIndex;

        // find the first +ve number in an array and start from there because Kadane's algorithm assumes that there is at least one +ve number in an array and so subarray start from +ve number.
        for (int i = 0; i < A.length; i++) {
            if (A[i] >= 0) {
                startIndex = i;
                endIndex = i;
                break;
            }
        }

        if (startIndex == -1) {// no +ve or 0 number found
            return Integer.MIN_VALUE;
        }

        for (int i = startIndex; i < A.length; i++) {

            sum = sum + A[i];

            if (startIndex == -1) {
                startIndex = i;
            }
            endIndex = i;

            if(sum < 0) {
                reset sum, start and end pointers.
            } else if(sum > maxSum) {
                maxSum = sum;
                finalStartPointer = startPointer;
                finalEndPointer = endPointer
            }
        }

XOR operations

    This is an amazing technique to find out missing numbers in an array for numbers 1 to n.

    FindANumberOccurringOddNumberOfTimes.java
    FindElementThatAppearsOnceWhereEveryOtherElementAppearTwice.java
    ReverseArrayWithoutUsingExtraSpace.java

    FindTwoMissingNumbersInArray.java - tricky
        Remember:
            a^a = 0 - XORing number with itself gives 0.

            a^b = a+b - a&b

            a ^ (a-1) unsets rightmost 1 bit in a

                e.g. 0110=6
                     6 ^ (6-1) = 0100

            a ^ ~(a-1) give rightmost set bit (1). you can use it to create two sets of numbers in array - one that has rightmost bit set and another that doesn't.
                e.g. 0110=6
                     6 ^ ~(6-1) = 0010


*/
public class _0ArrayFundamentals {
}
