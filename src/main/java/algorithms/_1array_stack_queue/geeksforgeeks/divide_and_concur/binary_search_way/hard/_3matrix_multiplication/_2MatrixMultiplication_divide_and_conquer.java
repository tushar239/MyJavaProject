package algorithms._1array_stack_queue.geeksforgeeks.divide_and_concur.binary_search_way.hard._3matrix_multiplication;

import algorithms.utils.ArrayUtils;

/*

Matrix Multiplication (Divide and Conquer):

    Matrix Multiplication.mp4
    https://stackoverflow.com/questions/21496538/square-matrix-multiply-recursive-in-java-using-divide-and-conquer


Matrix multiplication is done in following way

    |a,b,c,w|             |1,2,3,21   |             |a*1 + b*4 + c*7 + w*10     a*2 + b*5 + c*8 + w*11    a*3 + b*6 + c*9 + w*12    a*21 + b*22 + c*23 + w*24|
    |d,e,f,x|     X       |4,5,6,22   |     =       |d*1 + e*4 + f*7 + x*10     d*2 + e*5 + f*8 + x*11    d*3 + e*6 + f*9 + x*12    d*21 + e*22 + f*23 + x*24|
    |g,h,i,y|             |7,8,9,23   |             |g*1 + h*4 + i*7 + y*10     g*2 + h*5 + i*8 + y*11    g*3 + h*6 + i*9 + y*12    g*21 + h*22 + i*23 + y*24|
    |j,k,l,z|             |10,11,12,24|             |j*1 + k*4 + l*7 + z*10     j*2 + k*5 + l*8 + z*11    j*3 + k*6 + l*9 + z*12    j*21 + k*22 + l*23 + z*24|


Matrix addition is done in following way

    |a,b,c|             |1,2,3|             |a+1  b+4  c+7|
    |d,e,f|     X       |4,5,6|     =       |d+4  e+5  f+6|
    |g,h,i|             |7,8,9|             |g+7  h+8  i+9|


Important Assumption:

    This algorithm assumes that size of both matrices are same and size of each matrix is a power of 2.
    If that is not the case, then you can create matrices from with 0s filled up to meet above mentioned requirement.


Solution:


    watch 'Matrix Multiplication.mp4'



Time-Complexity:

    T(n) = 8 T(n/2) + n^2

    Master's theorem:
    https://www.geeksforgeeks.org/analysis-algorithm-set-4-master-method-solving-recurrences/

    T(n) = a T(n/b) + n^c ,  where a>=1 and b>1

        if c < logb a, then result is n ^ logb a
        if c = logb a, then result is logb a * n^c
        if c > logb a, then result is n^c

    Here,
        T(n) = 8 T(n/2) + n^2

             a = 8, b = 2, c = 2

             so, logb a = log2 8 = 3

             so, T(n) = n ^ logb a
                      = n ^ 3

    So, Time-Complexity of this algorithm is O(n^3). This is no better than Iterative approach (MatrixMultiplication_iterative.java).
    To get better performance, you need to use Strassens's algorithm (MatrixMultiplication_Stressens_Algorithm.java)


*/

// here it is assumed that it's a square matrix (number of rows and cols are same)
public class _2MatrixMultiplication_divide_and_conquer {

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
                new int[]{1, 2, 3, 4},
                new int[]{1, 2, 3, 4},
                new int[]{1, 2, 3, 4},
                new int[]{1, 2, 3, 4}
//                new int[]{4, 5},
//                new int[]{6, 7}

        };

        /*{
            int[][] result = matrixMultiplicationFinal(A, B);
            ArrayUtils.prettyPrintMatrix(result);
        }*/

        {
            int[][] result = matrixMult(A, B, 0, 0, 0, 0, A.length);
            ArrayUtils.prettyPrintMatrix(result);
        }


    }


    public static int[][] matrixMult(int[][] A, int[][] B, int startRowA, int startColA, int startRowB, int startColB, int size) {

        int[][] C = new int[size][size];

        if (size == 1) {
            C[0][0] = A[startRowA][startColA] * B[startRowB][startColB];
        } else {
            int newSize = size / 2;

            // you can avoid creating aux matrices by passing related start and end cols to matrixMult recursive calls.
            // for simplicity of understanding, I have created these aux arrays.
            int[][] A11 = partition(A, startRowA, startColA, newSize);
            int[][] A12 = partition(A, startRowA, startColA + newSize, newSize);
            int[][] A21 = partition(A, startRowA + newSize, startColA, newSize);
            int[][] A22 = partition(A, startRowA + newSize, startColA + newSize, newSize);

            int[][] B11 = partition(B, startRowB, startColB, newSize);
            int[][] B12 = partition(B, startRowB, startColB + newSize, newSize);
            int[][] B21 = partition(B, startRowB + newSize, startColB, newSize);
            int[][] B22 = partition(B, startRowB + newSize, startColB + newSize, newSize);

            int[][] A11_B11 = matrixMult(A11, B11, 0, 0, 0, 0, A11.length);
            int[][] A12_B21 = matrixMult(A12, B21, 0, 0, 0, 0, A12.length);

            int[][] A11_B12 = matrixMult(A11, B12, 0, 0, 0, 0, A11.length);
            int[][] A12_B22 = matrixMult(A12, B22, 0, 0, 0, 0, A12.length);

            int[][] A21_B11 = matrixMult(A21, B11, 0, 0, 0, 0, A21.length);
            int[][] A22_B21 = matrixMult(A22, B21, 0, 0, 0, 0, A22.length);

            int[][] A21_B12 = matrixMult(A21, B12, 0, 0, 0, 0, A21.length);
            int[][] A22_B22 = matrixMult(A22, B22, 0, 0, 0, 0, A22.length);

            int[][] C11 = sum(A11_B11, A12_B21, newSize);
            int[][] C12 = sum(A11_B12, A12_B22, newSize);
            int[][] C21 = sum(A21_B11, A22_B21, newSize);
            int[][] C22 = sum(A21_B12, A22_B22, newSize);

            fillup(C, C11, C12, C21, C22, newSize);
        }

        return C;

    }

    // O(n^2)
    private static void fillup(int[][] c, int[][] c11, int[][] c12, int[][] c21, int[][] c22, int newSize) {
        for (int i = 0; i < c11.length; i++) {
            for (int j = 0; j < c11[i].length; j++) {
                c[i][j] = c11[i][j];
            }
        }

        for (int i = 0; i < c12.length; i++) {
            for (int j = 0; j < c12[i].length; j++) {
                c[i][j + newSize] = c12[i][j];
            }
        }

        for (int i = 0; i < c21.length; i++) {
            for (int j = 0; j < c21[i].length; j++) {
                c[i + newSize][j] = c21[i][j];
            }
        }

        for (int i = 0; i < c22.length; i++) {
            for (int j = 0; j < c22[i].length; j++) {
                c[i + newSize][j + newSize] = c22[i][j];
            }
        }

    }

    // O(n^2)
    // Matrix addition - https://en.wikipedia.org/wiki/Matrix_addition
    private static int[][] sum(int[][] a, int[][] b, int newSize) {
        int[][] c = new int[newSize][newSize];

        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a[i].length; j++) {
                c[i][j] = a[i][j] + b[i][j];
            }
        }

        return c;
    }

    // O(n^2)
    private static int[][] partition(int[][] A, int startRowA, int startColA, int newSize) {
        int[][] c = new int[newSize][newSize];

        int startRowC = 0;

        for (int i = startRowA; i < startRowA + newSize; i++) {

            int startColC = 0;

            for (int j = startColA; j < startColA + newSize; j++) {
                c[startRowC][startColC] = A[i][j];
                startColC++;
            }

            startRowC++;
        }


        return c;
    }
/*
    // https://stackoverflow.com/questions/21496538/square-matrix-multiply-recursive-in-java-using-divide-and-conquer
    public static int[][] matrixMultiplicationFinal(int[][] A, int[][] B) {

        return matrixMultiplication(A, B, 0, 0, 0, 0, A.length);

    }

    public static int[][] matrixMultiplication(
            int[][] A, int[][] B, int startRowA, int startColA, int startRowB, int startColB, int size) {

        int[][] C = new int[size][size];

        if (size == 1)
            C[0][0] = A[startRowA][startColA] * B[startRowB][startColB];

        else {

            int newSize = size / 2;
            //C11
            sumMatrix(C,

                    matrixMultiplication(A, B, startRowA, startColA, startRowB, startColB, newSize),
                    matrixMultiplication(A, B, startRowA, startColA + newSize, startRowB + newSize, startColB, newSize),
                    0, 0);

            sumMatrix(C,

                    matrixMultiplication(A, B, startRowA, startColA, startRowB, startColB + newSize, newSize),
                    matrixMultiplication(A, B, startRowA, startColA + newSize, startRowB + newSize, startColB + newSize, newSize),
                    0, newSize);

            sumMatrix(C,

                    matrixMultiplication(A, B, startRowA + newSize, startColA, startRowB, startColB, newSize),
                    matrixMultiplication(A, B, startRowA + newSize, startColA + newSize, startRowB + newSize, startColB, newSize),
                    newSize, 0);

            sumMatrix(C,

                    matrixMultiplication(A, B, startRowA + newSize, startColA, startRowB, startColB + newSize, newSize),
                    matrixMultiplication(A, B, startRowA + newSize, startColA + newSize, startRowB + newSize, startColB + newSize, newSize),
                    newSize, newSize);
        }

        return C;

    }


    private static void sumMatrix(int[][] C, int[][] A, int[][] B, int rowC, int colC) {
        int n = A.length;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++)
                C[i + rowC][j + colC] = A[i][j] + B[i][j];
        }

    }*/
}
