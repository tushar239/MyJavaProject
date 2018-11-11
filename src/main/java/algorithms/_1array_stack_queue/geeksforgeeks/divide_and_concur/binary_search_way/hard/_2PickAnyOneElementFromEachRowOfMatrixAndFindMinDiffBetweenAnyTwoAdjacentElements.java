package algorithms._1array_stack_queue.geeksforgeeks.divide_and_concur.binary_search_way.hard;

import java.util.Arrays;

/*
    Minimum difference between adjacent elements of array which contain elements from each row of a matrix

    https://www.geeksforgeeks.org/minimum-difference-adjacent-elements-array-contain-elements-row-matrix/

    3   4   13
    2   12  15
    -1  0   5

    Find one element from each row in such a way that difference between any two adjacent elements is minimum.

    Brute-Force Approach:
        compare every element of first row with every element of second row and find the differences and keep the minimum one.
        then do the same between second and third row
        and so on.

    Better Approach:

        Looking at Brute-Force approach, you realize that you need closest >= element and <= element from second row for the first row element.

        First sort all elements in each row.

        Can you do better by not comparing every single element of second row?
        yes, by using binary search on second row.
        Using binary search, find closest >= element in second row for first row element.
        Element just before this closest >= element will be closest <= element.

        To do Binary Search, all rows should be sorted first.


    Important concept of when can you use Binary Search type divide-and-concur:

        When there are many possibilities, but you need to find one of them, then you can use Binary Search type divide-and-concur concept.
        There can be many >= elements in second row for a first row element, but you need to find just one. So, you can use Binary Search type divide-and-concur concept.
*/
public class _2PickAnyOneElementFromEachRowOfMatrixAndFindMinDiffBetweenAnyTwoAdjacentElements {

    public static void main(String[] args) {
        // Testing Binary Search algorithm to find closest greater or equal element from given element
        System.out.println("Testing Binary Search algorithm to find closest greater or equal element from given element");

        int arr[] = {1, 3, 5, 6, 7, 9, 10};

        int start = 0;
        int end = arr.length - 1;

        {
            int givenElement = 4;
            int closetGreaterOrEqualElementIndex = binarySearchToFindClosestGreaterOrEqualElement(arr, start, end, givenElement);
            System.out.println(closetGreaterOrEqualElementIndex);//2

        }

        {
            int givenElement = 2;
            int closetGreaterOrEqualElementIndex = binarySearchToFindClosestGreaterOrEqualElement(arr, start, end, givenElement);
            System.out.println(closetGreaterOrEqualElementIndex);//1
        }

        {
            int givenElement = 11;
            int closetGreaterOrEqualElementIndex = binarySearchToFindClosestGreaterOrEqualElement(arr, start, end, givenElement);
            System.out.println(closetGreaterOrEqualElementIndex);//-1
        }

        // Testing actual algorithm of finding min abs diff between any one-one elements from each row.
        System.out.println("Testing actual algorithm of finding min abs diff between any one-one elements from each row");
        {
            int A[][] = {
                    new int[]{3, 4, 13},
                    new int[]{2, 12, 15},
                    new int[]{-1, 0, 5},
            };

            //findMinAbsDiffBetElementsFromRows(A);//1  (diff bet 3 and 2)

            System.out.println("Trying Brute-Force Approach");
            bruteForceApproach(A);//1

            System.out.println("Trying Better Approach");
            betterApproach(A);//1
        }

        System.out.println();

        {
            int A[][] = {
                    new int[]{30, 40, 5},
                    new int[]{2, 12, 15},
                    new int[]{-1, 0, 5},
            };

            //findMinAbsDiffBetElementsFromRows(A);//2  (diff bet 2 and 0)

            System.out.println("Trying Brute-Force Approach");
            bruteForceApproach(A);//2

            System.out.println("Trying Better Approach");
            betterApproach(A);//2
        }
    }


    private static void findMinAbsDiffBetElementsFromRows(int[][] A) {
        if (A == null || A.length < 2) return;

        // sort all rows first
        sortAllRows(A);

        int minAbsDiffBetElementsFromRows = findMinAbsDiffBetElementsFromRows(A, 0, A.length - 1, 0, A[0].length - 1);
        System.out.println(minAbsDiffBetElementsFromRows);
    }

    private static void sortAllRows(int[][] A) {
        for (int row = 0; row <= A.length - 1; row++) {
            Arrays.sort(A[row]);
        }
    }

    private static void betterApproach(int[][] A) {
        if (A == null || A.length < 2) return;

        for (int row = 0; row < A.length; row++) {
            Arrays.sort(A[row]);
        }

        int min = Integer.MAX_VALUE;

        for (int row = 0; row < A.length; row++) {

            int nextRow = row + 1;

            if (nextRow == A.length) break;

            for (int col = 0; col < A[row].length; col++) {

                int ele = A[row][col];

                int closetGreaterOrEqualEleIndex = binarySearchToFindClosestGreaterOrEqualEleIndex(A[nextRow], 0, A[nextRow].length - 1, ele);

                int closetSmallerEleIndex = binarySearchToFindClosestSmallerEleIndex(A[nextRow], 0, closetGreaterOrEqualEleIndex == -1 ? A[nextRow].length - 1 : closetGreaterOrEqualEleIndex, ele);


                if (closetGreaterOrEqualEleIndex != -1) {
                    min = Math.min(min, Math.abs(ele - A[nextRow][closetGreaterOrEqualEleIndex]));
                }

                if (closetSmallerEleIndex != -1) {
                    min = Math.min(min, Math.abs(ele - A[nextRow][closetSmallerEleIndex]));
                }

            }
        }

        System.out.println("min: " + min);
    }

    private static int binarySearchToFindClosestGreaterOrEqualEleIndex(int[] nextRow, int start, int end, int ele) {
        if (start > end) return -1;

        int mid = (start + end) / 2;

        if (nextRow[mid] >= ele) {

            int closerGreater = binarySearchToFindClosestGreaterOrEqualEleIndex(nextRow, start, mid - 1, ele);

            if (closerGreater == -1) {
                return mid;
            } else {
                if (nextRow[closerGreater] < ele) {
                    return mid;
                }
                return closerGreater;
            }
        }

        return binarySearchToFindClosestGreaterOrEqualEleIndex(nextRow, mid + 1, end, ele);

    }

    private static int binarySearchToFindClosestSmallerEleIndex(int[] nextRow, int start, int end, int ele) {
        if (start > end) return -1;

        int mid = (start + end) / 2;

        if (nextRow[mid] < ele) {

            int closerSmaller = binarySearchToFindClosestSmallerEleIndex(nextRow, mid + 1, end, ele);

            if (closerSmaller == -1) {
                return mid;
            } else {
                if (nextRow[closerSmaller] >= ele) {
                    return mid;
                }
                return closerSmaller;
            }
        }

        return binarySearchToFindClosestSmallerEleIndex(nextRow, start, mid - 1, ele);
    }


    private static int findMinAbsDiffBetElementsFromRows(int[][] A, int startRow, int endRow, int startCol, int endCol) {
        int minDiff = Integer.MAX_VALUE;

        if (A == null) return minDiff;

        if (startRow == endRow) return minDiff;

        for (int col = startCol; col <= endCol; col++) {

            if (startRow < endRow) {// for making sure that A[startRow + 1] doesn't throw ArrayIndexOutOfBoundException

                // pick every element from a row for which closest greater or equal and closest lesser or equal elements need to be found
                int elementToCompareWithClosestGreaterAndLesserElementsFromNextRow = A[startRow][col];

                // find closest greater or equal element's index from next row (This is where Binary Search is useful)
                int closestGreaterOrEqualElementIndexFromNextRow = getClosestGreaterOrEqualElementIndexFromNextRow(binarySearchToFindClosestGreaterOrEqualElement(A[startRow + 1], startCol, endCol, elementToCompareWithClosestGreaterAndLesserElementsFromNextRow), endCol);

                // diff between elementToCompareWithClosestGreaterAndLesserElementsFromNextRow and closestGreaterOrEqualElement from next row
                int diff1 = findDiff1(A, startRow, endRow, startCol, endCol, closestGreaterOrEqualElementIndexFromNextRow, elementToCompareWithClosestGreaterAndLesserElementsFromNextRow);

                // diff between elementToCompareWithClosestGreaterAndLesserElementsFromNextRow and closestLesserOrEqualElement from next row
                int diff2 = findDiff2(A, startRow, endRow, startCol, endCol, closestGreaterOrEqualElementIndexFromNextRow, elementToCompareWithClosestGreaterAndLesserElementsFromNextRow);

                minDiff = Math.min(minDiff, Math.min(diff1, diff2));

            }
        }

//        if (startRow < endRow) { // there is an exit condition checking startRow == endRow to avoid this if condition
        return Math.min(minDiff, findMinAbsDiffBetElementsFromRows(A, startRow + 1, endRow, startCol, endCol));
//        }
//        return minDiff;

    }

    private static int getClosestGreaterOrEqualElementIndexFromNextRow(int closestGreaterOrEqualElementIndexFromNextRow1, int endCol) {
        int closestGreaterOrEqualElementIndexFromNextRow = closestGreaterOrEqualElementIndexFromNextRow1;
        if (closestGreaterOrEqualElementIndexFromNextRow == -1) {
            closestGreaterOrEqualElementIndexFromNextRow = endCol;
        }
        return closestGreaterOrEqualElementIndexFromNextRow;
    }

    private static int findDiff1(int[][] A, int startRow, int endRow, int startCol, int endCol, int closestGreaterOrEqualElementIndexFromNextRow, int elementToCompareWith) {
        int closetGreaterOrEqualElementFromNextRow = A[startRow + 1][closestGreaterOrEqualElementIndexFromNextRow];

        int diff = Math.abs(elementToCompareWith - closetGreaterOrEqualElementFromNextRow);

        return diff;
    }

    private static int findDiff2(int[][] A, int startRow, int endRow, int startCol, int endCol, int closestGreaterOrEqualElementIndexFromNextRow, int elementToCompareWith) {
        int diff = Integer.MAX_VALUE;

        if (closestGreaterOrEqualElementIndexFromNextRow > 0) {
            int closetLesserOrEqualElementIndexFromNextRow = closestGreaterOrEqualElementIndexFromNextRow - 1;
            int closetLesserOrEqualElementFromNextRow = A[startRow + 1][closetLesserOrEqualElementIndexFromNextRow];
            diff = Math.abs(elementToCompareWith - closetLesserOrEqualElementFromNextRow);
        }
        return diff;
    }

    private static int binarySearchToFindClosestGreaterOrEqualElement(int[] arr, int start, int end, int element) {
        if (start > end) return -1;

        int mid = (start + end) / 2;

        int midElement = arr[mid];

        if (midElement == element) {
            return mid;
        }

        if (midElement > element) {
            int evenCloserGreaterOrEqualElementIndex = binarySearchToFindClosestGreaterOrEqualElement(arr, start, mid - 1, element);
            if (evenCloserGreaterOrEqualElementIndex == -1) {
                return mid;
            }
            return evenCloserGreaterOrEqualElementIndex;
        }

        return binarySearchToFindClosestGreaterOrEqualElement(arr, mid + 1, end, element);
    }

    /*
        Brute-Force Approach:

        3   4   13
        2   12  15
        -1  0   2


        min(abs(3-2), abs(3-12), abs(3-15)

        min(abs(4-2), abs(4-12), abs(4-15)

        min(abs(13-2), abs(13-12), abs(13-15)


        min(abs(2-(-1)), abs(2-0), abs(2-2)

        min(abs(12-(-1)), abs(12-0), abs(12-2)

        min(abs(15-(-1)), abs(15-0), abs(15-2)


        min of all is the answer.

        Time complexity: you are visiting every single element of a matrix so O(rows * cols).
                        For every single element, you are visiting all elements of next row, so O(cols * (rows * cols)).
                        You can reduce it to O(2 log cols * (rows * cols)) using binary search.


    */
    private static void bruteForceApproach(int[][] A) {

        int rows = A.length;

        /*// sort all the rows first --- no need for Brute-Force

        for (int i = 0; i < rows; i++) {
            Arrays.sort(A[i]);
        }*/

        // find min

        int min = Integer.MAX_VALUE;

        for (int i = 0; i < rows; i++) {

            int currentRow = i;
            int nextRow = i + 1;

            if (nextRow == rows) break; // there is no next row to compare

            int cols = A[i].length;

            for (int j = 0; j < cols; j++) {

                int ele = A[i][j]; // visit every element of a matrix

                int minX = findMin(A, ele, nextRow); // find min diff between an element and next row elements

                if (minX < min) min = minX; // keep track of min of all
            }

        }

        System.out.println("min: " + min);
    }

    private static int findMin(int[][] A, int ele, int nextRow) {
        int min = Integer.MAX_VALUE;

        int cols = A[nextRow].length;

        for (int j = 0; j < cols; j++) {

            //int firstCol = j;
            //int secondCol = j+1;

            //if(secondCol == cols) break;

            //int minX = Math.min(Math.abs(ele - A[nextRow][firstCol]), Math.abs(ele-A[nextRow][secondCol]));
            int diff = Math.abs(ele - A[nextRow][j]);

            if (diff < min) min = diff;
        }

        return min;
    }
}
