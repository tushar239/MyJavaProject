package algorithms.crackingcodinginterviewbook._6sort__search_merge;


/*
Sorted Matrix Search:
Given an M x N matrix in which each row and each column is sorted in ascending order, write a method to find an element.

There multiple ways:
https://www.youtube.com/watch?v=ZhG1M_FzxgI

1) Brute Force O(N^2) - comparing every single element. No brain. So, that's why it is called Brute Force.

2) Binary Search in each row O(m log n) - m is number of rows and n is number of cols

3) Stair Search - O(m+n)- start from upper right corner cell. If element < cell value, decrement column by 1. If element > cell value, increment row by 1.
    - start from upper right cell
    - if element == upper right cell, return the coordinates of upper right cell
      if element < upper right cell, reduce row and col by 1 and recursively search in remaining matrix
      if element > upper right cell, increase row and keep the col same as upper right cell. Basically, recursively search in a remaining matrix of just one column.

4) Smart Binary Search ---- this is more complicated, but it takes O(log mn) only
    - find mid cell of the matrix
    - if element == mid cell element, then return mid
      if element < mid cell element, then element can be found in cells having row <= mid.col and col <= mid.col. So, first quandrant.
      else in one of the remaining 3 quadrants.

      e.g. element to search=85
      mid is 35
      As 85>35, 85 can't be found in first quadrant. It needs to be searched in rest 3.
      Starting from bottom right quadrant, as 85<95, this quadrant will be skipped.
      As 85>30, it will be searched in left bottom quadrant, but it won't be found there.
      As 85>70, it will be searched in upper right quadrant. It will be found there.


This class shows 3) and 4)
 */
public class _9SortedMatrixSearch {
    public static void main(String[] args) {
        int[][] matrix = new int[][]
                {
                        new int[]{15, 20, 70, 85},
                        new int[]{25, 35, 80, 95},
                        new int[]{30, 55, 95, 105},
                        new int[]{40, 80, 120, 120}
                };

        int elementToFind = 85;

        System.out.println("\033[1m" + "Find Element using Stair Search..." + "\033[0m");
        {
            // start from upper right column
            Coordinate start = new Coordinate(0, matrix[0].length - 1);

            Coordinate coordinateOfFoundElement = findElement_stair_search(matrix, start, elementToFind);
            System.out.println("Found Element's Coordinates:" + coordinateOfFoundElement);
            System.out.println("Time Complexity:" + c);
        }

        System.out.println();

        System.out.println("\033[1m" + "Find Element using Binary Search..." + "\033[0m");
        {
            Coordinate start = new Coordinate(0, 0);
            Coordinate end = new Coordinate(matrix.length - 1, matrix[0].length - 1);

            // my code is better than book's code from time complexity perspective
            System.out.println("\033[1m" + "My way..." + "\033[0m");
            {
                Coordinate coordinateOfFoundElement = findElement_binary_search_my_way(matrix, start, end, elementToFind);
                System.out.println("Found Element's Coordinates:" + coordinateOfFoundElement);
                System.out.println("Time Complexity:" + count);
            }
            System.out.println();

            System.out.println("\033[1m" + "Book way..." + "\033[0m");
            {
                Coordinate coordinateOfFoundElement = findElement_binary_search_book_way(matrix, start, end, elementToFind);
                System.out.println("Found Element's Coordinates:" + coordinateOfFoundElement);
                System.out.println("Time Complexity:" + cnt);
            }
        }
    }

    static int c = 0;

    private static Coordinate findElement_stair_search(int[][] matrix, Coordinate start, int elementToFind) {
        c++;
        if (start.row < 0 || start.col < 0) {
            return null;
        }
        if (elementToFind == matrix[start.row][start.col]) {
            return start;
        }

        if (elementToFind < matrix[start.row][start.col]) {
            return findElement_stair_search(matrix, new Coordinate(start.row, start.col - 1), elementToFind);
        }
        return findElement_stair_search(matrix, new Coordinate(start.row + 1, start.col), elementToFind);
    }

    static int cnt = 0;

    // for some reason, this is not working, but findElement_my_way is working
    private static Coordinate findElement_binary_search_book_way(int[][] matrix, Coordinate origin, Coordinate dest, int x) {
        cnt++;

        if (!origin.inbounds(matrix) || !dest.inbounds(matrix)) {
            //cnt--;
            return null;
        }
        if (matrix[origin.row][origin.col] == x) {
            return origin;
        } else if (!origin.isBefore(dest)) {
            //cnt--;
            return null;
        }

        Coordinate start = (Coordinate) origin.clone();
        int diagDist = Math.min(dest.row - origin.row, dest.col - origin.col);
        Coordinate end = new Coordinate(start.row + diagDist, start.col + diagDist);
        Coordinate mid = new Coordinate(-1, -1);

        // Do binary search on the diagonal, looking for the first element > x
        while (start.isBefore(end)) {
            // (1,1) - setting p to mid cell of the matrix
            mid.row = (start.row + end.row) / 2;
            mid.col = (start.col + end.col) / 2;

            if (x > matrix[mid.row][mid.col]) {
                start.row = mid.row + 1;
                start.col = mid.col + 1;
            } else {
                end.row = mid.row - 1;
                end.col = mid.col - 1;
            }
        }


        Coordinate lowerLeftOrigin = new Coordinate(mid.row, origin.col);
        Coordinate lowerLeftDest = new Coordinate(dest.row, mid.col - 1);
        Coordinate upperRightOrigin = new Coordinate(origin.row, mid.col);
        Coordinate upperRightDest = new Coordinate(mid.row - 1, dest.col);

        Coordinate lowerLeft = findElement_binary_search_book_way(matrix, lowerLeftOrigin, lowerLeftDest, x);
        if (lowerLeft == null) {
            return findElement_binary_search_book_way(matrix, upperRightOrigin, upperRightDest, x);
        }
        return lowerLeft;
    }


    static int count = 0;

    private static Coordinate findElement_binary_search_my_way(int[][] matrix, Coordinate start, Coordinate end, int x) {
        count++;

        if (start.row < 0 || start.col < 0 || end.row > matrix.length - 1 || end.col > matrix[0].length - 1) {
            //count--;
            return null;
        }
        if (!start.isBefore(end)) {
            //count--;
            return null;
        }

        if (x == matrix[start.row][start.col]) {
            return start;
        }

        if (x == matrix[end.row][end.col]) {
            return end;
        }

        // very important condition
        if (x < matrix[start.row][start.col] || x > matrix[end.row][end.col]) {
            return null;
        }

        Coordinate mid = new Coordinate((start.row + end.row) / 2, (start.col + end.col) / 2);

        if (x == matrix[mid.row][mid.col]) {
            return mid;
        } else if (x < matrix[mid.row][mid.col]) {
            return findElement_binary_search_my_way(matrix, start, mid, x);
        } else {
            // divide remaining matrix in 3 quadrants

            Coordinate leftBottomQuadrantStart = new Coordinate(mid.row + 1, start.col);
            Coordinate leftBottomQuadrantEnd = new Coordinate(end.row, mid.col);

            Coordinate upperRightQuadrantStart = new Coordinate(start.row, mid.col + 1);
            Coordinate upperRightQuadrantEnd = new Coordinate(mid.row, end.col);

            Coordinate rightBottomQuadrantStart = new Coordinate(mid.row + 1, mid.col + 1);
            Coordinate rightBottomQuadrantEnd = new Coordinate(end.row, end.col);

            Coordinate foundInRightBottomQuadrant = findElement_binary_search_my_way(matrix, rightBottomQuadrantStart, rightBottomQuadrantEnd, x);

            if (foundInRightBottomQuadrant == null) {
                Coordinate foundInLeftBottomQuadrant = findElement_binary_search_my_way(matrix, leftBottomQuadrantStart, leftBottomQuadrantEnd, x);

                if (foundInLeftBottomQuadrant == null) {
                    return findElement_binary_search_my_way(matrix, upperRightQuadrantStart, upperRightQuadrantEnd, x);
                }

                return foundInLeftBottomQuadrant;

            }

            return foundInRightBottomQuadrant;
        }

    }

    static class Coordinate implements Cloneable {
        public int row, col;

        public Coordinate(int r, int c) {
            this.row = r;
            this.col = c;
        }

        public boolean inbounds(int[][] matrix) { // just for checking exit condition
            return row >= 0 && col >= 0 &&
                    row < matrix.length && col < matrix[0].length;
        }

        public boolean isBefore(Coordinate p) { // just for checking exit condition
            return row <= p.row && col <= p.col;
        }

        @Override
        public Object clone() {
            return new Coordinate(row, col);
        }

        public void setToAverage(Coordinate min, Coordinate max) { // this is basically finding the mid
            row = (min.row + max.row) / 2;
            col = (min.col + max.col) / 2;
        }

        @Override
        public String toString() {
            return "Coordinate{" +
                    "row=" + row +
                    ", col=" + col +
                    '}';
        }
    }

}
