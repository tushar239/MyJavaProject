package algorithms._1array_stack_queue.interviewbit;

/*
https://www.interviewbit.com/problems/min-steps-in-infinite-grid/

Minimum Steps in Infinite Grid:
You are in an infinite 2D grid where you can move in any of the 8 directions :

 (x,y) to
    (x+1, y),
    (x - 1, y),
    (x, y+1),
    (x, y-1),
    (x-1, y-1),
    (x+1,y+1),
    (x-1,y+1),
    (x+1,y-1)
You are given a sequence of points and the order in which you need to cover the points. Give the minimum number of steps in which you can achieve it. You start from the first point.

Example :

Input : [(0, 0), (1, 1), (1, 2)]
Output : 2


Approach:
If you're required to move from point A to B to C, then try to find out the minimum steps required to move from point A to B.
Keep on moving from one point to another and keep adding the minimum steps into a variable.


If you see, directions says that you can go
horizontally  (x,y) to (x,y+1)
vertically    (x,y) to (x+1,y)
or
diagonally    (x,y) to (x+1,y+1)
in the matrix.


If it would have said that you can go only horizontally or vertically, then approach would have been different and easier.
answer would be abs(x1-x2)+abs(y1-y2)

But you can go diagonally also. It can reduce number of steps.
Number of steps that you can go diagonally - min(abs(x1-x2), abs(y1-y2))
e.g. to go from (0,0) to (2,3), you can go take two steps diagonally (min(abs(0-2),abs(0-3)) = 2)
and then remaining steps would be abs(abs(0-2) - abs(0-3))=1

        0   1     2   3     4
       --- |---|--- |---  |---|
    0|  .  |   |    |     |   |
          \
           v
    1|    |    |    |     |   |
              \
               v
    2|    |   | ----|-> . |   |

    3|    |   |     |     |   |

    4|    |   |     |     |   |

to go from (0,0) to (2,3), you go (0,0)->(1,1)->(2,2)->(2,3) = 3 steps
similarly, to go from (2,3) to (1,1), you go (2,3)->(1,2)->(1,1) = 2 steps
total 3+2=5 steps


Time Complexity:O(n)
Space Complexity:O(1)
*/
public class MinStepsInInfiniteGrid {
    public static void main(String[] args) {
        MinStepsInInfiniteGrid minStepsInInfiniteGrid = new MinStepsInInfiniteGrid();
        int[] one = { -7, -13};
        int[] two = { 1, -5};

        int steps = minStepsInInfiniteGrid.coverPoints(one, two);
        System.out.println(steps);
    }

    public int coverPoints(int[] A, int[] B) {
        if (A == null || B == null) return 0;

        if (A.length == 0 || B.length == 0) return 0;

        if (A.length == 1 && B.length == 1) return 0;

        int totalSteps = 0;

        for (int i = 1; i < A.length; i++) {
            int x1 = A[i - 1];//0
            int y1 = B[i - 1];//0

            int x2 = A[i];//2
            int y2 = B[i];//3

            int diffOfXs = Math.abs(x1 - x2);//2
            int diffOfYs = Math.abs(y1 - y2);//3

            if (diffOfXs == diffOfYs) {
                totalSteps += diffOfXs;

            } else {
                int diagonalSteps = Math.min(diffOfXs, diffOfYs);//2
                int lastStep = Math.abs(diffOfXs - diffOfYs);//1
                totalSteps += diagonalSteps + lastStep;
            }

        }

        return totalSteps;
    }
}
