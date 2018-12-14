package algorithms._5matrix._10dynamic_programming;

import algorithms.utils.ArrayUtils;

import static algorithms.utils.ArrayUtils.inRange;

/*
    Min Cost Path | DP-6
    https://www.geeksforgeeks.org/min-cost-path-dp-6/

    find min cost path from cell (0,0) to last cell.
    You can traverse right,bottom,diagonal right
                           -
                        | \

    This is a very important algorithm to start thinking about Dynamic Programming with matrix related algorithms.





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
public class _0_0MinCostPathInMatrix {

    public static void main(String[] args) {
        int matrix[][] = {
                {1, 2, 3},
                {4, 8, 2},
                {1, 5, 3}
        };// O/P = 8

        int startCellX = 0;
        int startCellY = 0;
        int endCellX = matrix.length - 1;
        int endCellY = matrix[0].length - 1;

        System.out.println("Memoization Table --- it looks different than 'Starting from End Cell'. Final result will be in the start cell.");
        {
            // initializing memoization table to Integer.MAX_VALUE because your top-down approach has a formula to find MIN cost coming from different paths.
            int memory[][] = new int[matrix.length][matrix[0].length];
            for (int row = 0; row < matrix.length; row++) {
                for (int col = 0; col < matrix[row].length; col++) {
                    memory[row][col] = Integer.MAX_VALUE;
                }
            }

            int minCost = findMinCostPath(matrix, startCellX, startCellY, endCellX, endCellY, memory);
            System.out.println(minCost);

            System.out.println("Memoization Table");
            ArrayUtils.prettyPrintMatrix(memory);
        }

        System.out.println("Starting from End Cell");
        {
            // initializing memoization table to Integer.MAX_VALUE because your top-down approach has a formula to find MIN cost coming from different paths.
            int memory[][] = new int[matrix.length][matrix[0].length];
            for (int row = 0; row < matrix.length; row++) {
                for (int col = 0; col < matrix[row].length; col++) {
                    memory[row][col] = Integer.MAX_VALUE;
                }
            }

            int minCost = findMinCostPath_from_end(matrix, startCellX, startCellY, endCellX, endCellY, memory);
            System.out.println(minCost);

            System.out.println("Memoization Table --- it looks different than 'Starting from Start Cell'. Final result will be in the end cell.");
            ArrayUtils.prettyPrintMatrix(memory);
        }

        {
            System.out.println("Bottom-Up approach having result in startCell");
            int minCost = findMinCostPath_bottom_up(matrix, startCellX, startCellY, endCellX, endCellY);
            System.out.println(minCost);
        }

    }

    /*
    e.g.
     int matrix[][] = {
                    {1, 2, 3},
                    {4, 8, 2},
                    {1, 5, 3}
            };

    Memoization table

         0    1    2
       ----|----|----|
    0| 8   |7   |8  |

    1| 12  |11  |5  |

    2| 9   |8   |3  |


    to find out a path of min cost, from first cell, you can find cell having min value at right,bottom or diagonal right and follow the path

    (0,0)->(0,1)0>(1,2)->(2,2)


    IMPORTANT:

    Based on the traversal directions, you will choose whether to recurse by changing

        startCell value (e.g. MinCostPathInMatrix.java, MinCostPathInMatrix_different_traversal_direction.java, MinOddCostPathInMatrix.java)
        or
        endCell value  (e.g. MaxPathSumInMatrix.java)

    If you choose to recurse by changing

        - startCell's value,
              your top-down approach's memoization table will have final result in the startCell.

              When you convert this kind of top-down approach to bottom-up, you will start initializing the endCell (from top-down's exit condition this will be very clear)
              and then last row and/or last col, if needed.

              e.g. MinCostPathInMatrix.java, MinCostPathInMatrix_different_traversal_direction.java
                    MinOddCostPathInMatrix.java

        - endCell's value,
              your top-down approach's memoization table will have final result in the endCell.

              When you convert this kind of top-down approach to bottom-up, you will start initializing the startCell (from top-down's exit condition this will be very clear)
              and then and/or start row and/or start col

              e.g. MaxPathSumInMatrix.java


     */
    private static int findMinCostPath(int[][] matrix, int startCellX, int startCellY, int endCellX, int endCellY, int[][] memory) {

        // IMPORTANT condition. All matrix related algorithms must have range checking condition.
        // return value might differ for different algorithms.
        // In this algorithm cell value is based on Math.min(...), so it makes sense to have return value as Integer.MAX
        if (!inRange(matrix, startCellX, startCellY)) {
            return Integer.MAX_VALUE;
        }

        int currentCellValue = matrix[startCellX][startCellY];

        // Dynamic programming: returning memoized value
        if (memory[startCellX][startCellY] != Integer.MAX_VALUE) {
            return memory[startCellX][startCellY];
        }

        /*
         IMPORTANT condition
         As you see, recursive calls are increasing row and increasing col.
         It means that it can reach to either last row or last col.

         If it reaches to last row,
            you can still traverse to right (except from last row and last col).
            If it reaches to last row and last col, result will be the value of that cell.

         If it reaches to last col,
            you can still traverse to bottom (except from last row and last col).
            If it reaches to last row and last col, result will be the value of that cell.

         If it reaches to last col,
            you cannot traverse further.
            result will be the value of that cell.

         From all above 3 conditions, it is sure that if you reach to last cell, result should be the value of that cell.

         In certain algorithms, you will end up having exit conditions like this for entire row and/or col.

        */
        // If you are on last cell, then to reach from last cell to last cell, min cost is the value of last cell
        if (startCellX == endCellX && startCellY == endCellY) {

            // Dynamic programming memoization
            memory[startCellX][startCellY] = currentCellValue;

            return currentCellValue;
        }


        int right = findMinCostPath(matrix, startCellX, startCellY + 1, endCellX, endCellY, memory);
        int bottom = findMinCostPath(matrix, startCellX + 1, startCellY, endCellX, endCellY, memory);
        int bottomRightDiagonal = findMinCostPath(matrix, startCellX + 1, startCellY + 1, endCellX, endCellY, memory);

        int min = Math.min(right, Math.min(bottom, bottomRightDiagonal));

    /*
        For this algorithm, this condition will never be possible because of the directions of the traversal

            right
            bottom
            diagonal right

        In certain algorithms, this condition can be possible
        e.g. MinCostPathInMatrix_different_traversal_direction.java

            when you need to traverse a matrix in following directions

            diagonal left
            bottom
            diagonal right

            When you are in last row (except last cell), if you try to go in above three directions, you will get Integer.MAX value from all the directions.
            In that case, min=Integer.MAX
            So, in that case current cell's value will be Integer.MAX because you will never be able to reach to last cell.

            your memoization table will look like

                 0    1    2
               ----|----|----|
            0| 8   |7   |8   |

            1| MAX |11  |5   |

            2| MAX |MAX |3   |


            Result will be 8

    */

        /*
        if(min == Integer.MAX_VALUE) { // this condition will never be possible for this algorithm

        }
         */

        int value = currentCellValue + min;

        // Dynamic Programming memoization
        memory[startCellX][startCellY] = value;

        return value;

    }

    private static int findMinCostPath_bottom_up(int[][] matrix, int startCellX, int startCellY, int endCellX, int endCellY) {
        if (matrix.length == 0) return 0;

        // create another matrix to keep track of number of memo you can come to cells
        int[][] memo = new int[matrix.length][matrix[0].length]; // all cells are initialized to 0

        // initializing last cell
        memo[endCellX][endCellY] = matrix[endCellX][endCellY];

        // initializing last row
        for (int col = endCellY - 1; col >= 0; col--) {
            memo[endCellX][col] = matrix[endCellX][col] + memo[endCellX][col + 1];
        }

        // initializing last col
        for (int row = endCellX - 1; row >= 0; row--) {
            memo[row][endCellY] = matrix[row][endCellY] + memo[row + 1][endCellY];
        }

        // Now, fill up remaining matrix
        for (int row = endCellX - 1; row >= 0; row--) {

            for (int col = endCellY - 1; col >= 0; col--) {

                int right = memo[row][col + 1];
                int bottom = memo[row + 1][col];
                int bottomRightDiagonal = memo[row + 1][col + 1];
                memo[row][col] = matrix[row][col] + Math.min(right, Math.min(bottom, bottomRightDiagonal));
            }
        }

        ArrayUtils.prettyPrintMatrix(memo);

        // result will be in start cell of memoization table 'memo'
        return memo[startCellX][startCellY];

    }


    private static int findMinCostPath_from_end(int[][] matrix, int startCellX, int startCellY, int endCellX, int endCellY, int[][] memory) {

        if (!inRange(matrix, endCellX, endCellY)) {
            return Integer.MAX_VALUE;
        }

        int currentCellValue = matrix[endCellX][endCellY];

        // Dynamic programming: returning memoized value
        if (memory[endCellX][endCellY] != Integer.MAX_VALUE) {
            return memory[endCellX][endCellY];
        }

        // If you are on last cell, then to reach from last cell to last cell, min cost is the value of last cell
        if (endCellX == startCellX && endCellY == startCellY) {

            // Dynamic programming memoization
            memory[endCellX][endCellY] = currentCellValue;

            return currentCellValue;
        }


        int up = findMinCostPath_from_end(matrix, startCellX, startCellY, endCellX - 1, endCellY, memory);
        int left = findMinCostPath_from_end(matrix, startCellX, startCellY, endCellX, endCellY - 1, memory);
        int upLeftDiagonal = findMinCostPath_from_end(matrix, startCellX, startCellY, endCellX - 1, endCellY - 1, memory);

        int min = Math.min(up, Math.min(left, upLeftDiagonal));

        int value = currentCellValue + min;

        // Dynamic Programming memoization
        memory[endCellX][endCellY] = value;

        return value;

    }
}
