package algorithms._5matrix._1layers_based_problems;

import algorithms.utils.ArrayUtils;

/*
    You have been given NxN matrix. Make rows as cols and cols as rows.

    Important:
    You can do this in-place, only when matrix is NxN.
    If it is MxN, then you have to do it in another matrix of NxM, which is simpler.

    Matrix:

    1   2   3   4
    5   6   7   8
    9   10  11  12
    13  14  15  16

    Just like RotateMatrix.java, you have to use level based approach. Memorize this approach.


    There are predefined steps for Layered approach.

    - Find total number of layers
    - loop through number of layers
        find rows and cols involved in a layer
        find total number of swaps possible in a particular layer
        Do the swaps


    1) In level=0, row=0 and col=0 are involved

      -----------------
      | 1   2   3   4 |
      |   -------------
      | 5 | 6   7   8
      | 9 | 10  11  12
      | 13| 14  15  16
      -----

        After exchanging row=0 and col=0

        1   5   9   13
        2   6   7   8
        3   10  11  12
        4  14  15  16

    2) In level=1, row=1 and col=1 are involved

        1   5   9   13
           ------------
        2  | 6   7   8 |
           |    --------
        3  |10 | 11  12
        4  |14 | 15  16
           -----
        After exchanging row=1 and col=1

        1   5   9   13
        2   6   10  14
        3   7   11  12
        4   8   15  16


    3) In level=2, row=2 and col=2 are involved

        1   5   9   13
        2   6   10  14
               --------
        3   7  |11  12 |
               |   -----
        4   8  |15 |16
               -----

        After exchanging row=2 and col=2

        1   5   9   13
        2   6   10  14
        3   7   11  15
        4   8   12  16


    4) In level=3, row=3 and col=3 are involved

        1   5   9   13
        2   6   10  14
        3   7   11  15
                   ----
        4   8   12 |16|
                   ----

        After exchanging row=3 and col=3

        1   5   9   13
        2   6   10  14
        3   7   11  15
        4   8   12  16


    Steps

     - number of levels = number of rows OR number of cols

     - For each level, you need to find

     startRow=level
     startCol=level

     - For each level, you need to find out required swaps

     for level 0,

         1 swaps with 1
         2 swaps with 5
         3 swaps with 9
         4 swaps with 13

     for level 1,

        6 swaps with 6
        7 swaps with 10
        8 swaps with 14

     and so on...

     So totalSwapsRequired = cols - level



     int cols = matrix[0].length

     for(int level=0; level<levels; level++) {

        startRow = level
        startCol = level

        int totalSwapsRequired = cols - level;

        for(int j=0; j<totalSwapsRequired; j++) {

            swap matrix[level][col+j] and matrix[level+j][col]
        }

     }

*/
public class _1MakeRowsAsColsAndViceAVersa {

    public static void main(String[] args) {
        System.out.println("........Test Exchange Rows and Cols algorithm........");

        // make rows as cols and cols as rows
        {
            int[][] matrix = new int[][]{

                    new int[]{1, 2, 3, 4},
                    new int[]{5, 6, 7, 8},
                    new int[]{9, 10, 11, 12},
                    new int[]{13, 14, 15, 16}

            };

            System.out.println("Before rows are exchanged with cols");
            ArrayUtils.prettyPrintMatrix(matrix);

            exchangeRowsAndCols(matrix);

            System.out.println("After rows are exchanged with cols");
            ArrayUtils.prettyPrintMatrix(matrix);

        }
    }

    private static void exchangeRowsAndCols(int[][] matrix) {
        int levels = matrix.length - 1;

        int cols = matrix[0].length;

        for (int level = 0; level < levels; level++) {


            // IMPORTANT: In each level, number of swipes differs. This is a common logic in matrix related algorithms, when you need write algorithm by levels.
            // With every level, number of swipes reduces
            int totalSwipes = matrix[0].length - level;

            for (int j = 0; j < totalSwipes; j++) {
                //int rowValueToExchange = matrix[level][level+j];
                //int colValueToExchange = matrix[level+j][level];
                exchange(matrix, level, level + j, level + j, level);

            }
        }

    }

    private static void exchange(int[][] matrix, int one, int two, int oneone, int twotwo) {
        int temp = matrix[one][two];
        matrix[one][two] = matrix[oneone][twotwo];
        matrix[oneone][twotwo] = temp;
    }
}
