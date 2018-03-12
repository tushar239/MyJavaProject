package algorithms._1array;

/*
Tricky:
    PlusOne.java

Sorting:
    There are multiple ways to sort an array

    See 'Sorting.java'

    Comparison sorts

        - Insertion sort
            It should be used only when array is almost sorted or array size is very small.
            For almost sorted array, it takes O(n) time to sort, otherwise it takes O(n^2) time.

        - Quick Sort
            It is an in-place sorting algorithm and takes O(n log n) time, if array is properly shuffled.
            Most of the time, you will use this sorting for Array related algorithms.
            But it is not stable.

        - 3-Way Quick Sort
            If there are too many duplicate elements, you can use 3-way quick sort to reduce the time even further (< O(n log n))

        - Merge Sort (uses 2n space for aux array and takes O(n log n) time)
            This should be used only if you need stable sorting and if you are ok with extra memory.

    Create BST + In-Order Traversal

            It uses o(n) space and O(n^2) time in worst case to create BST of all elements and O(n) time to do in-order traversal of entire tree.

            This method is useful only when there are too many duplicates, so that it takes <= O(n log n) time to create BST.
            Alternatively, you can use 3-way quick sort also, if there are too many duplicates

    Non-Comparison sorts (gives close to O(n) time complexity, but uses extra memory, they are not in-place sorting algorithms)

        - Bucket Sort
            If your elements can be uniformly distributed, you can use bucket sort.

            You can use it for any type of objects (not just numbers) because you can use comparison sort for sorting elements in each bucket.

            If elements are not uniformly distributed, you may end up with many elements in 1 bucket only and in that case it may not give any advantage of comparison sort.

        - Counting Sort
            You can use it if you have numbers in specific range.

            It needs Aux array (counting array) of size max element in an array.
            You can use it only for numbers.

        - Radix Sort
            It's the best non-comparison sorting algorithm for numbers because it overcomes the disadv of counting sort.
            It needs aux array of size 0 to 9 only.

            You can use it only for numbers.



    FindKthLargestElement.java ----- based on value of k, you decide whether to use aux array approach or Quick Sort approach.

Divide and Concur

    ClosestPairOfPoints.java --- This algorithm is extremely important to understand 'divide & concur' concept and 'Back Tracking' strategy to find time complexity.

Binary Search:

    FindAPairWithGivenDifference.java

More than one pointers:

    Very Very .... Important concepts:

        One thing we know that that convert O(n^3)to O(n^2), a trick is to keep one pointer stable and keep moving other twos together based on certain conditions.
        So, Keep i stable and move j and k together based on certain conditions, so that j and k covers entire array just once for every i.
        To make this trick work, sometimes you need to have sorted array.

        - MinimumSwapsRequiredToBringAllElementsLessThanOrEqualToKTogether.java
            This algorithm teaches you how to reduce O(n^2)to O(n).

        - CountTripletsWithSumSmallerThanGivenValue.java
          PrintTripletsInSortedArrayThatFormArithmeticProgression.java
            These algorithms teaches you how to reduce O(n^3)to O(n^2).


    Tricky algorithms related to Pointers:

        You need to memorize the solutions.

        - MaximumDifferenceBetweenTwoElementsOfAnArray.java
        - MaximumAbsoluteDifference.java
            This algorithms teaches you how to reduce O(n^2)to O(n).

        - MaximumAvgSubarrayOfSizeK.java

        - FindMajorityElementInArray.java

        - FindKthLargestElement.java ----- based on value of k, you decide whether to use aux array approach or quick sort approach.

        - CountStrictlyIncreasingSubarrays.java
          This algorithm teaches you
          - what questions to ask to interviewer
          - based on conditions, you have to reset the 'start' pointer.


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

Dynamic Programming

    If you use previously calculated result to figure out current result, it is called dynamic programmin.

    MaximumSumSubSequenceNonAdjacent.java
    You have to memorize it.
    It is different than MaxSumContiguousSubarray.java that uses Kadane's algorithm.

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
