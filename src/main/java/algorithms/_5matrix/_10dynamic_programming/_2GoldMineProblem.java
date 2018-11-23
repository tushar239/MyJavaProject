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


    Solutions:
    1) Recursion + Dynamic Programming
        Below implemented solution is based on this.
        geeksforgeeks has this solution.

    2) BFS
               x y parent value+it's value
       queue= (1,2,2),
              (0,1,5), (1,1,3), (2,1,8),
                       (1,1,3), (2,1,8), (0,2,8)
                                (2,1,8), (0,2,8), (0,2,4), (1,2,7), (2,2,7), (2,1,12)
                                         (0,2,8), (0,2,4), (1,2,7), (2,2,7), (2,1,12)
                                                  (0,2,4), (1,2,7), (2,2,7), (2,1,12), (2,2,12)

       Whenever you poll from queue, keep track of max value till queue is empty.


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
            int totalCollectedMaxGold = maxGold_Top_Down_Approach(matrix, startCellX, startCellY, map);
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
            int totalCollectedMaxGold = maxGold_Top_Down_Approach(matrix, startCellX, startCellY, map);
            System.out.println(totalCollectedMaxGold);

        }
    }

    private static int maxGold_Top_Down_Approach(int[][] matrix, int x, int y, Map<String, Integer> map) {

        // Dynamic programming memoization
        String coordinates = x + "" + y;
        if (map.containsKey(coordinates)) {
            return map.get(coordinates);
        }

        // In any matrix's recursive problem, this exit condition is mandatory
        // outside range
        if (x < 0 || x > matrix.length - 1 || y < 0 || y > matrix[x].length - 1) {
            return 0;
        }
        // OR
        /*
        // outside range
        if (x > matrix.length - 1 || x < 0 || y > matrix[x].length - 1 || y < 0) {
            return 0;
        }
        // when you reach to last col's cell, it always returns the value in that cell.
        // This condition will help you to think in Bottom-Up Dynamic Programming approach.
        if (y == matrix[x].length - 1) {
            return matrix[x][y];
        }
        */

        int rightDiagonalCollectedGold = maxGold_Top_Down_Approach(matrix, x - 1, y + 1, map);
        int rightCollectedGold = maxGold_Top_Down_Approach(matrix, x, y + 1, map);
        int downDiagonalCollectedGold = maxGold_Top_Down_Approach(matrix, x + 1, y + 1, map);

        int totalCollectedMaxGold = matrix[x][y] + Math.max(rightDiagonalCollectedGold, Math.max(rightCollectedGold, downDiagonalCollectedGold));

        map.put(coordinates, totalCollectedMaxGold);

        return totalCollectedMaxGold;
    }


}
