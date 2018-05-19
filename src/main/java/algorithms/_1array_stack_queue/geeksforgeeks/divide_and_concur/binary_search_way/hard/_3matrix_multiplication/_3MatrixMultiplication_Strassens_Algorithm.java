package algorithms._1array_stack_queue.geeksforgeeks.divide_and_concur.binary_search_way.hard._3matrix_multiplication;

/*
    Matrix Multiplication (Strassen's Algorithm):

        Watch 'Matrix Multiplication.mp4'
        https://www.geeksforgeeks.org/strassens-matrix-multiplication/


    Strassen's Algorithm is also based on divide and conquer, but with a little bit of improvement.

    Divide and Conquer approach takes O(n^3) - MatrixMultiplication_divide_and_conquer.java
    Stranssen's algorithm that is also based on Divide and Conquer takes O(n^2.81)

    If you look at MatrixMultiplication_divide_and_conquer.java,
    It needs A11, A12, A21, A22, B11, B12, B21, B22 matrices to get C11, C12, C21, C22.
    Strassen has given some formulas that reduces number of multiplications between A11, A12, A21, A22, B11, B12, B21, B22.
    It increases number of additions and subtractions, but that is cheaper than multiplications between matrices.

    https://www.geeksforgeeks.org/easy-way-remember-strassens-matrix-equation/

    This reduces number of multiplications from 8 to 7.

    So, Time Complexity:

        T(n) = 7 T(n/2) + n^2

        Master's theorem,

            T(n) = a T(n/b) + n^c ,  where a>=1 and b>1

            if c < logb a, then result is n ^ logb a
            if c = logb a, then result is logb a * n^c
            if c > logb a, then result is n^c

       logb a = log2 7 = 2.81
       c = 2

       here, c < logb a
       so time complexity = O(n^2.81)

*/
public class _3MatrixMultiplication_Strassens_Algorithm {
}
