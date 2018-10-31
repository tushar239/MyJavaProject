package algorithms._1array_stack_queue.geeksforgeeks.divide_and_concur.binary_search_way.easy;

/*
    Search element in a sorted matrix
    https://www.geeksforgeeks.org/search-element-sorted-matrix/


    This algorithm is based on classic Binary Search algorithm.

    This is an important algorithm to understand the difference between How O(C + log n) is different than O(C log n).

    How O(C + log n) is different than O(C log n) ?

      SearchElementInSortedMatrix.java is an important algorithm to understand how O(C + log n) is calculated.
      In recursive tree of height (log n), if only one node id doing some operation taking O(C) time, then it comes to O(C + log n). if every node is doing that operation, then it comes to O(C log n).

      This is classic Binary Search recursive calls.

                                F(n)
                            F(n/2)
                         F(n/4)
                         ...
      e.g.
      In Binary Search, every node does O(1) operation of comparing required element with mid. So, it is O(1 log n).
      If some algorithm does O(log m) taking operation on each node, then it is O((log m) (log n)).
      This algorithm does O(log m) taking operation on only one node of entire tree, so it is O(log m + log n).

                    m(rows, cols)
            m(rows/2, cols)
        m(rows/4, cols)
    m(rows/8, cols) ----------------- binary search operation that takes O(log cols) happens only on one node

    Time Complexity of this algorithm is O(log rows + log cols).

*/
public class _2SearchElementInSortedMatrix {

    public static void main(String[] args) {
        int[][] A = {
                new int[]{1, 2, 3, 4},
                new int[]{5, 6, 7, 8},
                new int[]{9, 10, 11, 12},
                new int[]{13, 14, 15, 16}
        };
        {
            int elementToFind = 3;
            find(A, elementToFind, 0, A.length - 1, 0, A[0].length - 1);//row:0,col:2
        }
        {
            int elementToFind = 16;
            find(A, elementToFind, 0, A.length - 1, 0, A[0].length - 1);//row:3,col:3
        }
        {
            int elementToFind = 17;
            find(A, elementToFind, 0, A.length - 1, 0, A[0].length - 1);//-1
        }

    }

    private static void find(int[][] A, int elementToFind, int startRow, int endRow, int startCol, int endCol) {
        if (A == null || A.length == 0) {
            System.out.println("-1");
            return;
        }

        if (startRow > endRow) {
            System.out.println("-1");
            return;
        }

        int midRow = (startRow + endRow) / 2;

        if (elementToFind >= A[midRow][startCol] && elementToFind <= A[midRow][endCol]) {
            int column = binarySearch(A[midRow], elementToFind, startCol, endCol);
            System.out.println("row:" + midRow + ",col:" + column);
        } else if (elementToFind < A[midRow][startCol]) {
            find(A, elementToFind, startRow, midRow - 1, startCol, endCol);
        } else {
            find(A, elementToFind, midRow + 1, endRow, startCol, endCol);
        }

    }

    private static int binarySearch(int[] arr, int elementToFind, int start, int end) {
        if (start > end) return -1;

        int mid = (start + end) / 2;

        if (arr[mid] == elementToFind) {
            return mid;
        }

        if (elementToFind < arr[mid]) {
            return binarySearch(arr, elementToFind, start, mid - 1);
        }

        return binarySearch(arr, elementToFind, mid + 1, end);

    }
}
