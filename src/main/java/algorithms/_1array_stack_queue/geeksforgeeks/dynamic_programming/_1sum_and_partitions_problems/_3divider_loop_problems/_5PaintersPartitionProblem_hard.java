package algorithms._1array_stack_queue.geeksforgeeks.dynamic_programming._1sum_and_partitions_problems._3divider_loop_problems;

/*
The painter’s partition problem

https://www.geeksforgeeks.org/painters-partition-problem/

We have to paint n boards of length {A1, A2, …, An}. There are k painters available and each takes 1 unit time to paint 1 unit of board. The problem is to partitionMaxSum_My_Way_BruteForce the minimum time to get
this job done under the constraints that any painter will only paint continuous sections of boards, say board {2, 3, 4} or only board {1} or nothing but not board {1, 3, 4, 5}.

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
public class _5PaintersPartitionProblem_hard {

    public static void main(String[] args) {

        {
            int[] A = {10, 20, 30, 40};
            int painters = 2;
            {
                int min = partitionMaxSum_My_Way_BruteForce(A, 0, A.length - 1, painters);
                System.out.println(min);//60
            }

            {
                int min = partitionMaxSum_BruteForce(A, 0, A.length - 1, painters);
                System.out.println(min);//60
            }

            {
                int min = partitionMaxSum_BruteForce_Staring_From_Last_Element_And_Improvement_By_Memoizing_Sum_of_Elements(A, 0, A.length - 1, painters);
                System.out.println(min);//60
            }

        }

        System.out.println();

        {
            int[] A = {10, 10, 10, 10};
            int painters = 2;

            {
                int min = partitionMaxSum_My_Way_BruteForce(A, 0, A.length - 1, painters);
                System.out.println(min);
            }
            {
                int min = partitionMaxSum_BruteForce(A, 0, A.length - 1, painters);
                System.out.println(min);//20
            }
            {

                int min = partitionMaxSum_BruteForce_Staring_From_Last_Element_And_Improvement_By_Memoizing_Sum_of_Elements(A, 0, A.length - 1, painters);
                System.out.println(min);//20
            }
        }

        System.out.println();

        {
            int[] A = {10, 20, 30, 40};
            int painters = 3;
            int min = partitionMaxSum_BruteForce(A, 0, A.length - 1, painters);
            System.out.println(min);//40

            int min2 = partitionMaxSum_BruteForce_Staring_From_Last_Element_And_Improvement_By_Memoizing_Sum_of_Elements(A, 0, A.length - 1, painters);
            System.out.println(min2);//40

            int min3 = partitionMaxSum_My_Way_BruteForce(A, 0, A.length - 1, painters);
            System.out.println(min3);
        }
    }

/*

    There is a similarity in this problem and MatrixChainMultiplication.java, LongestIncreasingSubSequenceInArray.java problems. All of these needs a loop of 'divider'.


    Reducing the problem by one element (last element)
    _________________       _____
    | 10,  20,  30, |       | 40 |
    -----------------       -----
    <-for k-1 painters->   <-for kth painter->

        element = A[end]

        workDoneByKthPainter = sum of continuous elements from selected element till last element
        workDoneByK-1Painters = find(A,start,end-1,k-1)

    You might think that formula for the result is Max(workDoneByKthPainter, workDoneByK-1Painters). But this is a partial result.

    kth painter should get a chance to paint last, then last two, then last three and so on boards.
    So, basically you need a for loop running from end-1 to start for kth painter

        10,  20,  30,           40
        <-for k-1 painters->    <-for kth painter->

        10,  20,                30, 40
        <-for k-1 painters->    <-for kth painter->

        10,                     20, 30, 40
        <-for k-1 painters->    <-for kth painter->

        and so on

    This can be achieved using a for loop that runs from divider=end-1 to start
    In this loop, we call another method, that will do sum of units given to kth painter and find the same for remaining painters and take the max of them.

    int findMinTimeTakenByAssigningDifferentNumberOfBoardsToPaintToKthPainter(...) {

        ...

        for (int divider = end - 1; divider >= 0; divider--) {
            min = Math.min(min, findMaxBetweenTimeTakenByKthPainterAndRemainingPainters(A, start, end, divider, painters));
        }
        return min;
    }

    int findMaxBetweenTimeTakenByKthPainterAndRemainingPainters(A, start, end, divider, painters) {
        ...

        int timeTakenByKthPainter = sum(A, divider + 1, end);

        // NOTE: we are calling original method and not the current method
        int timeTakenByRemainingPainters = findMinTimeTakenByAssigningDifferentNumberOfBoardsToPaintToKthPainter(A, start, divider, painters - 1);

        return Math.max(timeTakenByKthPainter, timeTakenByRemainingPainters);
    }


How to detect that you need a loop of divider?

    When you see that more than one parameters (here end index and painters) are being changed in the same recursive method call, you need extra divider loop(s) for one of the parameter(s).

    In above mentioned code, both end index and number of painters are changing in the same recursive call

    int timeTakenByRemainingPainters = findMinTimeTakenByAssigningDifferentNumberOfBoardsToPaintToKthPainter(A, start, divider, painters - 1);

What exit conditions are needed?

    As you see, two parameters are changing in recursive calls (end index and painters(k)).
    So, you need exit conditions for both these parameters.
    Each parameter is changing just once (endIndex is reduced by 1  and  k is reduced by 1), so you need one or more exit conditions endIndex and one or more exit conditions for painters.

        In recursive calls, if you see endIndex is changing multiple times, then you need two exit conditions for endIndex (start==end, end<start)
        e.g. find(A,start,end-1,k) || find(A,start,end-2,k)

        If you see just
        e.g. find(A,start,end-2,expectedSum)
        then also you need end==start and end<start conditions because end-2 can go lesser than start also.

        You might think to add just one exit condition for painters(k)==0, but that might not be enough because you can easily partitionMaxSum_My_Way_BruteForce the solution when painters(k)==1 also. So, you should add it for painters(k)==1 also.

    so, you need exit conditions for end index and painters(k)
        - end==start
        - painters(k)==0
        - painters(k)==1

Can you use Dynamic Programming to solve this problem?

    If there is just one recursive call, you don't need Dynamic Programming.
    If there is a recursive call based on divide-and-conquer approach, you don't need Dynamic Programming

    Here, there is no divide-and-conquer and for each method call, recursion is done multiple times (for loop of divider). So, you may be able to improve the performance by using Dynamic Programming.

    When you create a recursive tree for method calls, you will see that method is being called with same parameters multiple times (Overlapping Problem).

Time Complexity:

    For NP-Complete problems, either it takes O(2^n) or O(n!).

    I think this problem takes O(2^n) and not O(n!). Read the difference between these two in 'RecursionAndDynamicProgrammingFundamentals.java'.

 */

    private static int partitionMaxSum_My_Way_BruteForce(int[] A, int start, int end, int painters) {
        int min = Integer.MAX_VALUE;

        if (painters == 1) return sum(A, start, end);

        for (int divider = end - 1; divider >= 0; divider--) { // divider runs n times. This problem is similar to LIS (LongestIncreasingSubSequenceInArray.java) in terms of calculating time complexity
            min = Math.min(min, partition_for_Kth_and_Other_Painters(A, start, end, divider, painters));
        }
        return min;
    }

    private static int partition_for_Kth_and_Other_Painters(int[] A, int start, int end, int divider, int painters) {

        if (painters == 1) return sum(A, start, end);

        if (start == end) return A[end];

//        if(end < start) return 0;

        int timeTakenByKthPainter = sum(A, divider + 1, end);

        // NOTE: we are calling original method and not the current method
        int timeTakenByRemainingPainters = partitionMaxSum_My_Way_BruteForce(A, start, divider, painters - 1);

        return Math.max(timeTakenByKthPainter, timeTakenByRemainingPainters);
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

        if (end == start) return A[end];
        //if (end < start) return 0;

        if (painters == 0) {
            return Integer.MAX_VALUE;
        }
        if (painters == 1) {
            return sum(A, start, end);
        }

        int min = Integer.MAX_VALUE;

        for (int divider = start + 1; divider <= end; divider++) {

            int sumOfTimeToPaintBoardsTillDividerByKthPainter = sum(A, start, divider - 1);

            int minTimeTakenByTotalPaintersMinusOnePaintersToPaintBoardsFromDividerOnwards = partitionMaxSum_BruteForce(A, divider, end, painters - 1);

            min = Math.min(min, Math.max(sumOfTimeToPaintBoardsTillDividerByKthPainter, minTimeTakenByTotalPaintersMinusOnePaintersToPaintBoardsFromDividerOnwards));
        }
        return min;
    }

    private static int partitionMaxSum_BruteForce_Staring_From_Last_Element(int[] A, int start, int end, int painters) {
        if (A == null || A.length == 0) return 0;

        if (end == start) return A[end];
        //if (end < start) return 0;

        if (painters == 0) {
            return Integer.MAX_VALUE;
        }

        if (painters == 1) {
            return sum(A, start, end);
        }

        int element = A[end];

        int min = Integer.MAX_VALUE;

        for (int divider = end - 1; divider >= start; divider--) {

            int sumOfTimeTakenByLastPainter = sum(A, divider + 1, end);
            int timeTakenByRemainingPaintersToPaintRemainingBoards = partitionMaxSum_BruteForce_Staring_From_Last_Element(A, start, divider, painters - 1);

            int max = Math.max(sumOfTimeTakenByLastPainter, timeTakenByRemainingPaintersToPaintRemainingBoards);

            min = Math.min(min, max);
        }

        return min;
    }


    private static int partitionMaxSum_BruteForce_Staring_From_Last_Element_And_Improvement_By_Memoizing_Sum_of_Elements(int[] A, int start, int end, int painters) {
        if (A == null || A.length == 0) return 0;

        if (end == start) return A[end];
        //if (end < start) return 0;

        if (painters == 0) {
            return Integer.MAX_VALUE;
        }

        if (painters == 1) {
            return sum(A, start, end);
        }

        int element = A[end];

        int min = Integer.MAX_VALUE;

        int prevSum = 0;
        for (int divider = end - 1; divider >= start; divider--) {

            int sumOfTimeTakenByLastPainter = prevSum + A[divider + 1];
            prevSum = sumOfTimeTakenByLastPainter; // memoizing the sum, so that you don't have to iterate through same elements again and again.

            int timeTakenByRemainingPaintersToPaintRemainingBoards = partitionMaxSum_BruteForce_Staring_From_Last_Element_And_Improvement_By_Memoizing_Sum_of_Elements(A, start, divider, painters - 1);

            int max = Math.max(sumOfTimeTakenByLastPainter, timeTakenByRemainingPaintersToPaintRemainingBoards);

            min = Math.min(min, max);
        }

        return min;
    }


    // from geeksforgeeks
    static int partition(int arr[], int n, int k) {
        // base cases
        if (k == 1) // one partition
            return sum(arr, 0, n - 1);
        if (n == 1)  // one board
            return arr[0];

        int best = Integer.MAX_VALUE;

        // partitionMaxSum_My_Way_BruteForce minimum of all possible maximum
        // k-1 partitions to the left of arr[i],
        // with i elements, put k-1 th divider
        // between arr[i-1] & arr[i] to get k-th
        // partition
        for (int divider = 1; divider <= n; divider++)
            best = Math.min(best, Math.max(partition(arr, divider, k - 1),
                    sum(arr, divider, n - 1)));

        return best;
    }

}
