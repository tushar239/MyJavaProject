package algorithms._1array_stack_queue.geeksforgeeks.dynamic_programming._3matrix;

/*
Matrix Chain Multiplication

 https://www.geeksforgeeks.org/dynamic-programming-set-8-matrix-chain-multiplication/
 Matrix Chain Multiplication.mp4 (https://www.youtube.com/watch?v=D1U74eFLg_g)

 If we had four matrices A, B, C, and D, we would have:

    (ABC)D = (AB)(CD) = A(BCD) = ....

 However, the order in which we parenthesize the product affects the number of simple arithmetic operations needed to compute the product, or the efficiency. For example, suppose A is a 10 × 30 matrix, B is a 30 × 5 matrix, and C is a 5 × 60 matrix. Then,

    (AB)C = (10×30×5) + (10×5×60) = 1500 + 3000 = 4500 operations
    A(BC) = (30×5×60) + (10×30×60) = 9000 + 18000 = 27000 operations.


 e.g.
  - Input: p[] = {40, 20, 30, 10, 30}
  Output: 26000

  There are 4 matrices of dimensions 40x20, 20x30, 30x10 and 10x30.
  Let the input 4 matrices be A, B, C and D.  The minimum number of
  multiplications are obtained by putting parenthesis in following way

  (A(BC))D --> 20*30*10 + 40*20*10 + 40*10*30

  - Input: p[] = {10, 20, 30, 40, 30}
  Output: 30000

  There are 4 matrices of dimensions 10x20, 20x30, 30x40 and 40x30.
  Let the input 4 matrices be A, B, C and D.  The minimum number of
  multiplications are obtained by putting parenthesis in following way
  ((AB)C)D --> 10*20*30 + 10*30*40 + 10*40*30


Rules of Matrix Multiplication:

    See 'MatrixMultiplication_iterative.java'

Solutions:

 - Brute-Force solution will take O(2^n).

                        ABCD
            A BCD               AB CD           ABC D
              BC D  B CD        --            AB C  A BC
              --                              --      --

 There will be 2^n nodes in recursive tree. On each node, you are doing O(1) operation of keep track of min multiplication.

 - This is clearly a dynamic programming problem because there are repeating nodes in a recursion tree.
 Using dynamic programming, you can reduce time complexity to O(n).

*/
public class _1MatrixChainMultiplication {

    public static void main(String[] args) {
//        int[] A = {10, 20, 30, 40, 30};//30_000
        int[] A = {40, 20, 30, 10, 30};//26_000
//        int[] A = {10, 20, 30};//6000

        {
//            int min = matrixChainMultiplicationInOrder_Brute_Force(A, 1, A.length - 1);
            int min = matrixChainMultiplicationInOrder_Brute_Force(A, 0, A.length - 1);
            System.out.println(min);
        }
        {
            int min = matrixChainOrder(A, 1, A.length - 1);
            System.out.println(min);
        }

    }

    /*

        There is a similarity in this problem and PaintersPartitionProblem.java, LongestIncreasingSubSequenceInArray.java problems. All of these needs a loop of 'divider'.

        int[] A = [10 20 30 40 50 60 70]

        You can say this is how matrices are defined in given array

            A=10 x 20
            B=20 x 30
            C=30 x 40
            D=40 x 50
            E=50 x 60
            F=60 x 70

        There can multiple possible combinations of multiplying A with other matrices.

            A(BCDEF)
            (AB)(CDEF)
            (ABC)(DEF)
            (ABCD)(EF)
            (ABCDE)F


        start               end
        |                  |
        10 20 30 40 50 60 70
          A  B  C  D  E  F
             |
           divider(starts from 20 because second matrix is 20x30, ends at 60 (not at 70) because last matrix is 60x70)


        So, you need a 'divider' loop starting from B (20 in array) and that ends on E (not F)

        int find(A, start, end) {

            for(int divider=start; divider < end; divider++) {

                so you need to do

                int numberOfMultiplicationsFromStartToDivider = number of multiplications for multiplying matrices that starts from 'start' and ends at 'divider'

                int numberOfMultiplicationsFromDividerToEnd = number of multiplications for multiplying matrices that starts from 'divider' and ends at 'end'

                int lastMultiplicationNumbers = A[start]*A[divider]*A[end]

                    If you see, this two operations will actually give you multiplication numbers from two sets of matrices.
                    e.g. if divider is at B, then
                    (10 x 20) (20 x 70)

                    Now, you need to add number of multiplications of these two matrices also (10 x 20 x 70) that will give you a final matrix.


                result = numberOfMultiplicationsFromStartToDivider + numberOfMultiplicationsFromDividerToEnd + lastMultiplicationNumbers

            }
        }


        IMPORTANT:
        If you say divider <= end in above mentioned for loop, this recursive call will result in infinite recursion because when 'divider' reaches to 'end', it will be like a brand new call with (A, start, divider=end). This is where you started this algorithm from and it will recurse it again from the beginning.
        This is a very important concept for recursion.

    */
    private static int matrixChainMultiplicationInOrder_Brute_Force(int[] A, int start, int end) {

        /*if(start == end) return 0;
        if (start + 1 == end) return (A[start-1] * A[start] * A[start+1]);

        int min = Integer.MAX_VALUE;

        for (int divider = start; divider < end; divider++) {

            int multiplication =matrixChainMultiplicationInOrder_Brute_Force(A, start, divider) +
                    matrixChainMultiplicationInOrder_Brute_Force(A, divider+1, end) +
                    + (A[start-1] * A[divider] * A[end]);

            min = Math.min(min, multiplication);
        }

        return min;*/
        if (start == end) return 0; // there is no matrix
        if ((start + 1) == end) return 0; // there is just 1 matrix
        if (start + 2 == end) return (A[start] * A[start + 1] * A[start + 2]);// there are 2 matrices

        int min = Integer.MAX_VALUE;

        for (int divider = start + 1; divider < end; divider++) {// CAUTION: do not use 'divider <= end'

            int multiplication =
                    // CAUTION: if you say divider <= end, this recursive call will result in infinite recursion because when 'divider' reaches to 'end', it will be like a brand new call with (A, start, divider=end). This is where you started this algorithm from and it will recurse it again from the beginning.
                    matrixChainMultiplicationInOrder_Brute_Force(A, start, divider) +
                    matrixChainMultiplicationInOrder_Brute_Force(A, divider , end) +
                    +(A[start] * A[divider] * A[end]);

            min = Math.min(min, multiplication);
        }

        return min;
    }

    // From https://www.geeksforgeeks.org/dynamic-programming-set-8-matrix-chain-multiplication/
    // Matrix Ai has dimension p[i-1] x p[i] for i = 1..n
    private static int matrixChainOrder(int p[], int i, int j) {
        if (i == j)
            return 0;

        int min = Integer.MAX_VALUE;

        // place parenthesis at different places between first
        // and last matrix, recursively calculate count of
        // multiplications for each parenthesis placement and
        // return the minimum count
        for (int k = i; k < j; k++) {
            int count = matrixChainOrder(p, i, k) +
                    matrixChainOrder(p, k + 1, j) +
                    p[i - 1] * p[k] * p[j];


            min = Math.min(count, min);
        }

        // Return minimum count
        return min;
    }

}
