package algorithms.crackingcodinginterviewbook._5recursion_and_dynamic_programming;

import java.util.ArrayList;
import java.util.List;

/*
Eight Queens:
Write an algorithm to print all ways of arranging eight queens on an 8*8 chess board,
so that none of them share the same row, column or diagonal.
In this case, "diagonal" means all diagonals, not just the two that bisect the board.

Watch 'N Queen Problem Using Backtracking Algorithm.mp4'
This is a very good algorithm to understand matrix problems.
I could implement this algorithm in two ways.
 */
public class _12EightQueens {
    public static void main(String[] args) {

        int totalNumberOfQueens = 4;
        int matrixStartRow = 0;
        int matrixStartCol = 0;
        int matrixEndRow = totalNumberOfQueens - 1;
        int matrixEndCol = totalNumberOfQueens - 1;

        {
            List<Position> takenPositions = new ArrayList<>();
            placeQueens_brute_force_thinking_from_first_cell_and_first_queen(matrixStartRow, matrixEndRow, matrixEndCol, takenPositions, 1, totalNumberOfQueens);
            System.out.println(takenPositions);
            System.out.println(count);
        }

        {
            List<Position> takenPositions = new ArrayList<>();
            placeQueens_brute_force_thinking_from_last_cell_and_last_queen(matrixStartRow, matrixStartCol, matrixEndRow, matrixEndCol, matrixEndRow, takenPositions, totalNumberOfQueens, totalNumberOfQueens);
            System.out.println(takenPositions);
            System.out.println(count);
        }

        {
            List<Position> takenPositions = new ArrayList<>();
            placeQueens_brute_force_two_level_recursion_approach(matrixStartRow, matrixStartCol, matrixEndRow, matrixEndCol, matrixEndRow, matrixEndCol, takenPositions, totalNumberOfQueens, totalNumberOfQueens);
            System.out.println(takenPositions);
            System.out.println(cnt);

        }
    }


    static class Position {
        private int row;
        private int col;

        public Position(int row, int col) {
            this.row = row;
            this.col = col;
        }

        public int getRow() {
            return row;
        }

        public int getCol() {
            return col;
        }

        @Override
        public String toString() {
            return "Position{" +
                    "row=" + row +
                    ", col=" + col +
                    '}';
        }
    }

    /*
        Watch 'N Queen Problem Using Backtracking Algorithm.mp4'

        This algorithm describes how to start from the first queen and first cell of the matrix and it recurses it for remaining queens starting from 2nd row.
        As I mentioned in CoinsChange.java, for 2-D algorithms, it is easier to think from last elements of both dimensions and recurse using previous elements.

        After understanding over all approach as described below, you can see
        'placeQueens_brute_force_thinking_from_last_cell_and_last_queen' method.


        Remember:
            This is not a knapsack kind of problem (CoinChange.java). So, do not start drawing your matrix with additional row and col.

            This is a typical matrix traversal problem and not a knapsack kind of problem.
            In knapsack problem (Bottom-Up Dynamic Approach), you are given 2 dimensions of a problem and to determine the solution using Bottom-Up Dynamic approach, you are creating a memoization matrix and filling up the value in 2-D array.
            Im matrix problems, you are given a matrix as an input and you need to traverse it in certain way to fill up the values in it.

        Remember:
            In this algorithm, you actually do not need to create a matrix. It is just a game of row and col numbers.

        First approach:
            Using for loop for cols increment and recursion for row increment

            Recursion:
            Reduce the problem by 1.

            Try to put first queen in the row=0 and col=0.

            If it is not attacked by any other queens in (0,0), then
                add (0,0) in takenPositions list
                try to position remaining queens starting from row=1 and col=0
                if remaining queens are not placed because they are attacked, then
                    remove first queen from takenPositions list
                    try to place first queen in the next col (col=1) of the same row (row=0)
            else
                try to place first queen in the col=1 of the same row (row=0).


            what does queen is attacked mean?
                let's say, you have placed a queen at position  row=1 cell=2  ((1,2) cell in matrix).
                This queen can attack other queen, if you try to put another queen in the same row or same col or same diagonals.
                How to detect same diagonals?
                    if placed queen's row-col==other queen's row-col OR
                    if placed queen's row+col==other queen's row+col


            In this logic,
                I see a for loop in incrementing cols.

                boolean placeQueens(int startRow, int endRow, int 3, List<Position> takenPositions, int numberOfQueens) {

                    if(takenPositions.size() == numberOfQueens) return true; // Very Important: this condition is very important and has to be the first one.

                    if(startRow > endRow) return false; // Very Important: this cannot be the first exit condition.

                    boolean firstQueenPlaced = false;

                    for (int col = 0; col <= endCol; col++) {
                        if((startRow, col) is NOT attacked by other queens) {
                            takenPositions.add((0,0));
                            firstQueenPlaced=true;

                            // place remaining queens starting from next row
                            boolean otherQueensPlaced = placeQueens(1, int 3, int 3, List<Position> takenPositions, int numberOfQueens);

                            if(!otherQueensPlaced) {
                                takenPositions.remove(first queen's position);
                                firstQueenPlaced=false;

                                // try to put first queen in next col of the first row
                                continue;
                            }

                            // if both first and other queens are placed in matrix, then algorithm returns success(true)
                            if(firstQueenPlaced && otherQueensPlaced) {

                                return true;

                            }
                            // if other queens could not be placed, then first queen should be tried in the next cell of the same row
                            else if(firstQueenPlaced && !otherQueensPlaced) {

                                takenPositions.remove(position);
                                firstQueenPlaced=false;
                                continue;

                            }

                            // if both first and other queens could not be placed, then algorithm returns failure (false)
                            return false;

                        }
                    }

                    private boolean isAttacked(position, takenPositions) {
                        ...
                    }
                }


            Second Approach:

                for loop in above algorithm can be replaced by recursion. This is what I tried to do in my second approach.





            If you draw a matrix, this is how the effect of algorithm would look like:

                    0   1   2   3

              0     Q1

              1             Q2

              2     Unable to place Q3. So try to put Q2 in next cell.

              3

                        |
                        v

                   0   1   2   3

              0     Q1

              1                 Q2

              2         Q3

              3     Unable to place Q4.  So try to put Q3 in next cell.

                       |
                       v
                   0   1   2   3

              0     Q1

              1                 Q2

              2    Unable to place Q3.  So try to put Q2 in next cell.

              3

                       |
                       v

                   0   1   2   3

              0     Q1

              1     Unable to place Q2.  So try to put Q1 in next cell.

              2

              3
                         |
                         v
                Now all queens will be arranged fine.

                   0   1   2   3

              0         Q1

              1                 Q2

              2     Q3

              3             Q4



         Time Complexity:
            At first glance it appears that time complexity can be O(rc) because you are visiting every row and cell of the matrix.
            But remember, queens are traversing the row more than one time. See above example: you try to put Q3 in row=2 twice. Both times, you started from col=0.
            So, it seems like every cell of a matrix can be visited number of queens times or may be more.


    */
    private static int count = 0;

    private static boolean placeQueens_brute_force_thinking_from_first_cell_and_first_queen(int startRow, int endRow, int endCol, List<Position> takenPositions, int startQueen, int totalNumberOfQueens) {

        // If number of queens are more than matrix size, then queens cannot be placed because in one row, only one queen can be placed. So, for 4 queens, you need min 4 rows.
        if (totalNumberOfQueens > endRow + 1) {
            return false;
        }

        if (takenPositions.size() == totalNumberOfQueens) { // this has to be before below exit conditions
            return true;
        }


        if (startRow < 0 || startRow > endRow) {
            return false;
        }


        for (int col = 0; col <= endCol; col++) {
            count++;

            //boolean firstQueenPlaced = false;
            //System.out.println("visited cell: ("+startRow+", "+col+")");

            Position position = new Position(startRow, col);

            if (!isUnderAttack(position, takenPositions)) {

                takenPositions.add(position);
                //firstQueenPlaced = true;

                boolean otherQueensPlaced = placeQueens_brute_force_thinking_from_first_cell_and_first_queen(startRow + 1, endRow, endCol, takenPositions, startQueen + 1, totalNumberOfQueens);

                if (/*firstQueenPlaced &&*/ otherQueensPlaced) {
                    return true;
                } else if (/*firstQueenPlaced && */!otherQueensPlaced) {
                    takenPositions.remove(position);
                    //firstQueenPlaced = false;
                    continue;
                }
                return false;
            }
        }

        return false;
    }

    private static boolean placeQueens_brute_force_thinking_from_last_cell_and_last_queen(int startRow, int startCol, int endRow, int endCol, int lastRow, List<Position> takenPositions, int startQueen, int totalNumberOfQueens) {

        if (startQueen < 1) {
            return true;
        }

        // If number of queens are more than matrix size, then queens cannot be placed because in one row, only one queen can be placed. So, for 4 queens, you need min 4 rows.
        if (totalNumberOfQueens > lastRow + 1) {//lastRow parameter is passed just for this condition. In algorithm, endRow is changing but not totalNumberOfQueens. So, you cannot compare totalNumberOfQueens with endRow+1
            return false;
        }

        if (startQueen > totalNumberOfQueens) {
            return true;
        }

        if (takenPositions.size() == totalNumberOfQueens) { // this has to be before below exit conditions
            return true;
        }

        if (endRow < startRow) {
            return false;
        }

        if (endCol < startCol) {
            return false;
        }

        // does not work
        //boolean otherQueensPlaced = placeQueens_brute_force_thinking_from_last_cell_and_last_queen(startRow, endRow - 1, endCol, lastRow, takenPositions, startQueen-1, totalNumberOfQueens);

        for (int col = endCol; col >= 0; col--) {
            count++;

            //boolean lastQueenPlaced = false;

            //System.out.println("visited cell: (" + endRow + ", " + col + ")");

            Position position = new Position(endRow, col);

            if (isUnderAttack(position, takenPositions)) {
                continue;
            }

            takenPositions.add(position);
            //lastQueenPlaced = true;

            boolean otherQueensPlaced = placeQueens_brute_force_thinking_from_last_cell_and_last_queen(startRow, startCol, endRow - 1, endCol, lastRow, takenPositions, startQueen - 1, totalNumberOfQueens);

            if (/*lastQueenPlaced && */otherQueensPlaced) {
                return true;
            } else if (/*lastQueenPlaced && */!otherQueensPlaced) {
                takenPositions.remove(position);
                //lastQueenPlaced = false;
                continue;
            }
            return false;
        }

        return false;
    }

    /*
    Second Approach:
        Replacing a for loop of first approach with recursion.
        This is harder. I would normally go with first approach.
     */
    private static int cnt = 0;

    private static boolean placeQueens_brute_force_two_level_recursion_approach(int startRow, int startCol, int endRow, int endCol, int lastRow, int lastCol, List<Position> takenPositions, int startQueen, int totalNumberOfQueens) {
        cnt++;

        if (startQueen < 1) {
            return true;
        }

        if (startQueen > endRow + 1) {
            return true;
        }

        if (takenPositions.size() == totalNumberOfQueens) { // this has to be before below exit conditions
            return true;
        }

        if (endRow < startRow) {
            return false;
        }

        if (endCol < startCol) {
            return false;
        }

        boolean lastQueenPlaced = false;

        Position position = new Position(endRow, endCol);

        if (!isUnderAttack(position, takenPositions)) {
            takenPositions.add(position);
            lastQueenPlaced = true;

            boolean otherQueensPlaced = placeQueens_brute_force_two_level_recursion_approach(startRow, startCol, endRow - 1, lastCol, lastRow, lastCol, takenPositions, startQueen - 1, totalNumberOfQueens);

            if (lastQueenPlaced && !otherQueensPlaced) {
                takenPositions.remove(position);
                lastQueenPlaced = false;
                lastQueenPlaced = placeQueens_brute_force_two_level_recursion_approach(startRow, startCol, endRow, endCol - 1, lastRow, lastCol, takenPositions, startQueen, totalNumberOfQueens);
            } else if (lastQueenPlaced && otherQueensPlaced) {
                return true;
            }
        }

        if (!lastQueenPlaced) {
            lastQueenPlaced = placeQueens_brute_force_two_level_recursion_approach(startRow, startCol, endRow, endCol - 1, lastRow, lastCol, takenPositions, startQueen, totalNumberOfQueens);
        }
        return lastQueenPlaced;
    }

    private static boolean isUnderAttack(Position position, List<Position> takenPositions) {
        for (Position takenPosition : takenPositions) {
            if (position.getRow() == takenPosition.getRow()) {
                return true;
            }
            if (position.getCol() == takenPosition.getCol()) {
                return true;
            }
            if (position.getRow() - position.getCol() == takenPosition.getRow() - takenPosition.getCol()) {
                return true;
            }
            if (position.getRow() + position.getCol() == takenPosition.getRow() + takenPosition.getCol()) {
                return true;
            }
        }
        return false;
    }
}
