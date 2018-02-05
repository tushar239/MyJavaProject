package algorithms.crackingcodinginterviewbook._1stringmanipulations;

/*
CCI book pg 203

Zero Matrix:
Write an algorithm such that if an element in an M x N matrix is 0, it's entire row and column are set to 0

solution:

if(rows > 1 && cols > 1)
    start from row=1 and col=1. If 0 found, set related a[0][col] and a[row][0] to 0.
    iterate through row=0. if 0 found, then set related cols to 0.
    iterate through col=0. if 0 found, then set related rows to 0.
else if(rows > 1 && cols == 1)
    find 0 in a[row][0]. If found, then set entire row to 0.
else if(rows > 1 && cols == 1)
    find 0 in a[0][col]. If found, then set entire col to 0.
 */
public class _8Find0InMatrix {
    public static void main(String[] args) {

        int[][] a = new int[][]{
                {1, 1, 1},
                {1, 0, 1},
                {1, 1, 1},
        };

        findAndReplace(a);
        print(a);

        /*
            1   0   1
            0   0   0
            1   0   1
        */

        a = new int[][]{
                {1, 0, 1}
        };

        findAndReplace(a);
        print(a);

        /*
            0   0   0
        */

        a = new int[][]{
                {1},
                {0},
                {1}
        };

        findAndReplace(a);
        print(a);
        /*
            1
            1
            1
        */
    }

    private static void print(int[][] a) {
        System.out.println();

        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a[i].length; j++) {
                System.out.print(a[i][j] + "   ");
            }
            System.out.println();

        }
        System.out.println();
    }

    private static void findAndReplace(int[][] a) {
        if (a == null || a.length == 0) return;

        if (a.length > 1 && a[0].length > 1) {
            for (int i = 1; i < a.length; i++) {
                for (int j = 1; j < a[i].length; j++) {
                    if (a[i][j] == 0) {
                        a[i][0] = 0;
                        a[0][j] = 0;
                    }
                }
            }

            for (int i = 0; i < a.length; i++) {//rows
                if (a[i][0] == 0) { // checking first col
                    for (int j = 1; j < a[i].length; j++) {//cols
                        a[i][j] = 0;
                    }
                }
            }

            for (int j = 0; j < a[0].length; j++) {// cols
                if (a[0][j] == 0) {// checking first row
                    for (int i = 1; i < a.length; i++) {//rows
                        a[i][j] = 0;
                    }
                }
            }

        } else if (a.length == 1 && a[0].length > 1) {
            boolean foundZero = false;
            for (int j = 0; j < a[0].length; j++) {
                if (a[0][j] == 0) {
                    foundZero = true;
                    break;
                }
            }
            if (foundZero) {
                for (int j = 0; j < a[0].length; j++) {
                    a[0][j] = 0;
                }
            }
        } else if (a.length > 1 && a[0].length == 1) {
            boolean foundZero = false;
            for (int i = 0; i < a.length; i++) {
                if (a[i][0] == 0) {
                    foundZero = true;
                    break;
                }
            }
            if (foundZero) {
                for (int i = 0; i < a.length; i++) {
                    a[i][0] = 0;
                }
            }
        }
    }
}
