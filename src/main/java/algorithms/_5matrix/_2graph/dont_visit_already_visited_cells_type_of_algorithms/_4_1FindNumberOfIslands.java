package algorithms._5matrix._2graph.dont_visit_already_visited_cells_type_of_algorithms;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

/*

    Find the number of islands

    https://www.geeksforgeeks.org/find-number-of-islands/

    geeksforgeeks has solution using DFS, but it can be solved using BFS also.

    Input : mat[][] = {{1, 1, 0, 0, 0},
                       {0, 1, 0, 0, 1},
                       {1, 0, 0, 1, 1},
                       {0, 0, 0, 0, 0},
                       {1, 0, 1, 0, 1}
    Output : 5


    Solution:

    Iterate below logic for all cells one by one
        If cell has 1 and it is not already visited
            Use BFS.
            - Put all 8 neighbours. Put neighbours with 1 in queue and visited list.
            - When queue becomes empty, you will get first island.
*/
public class _4_1FindNumberOfIslands {

    public static void main(String[] args) {
        int matrix[][] = {
                {1, 1, 0, 0, 0},
                {0, 1, 0, 0, 1},
                {1, 0, 0, 1, 1},
                {0, 0, 0, 0, 0},
                {1, 0, 1, 0, 1}
        };// Answer = 5

        int totalIslands = countIslands(matrix);
        System.out.println(totalIslands);
    }

    private static int countIslands(int[][] matrix) {
        int totalIslands = 0;

        List<Cell> visitedCells = new LinkedList<>();

        // Iterate through each cell and do BFS for non-visited cells
        // Each BFS will give you one island and each BFS will add cells to visitedCells, so that you can skip BFS on visited cells.
        for (int row = 0; row < matrix.length; row++) {
            for (int col = 0; col < matrix[row].length; col++) {
                Cell cell = new Cell(row, col);
                if (matrix[row][col] == 1 && !visitedCells.contains(cell)) {

                    BFS(matrix, cell, new LinkedBlockingQueue<>(), visitedCells);
                    totalIslands++;

                }
            }
        }

        return totalIslands;
    }

    private static void BFS(int[][] matrix, Cell startCell, Queue<Cell> queue, List<Cell> visitedCells) {

        queue.add(startCell);
        visitedCells.add(startCell);

        while (!queue.isEmpty()) {

            Cell cell = queue.poll();

            int x = cell.getX();
            int y = cell.getY();

            Cell one = new Cell(x + 1, y);
            Cell two = new Cell(x, y + 1);
            Cell three = new Cell(x + 1, y + 1);
            Cell four = new Cell(x - 1, y);
            Cell five = new Cell(x, y - 1);
            Cell six = new Cell(x - 1, y - 1);
            Cell seven = new Cell(x + 1, y - 1);
            Cell eight = new Cell(x - 1, y + 1);

            if (isEligibleToAddToQueue(one, matrix, visitedCells)) {
                queue.add(one);
                visitedCells.add(one);
            }

            if (isEligibleToAddToQueue(two, matrix, visitedCells)) {
                queue.add(two);
                visitedCells.add(two);
            }

            if (isEligibleToAddToQueue(three, matrix, visitedCells)) {
                queue.add(three);
                visitedCells.add(three);
            }

            if (isEligibleToAddToQueue(four, matrix, visitedCells)) {
                queue.add(four);
                visitedCells.add(four);
            }

            if (isEligibleToAddToQueue(five, matrix, visitedCells)) {
                queue.add(five);
                visitedCells.add(five);
            }

            if (isEligibleToAddToQueue(six, matrix, visitedCells)) {
                queue.add(six);
                visitedCells.add(six);
            }

            if (isEligibleToAddToQueue(seven, matrix, visitedCells)) {
                queue.add(seven);
                visitedCells.add(seven);
            }

            if (isEligibleToAddToQueue(eight, matrix, visitedCells)) {
                queue.add(eight);
                visitedCells.add(eight);
            }

        }
    }

    private static boolean isEligibleToAddToQueue(Cell cell, int[][] matrix, List<Cell> visitedCells) {
        int x = cell.getX();
        int y = cell.getY();

        if (!visitedCells.contains(cell)) {// typical BFS condition
            if (x >= 0 && x <= matrix.length - 1) { // in range
                if (y >= 0 && y <= matrix[x].length - 1) { // in range
                    if (matrix[x][y] == 1) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    private static class Cell {
        private int x, y;

        public Cell(int x, int y) {
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
            Cell cell = (Cell) o;
            return x == cell.x &&
                    y == cell.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }

}