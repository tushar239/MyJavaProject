package algorithms._5matrix._10dynamic_programming;

import algorithms.utils.ArrayUtils;

/*
    Maximum points from top left of matrix to bottom right and return back

    https://www.geeksforgeeks.org/maximum-points-top-left-matrix-bottom-right-return-back/

    Problem statement:
    You can either go right or down (but not both) to cover maximum points ('*').
    '*' is one point
    '.' is zero points
    '#' is a cell that you can go through.

    You can try to cover max points from top-left corner to bottom-right corner once and
    from bottom-right corner to top-left corner once.
    But when you go from bottom-right to top-left, you cannot visit the the same '*' that you already visited when you traversed from top-left to bottom-right.

    e.g.

    char matrix[][] = {
                {'.', '*', '.', '*', '.'},
                {'*', '#', '#', '#', '.'},
                {'*', '.', '*', '.', '*'},
                {'.', '#', '#', '#', '*'},
                {'.', '*', '.', '*', '.'}
        };

    when you traverse from top-left to bottom-right, either of below paths would work, but you need to find the path that works best with bottom-right to top-left traversal.

                {'.',--->'*',--->'.',--->'*',--->'.'},
                                                  |
                                                  v
                {'*',    '#',    '#',    '#',    '.'},
                                                  |
                                                  v
                {'*',    '.',    '*',    '.',    '*'},
                                                  |
                                                  v
                {'.',    '#',    '#',    '#',    '*'},
                                                  |
                                                  v
                {'.',    '*',    '.',    '*',    '.'}

                                OR

                {'.',    '*',    '.',    '*',    '.'},
                  |
                  v
                {'*',    '#',    '#',    '#',    '.'},
                  |
                  v
                {'*',--->'.',--->'*',--->'.',--->'*'},
                                                  |
                                                  v
                {'.',    '#',    '#',    '#',    '*'},
                                                  |
                                                  v
                {'.',    '*',    '.',    '*',    '.'}

                                OR

                {'.',    '*',    '.',    '*',    '.'},
                  |
                  v
                {'*',    '#',    '#',    '#',    '.'},
                  |
                  v
                {'*',    '.',    '*',    '.',    '*'},
                  |
                  v
                {'.',    '#',    '#',    '#',    '*'},
                  |
                  v
                {'.',--->'*',--->'.',--->'*',--->'.'}


    From bottom-right to top-left corner traversal, either of below paths would work, but you need to find the path that works best with top-left to bottom-right traversal.


                {'.',    '*',    '.',    '*',    '.'},
                  ^
                  |
                {'*',    '#',    '#',    '#',    '.'},
                  ^
                  |
                {'*',    '.',    '*',    '.',    '*'},
                  ^
                  |
                {'.',    '#',    '#',    '#',    '*'},
                  ^
                  |
                {'.',<---'*',<---'.',<---'*',<---'.'}

                                OR

                {'.',    '*',    '.',    '*',    '.'},
                  ^
                  |
                {'*',    '#',    '#',    '#',    '.'},
                  ^
                  |
                {'*',<---'.',<---'*',<---'.',<---'*'},
                                                  ^
                                                  |
                {'.',    '#',    '#',    '#',    '*'},
                                                  ^
                                                  |
                {'.',    '*',    '.',    '*',    '.'}

                                OR

                {'.',    '*',    '.',    '*',    '.'},
                  ^
                  |
                {'*',    '#',    '#',    '#',    '.'},
                  ^
                  |
                {'*',    '.',    '*',    '.',    '*'},
                  ^
                  |
                {'.',    '#',    '#',    '#',    '*'},
                  ^
                  |
                {'.',<---'*',<---'.',<---'*',<---'.'}



    There are many possible combinations between top-left to bottom-right
                                                        and
                                                 bottom-right to top-left

    But the best combination that can collect max points is 3rd and 3rd that gives total 8 points without revisiting already visited points('*').


    NOTE:
    I couldn't solve this problem. Below algorithm just shows how to find max points from top-left corner to bottom-right corner.
    This problem becomes similar to GoldMineProblem.java

*/
public class _3MaxPointsFromLeftOfMatrixToBottomRightAndReturnBack {
    public static void main(String[] args) {
        char matrix[][] = {
                {'.', '*', '.', '*', '.'},
                {'*', '#', '#', '#', '.'},
                {'*', '.', '*', '.', '*'},
                {'.', '#', '#', '#', '*'},
                {'.', '*', '.', '*', '.'}
        };

        int startCellX = 0;
        int startCellY = 0;
        int endCellX = matrix.length - 1;
        int endCellY = matrix[0].length - 1;

        //boolean visited[][] = new boolean[matrix.length][matrix[startCellX].length];

        // initializing memoization table
        int[][] memory = new int[matrix.length][matrix[0].length];
        for (int row = 0; row < memory.length; row++) {
            for (int col = 0; col < memory[row].length; col++) {
                memory[row][col] = -1;
            }
        }

        int maxPointsFromTopLeftToBottomRight = findMaxPointsFromTopLeftToBottomRight(matrix, startCellX, startCellY, endCellX, endCellY, memory);
        System.out.println(maxPointsFromTopLeftToBottomRight);
        ArrayUtils.prettyPrintMatrix(memory);

        // How to find the path that was traversed to find max points?

        /*

        Memoization Table

             0    1    2    3    4
           ----|----|----|----|----|
        0| 5   |4   |3   |3   |2   |

        1| 5   |-1  |-1  |-1  |2   |

        2| 4   |3   |3   |2   |2   |

        3| 2   |-1  |-1  |-1  |1   |

        4| 2   |2   |1   |1   |-1  |


        Starting from top-left, go right and down, whichever cell has max points, that cell is a part of the path.

         */

/*        // end cell is already visited in findMaxPointsFromTopLeftToBottomRight, but you need to start from end cell again, so
        matrix[endCellX][endCellY] = '.'; // if it was '*', then change it to '.'
        visited[endCellX][endCellY] = false;

        ArrayUtils.prettyPrintMatrix(visited);

        int maxPointsFromTopBottomRightToTopLeft = findMaxPointsFromBottomRightToTopLeft(matrix, startCellX, startCellY, endCellX, endCellY, visited);
        System.out.println(maxPointsFromTopBottomRightToTopLeft);

        System.out.println("Max Points collected from Top-Left to Bottom-Right and from Bottom-Right to Top-Left: " + (maxPointsFromTopLeftToBottomRight + maxPointsFromTopBottomRightToTopLeft));*/
    }

    private static int findMaxPointsFromTopLeftToBottomRight(char[][] matrix, int startingCellX, int startingCellY, int endCellX, int endCellY, int[][] memory) {

        // IMPORTANT condition. All matrix related algorithms must have range checking condition.
        if (!inRange(matrix, startingCellX, startingCellY)) {
            return 0;
        }

/*
        if (visited[startingCellX][startingCellY]) {
            return 0;
        }
*/

        if (memory[startingCellX][startingCellY] != -1) {
            return memory[startingCellX][startingCellY];
        }

        char ch = matrix[startingCellX][startingCellY];

        //visited[startingCellX][startingCellY] = true;

        if (ch == '#') {
            return 0;
        }

        if (startingCellX == endCellX && startingCellY == endCellY) {
            if (ch == '#' || ch == '.') {
                return 0;
            }
            return 1; // if '*'
        }

        if (ch == '*') {

            int maxPointsFromDown = findMaxPointsFromTopLeftToBottomRight(matrix, startingCellX + 1, startingCellY, endCellX, endCellY, memory);
            int maxPointsFromRight = findMaxPointsFromTopLeftToBottomRight(matrix, startingCellX, startingCellY + 1, endCellX, endCellY, memory);

           /* if (maxPointsFromRight < maxPointsFromDown) {
                if (inRange(matrix, startingCellX, startingCellY + 1)) {
                    visited[startingCellX][startingCellY + 1] = false;
                }
            } else {
                if (inRange(matrix, startingCellX + 1, startingCellY)) {
                    visited[startingCellX + 1][startingCellY] = false;
                }
            }
*/
            int maxPoints = 1 + Math.max(maxPointsFromRight, maxPointsFromDown);

            // memoization for top-down approach
            memory[startingCellX][startingCellY] = maxPoints;

            return maxPoints;

        } else if (ch == '.') {

            int maxPointsFromDown = findMaxPointsFromTopLeftToBottomRight(matrix, startingCellX + 1, startingCellY, endCellX, endCellY, memory);
            int maxPointsFromRight = findMaxPointsFromTopLeftToBottomRight(matrix, startingCellX, startingCellY + 1, endCellX, endCellY, memory);

           /* if (maxPointsFromRight < maxPointsFromDown) {
                if (inRange(matrix, startingCellX, startingCellY + 1)) {
                    visited[startingCellX][startingCellY + 1] = false;
                }
            } else {
                if (inRange(matrix, startingCellX + 1, startingCellY)) {
                    visited[startingCellX + 1][startingCellY] = false;
                }
            }*/

            int maxPoints = Math.max(maxPointsFromRight, maxPointsFromDown);

            // memoization for top-down approach
            memory[startingCellX][startingCellY] = maxPoints;

            return maxPoints;
        } else { // if ch == '#'

            int maxPoints = 0;

            // memoization for top-down approach
            memory[startingCellX][startingCellY] = 0;

            return 0;
        }
    }

    private static int findMaxPointsFromBottomRightToTopLeft(char[][] matrix, int startingCellX, int startingCellY, int endCellX, int endCellY, boolean[][] visited) {

        // IMPORTANT condition. All matrix related algorithms must have range checking condition.
        if (!inRange(matrix, startingCellX, startingCellY)) {
            return 0;
        }

        if (visited[endCellX][endCellY]) {
            return 0;
        }

        char ch = matrix[endCellX][endCellY];

        visited[endCellX][endCellY] = true;

        if (ch == '#') {
            return 0;
        }

        if (startingCellX == endCellX && startingCellY == endCellY) {
            if (ch == '#' || ch == '.') {
                return 0;
            }
            return 1; // if '*'
        }

        if (ch == '*') {

            int maxPointsFromUp = findMaxPointsFromBottomRightToTopLeft(matrix, startingCellX, startingCellY, endCellX - 1, endCellY, visited);
            int maxPointsFromLeft = findMaxPointsFromBottomRightToTopLeft(matrix, startingCellX, startingCellY, endCellX, endCellY - 1, visited);

            if (maxPointsFromLeft < maxPointsFromUp) {
                if (inRange(matrix, endCellX, endCellY - 1)) {
                    visited[startingCellX][startingCellY + 1] = false;
                }
            } else {
                if (inRange(matrix, endCellX - 1, endCellY)) {
                    visited[endCellX - 1][endCellY] = false;
                }
            }

            int maxPoints = 1 + Math.max(maxPointsFromLeft, maxPointsFromUp);
            return maxPoints;

        } else if (ch == '.') {

            int maxPointsFromUp = findMaxPointsFromBottomRightToTopLeft(matrix, startingCellX, startingCellY, endCellX - 1, endCellY, visited);
            int maxPointsFromLeft = findMaxPointsFromBottomRightToTopLeft(matrix, startingCellX, startingCellY, endCellX, endCellY - 1, visited);

            if (maxPointsFromLeft < maxPointsFromUp) {
                if (inRange(matrix, endCellX, endCellY - 1)) {
                    visited[startingCellX][startingCellY + 1] = false;
                }
            } else {
                if (inRange(matrix, endCellX - 1, endCellY)) {
                    visited[endCellX - 1][endCellY] = false;
                }
            }

            int maxPoints = Math.max(maxPointsFromLeft, maxPointsFromUp);

            return maxPoints;
        } else { // if ch == '#'
            return 0;
        }
    }


    private static boolean inRange(char[][] matrix, int x, int y) {
        return (x >= 0 && x <= matrix.length - 1) &&
                (y >= 0 && y <= matrix[x].length - 1);
    }

}
