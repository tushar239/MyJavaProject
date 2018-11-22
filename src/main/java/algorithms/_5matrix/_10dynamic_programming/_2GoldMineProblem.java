package algorithms._5matrix._10dynamic_programming;

import java.util.HashMap;
import java.util.Map;

/*
    Gold Mine Problem

    https://www.geeksforgeeks.org/gold-mine-problem/

    Given a gold mine of n*m dimensions.
    Each field in this mine contains a positive integer which is the amount of gold in tons.
    Initially the miner is at first column but can be at any row.
    He can move only (right ,right up,right down) that is from a given cell, the miner can move to the cell diagonally up towards the right or right or diagonally down towards the right.
    Find out maximum amount of gold he can collect.

    int matrix[][] = {
                    {1, 3, 3},
                    {2, 1, 4},
                    {0, 6, 4}
            };

     startingCell = (1,0)

                                                /
     You can travers only in three directions   --
                                                \

                        (1,0)
                         |
        -----------------------------------------
        |                   |                   |
       (0,1)              (1,1)                 (2,1)
        |                   |                     |
        |              -----------                |
        |              |         |                |
       (0,2)         (0,2)      (2,2)           (2,2)


      This definitely has overlapping subproblems. So, this problem can definitely be solved using dynamic programming.


      Using Bottom-Up approach

      In every move, we move one step toward right side. So we always end up in last column.
      Last col will always return the value that is there in last col.
      So, starting with matrix like below

      0  0   3
      0  0   4
      0  0   1

      for(int col=second last col; col >= 0; col--)
        for(int row=0; row <= last row; row++)
            memo[row][col] = Math.max(memo[row-1][col], memo[row+1][col+1], memo[row-1][col+1])// check for out of range values before coming to this step.

      8  2   3
      12 5   4
      10 10  1

      memo[startRow][startCol] will have a result.
*/
public class _2GoldMineProblem {

    public static void main(String[] args) {

        {
            int matrix[][] = {
                    {1, 3, 3},
                    {2, 1, 4},
                    {0, 6, 4}
            };// 12

            int startCellX = 1;
            int startCellY = 0;
            Map<String, Integer> map = new HashMap<>();
            int totalCollectedMaxGold = find(matrix, startCellX, startCellY, map);
            System.out.println(totalCollectedMaxGold);
        }
        {
            int matrix[][] = {
                    {1, 3, 1, 5},
                    {2, 2, 4, 1},
                    {5, 0, 2, 3},
                    {0, 6, 1, 2}
            };//16

            int startCellX = 2;
            int startCellY = 0;

            Map<String, Integer> map = new HashMap<>();
            int totalCollectedMaxGold = find(matrix, startCellX, startCellY, map);
            System.out.println(totalCollectedMaxGold);

        }
    }

    private static int find(int[][] matrix, int x, int y, Map<String, Integer> map) {

        // Dynamic programming memoization
        String coordinates = x + "" + y;
        if (map.containsKey(coordinates)) {
            return map.get(coordinates);
        }

        // outside range
        if (x > matrix.length - 1 || x < 0 || y > matrix[x].length - 1 || y < 0) {
            return 0;
        }
        // OR
        /*if (x > matrix.length - 1 || x < 0 || y > matrix[x].length - 1 || y < 0) {
            return 0;
        }
        if (y == matrix[x].length - 1) {
            return matrix[x][y];
        }*/

        int rightDiagonalCollectedGold = find(matrix, x - 1, y + 1, map);
        int rightCollectedGold = find(matrix, x, y + 1, map);
        int downDiagonalCollectedGold = find(matrix, x + 1, y + 1, map);

        int totalCollectedMaxGold = matrix[x][y] + Math.max(rightDiagonalCollectedGold, Math.max(rightCollectedGold, downDiagonalCollectedGold));

        map.put(coordinates, totalCollectedMaxGold);

        return totalCollectedMaxGold;
    }


}
