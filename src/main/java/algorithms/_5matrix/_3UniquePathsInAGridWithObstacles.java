package algorithms._5matrix;

/*
    Unique paths in a Grid with Obstacles

    https://www.geeksforgeeks.org/unique-paths-in-a-grid-with-obstacles/

    Given a grid of size m * n, let us assume you are starting at (1, 1) and your goal is to reach (m, n). At any instance, if you are on (x, y), you can either go to (x, y + 1) or (x + 1, y).
    Now consider if some obstacles are added to the grids. How many unique paths would there be?

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

public class _3UniquePathsInAGridWithObstacles {

    public static void main(String[] args) {
        // There are two 3 unique paths to reach from upper left corner to bottom right corner
        int[][] matrix = {
                {0,0,0},
                {0,0,1},
                {0,0,0}
        };

        findUniquePaths(matrix);
    }

    private static int findUniquePaths(int[][] matrix) {
        if(matrix.length == 0) return 0;


        // If starting cell is 1, then you cannot start. So, you cannot reach to ending cell.
        // If ending cell is 1, then you cannot reach to ending cell.
        // So, in these bot cases, number of unique paths to reach from start to end cell is 0.
        if(matrix[0][0] == 1 || matrix[matrix.length-1][matrix[matrix.length-1][matrix[0].length-1]] == 0) {
            return 0;
        }

        // create another matrix to keep track of number of paths you can come to cells
        int[][] paths = new int[matrix.length][matrix[0].length]; // all cells are initialized to 0

        // There is only one way to come to starting cell. So, initialize it with 1.
        if(matrix[0][0] == 0) paths[0][0] = 1;

        // There is only one way to come to any cells in first row and first col.
        // e.g. you can come to matrix[0][1] only from matrix[0][0].
        // you can come to matrix[0][2] only from matrix[0][1]
        // you can come to matrix[1][0] only from matrix[0][0].
        // you can come to matrix[2][0] only from matrix[1][0]
        for(int col=1; col < matrix[0].length; col++) {
            if(matrix[0][col] == 0) {
                paths[0][col] = 1;
            }
        }

        for(int row=1; row < matrix.length; row++) {
            if(matrix[row][0] == 0) {
                paths[row][0] = 1;
            }
        }

        // Now, fill up remaining matrix
        for(int row=1; row < matrix.length; row++) {

            for(int col=1; col < matrix.length; col++) {

                if(matrix[row][col] == 0) {
                    paths[row][col] = paths[row-1][col] + paths[row][col-1];
                }
            }
        }

        // result in last cell is the answer (number of unique paths)
        return paths[matrix.length-1][matrix[0].length-1];
    }
}
