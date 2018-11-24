package algorithms._5matrix._10dynamic_programming;

/*
    Unique paths in a Grid with Obstacles

    https://www.geeksforgeeks.org/unique-paths-in-a-grid-with-obstacles/

    Given a grid of size m * n, let us assume you are starting at (1, 1) and your goal is to reach (m, n). At any instance, if you are on (x, y), you can either go to (x, y + 1) or (x + 1, y).
    Now consider if some obstacles(1s) are added to the grids. How many unique paths would there be?

    e.g.

    matrix=
        [
        {0,0,0},
        {0,0,1},
        {0,0,0}
        ]

    Below algorithm will create another matrix 'paths' with following values

    [
        {1,1,1},
        {1,2,0},
        {1,3,3}
    ]

    result in the last cell is the answer. There are 3 unique paths to reach from start to end cell.
*/

import java.util.HashMap;
import java.util.Map;

public class _2UniquePathsInAGridWithObstacles {

    public static void main(String[] args) {
        // There are two 3 unique paths to reach from upper left corner to bottom right corner
        int[][] matrix = {
                {0, 0, 0},
                {0, 0, 1},
                {0, 0, 0}
        };// unique paths 3


        System.out.println("Using Bottom-Up Dynamic Programming");
        {
            int totalUniquePaths = findUniquePaths_Bottom_Up_Approach(matrix);
            System.out.println(totalUniquePaths);
        }

        System.out.println("Using Recursion");
        {
            int startCellX = 0;
            int startCellY = 0;
            int endCellX = 2;
            int endCellY = 2;

            Map<String, Integer> memory = new HashMap<>();
            int totalUniquePaths = findUniquePaths_Top_Down(matrix, startCellX, startCellY, endCellX, endCellY, memory);
            System.out.println(totalUniquePaths);
            System.out.println(memory);
        }
    }

    private static int findUniquePaths_Bottom_Up_Approach(int[][] matrix) {
        if (matrix.length == 0) return 0;

        int startCellX = 0;
        int startCellY = 0;
        int endCellX = 2;
        int endCellY = 2;

        // If starting cell is 1, then you cannot start. So, you cannot reach to ending cell.
        // If ending cell is 1, then you cannot reach to ending cell.
        // So, in these both cases, number of unique paths to reach from start to end cell is 0.
        if (matrix[startCellX][startCellY] == 1 || matrix[endCellX][endCellY] == 1) {
            return 0;
        }

        // create another matrix to keep track of number of paths you can come to cells
        int[][] paths = new int[matrix.length][matrix[0].length]; // all cells are initialized to 0

        // There is only one way to come to starting cell. So, initialize it with 1.
        if (matrix[startCellX][startCellY] == 0) {
            paths[startCellX][startCellY] = 1;
        }

        // There is only one way to come to any cells in first row and first col.
        // e.g. you can come to matrix[0][1] only from matrix[0][0].
        // you can come to matrix[0][2] only from matrix[0][1]
        // you can come to matrix[1][0] only from matrix[0][0].
        // you can come to matrix[2][0] only from matrix[1][0]
        for (int col = startCellY + 1; col < matrix[0].length; col++) {
            if (matrix[startCellX][col] == 0) {
                paths[startCellY][col] = 1;
            }
        }

        for (int row = startCellX + 1; row < matrix.length; row++) {
            if (matrix[row][startCellY] == 0) {
                paths[row][startCellY] = 1;
            }
        }

        // Now, fill up remaining matrix
        for (int row = startCellX + 1; row < matrix.length; row++) {

            for (int col = startCellY + 1; col < matrix.length; col++) {

                if (matrix[row][col] == 0) {
                    paths[row][col] = paths[row - 1][col] + paths[row][col - 1];
                }
            }
        }

        // result in last cell is the answer (number of unique paths)
        return paths[endCellX][endCellY];
    }


    private static int findUniquePaths_Top_Down(int[][] matrix, int startCellX, int startCellY, int endCellX, int endCellY, Map<String, Integer> memory) {

        if (!inRange(matrix, startCellX, startCellY)) {
            return 0;
        }

        String coordinates = startCellX+""+startCellY;
        if(memory.containsKey(coordinates)) {
            return memory.get(coordinates);
        }

        // if destination cell has an obstacle(1), then you can never reach there.
        if (matrix[endCellX][endCellY] == 1) {
            return 0;
        }

        // if starting cell has an obstacle(1), then you can never start.
        if (matrix[startCellX][startCellY] == 1) {
            return 0;
        }

        // Important condition
        if(startCellX == endCellX && startCellY== endCellY) {
            return 1;
        }

        int rightX = startCellX;
        int rightY = startCellY + 1;

        int downX = startCellX + 1;
        int downY = startCellY;

        int uniquePathsFromRight = 0;
        int uniquePathsFromDown = 0;

        if (inRange(matrix, rightX, rightY)) {
            uniquePathsFromRight = findUniquePaths_Top_Down(matrix, rightX, rightY, endCellX, endCellY, memory);
        }
        if (inRange(matrix, downX, downY)) {
            uniquePathsFromDown = findUniquePaths_Top_Down(matrix, downX, downY, endCellX, endCellY, memory);
        }

        int totalUniquePaths = uniquePathsFromRight + uniquePathsFromDown;

/*
        if (totalUniquePaths == 0 && matrix[startCellX][startCellY] == 0) {
            totalUniquePaths = 1;
        }
*/

        memory.put(coordinates, totalUniquePaths);

        return totalUniquePaths;

    }

    private static boolean inRange(int[][] matrix, int x, int y) {
        if (x >= 0 && x <= matrix.length - 1) {
            if (y >= 0 && y <= matrix[x].length - 1) {
                return true;
            }
        }
        return false;
    }

}
