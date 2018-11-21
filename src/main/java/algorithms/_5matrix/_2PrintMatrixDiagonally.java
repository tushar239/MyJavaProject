package algorithms._5matrix;

/*
    Print Matrix Diagonally

    https://www.youtube.com/watch?v=T8ErAYobcbc


*/
public class _2PrintMatrixDiagonally {

    public static void main(String[] args) {
        int[][] matrix = new int[][]{

                new int[]{1, 2, 3, 4},
                new int[]{5, 6, 7, 8},
                new int[]{9, 10, 11, 12},
                new int[]{13, 14, 15, 16}

//                    new int[]{1, 2, 3},
//                    new int[]{4, 5, 6},
//                    new int[]{7, 8, 9}

        };

        printDiagonally(matrix);
    }
/*

    1   2   3   4
    5   6   7   8
    9   10  11  12
    13  14  15  16

    col is increasing in subsequent line. (so needs a for loop of cols starting from 0)
    In each line, row is increasing till col becomes 0
    (0,0)
    (0,1) (1,0)
    (0,2) (1,1) (2,0)
    (0,3) (1,2) (2,1) (3,0)

    From row=1 onwards, row is increasing in subsequent line. (so needs a for loop of rows starting from 1)
    In each line, row increases and col starts from last col and then decreases
    (1,3) (2,2) (3,1)
    (2,3) (3,2)
    (3,3)
 */
    private static void printDiagonally(int[][] matrix) {

        int rows = matrix.length;
        int cols = matrix[0].length;

        for (int col = 0; col < cols; col++) {

            int r = 0;
            int c = col;

            while(c >= 0) {
                System.out.print(matrix[r][c]+",");
                r++;
                c--;
            }
            System.out.println();

        }

        for(int row=1; row<rows; row++) {
            int r = row;
            int c = cols-1;

            while(r < rows) {
                System.out.print(matrix[r][c]+",");
                r++;
                c--;
            }
            System.out.println();
        }
    }
}
