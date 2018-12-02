package algorithms._5matrix._10dynamic_programming;

import algorithms.utils.ArrayUtils;

import static algorithms.utils.ArrayUtils.inRange;

/*
    Longest Increasing Path in Matrix

    https://www.geeksforgeeks.org/longest-increasing-path-matrix/

    Given a matrix of N rows and M columns.

    From m[i][j], we can move to
        m[i+1][j] (right), if m[i+1][j] > m[i][j],
        or
        m[i][j+1] (down), if m[i][j+1] > m[i][j].

    The task is print longest path length if we start from (0, 0).
*/
public class _4LongestIncreasingPathInMatrix {

    public static void main(String[] args) {
        {
            int matrix[][] = {
                    {1, 2, 3, 4},
                    {2, 2, 3, 4},
                    {3, 2, 3, 4},
                    {4, 5, 6, 7}
            };// O/P=7, Longest path is 1 2 3 4 5 6 7

            System.out.println("Input matrix:");
            ArrayUtils.prettyPrintMatrix(matrix);

            int startCellX = 0;
            int startCellY = 0;
            int endCellX = matrix.length - 1;
            int endCellY = matrix[0].length - 1;

            int lipSize_brute_force = findLIPSize_Brute_Force(matrix, startCellX, startCellY, endCellX, endCellY);
            System.out.println("LIP size using Brute-Force approach: " + lipSize_brute_force);


            int[][] memory = new int[matrix.length][matrix[0].length];// all cells are initialized to 0;
            int lipSize_top_down = findLIPSize_Top_Down(matrix, startCellX, startCellY, endCellX, endCellY, memory);
            System.out.println("LIP size using Top-Down approach: " + lipSize_top_down);

            int lipSize_bottom_up = findLIPSize_Bottom_Up(matrix);
            System.out.println("LIP size using Bottom-Up approach: " + lipSize_bottom_up);
        }
        System.out.println();
        {
            int matrix[][] = {
                    {1, 3, 3, 4},
                    {2, 4, 5, 6},
                    {3, 2, 3, 7},
                    {4, 5, 6, 9}
            };// O/P:7   1,3,4,5,6,7,9  or  1,2,4,5,6,7,9  or  1,2,3,4,5,6,9

            System.out.println("Input matrix:");
            ArrayUtils.prettyPrintMatrix(matrix);

            int startCellX = 0;
            int startCellY = 0;
            int endCellX = matrix.length - 1;
            int endCellY = matrix[0].length - 1;

            int lipSize_brute_force = findLIPSize_Brute_Force(matrix, startCellX, startCellY, endCellX, endCellY);
            System.out.println("LIP size using Brute-Force approach: " + lipSize_brute_force);

            int[][] memory = new int[matrix.length][matrix[0].length];// all cells are initialized to 0;
            int lipSize_top_down = findLIPSize_Top_Down(matrix, startCellX, startCellY, endCellX, endCellY, memory);
            System.out.println("LIP size using Top-Down approach: " + lipSize_top_down);

            int lipSize_bottom_up = findLIPSize_Bottom_Up(matrix);
            System.out.println("LIP size using Bottom-Up approach: " + lipSize_bottom_up);

        }
        System.out.println();

        {
            int matrix[][] = {
                    {1, 3, 3, 4},
                    {2, 4, 5, 6},
                    {3, 2, 3, 7},
                    {4, 5, 6, 1}
            };// O/P:6   1,3,4,5,6,7  or  1,2,4,5,6,7  or  1,2,3,4,5,6

            System.out.println("Input matrix:");
            ArrayUtils.prettyPrintMatrix(matrix);

            int startCellX = 0;
            int startCellY = 0;
            int endCellX = matrix.length - 1;
            int endCellY = matrix[0].length - 1;

            int lipSize_brute_force = findLIPSize_Brute_Force(matrix, startCellX, startCellY, endCellX, endCellY);
            System.out.println("LIP size using Brute-Force approach: " + lipSize_brute_force);

            int[][] memory = new int[matrix.length][matrix[0].length];// all cells are initialized to 0;
            int lipSize_top_down = findLIPSize_Top_Down(matrix, startCellX, startCellY, endCellX, endCellY, memory);
            System.out.println("LIP size using Top-Down approach: " + lipSize_top_down);

            //System.out.println("Memoized Table:");
            //ArrayUtils.prettyPrintMatrix(memory);

            int lipSize_bottom_up = findLIPSize_Bottom_Up(matrix);
            System.out.println("LIP size using Bottom-Up approach: " + lipSize_bottom_up);

        }
    }


    /*
        We can move to either right or down.
        We can move to
            right, only if right > current cell value
            down, only if down > current cell value

        You have to find the size of the path and not the sum of the cells involved in the path.

                 1 -> 2 -> 3 -> 4
                 |
                 v
                 2    2    3    4
                 |
                 v
                 3    2    3    4
                 |
                 v
                 4 -> 5 -> 6 -> 7

        There is a possibility that the same cell can be visited more than once to find out LIP from that cell.
        So, there is a possibility of overlapping subproblems. So, this algorithm can be solved using dynamic programming also.
        When you use dynamic programming, memoization table will help you to print the actual path also.

        Time Complexity:

        Brute-Force approach = O(2^mn), Dynamic Programming = O(mn)

                                                        LIP
                                                        |
                   --------------------------------------------------------------------------------------
                   |                                                                                    |
                LIP                                                                                    LIP
                 |                                                                                      |
        ---------------------                                                                  ---------------------
        |                   |                                                                  |                   |                |
        LIP                LIP                                                                LIP                LIP

        2^0 + 2^1+ 2^2 +...... ~= 2^mn nodes
        Each node does O(1) operation.
        So time complexity = O(2^mn),  which is same as Fibonacci Series problem.
        Just like fibonacci series problem, number of nodes can be reduces to 2*mn, and so time complexity can be reduced to O(2mn) ~= O(mn)

    */
    private static int findLIPSize_Brute_Force(int[][] matrix, int startCellX, int startCellY, int endCellX, int endCellY) {

        // IMPORTANT condition. All matrix related algorithms must have range checking condition.
        // return value might differ for different algorithms.
        // In this algorithm value can be 0 or more , so it makes sense to have return value as 0
        // In MinCostPathInMatrix.java, it makes sense to return Integer.MAX_VALUE
        if (!inRange(matrix, startCellX, startCellY)) {
            return 0;
        }

        /*
            IMPORTANT:
            You can traverse either right or down in the matrix. So, you can reach either last col or last row.

            If you reach to last row (except last cell), you can still traverse to right.
            If you reach to last col (except last cell), you can still traverse to down.
            If you reach to last cell, you cannot traverse further. Longest Increasing Path size from last cell will be always 1.
        */
        if (startCellX == endCellX && startCellY == endCellY) {
            return 1;
        }

        int currentCellValue = matrix[startCellX][startCellY];

        int right = 0;
        int down = 0;

        // recursing towards right
        if (inRange(matrix, startCellX, startCellY + 1) && matrix[startCellX][startCellY + 1] > currentCellValue) {
            right = findLIPSize_Brute_Force(matrix, startCellX, startCellY + 1, endCellX, endCellY);
        }

        // recursing towards down
        if (inRange(matrix, startCellX + 1, startCellY) && matrix[startCellX + 1][startCellY] > currentCellValue) {
            down = findLIPSize_Brute_Force(matrix, startCellX + 1, startCellY, endCellX, endCellY);
        }

        int pathSizeFromCurrentCell = 1 + Math.max(right, down);

        return pathSizeFromCurrentCell;
    }

    private static int findLIPSize_Top_Down(int[][] matrix, int startCellX, int startCellY, int endCellX, int endCellY, int[][] memory) {

        if (!inRange(matrix, startCellX, startCellY)) {
            return 0;
        }

        // Dynamic Programming: returning memoized value
        if (memory[startCellX][startCellY] != 0) {
            return memory[startCellX][startCellY];
        }

        if (startCellX == endCellX && startCellY == endCellY) {
            // Dynamic Programming memoization
            memory[startCellX][startCellY] = 1;
            return 1;
        }

        int currentCellValue = matrix[startCellX][startCellY];

        int right = 0;
        int down = 0;

        if (inRange(matrix, startCellX, startCellY + 1) && matrix[startCellX][startCellY + 1] > currentCellValue) {
            right = findLIPSize_Top_Down(matrix, startCellX, startCellY + 1, endCellX, endCellY, memory);
        }

        if (inRange(matrix, startCellX + 1, startCellY) && matrix[startCellX + 1][startCellY] > currentCellValue) {
            down = findLIPSize_Top_Down(matrix, startCellX + 1, startCellY, endCellX, endCellY, memory);
        }

        int pathSizeFromCurrentCell = 1 + Math.max(right, down);

        // Dynamic Programming memoization
        memory[startCellX][startCellY] = pathSizeFromCurrentCell;

        return pathSizeFromCurrentCell;
    }

    private static int findLIPSize_Bottom_Up(int[][] matrix) {

        if (matrix == null || matrix.length == 0) {
            return 0;
        }

        int[][] path = new int[matrix.length][matrix[0].length];

        // initializing the last cell (Just like recursive solution's exit condition)
        path[path.length - 1][path[0].length - 1] = 1;

        // initializing last row
        for (int cell = path[0].length - 2; cell >= 0; cell--) {
            if (matrix[matrix.length - 1][cell + 1] > matrix[matrix.length - 1][cell]) {
                path[path.length - 1][cell] = path[path.length - 1][cell + 1] + 1;
            } else {
                path[path.length - 1][cell] = 1;
            }
        }

        // initializing last col
        for (int row = path.length - 2; row >= 0; row--) {
            if (matrix[row + 1][matrix.length - 1] > matrix[row][matrix.length - 1]) {
                path[row][path.length - 1] = path[row + 1][path.length - 1] + 1;
            } else {
                path[row][path.length - 1] = 1;
            }
        }

        for (int row = path.length - 2; row >= 0; row--) {
            for (int cell = path[row].length - 2; cell >= 0; cell--) {

                int right = 0;
                int down = 0;

                if (matrix[row][cell + 1] > matrix[row][cell]) {
                    right = path[row][cell + 1];
                }
                if (matrix[row + 1][cell] > matrix[row][cell]) {
                    down = path[row + 1][cell];
                }

                path[row][cell] = 1 + Math.max(right, down);
            }
        }

        //ArrayUtils.prettyPrintMatrix(path);

        int longestIncreasingPathSize = path[0][0];

        return longestIncreasingPathSize;
    }
}
