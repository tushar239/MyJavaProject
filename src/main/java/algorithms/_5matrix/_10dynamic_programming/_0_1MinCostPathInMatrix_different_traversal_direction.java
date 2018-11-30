package algorithms._5matrix._10dynamic_programming;

import algorithms.utils.ArrayUtils;

import static algorithms.utils.ArrayUtils.inRange;

/*
    Minimum cost path in a matrix

    This is a slight modification of MinCostPathInMatrix.java.
    Traversal directions for matrix is different in this algorithm and that makes it a bit interesting.

    In MinCostPathInMatrix.java, traversal paths are right,bottom,bottom right

                           -
                        | \



                         1 -> 5 -> 2 ->
                         | \  | \  | \
                         v v  v v  v v
                         7 -> 2 -> 2 ->
                         | \  | \  | \
                         v v  v v  v v
                         2 -> 8 -> 1 ->
                         | \  | \  | \
                         v v  v v  v v

    In this algorithm, they are bottom left, bottom and bottom right

                        / | \

                         1     5     2
                        /| \
                         7     2     2
                        /| \  /| \
                         2     8     1
                        /| \  /| \  /| \

    Because of this kind of traversal path, there won't be any path from 2 and 8 to 1(end cell). So, the cost for these cells (2 and 8) will be Integer.MAX_VALUE(undetermined)

    So, exit condition for last row differs.
    You will not be able to reach from last row cells(except end cell) to end cell. So, values of last row cells(except end cell) will be Integer.MAX_VALUE.


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
public class _0_1MinCostPathInMatrix_different_traversal_direction {

    public static void main(String[] args) {
       /* int mat[][] =
                {
                        {1, 5, 2},
                        {7, 2, 2},
                        {2, 8, 1}
                };*/

        int mat[][] =
                {
                        {2, 3, 4},
                        {5, 9, 8},
                        {7, 2, 1}
                };
        int startCellX = 0;
        int startCellY = 0;
        int endCellX = mat.length - 1;
        int endCellY = mat[0].length - 1;

        // initializing memoization table to Integer.MAX_VALUE because your top-down approach has a formula to find MIN cost coming from different paths.
        int memory[][] = new int[mat.length][mat[0].length];
        for (int row = 0; row < mat.length; row++) {
            for (int col = 0; col < mat[row].length; col++) {
                memory[row][col] = Integer.MAX_VALUE;
            }
        }

        int minCost = find(mat, startCellX, startCellY, endCellX, endCellY, memory);
        System.out.println(minCost);

        ArrayUtils.prettyPrintMatrix(memory);

    }

    private static int find(int[][] mat, int startCellX, int startCellY, int endCellX, int endCellY, int[][] memory) {
        if (!inRange(mat, startCellX, startCellY)) {
            return Integer.MAX_VALUE;
        }

        int currentCellValue = mat[startCellX][startCellY];

        // Dynamic programming: returning memoized value
        if (memory[startCellX][startCellY] != Integer.MAX_VALUE) {
            return memory[startCellX][startCellY];
        }

        // If you are on last cell, then to reach from last cell to last cell, min cost is the value of last cell
        if (startCellX == endCellX && startCellY == endCellY) {
            memory[startCellX][startCellY] = currentCellValue;

            return currentCellValue;
        }

        // IMPORTANT: last row (except last cell) will have value Integer.MAX because you cannot reach to last cell
        if (startCellX == endCellX && startCellY != endCellY) {
            // Dynamic programming memoization
            memory[startCellX][startCellY] = Integer.MAX_VALUE;

            return Integer.MAX_VALUE;
        }

        int leftBottom = find(mat, startCellX + 1, startCellY - 1, endCellX, endCellY, memory);
        int bottom = find(mat, startCellX + 1, startCellY, endCellX, endCellY, memory);
        int rightBottom = find(mat, startCellX + 1, startCellY + 1, endCellX, endCellY, memory);

        int min = Math.min(leftBottom, Math.min(bottom, rightBottom));

        if (min == Integer.MAX_VALUE) {
            return Integer.MAX_VALUE;
        }

        int value = currentCellValue + min;

        // Dynamic programming memoization
        memory[startCellX][startCellY] = value;

        return value;

    }


}
