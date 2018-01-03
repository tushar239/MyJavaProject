package algorithms._5recursion_and_dynamic_programming;

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

        int numberOfQueens = 4;
        int matrixStartRow = 0;
        int matrixStartCol = 0;
        int matrixEndRow = numberOfQueens-1;
        int matrixEndCol = numberOfQueens-1;

        {
            List<Position> takenPositions = new ArrayList<>();
            placeQueens(matrixStartRow, matrixEndRow, matrixEndCol, takenPositions, numberOfQueens);
            System.out.println(takenPositions);
            System.out.println(count);
        }
        {
            List<Position> takenPositions = new ArrayList<>();
            placeQueens(matrixStartRow, matrixStartCol, matrixEndRow, matrixEndCol, takenPositions, numberOfQueens);
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

    private static boolean placeQueens(int startRow, int endRow, int endCol, List<Position> takenPositions, int numberOfQueens) {

        if (numberOfQueens > endRow + 1) {
            return false;
        }

        if (takenPositions.size() == numberOfQueens) { // this has to be before below exit conditions
            return true;
        }


        if (startRow < 0 || startRow > endRow) {
            return false;
        }

        boolean firstQueenPlaced = false;

        for (int col = 0; col <= endCol; col++) {
            count++;

            //System.out.println("visited cell: ("+startRow+", "+col+")");

            Position position = new Position(startRow, col);

            if (!isUnderAttack(position, takenPositions)) {

                takenPositions.add(position);
                firstQueenPlaced = true;

                boolean otherQueensPlaced = placeQueens(startRow + 1, endRow, endCol, takenPositions, numberOfQueens);

                if (firstQueenPlaced && otherQueensPlaced) {
                    return true;
                } else if (firstQueenPlaced && !otherQueensPlaced) {
                    takenPositions.remove(position);
                    firstQueenPlaced = false;
                    continue;
                }
                return false;
            }
        }

        return false;
    }

    /*
    Second Approach:
        Replacing a for loop of first approach with recursion.
     */
    private static int cnt = 0;

    private static boolean placeQueens(int startRow, int startCol, int endRow, int endCol, List<Position> takenPositions, int numberOfQueens) {
        cnt++;

        if (numberOfQueens > endRow + 1) {
            return false;
        }

        if (takenPositions.size() == numberOfQueens) {// this has to be before below exit conditions
            return true;
        }

        if (startRow < 0 || startRow > endRow) {
            return false;
        }

        if (startCol < 0 || startCol > endCol) {
            return false;
        }

        boolean firstQueenPlaced = false;

        Position position = new Position(startRow, startCol);

        if (!isUnderAttack(position, takenPositions)) {
            takenPositions.add(position);
            firstQueenPlaced = true;

            boolean otherQueensPlaced = placeQueens(startRow + 1, 0, endRow, endCol, takenPositions, numberOfQueens);

            if (firstQueenPlaced && !otherQueensPlaced) {
                takenPositions.remove(position);
                firstQueenPlaced = placeQueens(startRow, startCol + 1, endRow, endCol, takenPositions, numberOfQueens);
            } else if (firstQueenPlaced && otherQueensPlaced) {
                return true;
            } else {
                return false;
            }
        } else {
            firstQueenPlaced = placeQueens(startRow, startCol + 1, endRow, endCol, takenPositions, numberOfQueens);
        }

        return firstQueenPlaced;
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
