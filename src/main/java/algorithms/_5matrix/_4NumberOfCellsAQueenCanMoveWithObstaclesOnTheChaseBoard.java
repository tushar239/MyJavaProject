package algorithms._5matrix;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

/*

    Number of cells a queen can move with obstacles on the chessborad

    https://www.geeksforgeeks.org/number-cells-queen-can-move-obstacles-chessborad/

    Consider a N X N chessboard with a Queen and K obstacles. The Queen cannot pass through obstacles. Given the position (x, y) of Queen, the task is to find the number of cells the queen can move.

    There are two ways of understanding the problem

    - how many cells can be visited by a queen from its current position. it can go either straight up or straight down or straight left or straight right or diagonally in 4 directions.
      code is TO BE WRITTEN.

    - how many cells can be visited by a queen as queen can in any direction from any cell.
      BFS(...) method is implemented from that perspective in mind

      Solution:

        Every matrix is a GRAPH because cells are connected to each other.
        Whenever you have a situation of visiting some or all neighbour cells by keeping in mind that you don't visit already visited cells, then that problem can be solved using either BFS or DFS.

 */
public class _4NumberOfCellsAQueenCanMoveWithObstaclesOnTheChaseBoard {

    public static void main(String[] args) {

        // here 2 is a place for queen, 1 is an obstacle, 0 is an open space
        int[][] chaseBoard = {
                {0, 0, 0, 1, 0, 0, 0, 1},
                {1, 0, 0, 1, 0, 0, 1, 0},
                {0, 1, 0, 1, 0, 1, 0, 0},
                {0, 0, 1, 1, 1, 0, 0, 0},
                {1, 1, 1, 2, 1, 1, 1, 1},
                {0, 0, 1, 1, 0, 0, 0, 0},
                {0, 1, 0, 1, 0, 0, 0, 0},
                {1, 0, 0, 1, 0, 0, 0, 0},
        };// answer should be 24

        BFS(chaseBoard, new MatrixCell(4,3));
    }

    private static void BFS(int[][] matrix, MatrixCell queenPlace) {
        Queue<MatrixCell> queue = new LinkedBlockingQueue<>();
        queue.add(queenPlace);

        List<MatrixCell> visitedCells = new LinkedList<>();
        visitedCells.add(queenPlace);

        BFS(matrix, queue, visitedCells);

        System.out.println(visitedCells.size() - 1);// queen's place should not be counted, so visitedCells.size()-1
    }

    private static void BFS(int[][] matrix, Queue<MatrixCell> queue, List<MatrixCell> visitedCells) {

        while (!queue.isEmpty()) {
            MatrixCell cell = queue.poll();

            //visitedCells.add(cell);

            int x = cell.getX();
            int y = cell.getY();

            MatrixCell one = new MatrixCell(x + 1, y);
            MatrixCell two = new MatrixCell(x, y + 1);
            MatrixCell three = new MatrixCell(x + 1, y + 1);
            MatrixCell four = new MatrixCell(x - 1, y);
            MatrixCell five = new MatrixCell(x, y - 1);
            MatrixCell six = new MatrixCell(x - 1, y - 1);
            MatrixCell seven = new MatrixCell(x + 1, y - 1);
            MatrixCell eight = new MatrixCell(x - 1, y + 1);

            if (isEligible(matrix, visitedCells, one)) {queue.add(one);visitedCells.add(one);}
            if (isEligible(matrix, visitedCells, two)) {queue.add(two);visitedCells.add(two);}
            if (isEligible(matrix, visitedCells, three)) {queue.add(three);visitedCells.add(three);}
            if (isEligible(matrix, visitedCells, four)) {queue.add(four);visitedCells.add(four);}
            if (isEligible(matrix, visitedCells, five)) {queue.add(five);visitedCells.add(five);}
            if (isEligible(matrix, visitedCells, six)) {queue.add(six);visitedCells.add(six);}
            if (isEligible(matrix, visitedCells, seven)) {queue.add(seven);visitedCells.add(seven);}
            if (isEligible(matrix, visitedCells, eight)) {queue.add(eight);visitedCells.add(eight);}

        }

    }

    private static boolean isEligible(int[][] matrix, List<MatrixCell> visitedCells, MatrixCell cell) {
        int x = cell.getX();
        int y = cell.getY();

        if(!visitedCells.contains(cell)) {// typical BFS condition
            if (x >= 0 && x <= matrix.length - 1) { // in range
                if (y >= 0 && y <= matrix[x].length - 1) { // in range
                    if (matrix[x][y] != 1 && matrix[x][y] != 2) { // not an obstacle and not a queen's place
                        return true;
                    }
                }
            }
        }

        return false;
    }

    private static class MatrixCell {
        private int x, y;

        public MatrixCell(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            MatrixCell that = (MatrixCell) o;
            return x == that.x &&
                    y == that.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }
}
