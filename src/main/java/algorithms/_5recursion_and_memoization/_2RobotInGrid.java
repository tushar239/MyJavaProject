package algorithms._5recursion_and_memoization;

import java.util.ArrayList;
import java.util.List;

/*
Robot in a Grid:
Imagine a robot sitting on the upper left corner of grid with r rows and c columns.
The robot can only move in two directions, right and down, but certain cells are "off limits" such that
the robot cannot step on them. Design an algorithm to find a path for the robot from the top left to the bottom right.


 */
public class _2RobotInGrid {

    public static void main(String[] args) {
        {
            int[][] matrix = getMatrix();
            prettyPrintMatrix(matrix);

            List<Path> paths = new ArrayList<>();
            int startRow = 0;
            int startCol = 0;
            int endRow = 4;
            int endCol = 4;

            getPath_book_way(matrix, startRow, startCol, endRow, endCol, paths);
            System.out.println(paths);
            if (paths.contains(new Path(startRow, startCol))) {
                System.out.println("There is a path from " + new Path(startRow, startCol) + " to " + new Path(endRow, endCol));
            } else {
                System.out.println("There is no path from " + new Path(startRow, startCol) + " to " + new Path(endRow, endCol));
            }
        }

        {
            int[][] matrix = getMatrix();
            prettyPrintMatrix(matrix);

            List<Path> paths = new ArrayList<>();
            int startRow = 0;
            int startCol = 0;
            int endRow = 4;
            int endCol = 4;

            getPath_my_way(matrix, startRow, startCol, endRow, endCol, paths);
            System.out.println(paths);
            if (paths.contains(new Path(startRow, startCol))) {
                System.out.println("There is a path from " + new Path(startRow, startCol) + " to " + new Path(endRow, endCol));
            } else {
                System.out.println("There is no path from " + new Path(startRow, startCol) + " to " + new Path(endRow, endCol));
            }

        }

        {
            int[][] matrix = getMatrix();
            prettyPrintMatrix(matrix);

            int startRow = 0;
            int startCol = 0;
            int endRow = 4;
            int endCol = 4;
            try {
                List<Path> finalPaths = getPath_(matrix, startRow, startCol, endRow, endCol);
                System.out.println(finalPaths);
                if (finalPaths.contains(new Path(startRow, startCol))) {
                    System.out.println("There is a path from " + new Path(startRow, startCol) + " to " + new Path(endRow, endCol));
                } else {
                    System.out.println("There is no path from " + new Path(startRow, startCol) + " to " + new Path(endRow, endCol));
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
                System.out.println("There is no path from " + new Path(startRow, startCol) + " to " + new Path(endRow, endCol));
            }
        }
    }

    private static int[][] getMatrix() {
        int[][] matrix = new int[5][5];
        matrix[0] = new int[]{1, 1, 1, 1, 0};// change 2nd element from 1 to 0 to see that there is no path
        matrix[1] = new int[]{0, 0, 0, 1, 0};
        matrix[2] = new int[]{0, 1, 0, 1, 1};
        matrix[3] = new int[]{0, 1, 0, 0, 1};
        matrix[4] = new int[]{0, 1, 0, 0, 1};
        return matrix;
    }

    /*
    book starts the algorithm from endRow and endCol where Robot wants to reach.
    I start from startRow and startCol from where Robot starts.

    Remember:
    condition of the algorithm says that Robot can walk only right or down.
    It means right or down (one of these) place from start pos needs have 1 in matrix.
    If not, then Robot can not move from that cell onwards.

    Here, root is the cell with startPos and startCol.
    if matrix[startRow][startCol] == 1, then add it as one of the Path in 'paths' list.
    you need to look for the same condition recursively from right and down cells matrix[startRow+1][startCol] and matrix[startRow][startCol+1].
    If  matrix[startRow+1][startCol]==1, then you don't have look for matrix[startRow][startCol+1].

    sample matrix

        0   1   2   3   4
       ---|---|---|---|---|
    0| 1  |1  |1  |1  |0  |

    1| 0  |0  |0  |1  |0  |

    2| 0  |1  |0  |1  |1  |

    3| 0  |1  |0  |0  |1  |

    4| 0  |1  |0  |0  |1  |

    Path to reach to bottom right corner:
    [Path[0,0], Path[0,1], Path[0,2], Path[0,3], Path[1,3], Path[2,3], Path[2,4], Path[3,4], Path[4,4]]

     */
    private static void getPath_my_way(int[][] matrix, int startRow, int startCol, int endRow, int endCol, List<Path> paths) {
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

        if (matrix[startRow][startCol] != 1) {// Robot cannot reach to this cell of the matrix
            return;
        } else {
            paths.add(new Path(startRow, startCol));
        }

        getPath_my_way(matrix, startRow + 1, startCol, endRow, endCol, paths);
        if (!paths.contains(new Path(startRow + 1, startCol))) {
            getPath_my_way(matrix, startRow, startCol + 1, endRow, endCol, paths);
            if (!paths.contains(new Path(startRow, startCol + 1))) {
                return;
            }
        }
    }

   /* private static void getShortestPath(int[][] matrix, int startRow, int startCol, int endRow, int endCol, Queue queue, List<Path> paths) {
        if (matrix == null || matrix.length == 0) return;

        if (startRow < 0 || startCol < 0 || endRow < 0 || endCol < 0 ||
                startRow >= matrix.length || startCol >= matrix[startRow].length ||
                endRow >= matrix.length || endCol >= matrix[endRow].length) {
            return;
        }

        if (startRow == endRow && startCol == endCol && matrix[startRow][startCol] == 1) {
            paths.add(new Path(startRow, startCol));

            List<Path> neighbours = findEligibleNeigbours(matrix, startRow, startCol);
            if(neighbours != null) {
                for (Path neighbour : neighbours) {
                    queue.add(neighbour);
                    paths.add(neighbour);
                }
            }

            return;
        }

        if (matrix[startRow][startCol] != 1) {// Robot cannot reach to this cell of the matrix
            return;
        } else {
            paths.add(new Path(startRow, startCol));
        }

        getShortestPath(matrix, startRow + 1, startCol, endRow, endCol, queue, paths);
        if (!paths.contains(new Path(startRow + 1, startCol))) {
            getShortestPath(matrix, startRow, startCol + 1, endRow, endCol, queue, paths);
            if (!paths.contains(new Path(startRow, startCol + 1))) {
                return;
            }
        }
    }*/

    /*
        book starts the algorithm from endRow and endCol where Robot wants to reach.
     */
    private static void getPath_book_way(int[][] matrix, int startRow, int startCol, int endRow, int endCol, List<Path> paths) {
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

        getPath_book_way(matrix, startRow, startCol, endRow, endCol - 1, paths);
        if (!paths.contains(new Path(endRow, endCol - 1))) {
            getPath_book_way(matrix, startRow, startCol, endRow - 1, endCol, paths);
            if (!paths.contains(new Path(endRow - 1, endCol))) {
                return;
            }
        }
    }

    /*
    This code is same as that is from book. The only difference is that I am not passing 'paths' as a parameter to a method.
    Instead, I am creating initial 'paths' in the method and then merging it with recursive calls.
    Just like CreateLinkedListForEachLevelOfBinaryTree.java

    I wanted to experiment this way of writing the code.

    Conclusion:
    Value that is expected as a returned value from recursive method, don’t pass it as a method parameter to get away with the problem of hard coding return value of exit condition.
    If you want to do it, you need to think of Tail-Recursion strategy that is a bit complex.

    I would say that start with non-tail-recursion(not passing result as a method parameter).
    But if you find that you will end up with merging the results, then pass result container as method parameter.

     */
    private static List<Path> getPath_(int[][] matrix, int startRow, int startCol, int endRow, int endCol) throws Exception {

        List<Path> paths = new ArrayList<>();

        if (matrix == null || matrix.length == 0) return paths;

        if (startRow < 0 || startCol < 0 || endRow < 0 || endCol < 0 ||
                startRow >= matrix.length || startCol >= matrix[startRow].length ||
                endRow >= matrix.length || endCol >= matrix[endRow].length) {
            return paths;
        }

        if (startRow == endRow && startCol == endCol && matrix[startRow][startCol] == 1) {
            paths.add(new Path(startRow, startCol));
            return paths;
        }

        if (matrix[endRow][endCol] != 1) {// Robot cannot reach to this cell of the matrix
            return paths;
        } else {
            paths.add(new Path(endRow, endCol));
        }

        List<Path> paths1 = getPath_(matrix, startRow, startCol, endRow, endCol - 1);
        List<Path> mergedPathsAndPath1 = new ArrayList<Path>() {{
            addAll(paths);
            addAll(paths1);
        }};

        if (!mergedPathsAndPath1.contains(new Path(endRow, endCol - 1))) {
            List<Path> paths2 = getPath_(matrix, startRow, startCol, endRow - 1, endCol);
            List<Path> mergedPathsAndPaths1AndPaths2 = new ArrayList<Path>() {{
                addAll(mergedPathsAndPath1);
                addAll(paths2);
            }};
            if (!mergedPathsAndPaths1AndPaths2.contains(new Path(endRow - 1, endCol))) {
                throw new Exception("path not found");
            } else {
                return mergedPathsAndPaths1AndPaths2;
            }
        }
        return mergedPathsAndPath1;
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


    private static void prettyPrintMatrix(int[][] matrix) {
        System.out.print("   ");
        for (int i = 0; i < matrix.length; i++) {
            System.out.print(i + "   ");
        }

        System.out.println();
        System.out.print("   ");
        for (int i = 0; i < matrix.length; i++) {
            System.out.print("---|");
        }

        System.out.println();

        for (int from = 0; from < matrix.length; from++) {
            for (int to = 0; to <= from; to++) {

                System.out.print(from + "|" + " ");
                break;

            }
            for (int to = 0; to < matrix[from].length; to++) {
                System.out.print(matrix[from][to] + "  " + "|");
            }
            System.out.println();
            System.out.println();
        }

    }
}
