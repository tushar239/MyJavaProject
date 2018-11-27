package algorithms._5matrix._10dynamic_programming;

/*
    Largest Square of 1's in A Matrix (Dynamic Programming)

    https://www.techiedelight.com/find-size-largest-square-sub-matrix-1s-present-given-binary-matrix/
    https://www.youtube.com/watch?v=FO7VXDfS8Gk

*/
public class _10LargestSquareOf1s_hard {

    public static void main(String[] args) {
        int[][] m = {
                {1, 1, 1, 1},
                {1, 1, 1, 1},
                {0, 1, 1, 1},
                {0, 1, 1, 1}
        };
        /*int[][] m = {
                {0, 1},
                {1, 1}
        };*/
       /* int[][] m = {
                {1, 1, 1},
                {1, 1, 1},
                {1, 0, 1}
        };*/

        /*int[][] m = {
                {1, 1, 1},
                {1, 1, 1},
                {1, 1, 1}
        };*/
       /* int[][] m = {
                {1, 1, 1, 1},
                {1, 1, 1, 0},
                {0, 1, 1, 1}
        };*/

        /*int[][] m = {
                {1, 1},
                {1, 1}
        };*/

        /*int[][] m = {
                {1, 1}
        };*/
        /*int[][] m = {
                {1},
                {1}
        };*/

            int[][] memo = new int[m.length][m[0].length];
            for (int row = 0; row < memo.length; row++) {
                for (int col = 0; col < memo[row].length; col++) {
                    memo[row][col] = -1;
                }
            }
        {
            int max = findMax_Giving_Wrong_Result(m, 0, 0, m.length - 1, m[0].length - 1, memo);
            System.out.println(max);

            // printing memo
            for (int row = 0; row < memo.length; row++) {
                for (int col = 0; col < memo[row].length; col++) {
                    System.out.print(memo[row][col]);
                }
                System.out.println();
            }
        }

        {


            MaxClass maxClass = new MaxClass();
            findMax_Giving_Right_Result(m, 0, 0, m.length - 1, m[0].length - 1, maxClass);
            System.out.println(maxClass.getMax());
        }

    }

    private static int findMax_Giving_Wrong_Result(int[][] m, int startRow, int startCol, int endRow, int endCol, int[][] memo) {
        if (memo[startRow][startCol] != -1) {
            int memoizedValue = memo[startRow][startCol];
            return memoizedValue;
        }

        if (startRow == endRow || startCol == endCol) {

            int ele = m[startRow][startCol];

            if (ele == 1) {
                memo[startRow][startCol] = 1;
                return 1;
            } else {
                memo[startRow][startCol] = 0;
                return 0;
            }

        } else {

            // reducing the problem by 1
            int ele = m[startRow][startCol];

            int right = findMax_Giving_Wrong_Result(m, startRow, startCol + 1, endRow, endCol, memo);
            int bottom = findMax_Giving_Wrong_Result(m, startRow + 1, startCol, endRow, endCol, memo);
            int diagonal = findMax_Giving_Wrong_Result(m, startRow + 1, startCol + 1, endRow, endCol, memo);

            if (ele == 0) {
                int max = Math.max(right, Math.max(bottom, diagonal));

                memo[startRow][startCol] = max;

                return max;
            }

            // if ele == 1
            int minOfNeighbours = Math.min(right, Math.min(bottom, diagonal));

            int includingEle = minOfNeighbours + 1;
            int excludingEle = Math.max(right, Math.max(bottom, diagonal));

            int max = Math.max(includingEle, excludingEle);

            memo[startRow][startCol] = max;

            return max;

        }
    }

    private static int findMax_Giving_Wrong_Result(int[][] m, int startRow, int startCol, int endRow, int endCol) {

        if (startRow == endRow || startCol == endCol) {

            int ele = m[startRow][startCol];

            if (ele == 1)
                return 1;
            else
                return 0;

        } else {

            // reducing the problem by 1
            int ele = m[startRow][startCol];

            int right = findMax_Giving_Wrong_Result(m, startRow, startCol + 1, endRow, endCol);
            int bottom = findMax_Giving_Wrong_Result(m, startRow + 1, startCol, endRow, endCol);
            int diagonal = findMax_Giving_Wrong_Result(m, startRow + 1, startCol + 1, endRow, endCol);

            if (ele == 0) {
                int max = Math.max(right, Math.max(bottom, diagonal));
                return max;
            }

            // if ele == 1
            int minOfNeighbours = Math.min(right, Math.min(bottom, diagonal));

            int includingEle = minOfNeighbours + 1;
            int excludingEle = Math.max(right, Math.max(bottom, diagonal));

            int max = Math.max(includingEle, excludingEle);
            return max;

        }
    }

    // From
    // https://www.techiedelight.com/find-size-largest-square-sub-matrix-1s-present-given-binary-matrix/
    private static int findMax_Giving_Right_Result(int[][] m, int startRow, int startCol, int endRow, int endCol, MaxClass maxClass) {

        if (startRow == endRow || startCol == endCol) {

            int ele = m[startRow][startCol];

            if (ele == 1)
                return 1;
            else
                return 0;

        } else {

            // reducing the problem by 1
            int ele = m[startRow][startCol];

            //int right = findMax_Giving_Right_Result(m, startRow, startCol + 1, endRow, endCol, maxClass);
            //int bottom = findMax_Giving_Right_Result(m, startRow + 1, startCol, endRow, endCol, maxClass);
            //int diagonal = findMax_Giving_Right_Result(m, startRow + 1, startCol + 1, endRow, endCol, maxClass);

            int size = 0;

            if (ele == 0) {
                size = 0;

                int right = findMax_Giving_Right_Result(m, startRow, startCol + 1, endRow, endCol, maxClass);
                int bottom = findMax_Giving_Right_Result(m, startRow + 1, startCol, endRow, endCol, maxClass);
                int diagonal = findMax_Giving_Right_Result(m, startRow + 1, startCol + 1, endRow, endCol, maxClass);

            }
            if (ele == 1) {
                int right = findMax_Giving_Right_Result(m, startRow, startCol + 1, endRow, endCol, maxClass);
                int bottom = findMax_Giving_Right_Result(m, startRow + 1, startCol, endRow, endCol, maxClass);
                int diagonal = findMax_Giving_Right_Result(m, startRow + 1, startCol + 1, endRow, endCol, maxClass);


                int minOfNeighbours = Math.min(right, Math.min(bottom, diagonal));
                size = minOfNeighbours + 1;
            }

            maxClass.setMax(Math.max(size, maxClass.getMax()));

            return size;
        }
    }

    private static class MaxClass {
        private int max=0;

        public int getMax() {
            return max;
        }

        public void setMax(int max) {
            this.max = max;
        }
    }
}
