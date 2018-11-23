package algorithms._5matrix._1layers_based_problems;

import algorithms.utils.ArrayUtils;
/*
    Rotate a matrix by 90 degree.

    This problem can be solved using Layered approach.

    There are predefined steps for Layered approach.
    - Find total number of layers
    - loop through number of layers
        find rows and cols involved in a layer
        find total number of swaps possible in a particular layer
        Do the swaps

*/
public class _2RotateMatrix {
    public static void main(String[] args) {
        System.out.println("........Test Matrix Rotation by 90 degree algorithm - Book Way........");
        // Book way of rotating a matrix
        {
            int matrixSize = 4;

            int matrix[][] = new int[matrixSize][matrixSize];
            for (int i = 0; i < matrixSize; i++) {
                for (int j = 0; j < matrixSize; j++) {
                    matrix[i][j] = i * j;
                }
            }
            System.out.println("matrix before rotation");
            displayMatrix(matrix, matrixSize);
            //rotate(matrix, matrixSize);

            rotateAsPerBook(matrix, matrixSize);
        }

        System.out.println("........Test Matrix Rotation by 90 degree algorithm - My Way........");
        // My way of rotating a matrix
        {
            int[][] matrix = new int[][]{

                    new int[]{1, 2, 3, 4},
                    new int[]{5, 6, 7, 8},
                    new int[]{9, 10, 11, 12},
                    new int[]{13, 14, 15, 16}

//                    new int[]{1, 2, 3},
//                    new int[]{4, 5, 6},
//                    new int[]{7, 8, 9}

            };

            System.out.println("Before Rotation...");
            ArrayUtils.prettyPrintMatrix(matrix);

            rotate(matrix);

            System.out.println("After Rotation");
            ArrayUtils.prettyPrintMatrix(matrix);

        }
    }

    // This algorithm is for how to rotate a matrix by 90 degrees clockwise
    // If you want to do anti-clockwise or 180/270 degree, just the logic of how you exchange 4 corners differs.
    private static void rotate(int[][] matrix) {
        int levels = matrix.length / 2;

        int rows = matrix.length - 1;
        int cols = matrix[0].length - 1;

        /*

            1   2   3   4
            5   6   7   8
            9   10  11  12
            13  14  15  16

            1) you need to rotate the matrix level-by-level.


                First rotate rows and cols of level 0

                ------------------
                |1 |  2 |  3 |  4 |
                -------------------
                |5 |  6    7 |  8 |
                |9 |  10  11 | 12 |
                ------------------|
                |13|  14| 15 | 16 |
                -------------------

                Then rotate rows and cols of level 1

                1   2   3   4
                  ---------
                5 | 6 | 7 |  8
                  ---------
                9 | 10| 11|  12
                  ---------
                13  14  15  16




            2) For each level, you need to find out what rows and cols are involved in rotation.

            startRow = level
            endRow = rows - level
            startCol = level
            endCol = cols - level

            3) Then you need to find out total swipes required for a respective level.

               cols = matrix[0].length; //4

               // for level=0, you need to swipe 1,4,16,13 and then 2,8,15,9 and then 3,12,14,5
               // So, 3 swipes
               // for level=1, you need to swipe, 6,7 and 11,10
               // So, 2 swipes
               int totalSwapsRequired = (cols-1)/(level+1);

               for(j = 0; j <= totalSwapsRequired; j++) {

                   - find out positions of 1,4,16,13

                   ----          ----
                   | 1 |  2   3 | 4 |
                   -----         ----
                    5     6   7   8
                    9     10  11  12
                   -----         -----
                   |13 |  14  15 | 16 |
                   -----         -----

                    int topLeft = matrix[startRow][startCol + j];
                    int topRight = matrix[startRow + j][endCol];
                    int bottomRight = matrix[endRow][endCol - j];
                    int bottomLeft = matrix[endRow - j][startCol];

                    // swapping logic
                    temp        =   topLeft
                    topLeft     =   bottomLeft
                    bottomLeft  =   bottomRight
                    bottomRight =   topRight
                    topRight    =   temp


                    - find out positions of 2,8,15,9
                       ----
                    1  |2 |  3   4
                       ----
                               -----
                    5   6   7  | 8 |
                               -----
                   ----
                   |9 |  10  11  12
                   ----
                           -----
                    13  14 | 15 | 16
                           ------

                   swap them

                   - find out positions of 3,12,14,5
                             ----
                    1     2  | 3 |  4
                             -----
                   -----
                   | 5 |  6    7    8
                   -----
                                  -----
                    9     10   11 | 12 |
                                  ------
                       ------
                    13 | 14 |  15  16
                       ------

                 swap them

               }

         */
        for (int level = 0; level < levels; level++) {

            // IMPORTANT: for every level, startRow, endRow, startCol and endCol changes
            /*
             For level=0,
                startRow=first row,
                startCol=first col,
                endRow=last row,
                endCol=last col

                ------------------
                |1 |  2 |  3 |  4 |
                -------------------
                |5 |  6    7 |  8 |
                |9 |  10  11 | 12 |
                ------------------|
                |13|  14| 15 | 16 |
                -------------------

                total swipes required = 3 (total cols - 1 - level)

             For level=1,
                startRow=second row,
                startCol= second col,
                endRow= second last row,
                endCol=second last col

                1   2   3   4
                  ---------
                5 | 6 | 7 |  8
                  ---------
                9 | 10| 11|  12
                  ---------
                13  14  15  16

                total swipes required = 3 (total cols - 1 - level)

             It's like For
             level=0, outer square
             level=1, inner square
             level=2, inner-inner square
             and so on
            */
            int startRow = 0 + level;
            int startCol = 0 + level;
            int endRow = rows - level;
            int endCol = cols - level;

            // IMPORTANT: In each level, number of swipes differs. This is a common logic in matrix related algorithms, when you need write algorithm by levels.

            // if there are 4 columns, 3 swipes are required in level 0
            // and 1 swipe is required in level 1
            int totalSwaps = (cols - 1) / (level + 1);

            // if there are 4 cols in matrix, this loop iterates 3 times.
            for (int j = 0; j <= totalSwaps; j++) {

                int topLeft = matrix[startRow][startCol + j];
                int topRight = matrix[startRow + j][endCol];
                int bottomRight = matrix[endRow][endCol - j];
                int bottomLeft = matrix[endRow - j][startCol];

                // exchange 4 corners
                int temp = topLeft;
                matrix[startRow][startCol + j] = bottomLeft; // topLeft=bottomLeft
                matrix[endRow - j][startCol] = bottomRight; // bottomLeft=bottomRight
                matrix[endRow][endCol - j] = topRight; // bottomRight=topRight
                matrix[startRow + j][endCol] = temp; // topRight=topLeft

            }

            ArrayUtils.prettyPrintMatrix(matrix);
            System.out.println();

        }

    }

    private static void rotateAsPerBook(int[][] matrix, int n) {
        for (int layer = 0; layer < n / 2; layer++) {
            int first = layer;
            int last = n - 1 - layer;
            for (int i = first; i < last; i++) {
                int offset = i - first;
                // save top
                int top = matrix[first][i];

                // copy left to top
                matrix[first][i] = matrix[last - offset][first];

                // copy bottom to left
                matrix[last - offset][first] = matrix[last][last - offset];
                // copy right to bottom
                matrix[last][last - offset] = matrix[i][last];
                // copy top to right
                matrix[i][last] = top;

            }
        }
        System.out.println("matrix after rotation");
        displayMatrix(matrix, n);
    }

    private static void rotate(int[][] matrix, int matrixSize) {
        int numberOfLayers = matrixSize / 2; // n/2

        for (int layer = 0; layer < numberOfLayers; layer++) {

            for (int ele = layer; ele <= matrixSize - 1 - layer; ele++) {

                int left = matrix[matrixSize - 1 - layer][layer];
                int top = matrix[layer][ele];
                int right = matrix[matrixSize - 1 - ele][matrixSize - 1 - layer];
                int bottom = matrix[matrixSize - 1][ele];

                matrix[layer][ele] = left; // copy left to top

                matrix[ele][matrixSize - 1] = top; // copy top to right

                matrix[matrixSize - 1][ele] = right; // copy right to bottom

                matrix[ele][layer] = bottom;  // copy bottom to left


            }
            System.out.println("matrix after rotation");
            displayMatrix(matrix, matrixSize);
        }
        System.out.println("matrix after rotation");
        displayMatrix(matrix, matrixSize);
    }

    private static void displayMatrix(int[][] matrix, int matrixSize) {
        for (int i = 0; i < matrixSize; i++) {
            for (int j = 0; j < matrixSize; j++) {
                System.out.print(matrix[i][j] + "   ");
            }
            System.out.println();
        }
    }

}

