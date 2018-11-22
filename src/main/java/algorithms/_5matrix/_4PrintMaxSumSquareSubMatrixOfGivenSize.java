package algorithms._5matrix;

/*
    Print maximum sum square sub-matrix of given size

    https://www.geeksforgeeks.org/print-maximum-sum-square-sub-matrix-of-given-size/

    Given an N x N matrix, find a k x k submatrix where k <= N and k >= 1, such that sum of all the elements in submatrix is maximum.
    The input matrix can contain zero, positive and negative numbers.

    e.g.

    1   2   -1  4
    -8  -3  4   2
    3   8   10  -8
    -4  -1  1   7

    k=3

    answer is

    1    2   -1  4
       ------------
    -8 | -3  4   2 |
    3  | 8   10  -8|
    -4 | -1  1   7 |
       -------------

    Simple solution:

    Start from last cell.
    Calculate sum of K x K matrix from last cell. That becomes your so far max sum subarray.
    Then do the same for rest of the cells and keep track of max sum subarray.
    Time Complexity = O((N x N)(K x K))

    Better Solution:

    Count sum of all cells in each row. Takes O(N x N).

    row  sum
    0 -> 6
    1 -> -5
    2 -> 13
    3 -> 3

    Count sum of all cells in each col. Takes O(N x N).

    col sum
    0 -> -8
    1 -> 6
    2 -> 14
    3 -> 5

    Count sum of all cells in the matrix. Takes O(N), if you just iterate through one of the above map and take the sum.
    17


    Now, start from last cell (3,3). Submatrix will go till (1,1)
    This submatrix's sum will be 17 - 0th row's sum - 0th col's sum.

    Likewise, submatrix from (2,2) will go till (0,0).
    This submatrix's sum will be 17 - 3rd row's sum - 3rd col's sum.

    So, you have to subtract the sum of rows and cols that fall outside the submatrix.

    This process will take O(N x N).
*/
public class _4PrintMaxSumSquareSubMatrixOfGivenSize {
}
