package algorithms._5recursion_and_memoization;

import java.util.ArrayList;
import java.util.List;

public class _2RobotInGrid {

    public static void main(String[] args) {
        int[][] matrix = new int[5][5];
        matrix[0] = new int[]{1, 1, 1, 1, 0};// change 2nd element from 1 to 0 to see that there is no path
        matrix[1] = new int[]{0, 0, 0, 1, 0};
        matrix[2] = new int[]{0, 1, 0, 1, 1};
        matrix[3] = new int[]{0, 1, 0, 0, 1};
        matrix[4] = new int[]{0, 1, 0, 0, 1};

        List<Path> paths = new ArrayList<>();
        int startRow = 0;
        int startCol = 0;
        int endRow = 4;
        int endCol = 4;
        getPath(matrix, startRow, startCol, endRow, endCol, paths);
        System.out.println(paths);
        if (paths.contains(new Path(startRow, startCol))) {
            System.out.println("There is a path from " + new Path(startRow, endRow) + " to " + new Path(endRow, endCol));
        } else {
            System.out.println("There is no path from " + new Path(startRow, endRow) + " to " + new Path(endRow, endCol));
        }
    }

    static class Path {
        int row;
        int col;

        public Path(int row, int col) {
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
        public boolean equals(Object o) {

            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Path path = (Path) o;

            if (row != path.row) return false;
            return col == path.col;
        }

        @Override
        public int hashCode() {
            int result = row;
            result = 31 * result + col;
            return result;
        }

        @Override
        public String toString() {
            return "Path[" + row + "," + col + "]";
        }
    }

    private static void getPath(int[][] matrix, int startRow, int startCol, int endRow, int endCol, List<Path> paths) {
        if (matrix == null || matrix.length == 0) return;

        if (startRow < 0 || startCol < 0 || endRow < 0 || endCol < 0 ||
                startRow >= matrix.length || startCol >= matrix[startRow].length ||
                endRow >= matrix.length || endCol >= matrix[endRow].length) {
            return;
        }

        if (startRow == endRow && startCol == endCol && matrix[startRow][startCol] == 1) {
            paths.add(new Path(startRow, startCol));
            return;
        }

        if (matrix[endRow][endCol] != 1) {// Robot cannot reach to this cell of the matrix
            return;
        } else {
            paths.add(new Path(endRow, endCol));
        }

        getPath(matrix, startRow, startCol, endRow, endCol - 1, paths);
        if (!paths.contains(new Path(endRow, endCol - 1))) {
            getPath(matrix, startRow, startCol, endRow - 1, endCol, paths);
            if (!paths.contains(new Path(endRow - 1, endCol))) {
                return;
            }
        }

    }
}
