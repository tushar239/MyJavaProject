package algorithms._6sort__search_merge.sort_and_binarysearch;

// Binary Search can be applied only for SORTED array and it takes O(log n) execution time.
// It can be used to find an element or its index in an array in O(log n) time.
// Remember, Binary Search needs access by index, so it needs an array as an input, linked list will perform bad.

// During each recursion of while loop, array size will be divided in half. So elements to compare is very less each time.
// For 10 elements it hardly takes 2 to 3 recursion of while loop. log10 is around 2.3.
/*
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
        if (end < start) {
            return false;
        }

        int middle = (start + end) == 0 ? 0 : (start + end) / 2;// or (end-start)/2 + start
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
