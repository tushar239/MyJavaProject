package algorithms._0Fundamentals;

import com.google.common.collect.Lists;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/*

    Sorting Stack and Queue
    -----------------------
    Sorting Stack or Queue using additional data structures(stacks) is easier.
    See "SortStackUsingRecursion.java".

    In-place sorting
        Stack - not possible            see "SortStackUsingRecursion.java"
        Queue - possible, but harder    see "SortQueueWithoutUsingAnyExtraSpace_hard.java"

    Merging multiple sorted arrays and creating one array (K-Way Merge)
    -------------------------------------------------------------------
    This is a very important algorithm. Memorize it.

    MergeSortedArrays_OR_K_Way_Merge.java

    Sorting Array
    -------------

    COMPARISON SORTS

                                                    ------------------divide and concur recursive algorithms-------------
            -unstable-                              |                                                                   |
            |        |                              |                                                                   |
              O(n^2)                                              O(n log n)
        B   S   I   S       and                     M     -             H                                               Q
        |       |                                   |                   |                                               |
        - stable-                               space complexity=2n     ------------------ in-place ---------------------
                |                                                       |                                               |
        best for small and almost sorted array                     Used to find min/max in O(1)                         |
        It takes almost O(n) for almost sorted array               and for PQ                                           |
                                                                   and elements coming from multiple infinite streams   |
                                                                                                                        |
                                                                                                                     unstable  (when array has duplicates and pivot is on one of them, algorithm becomes unstable e.g. [5 4 0 4]. Try it)
                                                                                                                        |
                                                                                        if array is not properly shuffled, it takes O(n^2). In most cases, it takes O(n log n)
                                                                                        Because it is not stable, java uses merge sort for sorting objects and 3-way quick sort for sorting primitives. For sorting primitives, stability is not a concern.
                                                                                        Quick Sort also takes smaller constant time compared to merge sort. So, if you have a lot of memory and instability is ok, then I would choose Quick Sort instead of Merge Sort.
                                                                                        Quick Sort cannot be used for Linked List because Linked List doesn't work with indices and Quick Sort is based on indices. You have to use Merge Sort for sorting a Linked List.
                                                                                                                        |
                                                                                        For array having duplicate elements, 3-way quick sort is better

        BSIS

            Bubble – in place - stable – execution time O(n^2) to O(n^2)
            Selection – in place – unstable - execution time O(n^2) to O(n^2)
            Insertion – in place – stable - execution time O(n) to O(n^2) -------- Insertion Sort is the best sorting algorithm for almost sorted array
            Shell (h-sort) - in place - unstable -execution time O(n^3/2) to O(n^7/6) --- so far nobody could determine correct execution time

        BSIS, Insertion(I) is better because it gives O(n) best cases (when array is almost sorted)

        M-HQ  (M is not in-place sorting, but it is stable) (HQ are in-place, but they are unstable)

            Merge – not in place
                    space complexity is O(2n) for arrays and O(log n) for stack
                    execution time is ALWAYS O(n log n)
            Heap – in place
                   requires to maintain heap(binary heap tree - aux array)
                   execution time O(n log n) not stable
            Quick – in place
                    execution time O(n log n) to O(n^2). In most practical scenarios, it gives O(n log n)
                    not stable
                    Try to sort [2, 5, 7, 5] by keeping pivot as last index. you will realize that at some point you need to exchange pIndex 5 with pivot 5.
                    (IMPORTANT) Quick Sort can be made Stable, when the pivot chosen is ensured to be of a unique key.
                                You can write a quick sort using aux arrays also just like merge sort, but it is not advisable to do so. See notInPlaceQuickSort method.

        - Arrays.sort uses 3-Way-QuickSort for int[], float[] etc. But it uses Merge Sort/Insertion Sort for Object[].
          If positions for primitives are changed during sorting, then it's ok, but it's not ok for Objects.
        - Collections.sort uses Arrays.sort internally.
          Collections.sort(...) uses insertion sort for smaller number of Object elements and deleteRootAndMergeItsLeftAndRight sort for large number of elements
        - Heap Sort uses Binary Heap algorithm and Priority Queue uses Heap Sort. (BH -> HS -> PQ)

     NON-COMPARISON SORTS

        Best Comparison sort gives at the most O(n log n) time complexity in worst case, but is there any sorting algorithm that can give O(n) time complexity in worst case?
            Yes, Bucket sort, but with some conditions.
            It gives time complexity O(nb) in worst case and space complexity O(n+b) where b is a number of buckets.

        Bucket Sort - Stable, not in-place
            None of the COMPARISON sort gives O(n) time complexity in worst case. Bucket sort gives it, but with following CONDITIONS.
            1. Bucket sort is mainly useful when input is uniformly distributed over a range. Uniformly distributed means linkedlists inside each bucket gets almost same number of elements.
            2. Used in special cases when the key can be used to calculate the address of buckets.
         It internally uses COMPARISON sort for sorting each bucket.


    BST+In-Order Traversal

        It uses o(n) space.
        O(n^2) time in worst case to create BST of all elements.
        O(n) time to do in-order traversal of entire tree.

        This method is useful only when there are too many duplicates, so that it takes <= O(n log n) time to create BST.
        Alternatively, you can use 3-way quick sort also, if there are too many duplicates

        If there are duplicates, you can maintain the count inside each node of BST. In this way, total number of nodes in BST will be less.


What is stability?
http://programmers.stackexchange.com/questions/247440/what-does-it-mean-for-a-sorting-algorithm-to-be-stable


Which sorting algorithm to be used?
http://www.academia.edu/1976253/Selection_of_Best_Sorting_Algorithm

This explains why Java uses Insertion and Merge sorts when doing Collections.sort(collection).
If number of elements are less, then it uses Insertion Sort, otherwise it uses Merge Sort
Insertion Sort is good for mostly sorted less number of elements.

Comparison Sorts

    Bubble Sort

        1.Straightforward, simple and slow
        2.STABLE.
        3.Inefficient on large tables.

    Selection Sort

        1. Improves the performance of bubble sort a bit, but it is also slow.
        2. UNSTABLE but can be implemented as a stable sort.
        3. Quite slow for large amount of data.

    Insertion Sort (stable, good for mostly sorted array)

        1.EFFICIENT for small list and MOSTLY SORTED List because execution time is O(n) to O(n^2)
        2.STABLE
        3.Sorts big array slowly
        4.Saves memory

    Shell Sort

        1.Efficient for large nilList
        2.It requires relative small amount of memory, extension of insertion sort
        3.Fastest algorithm for small nilList of elements.
        4.More constraints, UNSTABLE.

    Heap Sort (good for O(1) time to find min/max, priority queue, k-sorted array)

        1.More efficient version of selection sort.
        2.No need extra buffer
        3.Its does not require recursion
        4.Slower than Quick and Merge sorts.

        MOST IMPORTANT

        5.Heap Sort is very useful when you need to find min/max in O(1) time and insert an element in O(log n) time. It requires an aux array through. so O(n) space and total execution time is O(n log n).
        6. Java's PRIORITY QUEUE uses this Sorting

        IMP: Usage of Binary Heap
             - Find min/max in O(1) time
             - Creating Priority Queue (BH -> HS -> PQ)
             - When data is flowing from multiple streams and you want to find min/max element at any given point in time.
               As data comes, keep inserting them in min/max heap. At any given point in time, you can find min/max element.
             - Sorting k-sorted array (when every element in an array is k positions away from its actual position in sorted array)
               For k-sorted array, insertion sort takes O(nk), while heap sort takes O(n log k)

               e.g. SortKSortedArray.java, MergeKSortedArray.java

       IMP: Time Complexity to create a Binary Heap from an array.
            To insert/delete an element in/from a Binary Heap takes O(log n).
            So, you must be thinking that creating a Binary Heap for n elements takes O(n log n), but that is not true.
            see https://www.geeksforgeeks.org/?p=12580
            It takes close to O(n) only.


    Merge Sort (stable, takes O(n log n), uses O(2n) extra space)

        1.Well for very LARGE nilList, STABLE sort.
        2.A fast recursive sorting
        3.Both useful for internal and external sorting
        4.It requires an auxiliary array that is as large as the original array to be sorted.

        e.g. CountInversionsInAnArray.java,
             Sorting a LinkedList. LinkedList doesn't support index based operation. So, quick sort cannot be used for sorting a LinkedList. You have to use Merge Sort.

    Quick Sort (unstable, takes O(n log n) in average case and O(n^2) for sorted array)

        1.Fastest sorting algorithm in practice but sometime Unbalanced partition can lead to very slow sort.
        2.Available in many standard libraries.
        3.O(log n) space usage.
        4.UNSTABLE sort and complex for choosing a good pivot element.
          Try to sort [2, 5, 7, 5] by keeping pivot as last index. you will realize that at some point you need to exchange pIndex 5 with pivot 5.
          Quick Sort can be made Stable, when the pivot chosen is ensured to be of a unique key.

        (IMP) If quicksort is O(n log n) on average, but merge sort is O(n log n) always, why not use merge sort? Isn’t it faster?
        Quicksort has a smaller constant than merge sort. So if they’re both O(n log n) time, quicksort is faster. And quicksort is faster in practice because it hits the average case way more often than the worst case.
        Constant time is something that is taken to do each operation. O notation doesn't reserve time into consideration. It takes only number of operations into consideration.
        +
        Merge sort needs auxiliary arrays. Quick sort can be done in-place.

        (IMP)
        To sort an array of integers, quick sort takes O(n log n), we know that. During quick sort, when comparison of 2 integers happens, it takes O(1). Look at Integer class’ compareTo method.
        But in case of Strings, to compare two strings of size s takes O(s). So, sorting of strings will reserve O(sn log n).
        pg 49 of CCA book.

        3-Way Quick Sort

        It is useful when you have many duplicates.
        It has two pIndexes.

         pIndexLow                               pIndexHigh
            7       6       6       6       6       1
            i                                     pivot


         At some point

            1   6   6   6   6   7
                L           H

         Basically, you don't have to sort elements from L to H. You always sort elements before L and after H.

        _3wayQS(A, start, end) {
            ...
            when A[pivot] > A[i], exchange (A[i], A[pIndexLow]),  pIndexLow++, i++
            when A[pivot] < A[i], exchange (A[i], A[pIndexHigh]), pIndexHigh--
            when A[pivot] == A[i], i++

            _3wayQS(A, start, pIndexLow-1)
            _3wayQS(A, pIndexHigh+1, end)
        }


    e.g. FindKthLargestElement.java
         See CountInversionsInAnArray.java also, where you cannot use Quick Sort. Merge Sort is better for this algorithm.

Non-Comparison Sorts  (BCR - Bucket, Counting, Radix sorts)

    None of the comparison sorts gives better than O(n log n) performance.
    Non-Comparison sorts can give better performance O(n).


    Bucket Sort

        https://pbalasundar.wordpress.com/2012/05/31/bucket-sort/
        https://www.geeksforgeeks.org/bucket-sort-2/

        It distributes the array elements into buckets. Each bucket is sorted using either comparison/non-comparison sort.
        Time Complexity = Average case is O(n+k) where k is a size of bucket.
                          Worst case where buckets are not created carefully to distribute the elements uniformly and a few buckets get a lot of elements whereas others are almost empty, then time complexity will be closer to O(n^2).

        1.IMP - Bucket sort is mainly useful when input is uniformly distributed over a range (buckets). Uniformly distributed means linked lists inside each bucket gets almost same number of elements.
        2.IMP - Used in special cases when the key can be used to calculate the address of buckets.
        3.None of the Comparison sort gives ~O(n) time complexity in worst case. Bucket sort gives it, but with CONDITIONS like point 1,2.
        4.Stable, fast. Fast because each bucket is of small size and can be sorted separately in O(nk) time complexity.
            IMP - Each bucket is sorted using any COMPARISON sort algorithm like Insertion Sort. As each bucket will have small number of elements, insertion sort is better from stability and speed perspective.
        5.Valid only in range 0 to some maximum value M.

        6. You can use bucket sort for Strings or other objects also as far as you can decide the bucket number based on string or object value.

    Counting Sort (good for ranged numbers)

        You need to create a 'counting array' of size 'max element' in array. It acts like a staging array between input array and output array.

        https://www.geeksforgeeks.org/counting-sort/
        https://www.youtube.com/watch?v=7zuGmKfUt7s

        1.Stable, used for REPEATED values
        2.Often used as a subroutine in RADIX sort
        3.Counting sort is efficient if the range of input data is not significantly greater than the number of objects to be sorted.
          e.g. Consider the situation where the input sequence is between range 1 to 10K and the data is 10, 5, 10K, 5K.

        4. The counting sort algorithm is designed to sort integer values that are in a fixed range, so it can't be applied to sort strings
           For floats/doubles, you can convert them into integers by multiplying them by 100 or 1000 etc and then do counting sort on integer values.
           After sorting you can convert them back to floats/doubles.
           e.g. 1.2, 1.5, 1.33
           all these numbers can be multiplied by 100
           120, 150, 133
           you can now sort them and convert them back to floats by dividing them by 100
           1.2, 1.33, 1.5


        Space Complexity is  Counting Array of size max element in an array + Aux array of size n
        Time complexity is ALWAYS O(n+k), where k is a size of array that is used to store the counts.

        It can be used only for numbers

        Here, size of count array has to be same as max element in the array.
        e.g. array = 10, 5, 10K, 5K
        count array has to be of size 10k. This is not efficient because number of elements are just 4 and for that you need count array of size 10k.

    Radix Sort

        https://www.geeksforgeeks.org/radix-sort/

        A Radix Sort internally uses Counting Sort, it can be used only for numbers.
        It needs counting array just from 0 to 9, which avoids the disadvantage of counting sort of having counting array of size same as max element in an array.
        It count sorts the elements for the 10th place then 100th place and then 1000 place and so on. To find max place, you need to know the max number in the array.

        Time complexity = O(n) to know max number + O(no of digits in number * n)

        Original, unsorted list:

        170, 45, 75, 90, 802, 24, 2, 66

        Sorting by least significant digit (1s place) gives: [*Notice that we keep 802 before 2, because 802 occurred before 2 in the original list, and similarly for pairs 170 & 90 and 45 & 75.]
        170, 90, 802, 2, 24, 45, 75, 66
          -   -    -  -   -   -   -   -

        Sorting by next digit (10s place) gives: [*Notice that 802 again comes before 2 as 802 comes before 2 in the previous list.]
        802, 2, 24, 45, 66, 170, 75, 90
         -      -   -   -    -   -   -

        Sorting by most significant digit (100s place) gives:
        2, 24, 45, 66, 75, 90, 170, 802


        1.Stable, straight forward code.
        2.Used by the card-sorting machines.
        3.Used to sort records that are keyed by multiple fields like date (Year, month and day)
        4.Radix sort can be used with or without Counting Sort. If you are using Counting Sort then Radix Sort = Doing Counting Sort in for loop for each digit in elements.

        Why Radix Sort is not being used universally in libraries?
        https://www.quora.com/If-Radix-sort-has-a-better-time-complexity-why-is-quick-sort-preferred-both-in-APIs-and-in-terms-of-interviews
            1. Quicksort is universal, while radix sort is only useful for fix length integer keys.
            2. Radix sort has variable space requirement. Bad scalability. Quicksort requires no extra memory.
            3. Time complexity of radix sort is O(n) which is k∗n, the constant factor k depends upon the number of bits in the integers sorted . For quicksort k∗nlogn the constant factor is actually a constant and is very small.
            4. More memory requirements in Radix sort leads to more cache misses and more page faults as the input size grows.

    When to use which Non-Comparison Sort?
        Bucket sort is good, if you know the range of elements and elements are distributed uniformly.
        Counting sort is good, if you know the range of elements and range of elements is not significantly greater than the number of objects to be sorted.
            Counting sort needs needs large counts array of size max-min+1.
            If input sequence is between range 1 to 10K and the elements are 10, 5, 10K, 5K, then it will create an counts array of size 10K for counting only 4 elements. It is not effective.
        If these requirements are not met, then Radix sort is a solution where you actually need an array of size only 10 and there is no range restriction.



    how to do non-comparison sorts for floats/words?????????????????????


    Why isn't heap sort preferred over quick sort even though it has O(n log n) complexity?
        https://www.quora.com/Why-isnt-heap-sort-preferred-over-quick-sort-even-though-it-has-O-nlgn-complexity
        Heap sort needs to create binary heap (aux array) whereas quick sort doesn't need any extra space.


    IMPORTANT -
        If you want just first sorted 10 element from thousands of elements.
        You partition once and then check whether number of element on left of pIndex <=9. if not then keep doing quicksort on left side of pivot.
        till pIndex > 10, keep copying array from 0 to pIndex-1 to another aux array.
        If pIndex == 10 then copy 0 to pIndex-1 to aux array.
        If pIndex < 10 then consider previously create aux array that might have more than 10 elements.
        do insertion sort (stable sort) on aux array and grab 1st 10 elements from that aux array.

        In this case, you never have to sort an array from pIndex+1 to end. It beans execution time of Quick sort will reduce drastically.
        But it requires an aux array to be created.


    You can not use a deleteRootAndMergeItsLeftAndRight sort in this case because in deleteRootAndMergeItsLeftAndRight sort till the first two L and R are merged, it is not guaranteed that L array will have elements which are <= elements in R array.
    Another way to see it, when we create L and R from A, we do it without sorting A, so R can have element >= elements in L. So, till the concur, elements won't be sorted.

    In quick sort, when we partition an array, it is guaranteed that left elements from from 0 to pIndex-1 are always sorted.


    How to get random number from an array?

        new Random().nextInt(number+1) gives a random number from 0 to number.

        To have similar functionality in case of array with array’s start and end index provided,

        int pivot = new Random().nextInt((end - start) + 1) + start;

        This functionality is used to choose a pivot in quick sort.

    How to Shuffle an array?

        private static <T> void shuffle(Comparable<T>[] numbers) {

            Random random = new Random();

            for (int i = 1; i < numbers.length; i++) {
                // pick random number between 0 and i
                // People sometimes choose random number between 0 and n-1, but it doesn't give uniformly random result

                int randomArrayIndex = random.nextInt(i);

                exchange(numbers, i, randomArrayIndex);
            }
            System.out.println("Shuffled Array:" + Arrays.asList(numbers));
        }


*/

public class SortingFundamentals {
    public static void main(String[] args) throws InterruptedException {
        /*{
            Integer[] numbers = {2, 5, 7, 1, 3, 9, -11, -10};

            // Bubble Sort compares first element with other elements and if first element > other element, then exchanges(swaps) them.
            // Number of comparisons are N^2 and and number of Swaps are N^2
            System.out.println("Bubble Sort Example");
            bubbleSort(numbers);
        }*/
        {
            Integer[] numbers = new Integer[]{2, 5, 7, 1, 3, 9, -11, -10};

            // Bubble Sort compares first element with other elements and if first element > other element, then exchanges(swaps) them.
            // Number of comparisons are N^2 and and number of Swaps are N^2
            System.out.println("Bubble Sort Example");
            bubbleSort(numbers);

            numbers = new Integer[]{2, 5, 7, 1, 3, 9, -11, -10};
            recursiveBubbleSort(numbers, 0, numbers.length - 1);
            System.out.println("Sorted Array using recursion:" + Arrays.asList(numbers));
        }

        System.out.println();

        {
            Integer[] numbers = {2, 5, 7, 1, 3, 9, -11, -10};
            // Unlike to Bubble Sort, Selection Sort does not swap the elements after every comparison.
            // It keeps min=i. Compares each element of array with a[min]. if a[min]>a[other index], then min=other index.
            // at the end, it swaps elements of a[i] and a[other index]

            // Number of Swaps reduces to N. That's a benefit over Bubble Sort. But number comparisons are still N^2.
            System.out.println("Selection Sort Example");
            selectionSort(numbers);
        }

        System.out.println();

        {
            Integer[] numbers = {2, 5, 7, 1, 3, 9, -11, -10};
            // Consider first element a part of Sorted side of an array and all other elements on Unsorted side of an array.
            // Each element from unsorted side of the array has to be INSERTED to sorted side of the array.
            System.out.println("Insertion Sort Example");
            insertionSort(numbers);

            Integer[] A = {2, 5, 7, 1, 3, 9, -11, -10};
            System.out.println("Insertion Sort Example");
            insertionSort_another_way(A);
        }

        System.out.println();

        {
            Integer[] numbers = {2, 5, 7, 1, 3, 9, -11, -10};
            //ShellSort is very simple. See the notes.
        }

        System.out.println();

        {
            // It's a divide and concur algorithm. It has time complexity O(nlogn) but space complexity of O(n)
            // https://www.youtube.com/watch?v=TzeBrDU-JaY
            Integer[] numbers = {2, 5, 7, 1, 3, 9, -11, -10};
            System.out.println("Merge Sort Example");
            mergeSort(numbers);
        }

        System.out.println();

        {
            System.out.println("Shuffling Example");
            //Shuffling - it is for unordering an ordered array
            Integer[] numbers = {-11, -10, 1, 2, 3, 5, 7, 9};
            shuffle(numbers);
        }

        System.out.println();

        {
            System.out.println("QuickSort Example");
            {
                Integer[] numbers = {7, 2, 1, 6, 8, 5, 3, 4};
                Comparable<Integer>[] result = notInPlaceQuickSort(numbers, 0, numbers.length - 1);
                System.out.println("Sorted Array:" + Arrays.asList(result));
            }
            {
                /* For testing Parallel Processing of Quick Sort
                Using a lot bigger array for seeing the difference in time of sorting

                Integer[] numbers1 = new Integer[1000000];
                for(int i=0; i< numbers1.length; i++) {
                    numbers1[i] = RandomUtils.nextInt();
                }
                long start = System.currentTimeMillis();
                Thread t = new Thread(new MyRunnable(numbers1, 0, numbers1.length - 1));
                t.start();
                t.join();
                long end = System.currentTimeMillis();
                System.out.println("Took: "+(end-start)+" ms");
                System.out.println("Sorted Array:" + Arrays.asList(numbers1));
                */
                Integer[] numbers1 = {7, 2, 1, 6, 8, 5, 3, 4};
                quickSort_In_Place(numbers1, 0, numbers1.length - 1);

                System.out.println("Sorted Array:" + Arrays.asList(numbers1));

            }

            {
                Integer[] numbers = {7, 2, 1, 6, 8, 5, 3, 4};
                int index = 6;
                Integer elementAtSpecificIndexAfterQuickSort = findElementAtSpecificIndexAfterQuickSort(numbers, 0, numbers.length - 1, index);
                System.out.println("Element at " + index + " is " + elementAtSpecificIndexAfterQuickSort);
            }

        }

        System.out.println();


        {
            System.out.println("3 Way QuickSort Example");
            Integer[] numbers = {7, 6, 6, 6, 6, 6, 1};
            _3WayQuickSort(numbers, 0, numbers.length - 1);
            System.out.println("Sorted Array:" + Arrays.asList(numbers));

        }

        System.out.println();


        {
            // for heap short, see BinaryHeap.java
        }

        // major problem is deciding a formula of calculating number of buckets and which element should go in which bucket number.
        // if these formulas are not proper then you may end up not having elements distributed properly in different buckets.
        // time complexity O(n+k) where k is number of buckets
        // space complexity O(n+k) where k is number of buckets
        {
            System.out.println("Bucket Sort Example");
            Integer[] numbers = new Integer[]{2, 1, 4, 6, 3, 5, 7, 9, 3};
            bucketSort(numbers);
            System.out.println("Sorted Array: " + Arrays.asList(numbers));
        }

        System.out.println();


        {
            // for words, you can assume to have total 26 buckets and then you can put a word as per its first letter in the bucket.
        }
        /*{
            System.out.println("Bucket Sort Example for double numbers");
            double[] numbers = new double[]{1.120,1.170,1.130,1.140,1.160,1.130,1.140,1.110,1.190,1.180};
            bucketSortForDoubles(numbers);
            System.out.println("Sorted Array: "+Arrays.asList(numbers));
        }*/

        System.out.println();

        // Counting Sort
        // https://www.youtube.com/watch?v=zhDmVF_NdjM
        // http://www.programming-algorithms.net/article/40549/Counting-sort
        //Time Complexity: O(n+k) where n is the number of elements in input array and k is the range of input.
        //Auxiliary Space: O(n+k) where k is a size of counting array k=max-min+1
        {

        }

        System.out.println();

        // Radix Sort
        // https://www.youtube.com/watch?v=YXFI4osELGU
        // http://www.geekviewpoint.com/java/sorting/radixsort

        // You can using Counting Sort for Radix Sort internally
        // Radix Sort = Doing Counting Sort in for loop for each digit in elements.
        // Radix Sort using Counting Sort
        // http://www.geeksforgeeks.org/radix-sort/

        // Best Case O(n * k); Average Case O(n * k); Worst Case O(n * k),
        // where k is the length of the longest number and n is the size of the input array.
        {

        }

    }

    // From Grokking Algorithms book (Chapter 4)
    private static Integer[] notInPlaceQuickSort(Integer[] A, int start, int end) {
        if (start >= end) return A;

        int pivot = end;

        List<Integer> left = new LinkedList<>();
        List<Integer> right = new LinkedList<>();

        for (int i = start; i < end; i++) {
            if (A[i] < A[pivot]) {
                left.add(A[i]);
            } else if (A[i] > A[pivot]) {
                right.add(A[i]);
            }
        }
        Integer[] leftArray = left.toArray(new Integer[0]);
        Integer[] rightArray = right.toArray(new Integer[0]);

        // qs(left)+A[pivot]+qs(right)
        List<Integer> list1 = Lists.newArrayList(notInPlaceQuickSort(leftArray, 0, leftArray.length - 1));
        List<Integer> list2 = Lists.newArrayList(notInPlaceQuickSort(rightArray, 0, rightArray.length - 1));

        list1.add(A[pivot]);
        list1.addAll(list2);
        return list1.toArray(new Integer[0]);
    }

    // Quick Sort - https://www.youtube.com/watch?v=aQiWF4E8flQ
    // Quick Sort, Randomized Quick Sort: https://www.youtube.com/watch?v=COk73cpQbFQ
    // To use shuffling in quick sort, read coursera video.
    // Analysis of time and space complexity: https://www.youtube.com/watch?v=3Bbm3Prd5Fo
    /*

      Just like Merge Sort, Quick Sort is also Divide and Concur algorithm.
      Just like Merge Sort, you can create aux arrays in Quick Sort also (see notInPlaceQuickSort method), but better approach is to do quick sort in-place.
      This teaches us a trick: whenever you need to do something in-place, think of using an additional pointer. One pointer is for normal traversal of an array and another pointer increments on some special condition. Hard thing is to find this special condition.

       You have two choices to remove the worst case scenario when input array is already sorted.
       - shuffle an array and choose last element as a pivot
       - choose random pivot (no shuffling is required). choose a random element and replace it with last element and make last element as pivot.
       2nd option is better

       i
       5       3       1       6       4       2
       pIndex                                  pivot

        if pivot > i, then exchange i with pIndex and increase both i and pindex
        otherwise just increase i

                        i
        5       3       1       6       4       2
        pIndex                                  pivot


        exchange i with pIndex and move pIndex by 1

                                i
        1       3       5       6       4       2
                pIndex                          pivot


        Finally, you exchange pivot and pIndex.

        you will collect smaller elements than pivot on left side of the pivot and all bigger elements than pivot on its right side


        1     |  2  |     5       6       4       3
               pIndex                           pivot

        Pivot element has reached to its right place in the array.

        quickSort_In_Place(A, start, pIndex - 1);
        quickSort_In_Place(A, pIndex + 1, end);

     To understand how the big O notation value is calculated, read 'readme.docx'


    (IMP)
    To sort an array of integers, quick sort takes O(n log n), we know that. During quick sort, when comparison of 2 integers happens, it takes O(1). Look at Integer class’ compareTo method.
    But in case of Strings, to compare two strings of size s takes O(s). So, sorting of strings will reserve O(sn log n).
    pg 49 of CCA book.


     */
    private static <T> void quickSort_In_Place(Comparable<T>[] A, int start, int end) throws InterruptedException {
        if (start >= end) return; //exit condition
        //shuffle(A); // as suggested by coursera video to avoid worst case scenario O(n^2) time complexity. this adds extra execution time. Better approach might be using random pivot.
        //int pivot = end;

        // You HAVE TO choose pivot as the first or last element only, so that pivot is compared with all other elements after/before it respectively.
        // To achieve, O(n log n) even the array is already sorted, first replace last element by some random element and choose last index as pivot.
        // (IMP) you need to memorize the way of choosing a random number
        int pivot = new Random().nextInt((end - start) + 1) + start; // generating random number from start to end (inclusive)
        exchange(A, pivot, end);

        pivot = end; // start pivot from end after putting some random number at the end
        int pIndex = start;

        for (int i = start; i < end; i++) {
            if (greater(A[pivot], A[i])) { // if pivot is greater than i, then exchange i with pIndex and increment pIndex
                exchange(A, i, pIndex);
                pIndex++;
            }
        }
        exchange(A, pIndex, pivot);


        quickSort_In_Place(A, start, pIndex - 1);
        quickSort_In_Place(A, pIndex + 1, end);
        // Parallel Programming --- takes lot lesser than non-parallel code.
//        Thread threadL = new Thread(new MyRunnable(A, start, pIndex-1), ++count+"-Thread");
//        Thread threadR = new Thread(new MyRunnable(A, pIndex + 1, end), ++count+"-Thread");
//        executor.submit(threadL);
//        executor.submit(threadR);

    }

    private static ExecutorService executor = Executors.newFixedThreadPool(100);

    static int count = 0;

    private static class MyRunnable<T> implements Runnable {
        private final Comparable<T>[] A;
        private final int start;
        private final int end;

        public MyRunnable(Comparable<T>[] A, int start, int end) {
            this.A = A;
            this.start = start;
            this.end = end;
        }

        @Override
        public void run() {
            //System.out.println(Thread.currentThread().getName() + " A: " + A + ", start: " + start + ", end: " + end);
            try {
                quickSort_In_Place(A, start, end);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /*
    If you see here it will create a tree similar to Binary Search Tree with log n height, but there will be some number comparisons at each level.
                             o
n comparison                /\
                           o   ignore this half (no comparisons)
n/2 comparison            /\
                         o   ignore this half (no comparisons)
n/4 comparison          /\
                       o  ignore this half (no comparisons)
etc comparison        /

So, I would say it has time complexity of O(n log n)-many comparisons. So, it will befinitely faster than normal Quick Sort.
     */
    private static Integer findElementAtSpecificIndexAfterQuickSort(Integer[] A, int start, int end, int k) {
        if (start >= end) {
            if (A[k] == A[end]) {
                return A[end];
            } else {
                return null;
            }
        }

        //shuffle(A); // as suggested by coursera video to avoid worst case scenario O(n^2) time complexity. this adds extra execution time. Better approach might be using random pivot.
        //int pivot = end;

        // choose random pivot --- you need to memorize the way of choosing a random number
        int pivot = new Random().nextInt((end - start) + 1) + start; // generating random number from start to end (inclusive)
        exchange(A, pivot, end);

        pivot = end;
        int pIndex = start;

        for (int i = start; i < end; i++) {
            if (A[pivot] > A[i]) { // if pivot is greater than i, then exchange i with pIndex and increment pIndex
                exchange(A, i, pIndex);
                pIndex++;
            }
        }
        exchange(A, pIndex, pivot);

        if (pIndex == k) {
            return A[pIndex];
        } else if (k < pIndex) {
            return findElementAtSpecificIndexAfterQuickSort(A, start, pIndex - 1, k);
        } else {
            return findElementAtSpecificIndexAfterQuickSort(A, pIndex + 1, end, k);
        }

    }
/*
    private static <T> void quickSort(Comparable<T>[] A, int start, int end) {
        if(start >= end) return; //exit condition
        //shuffle(A); // as suggested by coursera video to avoid worst case scenario O(n^2) time complexity. this adds extra execution time. Better approach might be using randomizedPartition method
        //int pIndex = partition(A, start, end);
        int pIndex =randomizedPartition(A, start, end);// calling randomizedPartition method instead of partition method as an alternative of shuffling
        quickSort(A, start, pIndex-1);
        quickSort(A, pIndex + 1, end);
    }*/

    /*
        In Randomized partition, pivot still remains end of an array, but before selecting a pivot, just replace end element with some other element and that is called randomized partition.
     */
    private static <T> int randomizedPartition(Comparable<T>[] A, int start, int end) {
        Random random = new Random();
        int randomPivot = random.nextInt((end - start) + 1) + start; // generating random number from start to end (inclusive)
        exchange(A, randomPivot, end);
        return partition(A, start, end);
    }

    private static <T> int partition(Comparable<T>[] A, int start, int end) {
        int pivot = end; // select last element of an array as a pivot
        int pIndex = start;
        for (int i = start; i < end; i++) {
            if (lessOrEqual(A[i], A[pivot])) {
                exchange(A, i, pIndex);
                pIndex++;
            }
        }

        exchange(A, pIndex, pivot);

        System.out.println("next pivot:" + pIndex + " ,Array state:" + Arrays.asList(A));
        return pIndex;
    }

    /*
    Consider an array which has many redundant elements. For example, {1, 4, 2, 4, 2, 4, 1, 2, 4, 1, 2, 2, 2, 2, 4, 1, 4, 4, 4}. If 4 is picked as pivot in Simple QuickSort, we fix only one 4 and recursively process remaining occurrences.
     */
    private static <T> void _3WayQuickSort(Comparable<T>[] A, int start, int end) {
        if (start >= end) return; //exit condition
        TwoPIndex twoPIndex = _3WayPartition(A, start, end);

        _3WayQuickSort(A, start, twoPIndex.getpIndexLow() - 1);
        _3WayQuickSort(A, twoPIndex.getpIndexHigh() + 1, end);
    }

    private static <T> TwoPIndex _3WayPartition(Comparable<T>[] A, int start, int end) {
        int pivot = end; // select last element of an array as a pivot
        int pIndexLow = start;
        int pIndexHigh = end;
        for (int i = start; i <= end - 1; ) {
            if (less(A[i], A[pivot])) {
                exchange(A, i, pIndexLow);
                i++;
                pIndexLow++;
            } else if (greater(A[i], A[pivot])) {
                exchange(A, i, pIndexHigh);
                pIndexHigh--;
            } else {
                i++;
            }
            // if A[i] equals A[pivot], then just do i++
        }

        //System.out.println("next pivot:"+pIndex+" ,Array state:"+Arrays.asList(A));
        return new TwoPIndex(pIndexLow, pIndexHigh);
    }

    static class TwoPIndex {
        int pIndexLow, pIndexHigh;

        TwoPIndex(int pIndexLow, int pIndexHigh) {
            this.pIndexLow = pIndexLow;
            this.pIndexHigh = pIndexHigh;
        }

        public int getpIndexLow() {
            return pIndexLow;
        }

        public int getpIndexHigh() {
            return pIndexHigh;
        }
    }

    /*
        Merge Sort:

        https://www.youtube.com/watch?v=TzeBrDU-JaY

        Divide the problem in subproblems until problem become so small that it becomes simple enough to solve.

        Time complexity - https://youtu.be/nfg4A-X6lLM

     */

    private static <T> void mergeSort(Comparable<T>[] A) {
        if (A.length == 1) return;
        divide(A);
        System.out.println("Sorted Array:" + Arrays.asList(A));
    }

    // See excel Sorting Algorithm Worksheet.xlsx to know how to calculate space and time complexity for deleteRootAndMergeItsLeftAndRight sort.
    // Time Complexity = O(n log n)
    // Space Complexity = O(2n)
    private static <T> void divide(Comparable<T>[] A) { // if size of array is n
        if (A.length <= 1) return; // exit condition

        // find mid
        int mid = A.length / 2;

        // create L and R subarrays
        Comparable<T>[] L = new Comparable[mid];
        Comparable<T>[] R = new Comparable[A.length - mid];
        for (int index = 0; index < mid; index++) { // runs n/2 times
            L[index] = A[index];
        }
        for (int index = mid; index < A.length; index++) { // runs n/2 times
            R[index - mid] = A[index];
        }
        //System.out.println("L="+Arrays.asList(L)+" ,R="+Arrays.asList(R));

        //if (L.length > 1) { // stop recursion of dividing L array, when L has only 1 element.
        divide(L);
        //}
        //if (R.length > 1) { // stop recursion of dividing R array, when R has only 1 element
        divide(R);
        //}
        concur(A, L, R); // concur's while loops runs n times

    }

    private static <T> void concur(Comparable<T>[] A, Comparable<T>[] L, Comparable<T>[] R) {
        //System.out.println("Calling deleteRootAndMergeItsLeftAndRight for "+"L="+Arrays.asList(L)+" ,R="+Arrays.asList(R));
        int i = 0;
        int j = 0;
        int k = 0;

        // concur L and R elements in original array A
        while (i < L.length && j < R.length) {
            if (lessOrEqual(L[i], R[j])) {
                A[k] = L[i];
                i++;
                k++;
            } else {
                A[k] = R[j];
                j++;
                k++;
            }
        }
        while (i < L.length) {
            A[k] = L[i];
            i++;
            k++;
        }
        while (j < R.length) {
            A[k] = R[j];
            j++;
            k++;
        }

    }

    private static <T> void shuffle(Comparable<T>[] numbers) {
        Random random = new Random();
        for (int i = 1; i < numbers.length; i++) {
            // pick random number between 0 and i
            // People sometimes choose random number between 0 and n-1, but it doesn't give uniformly random result
            int randomArrayIndex = random.nextInt(i);
            exchange(numbers, i, randomArrayIndex);
        }
        System.out.println("Shuffled Array:" + Arrays.asList(numbers));
    }

    private static <T> void bubbleSort(Comparable<T>[] comparables) {
        for (int i = 0; i < comparables.length; i++) { // outer loop is backward
            for (int j = i + 1; j < comparables.length; j++) { // inner loop is forward from 0 to i-1
                if (greater(comparables[i], comparables[j])) { // compare inner loops two adjutant elements
                    exchange(comparables, i, j);// swap elements --- number of swaps are N^2
                }
            }
        }
        System.out.println("Sorted Array:" + Arrays.asList(comparables));
    }

    private static void recursiveBubbleSort(Integer[] array, int start, int end) {
        if (start == end) return;

        int element = array[start];
        recursiveBubbleSort(array, start + 1, end);

        int indexToCompare = start;
        for (int i = start + 1; i <= end; i++) {
            if (element > array[i]) {
                exchange(array, indexToCompare, i);
                indexToCompare = i;
            }
        }
    }

    // As you are breaking out of the loop, it is not a bubble sort. it is an insertion sort.
    public static void approachTowardsRecursiveBubbleSort(int array[], int start, int end) {

        // exit/base condition
        if (start == end) return;
        // reduce the problem by 1
        int element = array[start];

        // use recursion and assume that all other elements will be sorted automatically
        approachTowardsRecursiveBubbleSort(array, start + 1, end);

        // think how will you mix that separated element with sorted array of rest of the elements
        // e.g. [11, 1, 2, 3, 4, 5, 12]. element = 11 here
        for (int i = start; i <= end - 1; i++) {
            int temp = array[i];
            if (temp > array[i + 1]) {
                array[i] = array[i + 1];
                array[i + 1] = temp;
            } else {
                break;// As you are breaking out of the loop, it is not a bubble sort. it is an insertion sort.
            }
        }
    }

    /*private static <T> void bubbleSort(Comparable<T>[] comparables) {
        for (int i = comparables.length - 1; i >= 0; i--) { // outer loop is backward
            for (int j = 0; j <= i - 1; j++) { // inner loop is forward from 0 to i-1
                if (greater(comparables[j], (T) comparables[j + 1])) { // compare inner loops two adjustant elements
                    exchange(comparables, i, j);// swap elements --- number of swaps are N^2
                }
            }
        }
        System.out.println("Sorted Array:" + Arrays.asList(comparables));
    }*/

    /*
        int[] A = {9 5 4 2 1 7}

        Unlike to Bubble Sort, instead of comparing an element with rest of the elements, selection sort finds out the min from rest of the elements and does just one swap.

        e.g.
        9 and min from rest of the elements is 1 (its index is 4).
        So, exchange A[0] with A[4]

        1 5 4 2 9 7

        5 and min from rest of the elements is 2 (its index is 3).
        So, exchange A[1] with A[3]

        1 2 4 5 9 7

        and so on...
     */
    private static <T> void selectionSort(Comparable<T>[] comparables) {
        for (int i = 0; i <= comparables.length - 1; i++) {// outer loop is forward
            int min = i; // min variable contains an index of array and not an array element
            for (int j = i + 1; j <= comparables.length - 1; j++) {// inner loop is also forward
                if (greater(comparables[min], comparables[j])) {
                    min = j; // you are swapping here after every comparison
                }
            }
            exchange(comparables, i, min);
        }
        System.out.println("Sorted Array:" + Arrays.asList(comparables));
    }

    /*
            i
        5   1   2   9   0
            j

        consider left side of i is always sorted.
        to sort i to right side of i, compare j with j-1
        if j is less than j-1, then swap them and so on
        otherwise, break and increment i and j

                i
        1   5   2   9   0
           j-1  j

                i
        1   2   5   9   0
       j-1  j

                   i
       1   2   5   9   0
              j-1  j

                       i
       1   2   5   9   0
                  j-1  j


                       i
       1   2   5   0   9
              j-1   j

                       i
       1   2   0   5   9
          j-1  j

                       i
       1   0   2   5   9
      j-1  j

                      i
      0  1   2   5   9
      j

     */
    private static <T> void insertionSort(Comparable<T>[] comparables) {
        // worst case (when elements are in descending order) execution time: 1 + 2 + 3 + 4 + 5 + ..... (N-1) = N(N-1)/2 = O(N^2)
        // best case (when all elements are already sorted) execution time: 1 + 1 + 1 +.....+1 = N
        for (int i = 1; i < comparables.length; i++) {// outer loop starts from 2nd element
            for (int j = i; j - 1 >= 0; j--) {// inner loop - all left side elements to outer loop element
                if (less(comparables[j], comparables[j - 1])) { // Swap elements till outer loop element < inner loop element.
                    exchange(comparables, j, j - 1);

                } else {
                    // break is very important to give O(N) in best case scenario
                    break; // As soon as you find that outer loop element is greater or equal to one left side element, break the inner loop.
                }
            }
        }
        System.out.println("Sorted Array:" + Arrays.asList(comparables));
    }

    public static void insertionSort2(int array[]) {
        for (int i = 1; i < array.length; i++) {
            int ele = array[i];
            int ii = i;
            for (int j = i - 1; j >= 0; j--) {
                int ele2 = array[j];
                if (ele2 <= ele) {
                    break;
                }
                int temp = array[ii];
                array[ii] = array[j];
                array[j] = temp;
                ii--;

            }

        }
    }
    /*
            i
        5   1   2   9   0

        if(A[i] < A[i-1]) {

            compare and swap A[i] with previous elements of an array.

        }
     */
        private static void insertionSort_another_way (Integer[]A){

            for (int i = 1; i < A.length; i++) {
                if (A[i] < A[i - 1]) {
                    compareAndSwap(A, i);
                }
            }
            System.out.println("Sorted Array:" + Arrays.asList(A));
        }

        private static void compareAndSwap (Integer[]A,int eleIndex){

            for (int i = eleIndex - 1; i >= 0; i--) {

                if (A[eleIndex] < A[i]) {

                    // swap
                    int temp = A[eleIndex];
                    A[eleIndex] = A[i];
                    A[i] = temp;

                    // after swapping, eleIndex changes to new position
                    eleIndex = i;

                } else {

                    break;

                }
            }
        }

        // use for descending sort
        private static <T > boolean less (Comparable < T > t1, T t2){
            return t1.compareTo(t2) < 0;
        }

        // use for descending sort
        private static <T > boolean less (Comparable < T > t1, Comparable < T > t2){
            return t1.compareTo((T) t2) < 0;
        }

        private static <T > boolean lessOrEqual (Comparable < T > t1, Comparable < T > t2){
            return t1.compareTo((T) t2) <= 0;
        }

        // Use for ascending sort
        private static <T > boolean greater (Comparable < T > t1, T t2){
            return t1.compareTo(t2) > 0;
        }

        private static <T > boolean greater (Comparable < T > t1, Comparable < T > t2){
            return t1.compareTo((T) t2) > 0;
        }

        private static void exchange ( int[] array, int i, int j){
            int temp = array[i];
            array[i] = array[j];
            array[j] = temp;
        }
        private static <T > void exchange (Comparable < T >[]comparables,int i, int j){
            Comparable<T> comparable = comparables[i];
            comparables[i] = comparables[j];
            comparables[j] = comparable;
        }


        // http://www.growingwiththeweb.com/2015/06/bucket-sort.html
        // http://www.geeksforgeeks.org/bucket-sort-2/

        // space complexity = linkedlists of size n + array of some size k. So O(n+k).
        // time complexity = O(n+k). where k is a number of buckets.
        private static void bucketSort (Integer[]array){
            int DIVIDER = 3;

            int min = array[0];
            int max = array[0];

            for (Integer element : array) {
                if (element < min) {
                    min = element;
                } else if (element > max) {
                    max = element;
                }
            }

            int totalBuckets = (max - min) / DIVIDER + 1;

        /*
            array = [2,1,4,6,3,5,7,9,3];
            bucket[0] = 2->1->3->3   --- convert linkedlist to array and sort = 1,2,3,3
            bucket[1] = 4->6->5     --- convert linkedlist to array and sort = 4,5,6
            bucket[2] = 7->9       --- convert linkedlist to array and sort = 7,9

            put them back to original array
            array = [1,2,3,3,4,5,6,7,9]

            k = total buckets
            n = number of array elements
            space complexity = linkedlists of size n + array of some size k. So n+k.
            time complexity = O(n+k) to O(nk).


         */
            LinkedList<Integer>[] buckets = new LinkedList[totalBuckets]; // space complexity = k where k is total buckets
            for (int i = 0; i < buckets.length; i++) {
                buckets[i] = new LinkedList<>();
            }

            for (Integer element : array) { // time complexity = n
                int bucketIndex = 0;
                if (min == 0 && element == 0) {
                    bucketIndex = 0;
                } else {
                    bucketIndex = (element - min) / DIVIDER; // IMPORTANT: bucket sort can be implemented only for those elements whose keys can be used as array index. So, you need a property in each element that can be used as an index of bucket.
                }
                // inserting as first element, so that while insertion, it doesn't have to traverse entire linked nilList.
                // but if you want a stable sorting, then you need to insert an element at the end of linked nilList
                buckets[bucketIndex].addFirst(element); // space complexity of all linkedlists in all buckets will be n
            }

            // time complexity = m^2 + m^2 + m^2 ~= max n
            for (int i = 0; i < buckets.length; i++) { // time complexity = k where k is total buckets
                Integer[] a = buckets[i].toArray(new Integer[0]); // space complexity = additional array of size m
                insertionSort(a); // worst time complexity = m^2  (m is number of elements in a bucket)
                buckets[i] = new LinkedList<>(Arrays.asList(a));
            }


            int count = 0;
            // time complexity = k * m = n
            for (LinkedList<Integer> bucket : buckets) {// time complexity = k
                for (Integer element : bucket) { // time complexity = m
                    array[count++] = element;
                }
            }
        }
/*
    private static void bucketSortForDoubles(double[] array) {
        int TOTAL_BUCKETS = array.length;

        double min = array[0];
        double max = array[0];

        for (double element : array) {
            if(element < min) {
                min = element;
            }
            else if(element > max) {
                max = element;
            }
        }

        double interval = ((max-min+1))/TOTAL_BUCKETS;

        LinkedList<Double>[] buckets = new LinkedList[TOTAL_BUCKETS]; // space complexity = k where k is total buckets
        for (int i = 0; i < buckets.length; i++) {
            buckets[i] = new LinkedList<>();
        }

        for (double element : array) { // time complexity = n

            buckets[(int)((element - min)/interval)].addFirst(element);
        }

        // time complexity = m^2 + m^2 + m^2 ~= max n
        for (int i = 0; i < buckets.length; i++) { // time complexity = k where k is total buckets
            Double[] a = buckets[i].toArray(new Double[0]); // space complexity = additional array of size m
            insertionSort(a); // worst time complexity = m^2  (m is number of elements in a bucket)
            buckets[i] = new LinkedList<>(Arrays.asList(a));
        }


        int count = 0;
        // time complexity = k * m = n
        for (LinkedList<Double> bucket : buckets) {// time complexity = k
            for(Double element:bucket) { // time complexity = m
                array[count++] = element;
            }
        }
    }*/
    }
