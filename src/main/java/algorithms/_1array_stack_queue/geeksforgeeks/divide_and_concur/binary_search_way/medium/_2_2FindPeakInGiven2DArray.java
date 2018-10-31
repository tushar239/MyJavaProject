package algorithms._1array_stack_queue.geeksforgeeks.divide_and_concur.binary_search_way.medium;

/*
    Find a peak element in a 2D array

    https://www.geeksforgeeks.org/find-peak-element-2d-array/

    An element is a peak element if it is greater than or equal to its four neighbors, left, right, top and bottom.
    For example neighbors for A[i][j] are A[i-1][j], A[i+1][j], A[i][j-1] and A[i][j+1].
    For corner elements, missing neighbors are considered of negative infinite value.

    Examples:

    Input : 10 20 15
            21 30 14
            7  16 32
    Output : 30

    30 is a peak element because all its
    neighbors are smaller or equal to it.
    32 can also be picked as a peak.

    Input : 10 7
            11 17
    Output : 17


    This problem is similar to 'FindPeakInGivenArray.java'

    Brute-Force will take O(rows * columns) time.
        bottom right corner and evaluate every single element in a matrix till you find a peak.

    Better solution-1

        It takes O(rows * log(columns)) time + no extra space other than recursive call stack that will be O(log columns).

        1. find max element in middle column.
        2. compare that max element with its neighbours in adjacent columns to check whether it's a peak in 2D.
        3. If not, then if bigger element is on left column, then repeat step1 and 2 for left side matrix, otherwise do it for right side matrix.

        Basically, you are doing Binary Search kind of divide-and-concur on columns and in each column you are visiting all rows to find a max.
        So, O(rows * log(columns)).

        GeeksForGeeks site suggests this solution.

    Better solution-2

        It takes O(log rows * log columns) time, but it uses Aux array apart from recursive call stack as explained below.

        1. find a PEAK in middle column, just like how you did it in FindPeakInGivenArray.java
        2. Once PEAK is found in middle column, compare it with its neighbours in adjacent columns to check whether it's a peak in 2D.
        3. If not, then if bigger element is on left column, then repeat step1 and 2 for left side matrix, otherwise do it for right side matrix.

        Following site suggests this solution.
        http://kamalmeet.com/algorithms/finding-a-peak-in-2-dimensional-array/

        Problem with this approach is it uses Aux array O(rows) because you need to convert a column in 1D array to find a peak in it.
        +
        It takes O(log columns) for recursive stack.
*/
public class _2_2FindPeakInGiven2DArray {

    public static void main(String[] args) {
       /* int[][] A = {
                new int[]{10, 20, 15},
                new int[]{21, 30, 14},
                new int[]{7, 16, 32}
        }; // 30
*/
/*
        int[][] A = {
                new int[]{10, 20, 15},
                new int[]{30, 21, 14},
                new int[]{7, 16, 32}
        }; // 30
*/

       /* int[][] A = {
                new int[]{10, 30, 35},
                new int[]{21, 20, 14},
                new int[]{74, 16, 32}
        }; // 35*/

       /* int[][] A = {
                new int[]{10, -2, -1},
                new int[]{21, 20, 14},
                new int[]{74, 16, 32}
        }; // 74*/

        int[][] A = {
                new int[]{10, -2, -1},
                new int[]{74, 20, 14},
                new int[]{21, 16, 32}
        }; // 74
        int peak = findPeak(A, 0, 0, A.length-1, A[0].length-1);
        System.out.println(peak);
    }

    // Important:
    // It is very important to remember that when you do divide and concur in Binary Search style, you cannot pass 'mid' as an 'end' index in recursive call.
    // If you do that, it will end up in infinite recursion.
    private static int findPeak(int[][] A, int startRow, int startCol, int endRow, int endCol) {

        if (startCol > endCol) return Integer.MIN_VALUE;

        int midCol = (startCol + endCol) / 2;

        int maxElementRowIndex = findMaxElementIndex(A, midCol);
        int maxElement = A[maxElementRowIndex][midCol];

        // Important:
        // Don't compare midCol-1 >= startCol. Always do midCol > startCol.
        // Similarly, don't compare midCol+1 <= endCol. Always do midCol < endCol.
        if ((midCol == startCol && midCol == endCol) ||
                (midCol > startCol && midCol < endCol && A[maxElementRowIndex][midCol - 1] <= maxElement && A[maxElementRowIndex][midCol + 1] <= maxElement) ||
                (midCol > startCol && midCol == endCol && A[maxElementRowIndex][midCol - 1] <= maxElement) ||
                (midCol == startCol && midCol < endCol && A[maxElementRowIndex][midCol + 1] <= maxElement)) {
            return A[maxElementRowIndex][midCol];
        }

        if (midCol > startCol && A[maxElementRowIndex][midCol - 1] > maxElement) {
            return findPeak(A, startRow, startCol, endRow, midCol - 1);// important: you cannot pass midCol. If you do then, it will end up with infinite recursion.
        }
        return findPeak(A, startRow, midCol + 1, endRow, endCol);// important: you cannot pass midCol. If you do then, it will end up with infinite recursion.
    }

    private static int findMaxElementIndex(int[][] A, int col) {
        int maxElementRow = Integer.MIN_VALUE;
        int maxElement = Integer.MIN_VALUE;

        for (int row = 0; row < A.length; row++) {
            if (A[row][col] > maxElement) {
                maxElement = A[row][col];
                maxElementRow = row;
            }
        }
        return maxElementRow;
    }

}
