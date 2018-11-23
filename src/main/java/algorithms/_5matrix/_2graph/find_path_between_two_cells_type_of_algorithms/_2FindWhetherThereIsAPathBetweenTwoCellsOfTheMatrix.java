package algorithms._5matrix._2graph.find_path_between_two_cells_type_of_algorithms;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

/*

    Find whether there is path between two cells in matrix

    https://www.geeksforgeeks.org/find-whether-path-two-cells-matrix/


    Given N X N matrix filled with 1 , 0 , 2 , 3 . Find whether there is a path possible from source to destination, traversing through blank cells only. You can traverse up, down, right and left.

    - A value of cell 1 means Source.
    - A value of cell 2 means Destination.
    - A value of cell 3 means Blank cell.
    - A value of cell 0 means Blank Wall.

    Examples:

    Input : M[3][3] = {{ 0 , 3 , 2 },
                       { 3 , 3 , 0 },
                       { 1 , 3 , 0 }};
    Output : Yes

    Input : M[4][4] = {{ 0 , 3 , 1 , 0 },
                       { 3 , 0 , 3 , 3 },
                       { 2 , 3 , 0 , 3 },
                       { 0 , 3 , 3 , 3 }};
    Output : Yes


    Solution:

    Matrix is one type of graph.

    BFS has three main algorithms
    - Find whether there is a path between two vertices of a graph. (This algorithm is based on this).
    - Find Shortest Path between two vertices of a graph.
    - Find the Shortest Distance between two vertices of a graph.

    Read BfsDfsFromGrokkingAlgorithmBook.java.

*/
public class _2FindWhetherThereIsAPathBetweenTwoCellsOfTheMatrix {

    public static void main(String[] args) {
        int[][] matrix = {
                {0, 3, 2},
                {3, 3, 0},
                {1, 3, 0}
        };
        CellWithParent startingCell = new CellWithParent(2,0, null);
        CellWithParent destinationCell = new CellWithParent(0,2, null);

        boolean pathFromStartToDestinationCellsFound = BFS(matrix, startingCell, destinationCell);
        System.out.println(pathFromStartToDestinationCellsFound);
    }

    private static boolean BFS(int[][] matrix, CellWithParent startingCell, CellWithParent destinationCell) {
        if (matrix == null || matrix.length == 0) return false;

        Queue<CellWithParent> queue = new LinkedBlockingQueue<>();
        queue.add(startingCell);

        List<CellWithParent> visitedCells = new LinkedList<>();
        visitedCells.add(startingCell);

        BFS(matrix, startingCell, destinationCell, queue, visitedCells);

        for (CellWithParent visitedCell : visitedCells) {
            if(visitedCell.getX() == destinationCell.getX() && visitedCell.getY() == destinationCell.getY()) {
                return true;
            }
        }
        return false;
    }

    private static void BFS(int[][] matrix, CellWithParent startingCell, CellWithParent destinationCell, Queue<CellWithParent> queue, List<CellWithParent> visitedCells) {
        while (!queue.isEmpty()) {

            CellWithParent cell = queue.poll();

            if (cell.equals(destinationCell))
                return; // you could reach to destination cell. it is already put in visitedCells.

            int x = cell.getX();
            int y = cell.getY();

            // you need to put neighbour cells in queue with cell as their parent cell
            // Assuming that matrix can be traversed in four directions
            CellWithParent up = new CellWithParent(x - 1, y, cell);
            CellWithParent down = new CellWithParent(x + 1, y, cell);
            CellWithParent left = new CellWithParent(x, y - 1, cell);
            CellWithParent right = new CellWithParent(x, y + 1, cell);

            if (isEligibleToAddToQueue(up, matrix, visitedCells)) { queue.add(up); visitedCells.add(up); }
            if (isEligibleToAddToQueue(down, matrix, visitedCells)) { queue.add(down); visitedCells.add(down); }
            if (isEligibleToAddToQueue(left, matrix, visitedCells)) { queue.add(left); visitedCells.add(left); }
            if (isEligibleToAddToQueue(right, matrix, visitedCells)) { queue.add(right); visitedCells.add(right); }

        }
    }

    private static boolean isEligibleToAddToQueue(CellWithParent cell, int[][] matrix, List<CellWithParent> visitedCells) {
        int x = cell.getX();
        int y = cell.getY();

        // is the cell already visited?
        // IMPORTANT: if(visitedCell.contains(cell)) will not work because cell has parentCell in it and same cell can be reached from more than one parents.
        boolean isVisited = false;
        for (CellWithParent visitedCell : visitedCells) {
            if (visitedCell.getX() == cell.getX() && visitedCell.getY() == cell.getY()) {
                isVisited = true;
            }
        }

        // if cell is not yet visited AND cell is inside the range of matrix AND it is a blank or destination cell, then that cell is eligible to be added to queue
        if (!isVisited) {// typical BFS condition
            if (x >= 0 && x <= matrix.length - 1) { // in range
                if (y >= 0 && y <= matrix[x].length - 1) { // in range
                    if (matrix[x][y] == 3 || matrix[x][y] == 2 ) { // is a blank cell or destination cell. IMPORTANT: don't just consider blank cell. Also consider destination cell also.
                        return true;
                    }
                }
            }
        }

        return false;
    }

    private static class CellWithParent {
        private int x;
        private int y;
        private CellWithParent parentCell;

        public CellWithParent(int x, int y, CellWithParent parentCell) {
            this.x = x;
            this.y = y;
            this.parentCell = parentCell;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public CellWithParent getParentCell() {
            return parentCell;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            CellWithParent that = (CellWithParent) o;
            return x == that.x &&
                    y == that.y &&
                    Objects.equals(parentCell, that.parentCell);
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y, parentCell);
        }
    }
}
