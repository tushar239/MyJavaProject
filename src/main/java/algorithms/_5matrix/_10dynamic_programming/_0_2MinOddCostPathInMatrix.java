package algorithms._5matrix._10dynamic_programming;

import algorithms.utils.ArrayUtils;

/*
    Minimum odd cost path in a matrix

    https://www.geeksforgeeks.org/minimum-odd-cost-path-in-a-matrix/

    My understanding of this algorithm requirement:
    Given a matrix, the task is to find the cost of the minimum path anywhere from top of the matrix to anywhere to bottom of the matrix.
    This cost has to be odd. If no such path exists, print -1.

    Only right-bottom, left-bottom and direct bottom moves are allowed.
                    / | \


    e.g.
        {1,  5,  2},
        /|\
        {7,  2,  2},
        /|\ /|\
        {2,  8,  1}


    IMPORTANT:
    This is a very important algorithm to understand when do you need find min cost for each cell of first row separately.


    Solution:
    You need to find min cost to reach from any cell in first row to any cell last row. And then find the min odd cost from them.

    First find min cost using dynamic programming for each cell.

         IMPORTANT: It is not enough to find min cost only for first cell of top row. You need to find it for each cell of top row.
         For each cell of top row, memoizing min cost to reach from top to bottom of the matrix
         you need to do this because of directions of traversal

        {1,  5,  2},
        /|\
        {7,  2,  2},
        /|\ /|\
        {2,  8,  1}

        This would be the case, if you just find the cost for first cell. other cells of first row will not be traversed.
        So, you need to find the min cost for each cell of the first row.

    If you use startCell to start traversing, then min odd of all cost values in first row will be the answer.
    If you use endCell to start traversing, then min odd of all cost values in last row will be the answer.


    Time Complexity:

        Brute-Force:
                            min
              min           min          min
           min min min  min min min  min min min


           3^0 + 3^1 + 3^2 +..... = O(3^mn) - exponential
           Just like how fibonacci series takes O(2^n) using Brute-Force

        Dynamic Programming can reduce it to O(3mn). Just like how fibonacci series takes O(2n) using Dynamic Programming
        m is number of rows
        n is number of cols
*/
public class _0_2MinOddCostPathInMatrix {
    public static void main(String[] args) {
        {
            int matrix[][] =
                    {
                            {1, 5, 2},
                            {7, 2, 2},
                            {2, 8, 1}
                    };// O/P=5  (0,1)->(1,1)->(2,2)
            int minOddCostFromTopToBottom = findMinOddCostFromTopToBottom(matrix);
            System.out.println(minOddCostFromTopToBottom);
        }
        {
            int matrix[][] =
                    {
                            {1, 2, 3, 4, 6},
                            {1, 2, 3, 4, 5},
                            {1, 2, 3, 4, 5},
                            {1, 2, 3, 4, 5},
                            {100, 2, 3, 4, 5}
                    };

            int minOddCostFromTopToBottom = findMinOddCostFromTopToBottom(matrix);
            System.out.println(minOddCostFromTopToBottom);

            int min_odd_cost = find_min_odd_cost_from_geeks_for_geeks(matrix);
            System.out.println(min_odd_cost);

        }
    }

    private static int findMinOddCostFromTopToBottom(int[][] matrix) {
        if (matrix.length == 0) return 0;

        // initializing memoization table to Integer.MAX_VALUE because your top-down approach has a formula to find MIN cost coming from different paths.
        int[][] memory = new int[matrix.length][matrix[0].length];
        for (int row = 0; row < matrix.length; row++) {
            for (int col = 0; col < matrix[row].length; col++) {
                memory[row][col] = Integer.MAX_VALUE;
            }
        }

/*
         IMPORTANT:
         Requirement is to be able to traverse from any cell of first row any cell of bottom row.
         When you start from top-left cell, due to directions of traversal /|\, you will able to find out the min cost only for top-left cell in the first row.
         All other cells in the the first row will not be traversed. So, you will not be able to find min cost for them.

        {1,  5,  2},
        /|\
        {7,  2,  2},
        /|\ /|\
        {2,  8,  1}

        So, you need to find the min cost for each cell of the first row.
        You need to call findMinCost method for each cell in the first row and you need to take min of those.

*/

        int minOddCostPathFromTopToBottom = Integer.MAX_VALUE;

        for (int col = 0; col < matrix[0].length; col++) {
            int startCellX = 0;
            int startCellY = col;//for each cell of first row
            int endCellX = matrix.length - 1;
            int endCellY = matrix[startCellX].length - 1;

            int minCost = findMinCost(matrix, startCellX, startCellY, endCellX, endCellY, memory);

            // Finding the min odd cost from first row.
            if (minCost % 2 == 1) {
                if (minCost < minOddCostPathFromTopToBottom) {
                    minOddCostPathFromTopToBottom = minCost;
                }
            }
        }
        //System.out.println(minCost);

        System.out.println("Memoized Table");
        ArrayUtils.prettyPrintMatrix(memory);

        /* This will also work.
        // Finding the min odd cost from first row.
        int minOddCostPathFromTopToBottom = Integer.MAX_VALUE;
        for (int cell = 0; cell < memory.length; cell++) {
            int topRowCellValue = memory[0][cell];
            if (topRowCellValue % 2 == 1) {
                if (topRowCellValue < minOddCostPathFromTopToBottom) {
                    minOddCostPathFromTopToBottom = topRowCellValue;
                }
            }
        }*/
        if (minOddCostPathFromTopToBottom != Integer.MAX_VALUE) {
            return minOddCostPathFromTopToBottom;
        }
        return -1;
    }

    private static int findMinCost(int[][] matrix, int startCellX, int startCellY, int endCellX, int endCellY, int[][] memory) {
        // As you need to find min cost from 3 directions of traversal, you should return Integer.MAX_VALUE in case of out of range condition.
        if (!ArrayUtils.inRange(matrix, startCellX, startCellY)) {
            return Integer.MAX_VALUE;
        }

        // Dynamic Programming: returning memoized value
        if (memory[startCellX][startCellY] != Integer.MAX_VALUE) {
            return memory[startCellX][startCellY];
        }

        int currentCellValue = matrix[startCellX][startCellY];

        // If you reach to the last row (bottom of the matrix), min cost to reach from those cells to bottom of the matrix will be those cell values only.
        if (startCellX == endCellX) {
            // Dynamic Programming memoization
            memory[startCellX][startCellY] = currentCellValue;

            return currentCellValue;
        }

        int downLeftDiagonal = findMinCost(matrix, startCellX + 1, startCellY - 1, endCellX, endCellY, memory);
        int down = findMinCost(matrix, startCellX + 1, startCellY, endCellX, endCellY, memory);
        int downRightDiagonal = findMinCost(matrix, startCellX + 1, startCellY + 1, endCellX, endCellY, memory);

        int min = Math.min(downLeftDiagonal, Math.min(down, downRightDiagonal));

/*
        // This will never be the case
        if(min == Integer.MAX_VALUE) {
            return currentCellValue;
        }
*/

        int value = currentCellValue + min;

        // Dynamic Programming memoization
        memory[startCellX][startCellY] = value;

        return value;
    }


    // from geeksforgeeks
    private static int find_min_odd_cost_from_geeks_for_geeks(int matrix[][]) {
        int memory[][] = new int[matrix.length][matrix[0].length];
        int minOddCost = 0;
        int row, col, temp;

        for (col = 0; col < matrix[0].length; col++) {
            memory[0][col] = matrix[0][col];
        }

        int lastRow = matrix.length - 1;
        for (row = 1; row < matrix.length; row++)
            for (col = 0; col < matrix[row].length; col++) {

                // leftmost element
                if (col == 0) {
                    memory[row][col] = matrix[row][col];
                    memory[row][col] += Math.min(memory[row - 1][col], memory[row - 1][col + 1]);
                }

                // rightmost element
                else if (col == lastRow) {
                    memory[row][col] = matrix[row][col];
                    memory[row][col] += Math.min(memory[row - 1][col], memory[row - 1][col - 1]);
                }

                // Any element except leftmost and rightmost element of a row
                // is reachable from direct upper or left upper or right upper
                // row's block
                else {

                    // Counting the minimum cost
                    temp = Math.min(memory[row - 1][col], memory[row - 1][col - 1]);
                    temp = Math.min(temp, memory[row - 1][col + 1]);
                    memory[row][col] = matrix[row][col] + temp;
                }
            }

        minOddCost = Integer.MAX_VALUE;

        // Find the minimum cost
        for (col = 0; col < matrix.length; col++) {
            int value = memory[lastRow][col];
            if (value % 2 == 1) {
                if (value < minOddCost)
                    minOddCost = value;
            }
        }

        if (minOddCost == Integer.MIN_VALUE)
            return -1;

        return minOddCost;
    }

}
