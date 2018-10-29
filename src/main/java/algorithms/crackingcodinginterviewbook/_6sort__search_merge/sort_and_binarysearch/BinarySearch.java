package algorithms.crackingcodinginterviewbook._6sort__search_merge.sort_and_binarysearch;

/*

Fundamentals:

    When to use Binary Search?

        Binary Search works best on already sorted array (SortedSearch.java, SparseSearch.java) or matrix (SortedMatrixSearch.java)

        Binary Search can be used on unsorted array to find peaks and valleys (PeakAndValleyInUnOrderedArray.java)

        Remember, Binary Search needs access by index, so it needs an array as an input, it will perform bad on sorted linkedlist.
        You can create an array from sorted linkedlist first and apply binary search on it.

    When to use Binary Search Tree instead of Binary Search?

        Binary Search works best on already sorted array (SortedSearch.java, SparseSearch.java) or matrix (SortedMatrixSearch.java)

        If you want to search an element in unsorted array, you need to sort it first before you can search. This takes at least O(n log n) for sorting and O(log n) for binary search.

        You can do better by searching an element in BST. Inserting elements will reserve O(n) and searching an element will reserve O(log n) provided created BST is close to balanced.
        BST is worse for sorted array. It will created totally unbalanced tree.

    When to use Min/Max-Heap(Priority Queue) Binary Search or BST?

        When you need to search min/max element in O(1), then you use Min/Max-Heap(Priority Queue) because min element is always on the top of of the min-heap and similary max element is always on the top of max-heap.
        Remember, min/max-heap are not trees. It just keeps track of indices in the array to keep track of min/max element.

During each recursion of while loop, array size will be divided in half. So elements to compare is very less each time.
For 10 elements it hardly takes 2 to 3 recursion of while loop. log10 is around 2.3.

It will create tree only one side. As it is ignoring half of array at each level, tree height is still log n with base 2.
                             o
1 comparison                /\
                         o   ignore this half (no comparisons)
1 comparison            /\
                     o   ignore this half (no comparisons)
1 comparison        /\
                  o  ignore this half (no comparisons)
1 comparison     /

In binary search to search an element, you divide an array into two and then you keep searching on its one side.
At each level, there is only 1 comparision happens (numberToSearch with a[middle]). It takes max O(1 log n) to search an element.


 */
public class BinarySearch {
    public static void main(String[] args) {
        {
            int array[] = {1, 5, 6, 7, 9, 10, 13};
            int elementToSearch = 5;

            // Binary Search recursively
            // recursive method is a lot more easier than iterative method. It can be written with minimal code.
            // Only thing is it will use stack size of O(log n) for recursive method calls. Height of the tree created by recursive calls is same as required stack size.
            // So, Space Complexity will increase compared iterative method but Time Complexity remains same.
            if (binarySearchAlgorithmRecursive(array, 0, array.length - 1, elementToSearch)) {
                System.out.println("Result of BinarySearch using Recursive method: element " + elementToSearch + " found ");
            } else {
                System.out.println("Result of BinarySearch using Recursive method: element " + elementToSearch + " not found ");
            }

            // Binary Search iteratively
            if (binarySearchAlgorithmIterative(array, elementToSearch)) {
                System.out.println("Result of BinarySearch using Iterative method: element " + elementToSearch + " found ");
            } else {
                System.out.println("Result of BinarySearch using Iterative method: element " + elementToSearch + " not found ");
            }

            System.out.println(binary_search(array, elementToSearch)); //1

        }
    }

/*
    private static boolean binarySearchAlgorithmRecursiveAnotherWay(int[] array, int start, int end, int numberToSearch) {
        if (end < start) {
            return false;
        }

        int middle = (start + end) == 0 ? 0 : (start + end) / 2;
        if (array[middle] == numberToSearch) {
            return true;
        } else if (numberToSearch < array[middle] && binarySearchAlgorithmRecursiveAnotherWay(array, start, (middle - 1), numberToSearch)) {
            return true;
        } else if (numberToSearch > array[middle] && binarySearchAlgorithmRecursiveAnotherWay(array, middle + 1, end, numberToSearch)) {
            return true;
        }

        return false;
    }*/

    public static boolean binarySearchAlgorithmRecursive(int[] array, int start, int end, int numberToSearch) {
        if (start > end) {
            return false;
        }
        /*
            or

            if(start == end) {
                if(array[start] == numberToSearch) {
                    return true;
                }
                return false;
            }
         */

        //int middle = (start + end) == 0 ? 0 : (start + end) / 2;// or (end-start)/2 + start
        int middle = (start + end) / 2;// or (end-start)/2 + start
        if (array[middle] == numberToSearch) {
            return true;
        } else if (numberToSearch < array[middle]) {
            return binarySearchAlgorithmRecursive(array, start, (middle - 1), numberToSearch);
        }
        return binarySearchAlgorithmRecursive(array, middle + 1, end, numberToSearch);

    }

    /*
    Only Tail Recursive algorithm can be converted into Iterative approach.
    Tail Recursion means recursion happens at the end of the algorithm

    http://blog.moertel.com/posts/2013-05-11-recursive-to-iterative.html
    https://en.wikipedia.org/wiki/Tail_call
     */
    protected static boolean binarySearchAlgorithmIterative(int[] array, int numberToSearch) {
        int start = 0;
        int end = array.length - 1;
        while (end >= start) {
            int middle = (start + end) == 0 ? 0 : (start + end);
            if (array[middle] == numberToSearch) {
                return true;
            } else if (numberToSearch < array[middle]) {
                start = start;
                end = middle - 1;
            } else {
                start = middle + 1;
                end = end;
            }
        }
        return false;
    }


    protected static Integer binary_search(int[] array, int numberToSearch) {
        int start = 0;
        int end = array.length - 1;

        while (start < end) {
            if (array[start] == numberToSearch) {
                return start;
            }
            int mid = (start + end) / 2;


            if (numberToSearch < array[mid]) {
                end = mid;
            } else {
                start = mid;
            }
        }

        return null;
    }
}
