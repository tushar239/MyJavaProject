package algorithms._1array_stack_queue.geeksforgeeks.divide_and_concur.binary_search_way.hard._3matrix_multiplication;

import algorithms.utils.ArrayUtils;

/*
    Matrix Multiplication (Iterative approach)

    https://www.geeksforgeeks.org/c-program-multiply-two-matrices/

    Time Complexity = O(n^3)


    Rules of Matrix Multiplication:

        Two matrices can be multiplied only when number of cols of one matches with number rows of another.
        e.g. A=4x3  B=3x5 can be multiplied with each other and resulting matrix will be of size 4x5 and total number of multiplications will be 4*3*5
             A=4x4  B=3x5 can't be multiplied with each other.

        Matrix multiplication is done in following way

        |a,b,c|             |1,2,3|             |a*1 + b*4 + c*7     a*2 + b*5 + c*8     a*3 + b*6 + c*9|
        |d,e,f|     X       |4,5,6|     =       |d*1 + e*4 + f*7     d*2 + e*5 + f*8     d*3 + d*6 + f*9|
        |g,h,i|             |7,8,9|             |g*1 + h*4 + i*7     g*2 + h*5 + i*8     g*3 + h*6 + i*9|



    Remember:
    If one matrix is of 4 x 3 size and another one is of 3 x 5 size,
    then resulting matrix after multiplication will be of 4 x 5 size.


*/
public class _1MatrixMultiplication_iterative {

    public static void main(String[] args) {
        int[][] A = {
                new int[]{1, 2, 3, 4},
                new int[]{1, 2, 3, 4},
                new int[]{1, 2, 3, 4},
                new int[]{1, 2, 3, 4}

//                new int[]{1, 2},
//                new int[]{1, 2}
        };

        int[][] B = {
                new int[]{1, 2, 3},
                new int[]{1, 2, 3},
                new int[]{1, 2, 3},
                new int[]{1, 2, 3}
//                new int[]{4, 5},
//                new int[]{6, 7}

        };

        int[][] C = matrixMultiplication(A, B);
        ArrayUtils.prettyPrintMatrix(C);
    }

    private static int[][] matrixMultiplication(int[][] A, int[][] B) {

        // assuming that input matrices have size >=1.
        // assuming that number of A's columns is same as number of B's rows.

        int rowsSizeOfResultingMatrix = A.length;
        int colsSizeOfResultingMatrix = B[0].length;

        int[][] C = new int[rowsSizeOfResultingMatrix][colsSizeOfResultingMatrix];

        for (int i = 0; i < rowsSizeOfResultingMatrix; i++) { // this for loop is for resulting matrix's number of rows
            for (int j = 0; j < colsSizeOfResultingMatrix; j++) {// this for loop is for resulting matrix's number of cols
                C[i][j] = 0;
                for (int k = 0; k < A[0].length; k++) {// this for loop is for A's number of cols or B's number of rows.
                    C[i][j] += A[i][k] * B[k][j];
                }
            }
        }

        return C;
    }

}
