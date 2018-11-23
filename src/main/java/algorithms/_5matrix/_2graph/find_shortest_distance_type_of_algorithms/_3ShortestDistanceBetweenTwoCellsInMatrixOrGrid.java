package algorithms._5matrix._2graph.find_shortest_distance_type_of_algorithms;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

/*
    Shortest distance between two cells in a matrix or grid

    https://www.geeksforgeeks.org/shortest-distance-two-cells-matrix-grid/

    Given a matrix of N*M order. Find the shortest distance from a source cell to a destination cell, traversing through limited cells only.
    Also you can move only up, down, left and right. If found output the distance else -1.

    s represents ‘source’
    d represents ‘destination’
    * represents cell you can travel
    0 represents cell you can not travel

    This problem is meant for single source and destination.

    Examples:

    Input : {'0', '*', '0', 's'},
            {'*', '0', '*', '*'},
            {'0', '*', '*', '*'},
            {'d', '*', '*', '*'}
    Output : 6

    Input :  {'0', '*', '0', 's'},
             {'*', '0', '*', '*'},
             {'0', '0', '*', '*'},
             {'d', '0', '0', '0'}
    Output :  -1

    Solution:

    BFS has three main algorithms
    - Find whether there is a path between two vertices of a graph.
    - Find Shortest Path between two vertices of a graph.
    - Find the Shortest Distance between two vertices of a graph. (This algorithm is based on this).

    Read BfsDfsFromGrokkingAlgorithmBook.java.


    In geeksforgeeks, they are considering that you can traverse a matrix only in 4 directions (up,down,left,right).
    My below code assumes that you can traverse in all 8 directions (up,down,left,right,4 diagonals).
*/
public class _3ShortestDistanceBetweenTwoCellsInMatrixOrGrid {

    public static void main(String[] args) {
        {
            char[][] matrix = {
                    {'0', '*', '0', 's'},
                    {'*', '0', '*', '*'},
                    {'0', '*', '*', '*'},
                    {'d', '*', '*', '*'}
            };
            Cell startCell = new Cell(0, 3);
            Cell destinationCell = new Cell(3, 0);
            shortestDistance(matrix, startCell, destinationCell);
        }

        {
            char[][] matrix = {
                    {'0', '*', '0', 's'},
                    {'*', '0', '*', '*'},
                    {'0', '0', '*', '*'},
                    {'d', '0', '0', '0'}
            };

            Cell startCell = new Cell(0, 3);
            Cell destinationCell = new Cell(3, 0);
            shortestDistance(matrix, startCell, destinationCell);
        }
    }

    private static void shortestDistance(char[][] matrix, Cell startCell, Cell destinationCell) {

        Queue<Cell> queue = new LinkedBlockingQueue<>();
        List<Cell> visitedCells = new LinkedList<>();

        Cell start = new Cell(startCell.getX(), startCell.getY(), 0);
        queue.add(start);
        visitedCells.add(start);

        int shortestDistance = BFS(matrix, queue, visitedCells, destinationCell);
        if (shortestDistance != -1) {
            System.out.println(shortestDistance + " is the shortest distance from source cell to destination cell");
        }
        else {
            System.out.println("There is no path from source cell to destination cell");
        }
    }

    private static int BFS(char[][] matrix, Queue<Cell> queue, List<Cell> visitedCells, Cell destinationCell) {
        while (!queue.isEmpty()) {
            Cell cell = queue.poll();

            // destination cell is found from the queue
            if (destinationCell.getX() == cell.getX() && destinationCell.getY() == cell.getY()) {
                return cell.getDistance();
            }

            int x = cell.getX();
            int y = cell.getY();
            int distance = cell.getDistance();

            // you need to put neighbour cells with increased distance
            // if cell's distance=0, then neighbour cells' distances will be 1
            // Assuming that matrix can be traversed in eight directions
            Cell one = new Cell(x + 1, y, distance + 1);
            Cell two = new Cell(x, y + 1, distance + 1);
            Cell three = new Cell(x + 1, y + 1, distance + 1);
            Cell four = new Cell(x - 1, y, distance + 1);
            Cell five = new Cell(x, y - 1, distance + 1);
            Cell six = new Cell(x - 1, y - 1, distance + 1);
            Cell seven = new Cell(x + 1, y - 1, distance + 1);
            Cell eight = new Cell(x - 1, y + 1, distance + 1);

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
        return -1;
    }

    private static boolean isEligibleToAddToQueue(Cell cell, char[][] matrix, List<Cell> visitedCells) {
        int x = cell.getX();
        int y = cell.getY();

        // is the cell already visited?
        // IMPORTANT: if(visitedCell.contains(cell)) will not work because cell has distance in it and same cell can be reached from more than one parents with different destinations.
        boolean isVisited = false;
        for (Cell visitedCell : visitedCells) {
            if (visitedCell.getX() == cell.getX() && visitedCell.getY() == cell.getY()) {
                isVisited = true;
            }
        }

        // if cell is not yet visited AND cell is inside the range of matrix AND it is a blank or destination cell, then that cell is eligible to be added to queue
        if (!isVisited) {// typical BFS condition
            if (x >= 0 && x <= matrix.length - 1) { // in range
                if (y >= 0 && y <= matrix[x].length - 1) { // in range
                    if (matrix[x][y] == '*' || matrix[x][y] == 'd') { // is a blank cell or destination cell. IMPORTANT: don't just consider blank cell. Also consider destination cell also.
                        return true;
                    }
                }
            }
        }

        return false;
    }

    private static class Cell {
        private int x, y;
        private int distance;

        public Cell(int x, int y) {
            this.x = x;
            this.y = y;
            this.distance = -1;
        }

        public Cell(int x, int y, int distance) {
            this.x = x;
            this.y = y;
            this.distance = distance;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public int getDistance() {
            return distance;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Cell cell = (Cell) o;
            return x == cell.x &&
                    y == cell.y &&
                    distance == cell.distance;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y, distance);
        }
    }
}
