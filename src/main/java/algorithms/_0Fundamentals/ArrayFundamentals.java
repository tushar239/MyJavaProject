package algorithms._0Fundamentals;

/*
Difference between Subsequence and Substring

    str = "ABCDE"

    "ACD","ABD", BCE" etc are subsequences, but not substrings
    "A", "AB", "ABC", "BCD", "CDE" etc are subsequences of continuous characters in a string, they are called substrings.

Difference between Subset/SubSequence and SubArray/SubString

    Subset is a set of any elements in an array. There can be total 2^n - 1  Subsets/SubSequences.
    Subarray is a subset of continuous elements in an array. There can be total n(n+1)/2 SubArrays/SubStrings.

    Subset of array is like a Subsequence of a String.
    Subarray of array is like a Substring of a String.

    A[] = {1,2,3,4,5}
    {1,3,5} is a subset but not a subarray
    {2,3,4} is a subset and a subarray

    e.g. PrintAllSubSequencesAndSubArrays.java

    This algorithm also teaches you how to determine exit condition of recursive method.

Tricky:

    PlusOne.java

    FindASortedSubsequenceOfSize3InAnArray.java (Sorted Subsequence Triplet)--- Remember this trick to find out one smaller and one bigger element for each element of array.
    CountStrictlyIncreasingSubarrays.java --- Above algorithm is for subsequence. this one is for subarray.
                                              Remember this trick. For increasing subarray, you need to compare element with its previous element.

Convert a number into array:


    int num = 32841;

    List<Integer> list = new LinkedList<>();

    while (true) {
        int remainder = num % 10;

        list.add(remainder);

        num = num / 10;

        if (num == 0) {
            break;
        }
    }


    OR  (little complicated)

    int num=32841;

    List<Integer> list = new LinkedList<>();

    while (true) {
        int remainder = num % 10;
        int num = num / 10;

        if(num > 0) {
            list.add(remainder);
        }
        else {
            list.add(remainder);
            break;
        }
    }

    System.out.println(list); // [1, 4, 8, 2, 3] --- reverse of number

    e.g. NextGreaterNumberWithSameSetOfDigits.java


How to reduce time complexity?

    If brute-force takes O(n^2), think how can you reduce it to O(n) using two pointers moving together.

        e.g. MaximumAvgSubarrayOfSizeK.java, MinimumSwapsRequiredToBringAllElementsLessThanOrEqualToKTogether.java
             Triplets related algorithm that can be reduced from O(n^3) to O(n^2).

    If that is not possible, then think how can you reduce it to at least O(n log n) using divide-and-concur algorithms like merge sort/quick sort

        e.g. CountInversionsInAnArray.java



Sorting:

    There are multiple ways to sort an array

    See 'SortingFundamentals.java'

    Comparison sorts

        - Insertion sort
            It should be used only when array is almost sorted or array size is very small.
            For almost sorted array, it takes O(n) time to sort, otherwise it takes O(n^2) time.

            e.g. SortKSortedArray.java - this can be done using Insertion Sort as well as Heap Sort.

        - Quick Sort
            It is an in-place sorting algorithm and takes O(n log n) time, if array is properly shuffled.
            Most of the time, you will use this sorting for Array related algorithms.
            But it is not stable.

        - 3-Way Quick Sort
            If there are too many duplicate elements, you can use 3-way quick sort to reduce the time even further (< O(n log n))

        - Merge Sort (uses 2n space for aux array and takes O(n log n) time)
            This should be used only if you need stable sorting and if you are ok with extra memory.

           IMP: CountInversionsInAnArray.java

        - Heap Sort

            Usage of Binary Heap

            BinaryHeap.java

             - Find min/max in O(1) time
             - Creating Priority Queue (BH -> HS -> PQ)
             - When data is flowing from multiple streams and you want to find min/max element at any given point in time.
               As data comes, keep inserting them in min/max heap. At any given point in time, you can find min/max element.
             - Sorting k-sorted array (when every element in an array is k positions away from its actual position in sorted array)
               For k-sorted array, insertion sort takes O(nk), while heap sort takes O(n log k)

               e.g. SortKSortedArray.java, MergeKSortedArray.java

            Time Complexity to create a Binary Heap from an array.

                To insert/delete an element in/from a Binary Heap takes O(log n).
                So, you must be thinking that creating a Binary Heap for n elements takes O(n log n), but that is not true.
                see https://www.geeksforgeeks.org/?p=12580
                It takes close to O(n) only.


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


Merge Sorted Arrays (K-Way Merge)

    Memorize this algorithm

    MergeSortedArrays.java

Divide and Concur

    There are following ways to implement divide-and-concur algorithms.

    - Binary Search Type divide-and-concur (searching for required element on one side of a divider element(mid element))

        - This is useful, when you need to find ONE possible element out of just one or many available possible elements.
        This helps to reduce time complexity from O(n) to O(log n)

        IMPORTANT:
        Read BitonicPoint.java first. This will explain the basics of how to think about any BinarySearch related algorithm.


        FindPeakInGivenArray and FindPeakInGiven2DArray.
        FindRotationCountInRotatedSortedArray.java
        SearchElementInSortedMatrix.java
        FindAPairWithGivenDifference.java

        - Binary Search can be used not just to find a specific element in a sorted array, but it can also be used to find closest > or < elements than given element.
        e.g. PickAnyOneElementFromEachRowOfMatrixAndFindMinDiffBetweenAnyTwoAdjacentElements.java

        - There are some important concepts to remember in this kind of algorithms.

            - When you create a divider (mid)

              When you need to to access
                    A[mid-1], it is must to check whether mid == 0   (not mid == start)
                    A[mid+1], it is must to check whether mid == A.length-1  (not mid == A.length-1)

              e.g. FindPeakInGivenArray.java, FindBitonicPoint.java


              Don't compare mid-1 >= start. Always do mid > start.
              Similarly, don't compare mid+1 <= end. Always do mid < end.


            - Always recurse with

                findPeak(A, start, mid - 1);
                and/or
                findPeak(A, mid+1, end);

                Do not recurse with

                    findPeak(A, start, mid).
                    and/or
                    findPeak(A, mid, end);

                It will result in infinite recursions. Because at some point mid=1 will come. At this point, when you do findPeak(A,start=0,mid=1), in this call again mid=(0+1)/2=1. So, you will infinitely end up calling findPeak(A,0,1).

            - If you are using arr[mid-1] and/or arr[mid+1] in the code, it can throw ArrayIndexOutOfBoundException.

              To avoid that, there has to be condition(s) checking 'mid' with 0 and A.length-1
                See FindPeakInGivenArray.java, FindBitonicPoint.java
              OR
              checking 'mid' with 'start' and/or 'end'
                See FindRotationCountInRotatedSortedArray.java

            - When you have an exit condition to check start==end, then you need to have a sense that
              if your array is 1,2,3 and start and end pointers are at 3 (at the end) or at 1 (at the beginning), what should you do?

              for start==end exit condition, if you need to repeat the logic same as rest of the algorithm,
              checking start>end may also work fine.
              e.g. FindPeakInGivenArray.java


      How O(C + log n) is different than O(C log n) ?

          SearchElementInSortedMatrix.java is an important algorithm to understand how O(C + log n) is calculated.
          In recursive tree of height (log n), if only one node id doing some operation taking O(C) time, then it comes to O(C + log n). if every node is doing that operation, then it comes to O(C log n).

          This is classic Binary Search recursive calls.

                                    BS(n)
                                BS(n/2)
                             BS(n/4)
                             ...
          e.g.
          In Binary Search, every node does O(1) operation of comparing required element with mid. So, it is O(1 log n).

          If some algorithm does O(log m) taking operation on each node, then it is O((log m) (log n)).

          If some algorithm does O(log m) taking operation on only one node of entire tree, then it is O(log m + log n).
          e.g. SearchElementInSortedMatrix.java

    - Same as Binary Search type divide-and-concur, but searching on both sides of divider element

        Sometimes, this may or this may not reduce the time complexity, but it definitely makes the problem solving easier using recursion.

        For these algorithms, it reduces the time complexity compare to Brute-Force approach.

            CollectAllStacksOfCoinsInMinimumNumberOfSteps.java
            ClosestPairOfPoints.java --- This algorithm is extremely important to understand 'divide & concur' concept and 'Back Tracking' strategy to find time complexity.

        Let's take an example of a divide-and_concur algorithm that does not reduce time complexity compared to Brute-Force approach:

            If you need to find all peak elements in an array, then you need to enhance 'FindPeakInGivenArray.java' to find peaks on both sides of 'mid'.
            This will take O(n), which will be same as the time complexity of Brute-Force approach.

    - Merge Sort Type divide-and-concur

        CountInversionsInAnArray.java - this particular problem doesn't work with well using Quick Sort kind of divide-and-concur, when there are duplicate elements.

    - Quick Sort Type divide-and-concur

        FindKthLargestElement.java

More than one pointers:

    Very Very .... Important concepts:

        One thing we know that that convert O(n^3)to O(n^2), a trick is to keep one pointer stable and keep moving other twos together based on certain conditions.
        So, Keep i stable and move j and k together based on certain conditions, so that j and k covers entire array just once for every i.
        To make this trick work, sometimes you need to have sorted array.

        - MinimumSwapsRequiredToBringAllElementsLessThanOrEqualToKTogether.java
            This algorithm teaches you how to reduce O(n^2) to O(n).

        - CountTripletsWithSumSmallerThanGivenValue.java
          PrintTripletsInSortedArrayThatFormArithmeticProgression.java
            These algorithms teaches you how to reduce O(n^3) to O(n^2).


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

          Hint: for increasing subarray problem, you have to compare an element with its previous element.

        - NumberOfSubarraysWhoseMinAndMaxElementsAreSame.java

          No of subarrays possible with d elements = ( d * (d+1) / 2 )
          where d is number of CONTINUOUS elements.

          e.g. {1,2,3}

          Total number of subarrays that you can make from this array is
          ( 3 * (3+1) ) / 2 = 6

          (1), (1,2), (1,2,3), (2), (2,3), (3)

          e.g. {1,2,3,4,5}

          Total number of subarrays that you can make from index 1 to 3 is 6.
          From {2,3,4}, you can make 6 subarrays with continuous elements.

Sorted Array related Algorithms

    When you are given a sorted array, there are multiple ways to think
    - use binary search
    - use two pointers - one starts from beginning and another starts from end (FindThePairInSortedArrayWhoseSumIsClosestToX.java)

Kadane's Algorithm

    Kadane's algorithm can be used in algorithms, where you want to find out biggest possible subarray that gives something.

    In MaxSumContiguousSubarray.java algorithm, it is used to find max sum.
    In FlipElementsInArrayToGetMaximum1s.java, it is used to find max number of continuous 1s.


    MaxSumContiguousSubarray.java, FlipElementsInArrayToGetMaximum1s.java

        This algorithm is mainly used to find contiguous subarray that gives max sum in O(n). But similar concept can be applied to other algorithms also.

        This algorithm assumes that there is at least one 0 or +ve number.

        You have to memorize the approach.

Comparator and Comparable interfaces

    MergeSortedArrays.java(k-way merge sort) is a good example of Comparator and Comparable interfaces.

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

Matrix Multiplication

    Remember, Matrix Multiplication takes O(n^3).

    MatrixMultiplication_iterative.java ---- O(n^3)
    MatrixMultiplication_divide_and_conquer.java --- O(n^3) + stack space is used for recursion
    MatrixMultiplication_Strassens_Algorithm.java --- O(n^2.81) + stack space is used for recursion

Find smallER element from left and/or greatER element from right

    e.g. FindASortedSubsequenceOfSize3InAnArray.java

Find smallEST/greatEST element from the right

    e.g. ReplaceEveryElementWithTheGreatestElementOnRightSide.java

Stack

    When to use stack for array related problems?

    Whenever you need to find 'first possible ...' element in remaining array after current element, then think of using stack.

    e.g. FindFirstGreaterElementFromNextElementForEveryElement.java, LengthOfLongestValidSubstring.java

    You cannot use stack for finding the 'best' element in remaining array after current element (e.g. ReplaceEveryElementWithTheGreatestElementOnRightSide.java)


Sorting an array using Arrays.sort and Comparator

    String[] strs = new String[] {"a", "abc", "ab", "b"};

    Arrays.sort(strs, new Comparator<String>() {
        @Override
        public int compare(String o1, String o2) {
            if(o1.length() ==o2.length()) {
                return 0;
            }
            if (o1.length() < o2.length()) {// remember that -1 goes with <
             return -1;
            }
            return 1;
        }
    });

*/
public class ArrayFundamentals {
}
