package algorithms._5matrix._10dynamic_programming;

import algorithms.utils.ArrayUtils;

/*
    1) Maximum path sum in matrix
    https://www.geeksforgeeks.org/maximum-path-sum-matrix/


    Given a matrix of N * M. Find the maximum path sum in matrix. The maximum path is sum of all elements from first row to last row where you are allowed to move only down or diagonally to left or right.
    NOTE: You can start from any element in first row.

    This algorithm is similar to MinOddCostPathInMatrix.java




   2) Maximum sum path in a matrix from top to bottom
    https://www.geeksforgeeks.org/maximum-sum-path-matrix-top-bottom/

    Consider a n*n matrix. Suppose each cell in the matrix has a value assigned.
    We can go from each cell in row i to a diagonally higher cell in row i+1 only [i.e from cell(i, j) to cell(i+1, j-1) and cell(i+1, j+1) only].
    Find the path from the top row to the bottom row following the aforementioned condition such that the maximum sum is obtained.


    IMPORTANT:
    This algorithm is similar to MinOddCostPathInMatrix.java with just one difference.
                                                                                                                            ^   ^
    It will be easier to implement this algorithm starting from endCell (instead of startCell) due to traversal directions   \ / . You need to go up. It is easier to think of going up from last row, instead of first row.
    maxSumPath value for first row will be same as cell value. This will be an exit condition.
    As you can go from any cell of first row to any cell of last row, you need to find out maxSumPath for all cells of last row.
    In memoization table, result will in the last row cells.

 */
public class _0_3MaxPathSumInMatrix {

    public static void main(String[] args) {
        int matrix[][] = {
                {5, 6, 1, 7},
                {-2, 10, 8, -1},
                {3, -7, -9, 11},
                {12, -4, 2, 6}
        };// O/P: 28

        int maxSumPathFromTopToBottom = findMaxSumPath(matrix);
        System.out.println("Max Sum Path from any cell of top row to any cell of bottom row: " + maxSumPathFromTopToBottom);
    }

    private static int findMaxSumPath(int[][] matrix) {
        if (matrix == null) return Integer.MIN_VALUE;

        // initializing memoization table to Integer.MIN_VALUE because your top-down approach has a formula to find MAX sum path value coming from different paths.
        int memory[][] = new int[matrix.length][matrix[0].length];
        for (int row = 0; row < matrix.length; row++) {
            for (int col = 0; col < matrix[row].length; col++) {
                memory[row][col] = Integer.MIN_VALUE;
            }
        }

        int startCellX = 0;
        int startCellY = 0;
        int endCellX = matrix.length - 1;

        /*
         IMPORTANT:
         Requirement is to be able to traverse from any cell of first row any cell of bottom row.
         When you start from top-left cell, due to directions of traversal \/, you will able to find out the maxSumPath only for bottom-right cell.
         All other cells in the the last row will not be traversed. So, you will not be able to find maxSumPath for them. Memoization table will not have their maxSumPath values.

         e.g. matrix
                 5   6   1   7
                   ^    ^
                    \  /
                 -2  10  8  -1
                       ^    ^
                       \   /
                 3  -7  -9  11
                          ^    ^
                          \   /
                 12 -4  2   6


         Memoization table

             0    1    2    3
           ----|----|----|----|
        0| 5   |MIN |1   |MIN |

        1| MIN |15  |MIN  |0  |

        2| MIN  |MIN  |6  |MIN  |

        3| MIN  |MIN  |MIN  |12  |

        So, you need to find the min cost for each cell of the first row.


        You need to call findMinCost method for each cell in the first row and you need to take min of those.

        Memoization table

             0    1    2    3
           ----|----|----|----|
        0| 5   |6   |1   |7   |

        1| 4   |15  |15  |0   |

        2| 18  |8   |6   |26  |

        3| 20  |14  |28  |12  |
        */

        int maxSumPathFromTopToBottom = Integer.MIN_VALUE;
        for (int col = matrix[endCellX].length - 1; col >= 0; col--) {
            int endCellY = col;

            int maxSumPath = findMaxSumPath(matrix, startCellX, startCellY, endCellX, endCellY, memory);

            if (maxSumPath > maxSumPathFromTopToBottom) {
                maxSumPathFromTopToBottom = maxSumPath;
            }
        }

        System.out.println("Memoized Table");
        ArrayUtils.prettyPrintMatrix(memory);

        return maxSumPathFromTopToBottom;
    }

    /*
                                           ^     ^
        Due to the directions of traversal \ or /  (upLeftDiagonal, upRightDiagonal), it is easier to think of traversing from last row to first row instead of first row to last row.
        We are using dynamic programming due to overlapping traversal paths. Memoization table will have maxSumPath value in the cell from where you started the traversal(endCell).
    */
    private static int findMaxSumPath(int[][] matrix, int startCellX, int startCellY, int endCellX, int endCellY, int[][] memory) {
        if (!ArrayUtils.inRange(matrix, endCellX, endCellY)) {
            return Integer.MIN_VALUE;
        }

        // Dynamic Programming: returning memoized value
        if (memory[endCellX][endCellY] != Integer.MIN_VALUE) {
            return memory[endCellX][endCellY];
        }

        int currentCellValue = matrix[endCellX][endCellY];

        /*
         IMPORTANT:
         You are decreasing row value and increasing/decreasing col value.
         So, you can reach to any cell in first row or first/last col
         If you reach to any cell in first/last col (except first row cell), you can still traverse up in the matrix. So, there is no need of any exit condition for reaching to first/last col cell.
         If you reach to any cell in first row, there is no way you can traverse more in the matrix. So, you need an exit condition for reaching to any cell in first row.
         When you reach to any cell of top row, there is no way that you can traverse further.
         So, max sum path value from first row cell to first row cell will be the value of that cell.
        */
        if (endCellX == startCellX) {
            // Dynamic Programming memoization
            memory[endCellX][endCellY] = currentCellValue;
            return currentCellValue;
        }

        int upLeftDiagonal = findMaxSumPath(matrix, startCellX, startCellY, endCellX - 1, endCellY - 1, memory);
        int downLeftDiagonal = findMaxSumPath(matrix, startCellX, startCellY, endCellX - 1, endCellY + 1, memory);

        int max = Math.max(upLeftDiagonal, downLeftDiagonal);

        /*// this condition is not possible because at least one of the upLeftDiagonal and downLeftDiagonal will be other than Integer.MIN_VALUE
        if(max == Integer.MIN_VALUE) {

        }*/

        int value = currentCellValue + max;

        // Dynamic Programming memoization
        memory[endCellX][endCellY] = value;

        return value;

    }
}
