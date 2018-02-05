package algorithms.crackingcodinginterviewbook._5recursion_and_dynamic_programming;

/*
pg 362 of Cracking Coding Interview Book

Paint Fill:
Implement the "paint fill" function that one might see on many image editing program.
That is, given a screen (represented b 2-D array of colors), a point, and a new color,
fill in the surrounding area until the color changers from original color.
 */

import static algorithms.crackingcodinginterviewbook._5recursion_and_dynamic_programming._10PaintFill.Color.*;

public class _10PaintFill {

    public static void main(String[] args) {
        Color[][] matrix = {
                {RED, WHITE, YELLOW},
                {RED, WHITE, YELLOW},
                {RED, WHITE, YELLOW}
        };
        paintFill(matrix, 0, 0, GREEN);
        for (Color[] colors : matrix) {
            for (Color color : colors) {
                System.out.print(color + " ");
            }
            System.out.println();
        }
        System.out.println(count);
    }

    enum Color {BLACK, WHITE, RED, YELLOW, GREEN}


    static int count = 0;

    /*
    choose starting cell from which you want to change the color.
    if that cell's color is already a new color, then you don't try to change surrounding cells colors as well.
    Otherwise, you change that cell's color and go for surrounding cells.

    If you observer, this algorithm is implemented using DFS.
    You can implement it using BFS also.

    Time Complexity:
                                                            PF(0,0)
                                                               |
             Pf(-1,0)                       PF(1,0)                             PF(0,-1)                    PF(0,1)
                                                |                                                               |
                        Pf(0,0)     PF(2,0)     PF(1,-1)    Pf(1,1)
                                        |
                       Pf(3,0)     PF(1,0)     PF(2,-1)    Pf(2,1)
                         |                                    |
                       .....                                .....
                         \

    This will go just till rc levels down and at every level, just 4 pf calls will happen. All other pf calls will not happen because of important exit condition checking color.
    So, time complexity = O(4rc)

     */
    private static void paintFill(Color[][] screen, int r, int c, Color newColor) {
        count++;
        if (screen == null) return;

        if (r < 0 || r >= screen.length || c < 0 || c >= screen[0].length) return;

        if(screen[r][c] == newColor) return; // very important exit condition. This condition is mandatory, otherwise it will go in infinite loop

        screen[r][c] = newColor;

        paintFill(screen, r - 1, c, newColor);
        paintFill(screen, r + 1, c, newColor);
        paintFill(screen, r, c - 1, newColor);
        paintFill(screen, r, c + 1, newColor);

    }

}
