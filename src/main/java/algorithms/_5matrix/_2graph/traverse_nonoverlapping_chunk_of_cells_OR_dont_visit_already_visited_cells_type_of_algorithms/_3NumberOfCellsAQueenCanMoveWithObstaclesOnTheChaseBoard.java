package algorithms._5matrix._2graph.traverse_nonoverlapping_chunk_of_cells_OR_dont_visit_already_visited_cells_type_of_algorithms;

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
public class _3NumberOfCellsAQueenCanMoveWithObstaclesOnTheChaseBoard {

    public static void main(String[] args) {

        {
            // I misunderstood the problem, but BFS solution works for the problem that I thought of.
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
            };// answer should be 12 because queen can visit cells on bottom right corner

            MatrixCell queenPlace = new MatrixCell(4, 3);
            BFS(chaseBoard, queenPlace);
        }

        {
            // here 2 is a place for queen, 1 is an obstacle, 0 is an open space
            // This example is same from geeksforgeeks
            int[][] chaseBoard = {
                    {0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 2, 0, 0, 0, 0},
                    {0, 0, 0, 0, 1, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0},
            };// answer should be 24 because queen can visit all cells straight in four directions (up,down,left,right), all cells in 3 diagonal directions. Only one diagonal direction cannot be visited because there is 1.

            MatrixCell queenCell = new MatrixCell(4, 3);
            MatrixCell obstacleCell = new MatrixCell(5, 4);
            // This is a simple way. Better way is given in geeksforgeeks.
            count_simple_way(chaseBoard, queenCell, obstacleCell);
        }
    }

    private static void count_simple_way(int[][] matrix, MatrixCell queenCell, MatrixCell obstacleCell) {

        int x = queenCell.getX();
        int y = queenCell.getY();

        MatrixCell one = new MatrixCell(x + 1, y);
        MatrixCell two = new MatrixCell(x, y + 1);
        MatrixCell three = new MatrixCell(x + 1, y + 1);
        MatrixCell four = new MatrixCell(x - 1, y);
        MatrixCell five = new MatrixCell(x, y - 1);
        MatrixCell six = new MatrixCell(x - 1, y - 1);
        MatrixCell seven = new MatrixCell(x + 1, y - 1);
        MatrixCell eight = new MatrixCell(x - 1, y + 1);

        int numOfCellsThatCanBeVisitedByQueen = 0;

        if(inRange(matrix, one) && !isObstacleCell(one, obstacleCell)) {
            int x1 = x+1;
            while(x1 <= matrix.length-1) {
                numOfCellsThatCanBeVisitedByQueen++;
                x1++;
            }
        }

        if(inRange(matrix, two) && !isObstacleCell(two, obstacleCell)) {
            int y1 = y+1;
            while(y1 <= matrix[x].length-1) {
                numOfCellsThatCanBeVisitedByQueen++;
                y1++;
            }
        }

        if(inRange(matrix, three) && !isObstacleCell(three, obstacleCell)) {
            int x1 = x+1;
            int y1 = y+1;

            while(x1 <= matrix.length-1 && y1 <= matrix[x].length-1) {
                numOfCellsThatCanBeVisitedByQueen++;
                x1++;
                y1++;
            }
        }

        if(inRange(matrix, four) && !isObstacleCell(four, obstacleCell)) {
            int x1 = x-1;

            while(x1 >= 0) {
                numOfCellsThatCanBeVisitedByQueen++;
                x1--;
            }
        }

        if(inRange(matrix, five) && !isObstacleCell(five, obstacleCell)) {
            int y1 = y-1;

            while (y1 >= 0) {
                numOfCellsThatCanBeVisitedByQueen++;
                y1--;
            }
        }

        if(inRange(matrix, six) && !isObstacleCell(six, obstacleCell)) {
            int x1 = x-1;
            int y1 = y-1;

            while(x1 >=0 && y1 >= 0) {
                numOfCellsThatCanBeVisitedByQueen++;
                x1--;
                y1--;
            }
        }

        if(inRange(matrix, seven) && !isObstacleCell(seven, obstacleCell)) {
            int x1 = x+1;
            int y1 = y-1;

            while(x1 <= matrix.length-1 && y1 >= 0) {
                numOfCellsThatCanBeVisitedByQueen++;
                x1++;
                y1--;
            }
        }

        if(inRange(matrix, eight) && !isObstacleCell(eight, obstacleCell)) {
            int x1 = x-1;
            int y1 = y+1;

            while(x1 >= 0 && y1 <= matrix[x].length-1) {
                numOfCellsThatCanBeVisitedByQueen++;
                x1--;
                y1++;
            }
        }

        System.out.println(numOfCellsThatCanBeVisitedByQueen);
    }

    private static boolean isObstacleCell(MatrixCell cell, MatrixCell obstacleCell) {
        return cell.equals(obstacleCell);
    }

    private static boolean inRange(int[][] matrix, MatrixCell cell) {
        int x = cell.getX();
        int y = cell.getY();

        if (x >= 0 && x <= matrix.length - 1) { // in range
            if (y >= 0 && y <= matrix[x].length - 1) { // in range
                return true;
            }
        }
        return false;
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

            if (isEligibleToAddToQueue(matrix, visitedCells, one)) {queue.add(one);visitedCells.add(one);}
            if (isEligibleToAddToQueue(matrix, visitedCells, two)) {queue.add(two);visitedCells.add(two);}
            if (isEligibleToAddToQueue(matrix, visitedCells, three)) {queue.add(three);visitedCells.add(three);}
            if (isEligibleToAddToQueue(matrix, visitedCells, four)) {queue.add(four);visitedCells.add(four);}
            if (isEligibleToAddToQueue(matrix, visitedCells, five)) {queue.add(five);visitedCells.add(five);}
            if (isEligibleToAddToQueue(matrix, visitedCells, six)) {queue.add(six);visitedCells.add(six);}
            if (isEligibleToAddToQueue(matrix, visitedCells, seven)) {queue.add(seven);visitedCells.add(seven);}
            if (isEligibleToAddToQueue(matrix, visitedCells, eight)) {queue.add(eight);visitedCells.add(eight);}

        }

    }

    private static boolean isEligibleToAddToQueue(int[][] matrix, List<MatrixCell> visitedCells, MatrixCell cell) {
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
