package algorithms._5matrix._10dynamic_programming;

import algorithms.utils.ArrayUtils;

/*
    Gold Mine Problem

    https://www.geeksforgeeks.org/gold-mine-problem/

    Given a gold mine of n*m dimensions.
    Each field in this mine contains a positive integer which is the amount of gold in tons.
    Initially the miner is at first column but can be at any row.
    He can move only (right ,right up,right down) that is from a given cell, the miner can move to the cell diagonally up towards the right or right or diagonally down towards the right.
    Find out maximum amount of gold he can collect.


    Solutions:

    1) BFS  ------ I wouldn't use this solution for this algorithm even though it may work

       NOTE: I wouldn't use this approach even though it may work.
       why?
       Rule of thumb:
       Use BFS,
            If you need to process chunk of cells in such a way that
            these chunks/paths should not be overlapping or in other words visited cells should not be visited again.
            e.g. FindNumberOfIslands.java
       Use Dynamic Programming,
            If you need to process chunks of cells in such a way that
            these chunks/paths can be overlapping

               x y parent value+it's value
       queue= (1,2,2),
              (0,1,5), (1,1,3), (2,1,8),
                       (1,1,3), (2,1,8), (0,2,8)
                                (2,1,8), (0,2,8), (0,2,4), (1,2,7), (2,2,7), (2,1,12)
                                         (0,2,8), (0,2,4), (1,2,7), (2,2,7), (2,1,12)
                                                  (0,2,4), (1,2,7), (2,2,7), (2,1,12), (2,2,12)

       Whenever you poll from queue, keep track of max value till queue is empty.

    2) Recursion + Dynamic Programming
        Below implemented solution is based on this.
        geeksforgeeks has this solution.


        int matrix[][] = {
                        {1, 3, 3},
                        {2, 1, 4},
                        {0, 6, 4}
                };

         startingCell = (1,0)

                                                    /
         You can traverse only in three directions   --
                                                    \

                            (1,0)
                             |
            -----------------------------------------
            |                   |                   |
           (0,1)              (1,1)                 (2,1)
            |                   |                     |
            |              -----------                |
            |              |    |     |               |
           (0,2)         (0,2) (1,2) (2,2)          (2,2)


          This definitely has overlapping subproblems. So, this problem can definitely be solved using dynamic programming.

          Without dynamic programming, time complexity be O(3^mn). With Dynamic programming, it will be O(3 * mn).
          Where m is number of rows and n is number of cols.


          This algorithm is asking to find the max number of gold that can be collected.
          How to find the path that was traversed to find the max number of gold?
          It is very hard to do that, if you use only Brute-Force approach.
          If you have used Top-Down or Bottom-Up approach, then using memoized table, you can find out the path
          e.g.
          Top-Down approach will create following memoized table.

                {.., 7, 3},
                {12, 5, 4},
                {.., 10, 4}

          Now, traverse this table from starting cell and go diagonal up, right and diagonal down. cell with max value becomes a part of path.
          result: (1,0)->(2,1)->(1,2)



          Using Bottom-Up approach:

          As you see, in Brute-Force or Top-Down approach, there is no specific exit condition that you can use to predefine the values
          in Bottom-Up approach's memory table. But it is a prerequisite of Bottom-Up's approach to predefine some of the values in memory table.
          How will you do that?
          Think about what happens if you reach to the last column. Can last col's values be predefined?
          If yes, then filling up memory table will start from last to first col.

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
public class _1GoldMineProblem {

    public static void main(String[] args) {

        System.out.println("Example 1");
        {
            int matrix[][] = {
                    {1, 3, 3},
                    {2, 1, 4},
                    {0, 6, 4}
            };// 12

            int startCellX = 1;
            int startCellY = 0;

/*
            {
                Map<String, Integer> map = new HashMap<>();
                int totalCollectedMaxGold = maxGold_Top_Down_Approach(matrix, startCellX, startCellY, map);
                System.out.println("Max gold collected:" + totalCollectedMaxGold);
            }
*/


            int[][] memory = new int[matrix.length][matrix[0].length];

            for (int row = 0; row < matrix.length; row++) {
                for (int col = 0; col < matrix[row].length; col++) {
                    memory[row][col] = Integer.MIN_VALUE;
                }
            }

            int totalCollectedMaxGold = maxGold_Top_Down_Approach_(matrix, startCellX, startCellY, memory);
            System.out.println("Max gold collected:" + totalCollectedMaxGold);

            System.out.println("Memoized Table");
            ArrayUtils.prettyPrintMatrix(memory);

            System.out.println("Print the path to collect max gold mines");
            printPath(memory, startCellX, startCellY);


        }
        System.out.println("Example 2");
        {
            int matrix[][] = {
                    {1, 3, 1, 5},
                    {2, 2, 4, 1},
                    {5, 0, 2, 3},
                    {0, 6, 1, 2}
            };//16

            int startCellX = 2;
            int startCellY = 0;

            int[][] memory = new int[matrix.length][matrix[0].length];

            for (int row = 0; row < matrix.length; row++) {
                for (int col = 0; col < matrix[row].length; col++) {
                    memory[row][col] = Integer.MIN_VALUE;
                }
            }

            int totalCollectedMaxGold = maxGold_Top_Down_Approach_(matrix, startCellX, startCellY, memory);
            System.out.println("Max gold collected:" + totalCollectedMaxGold);

            System.out.println("Memoized Table");
            ArrayUtils.prettyPrintMatrix(memory);

            System.out.println("Print the path to collect max gold mines");
            printPath(memory, startCellX, startCellY);

        }
    }


/*    private static int maxGold_Top_Down_Approach(int[][] matrix, int startingCellX, int startingCellY, Map<String, Integer> map) {

        // Dynamic programming memoization
        String coordinates = startingCellX + "" + startingCellY;
        if (map.containsKey(coordinates)) {
            return map.get(coordinates);
        }

        // IMPORTANT condition. All matrix related algorithms must have range checking condition.
        if (!inRange(matrix, startingCellX, startingCellY)) {
            return 0;
        }

        *//*
         IMPORTANT condition
         As you see, recursive calls are increasing/reducing row and increasing col.
         It means that it can reach to either first row or last row or last col.

         If it reaches to first row,
            you can still traverse to right (except from first row and last col).
            If it reaches to first row and last col, result will be the value of that cell.

         If it reaches to last row,
            you can still traverse to right (except from last row and last col).
            If it reaches to last row and last col, result will be the value of that cell.

         If it reaches to last col,
            you cannot traverse further.
            result will be the value of that cell.


         From all above 3 conditions, it is sure that if you reach to last col, result should be the value of that cell.

        *//*
        if (startingCellY == matrix[startingCellX].length - 1) {
            map.put(coordinates, matrix[startingCellX][startingCellY]);

            return matrix[startingCellX][startingCellY];
        }

        //OR just range checking condition will be enough for this algorithm. I would go with above option because it is necessary, if you want to code bottom-up approach.
        *//*
        if (!inRange(matrix, startingCellX, startingCellY)) {
            return 0;
        }
        *//*


        int rightDiagonalCollectedGold = maxGold_Top_Down_Approach(matrix, startingCellX - 1, startingCellY + 1, map);
        int rightCollectedGold = maxGold_Top_Down_Approach(matrix, startingCellX, startingCellY + 1, map);
        int downDiagonalCollectedGold = maxGold_Top_Down_Approach(matrix, startingCellX + 1, startingCellY + 1, map);

        int totalCollectedMaxGold = matrix[startingCellX][startingCellY] +
                                    Math.max(rightDiagonalCollectedGold, Math.max(rightCollectedGold, downDiagonalCollectedGold));

        // Dynamic programming memoization
        map.put(coordinates, totalCollectedMaxGold);

        return totalCollectedMaxGold;
    }*/

    private static int maxGold_Top_Down_Approach_(int[][] matrix, int startingCellX, int startingCellY, int[][] memory) {

        // IMPORTANT condition. All matrix related algorithms must have range checking condition.
        if (!inRange(matrix, startingCellX, startingCellY)) {
            return 0;
        }

        // Dynamic programming : returning memoized value
        int memoizedValue = memory[startingCellX][startingCellY];
        if (memoizedValue != Integer.MIN_VALUE) {
            return memoizedValue;
        }

        /*
         IMPORTANT condition
         As you see, recursive calls are increasing/reducing row and increasing col.
         It means that it can reach to either first row/last row or last col.

         If it reaches to first row,
            you can still traverse to right (except from first row and last col).
            If it reaches to first row and last col, result will be the value of that cell because you cannot traverse anymore in any direction.

         If it reaches to last row,
            you can still traverse to right (except from last row and last col).
            If it reaches to last row and last col, result will be the value of that cell because you cannot traverse anymore in any direction.

         If it reaches to last col,
            you cannot traverse further.
            result will be the value of that cell because you cannot traverse anymore in any direction.


         From all above 3 conditions, it is sure that if you reach to last col, result should be the value of that cell.
        */

        if (startingCellY == matrix[startingCellX].length - 1) {
            // Dynamic programming: memoizing the value
            memory[startingCellX][startingCellY] = matrix[startingCellX][startingCellY];

            return matrix[startingCellX][startingCellY];
        }


        int rightDiagonalCollectedGold = maxGold_Top_Down_Approach_(matrix, startingCellX - 1, startingCellY + 1, memory);
        int rightCollectedGold = maxGold_Top_Down_Approach_(matrix, startingCellX, startingCellY + 1, memory);
        int downDiagonalCollectedGold = maxGold_Top_Down_Approach_(matrix, startingCellX + 1, startingCellY + 1, memory);


        int totalCollectedMaxGold = matrix[startingCellX][startingCellY] +
                Math.max(rightDiagonalCollectedGold, Math.max(rightCollectedGold, downDiagonalCollectedGold));

        // Dynamic programming: memoizing the value
        memory[startingCellX][startingCellY] = totalCollectedMaxGold;

        return totalCollectedMaxGold;
    }

    /*
        Memoization Table:

             0            1             2
           ------------ |------------ |---|
        0| -2147483648  |7            |3  |
        1| 12           |5            |4  |
        2| -2147483648  |10           |4  |
                                                            /
        Start from starting point (1,0) and find max from   -
                                                            \

        which will be (2,1)


        From (2,1) do the same. Which will be (1,2) or (2,2)

        So, path to collect max gold is (1,0)->(2,1)->(1,2)/(2,2)
     */
    private static void printPath(int[][] memory, int startingCellX, int startingCellY) {

        System.out.println("Path: (" + startingCellX + "," + startingCellY + ")");


        while (true) {
            int rightDiagonalX = startingCellX - 1;
            int rightDiagonalY = startingCellY + 1;

            int rightX = startingCellX;
            int rightY = startingCellY + 1;

            int downDiagonalX = startingCellX + 1;
            int downDiagonalY = startingCellY + 1;

            int max = Integer.MIN_VALUE;
            int maxX = Integer.MIN_VALUE;
            int maxY = Integer.MIN_VALUE;

            if (inRange(memory, rightDiagonalX, rightDiagonalY)) {
                if (memory[rightDiagonalX][rightDiagonalY] > max) {

                    max = memory[rightDiagonalX][rightDiagonalY];

                    maxX = rightDiagonalX;
                    maxY = rightDiagonalY;

                    startingCellX = rightDiagonalX;
                    startingCellY = rightDiagonalY;
                }
            }

            if (inRange(memory, rightX, rightY)) {
                if (memory[rightX][rightY] > max) {

                    max = memory[rightX][rightY];

                    maxX = rightX;
                    maxY = rightY;

                    startingCellX = rightX;
                    startingCellY = rightY;

                }
            }

            if (inRange(memory, downDiagonalX, downDiagonalY)) {
                if (memory[downDiagonalX][downDiagonalY] > max) {

                    max = memory[downDiagonalX][downDiagonalY];

                    maxX = downDiagonalX;
                    maxY = downDiagonalY;

                    startingCellX = downDiagonalX;
                    startingCellY = downDiagonalY;
                }
            }

            if (maxX == Integer.MIN_VALUE) break;

            System.out.println("Path: (" + maxX + "," + maxY + ")");
        }
    }

    private static boolean inRange(int[][] matrix, int x, int y) {
        return (x >= 0 && x <= matrix.length - 1) &&
                (y >= 0 && y <= matrix[x].length - 1);
    }


}
