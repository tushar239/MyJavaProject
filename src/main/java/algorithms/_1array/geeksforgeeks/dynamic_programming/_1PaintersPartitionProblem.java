package algorithms._1array.geeksforgeeks.dynamic_programming;

/*
The painter’s partition problem

https://www.geeksforgeeks.org/painters-partition-problem/

We have to paint n boards of length {A1, A2…An}. There are k painters available and each takes 1 unit time to paint 1 unit of board. The problem is to find the minimum time to get
this job done under the constraints that any painter will only paint continuous sections of boards, say board {2, 3, 4} or only board {1} or nothing but not board {2, 4, 5}.

Examples:

    Input : k = 2, A = {10, 10, 10, 10}
    Output : 20.
    Here we can divide the boards into 2
    equal sized partitions, so each painter
    gets 20 units of board and the total
    time taken is 20.

    Input : k = 2, A = {10, 20, 30, 40}
    Output : 60.
    Here we can divide first 3 boards for
    one painter and the last board for
    second painter.


IMPORTANT:
    This is a unique algorithm. You need to memorize the approach of this algorithm.
    Constraint (any painter will only paint continuous sections of boards) makes it different from PartitionASetIntoTwoSubsetsSuchThatSumOfElementsInBothSubsetsIsMinimal.java.
    If there were no constraint, then you could simply change 'PartitionASetIntoTwoSubsetsSuchThatSumOfElementsInBothSubsetsIsMinimal.java' to handle 3 subsets.

    If there was no constraint of 'any painter will only paint continuous sections of boards', then this problem would be same as 'PartitionASetIntoTwoSubsetsSuchThatSumOfElementsInBothSubsetsIsMinimal.java'.
    You could simply find out the result by (sum of all elements/3).

*/
public class _1PaintersPartitionProblem {

    public static void main(String[] args) {

        {
            int[] A = {10, 20, 30, 40};
            int painters = 2;
            int min = partitionMaxSum_BruteForce(A, 0, A.length - 1, painters);
            System.out.println(min);//60

            /*int min2 = partitionMaxSum_BruteForce_2(A, 0, A.length - 1, painters);
            System.out.println(min2);//10*/
        }

        System.out.println();

        {
            int[] A = {10, 10, 10, 10};
            int painters = 2;
            int min = partitionMaxSum_BruteForce(A, 0, A.length - 1, painters);
            System.out.println(min);//20
        }

        System.out.println();

        {
            int[] A = {10, 20, 30, 40};
            int painters = 3;
            int min = partitionMaxSum_BruteForce(A, 0, A.length - 1, painters);
            System.out.println(min);//40

           /* int result = partition(A, A.length, painters);
            System.out.println(result);//40
            */
        }
       /* {
            int min = partitionMaxSum_2(A, 0, A.length - 1, painters);
            System.out.println(min);
        }*/
    }


    private static int sum(int[] A, int start, int end) {
        int sum = 0;
        for (int i = start; i <= end; i++) {
            sum += A[i];
        }
        return sum;
    }

    /*
        Even though this is a Dynamic Programming related algorithm, I could not figure out the formula by drawing below 2-D matrix ?????????

        unit boards     painters
                     0   1   2   3
         0   0
         1   1
         2   2
         3   3
         4   4

        Two painters example:

                        Put a divider after 10. It means that one painter will paint all boards before a divider and remaining painters will paint remaining boards.
                        (10) & (20, 30, 40), so time is 90.

                        Similarly we can put the
                        divider after 20 -> (10, 20) (30, 40) -> time 70
                        divider after 30 -> (10,20,30) (40) -> time 60

                        This means the minimum time: (90, 70, 60) is 60, when there are two painters.


        You can optimize this algorithm using Top-Down Dynamic Programming.
     */
    private static int partitionMaxSum_BruteForce(int[] A, int start, int end, int painters) {
        if (A == null || A.length == 0) return 0;

        if (start == end) return A[start];
        if (start > end) return 0;

        if (painters == 1) {
            return sum(A, start, end);
        }

        int min = Integer.MAX_VALUE;

        for (int divider = start + 1; divider <= end; divider++) {

            int sumOfTimeToPaintBoardsTillDivider = sum(A, start, divider - 1);

            int minTimeTakenByTotalPaintersMinusOnePaintersToPaintBoardsFromDividerOnwards = partitionMaxSum_BruteForce(A, divider, end, painters - 1);

            min = Math.min(min, Math.max(sumOfTimeToPaintBoardsTillDivider, minTimeTakenByTotalPaintersMinusOnePaintersToPaintBoardsFromDividerOnwards));
        }
        return min;
    }


   /* private static int partitionMaxSum_BruteForce_2(int[] A, int start, int end, int painters) {
        if (A == null || A.length == 0) return 0;

        if (start == end) return A[start];
        if (start > end) return 0;

        if (painters == 1) {
            return sum(A, start, end);
        }


        int sumOfTimeToPaintBoardsTillDivider = sum(A, end, A.length - 1);

        int minTimeTakenByTotalPaintersMinusOnePaintersToPaintBoardsFromDividerOnwards = partitionMaxSum_BruteForce_2(A, start, end - 1, painters - 1);

        int minTimeTakenByOnePainterIfHeCoversOneLessBoard = partitionMaxSum_BruteForce_2(A, start, end - 1, painters);

        return Math.min(minTimeTakenByOnePainterIfHeCoversOneLessBoard, Math.max(sumOfTimeToPaintBoardsTillDivider, minTimeTakenByTotalPaintersMinusOnePaintersToPaintBoardsFromDividerOnwards));

    }*/

    // from geeksforgeeks
    static int partition(int arr[], int n, int k) {
        // base cases
        if (k == 1) // one partition
            return sum(arr, 0, n - 1);
        if (n == 1)  // one board
            return arr[0];

        int best = Integer.MAX_VALUE;

        // find minimum of all possible maximum
        // k-1 partitions to the left of arr[i],
        // with i elements, put k-1 th divider
        // between arr[i-1] & arr[i] to get k-th
        // partition
        for (int divider = 1; divider <= n; divider++)
            best = Math.min(best, Math.max(partition(arr, divider, k - 1),
                    sum(arr, divider, n - 1)));

        return best;
    }
    /*private static int partitionMaxSum_2(int[] A, int start, int end, int painters) {
        if (A == null || A.length == 0) return 0;

        if (start == end) return A[start];// if there is just one board


        if (painters == 0) {// if there are 0 painters
            return Integer.MAX_VALUE;
        }

        if (painters == 1) { // if there is just 1 painter, then he has to paint all the boards

            return sum(A, start, end);
        }

        int element = A[end];

        if (element == 0) { // if element==0
            return 0;
        }

        int maxTimeTakenByTotalPaintersMinusOnePaintersToPaintBoardsFromDividerOnwards = partitionMaxSum_2(A, start, end - 1, painters - 1);
        int timeTakenByOneLastPainter = sum(A, end, A.length - 1);

        return Math.max(maxTimeTakenByTotalPaintersMinusOnePaintersToPaintBoardsFromDividerOnwards, timeTakenByOneLastPainter);

    }*/
}
