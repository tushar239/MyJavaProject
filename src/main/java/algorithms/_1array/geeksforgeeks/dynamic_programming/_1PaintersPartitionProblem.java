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


Reducing the problem by one element (last element)
_________________       _____
| 10,  20,  30, |       | 40 |
-----------------       -----
<-for k-1 painters->   <-for kth painter->

    element = A[end]

    workDoneByKthPainter = sum of continuous elements from selected element till last element
    workDoneByK-1Painters = find(A,start,end-1,k-1)

You might think that formula for the result is Max(workDoneByKthPainter, workDoneByK-1Painters). But this is a partial result

This solution will result in

    10,  20,  30,           40                      ------ kth painter paints 40
    <-for k-1 painters->    <-for kth painter->

    10,  20,                30                      ------ k-1st painter paints 30+40
    <-for k-2 painters->    <-for k-1st painter->

    10,                     20                      ------ k-2nd painter paints 20+30+40
    <-for k-3 painters->    <-for k-2nd painter->

    and so on

As you see, kth painter never got a chance to paint anything other than 40. So, this gives partial result.

How to detect this problem from your code?

    In above mentioned recursive call,
        workDoneByK-1Painters = find(A,start,end-1,k-1)
    You are reducing both index an painters(k) together. There is no other recursive calls that reduces an index without reducing painters(k) or vice-a-versa.
    And also you cannot have that kind of recursive call for this problem.
    So, solution is to wrap this code with a for loop of 'divider' pointer that iterates from end-1 to start as shown below.

    int find(A,start,end,k) {

        ..... exit conditions .....

        element = A[end]

        int min = Integer.MAX_VALUE;

        for (int divider = end - 1; divider >= start; divider--) { --------------- IMPORTANT

            workDoneByKthPainter = sum of continuous elements from selected element till last element
            workDoneByK-1Painters = find(A, start, divider, k-1) ------ use 'divider' instead of 'end'

            min = Math.min(min, Max(workDoneByKthPainter, workDoneByK-1Painters))
        }

        return min;
    }

What exit conditions are needed?

    As you see, two parameters are changing in recursive calls (end index and painters(k)).
    So, you need exit conditions for both these parameters.
    Each parameter is changing just once (endIndex is reduced by 1  and  k is reduced by 1), so you need one or more exit conditions endIndex and one or more exit conditions for painters.

        In recursive calls, if you see endIndex is changing multiple times, then you need two exit conditions for endIndex (start==end, end<start)
        e.g. find(A,start,end-1,k) || find(A,start,end-2,k)

        If you see just
        e.g. find(A,start,end-2,expectedSum)
        then also you need end==start and end<start conditions because end-2 can go lesser than start also.

        You might think to add just one exit condition for painters(k)==0, but that might not be enough because you can easily find the solution when painters(k)==1 also. So, you should add it for painters(k)==1 also.

    so, you need exit conditions for end index and painters(k)
        - end==start
        - painters(k)==0
        - painters(k)==1

Can you use Dynamic Programming to solve this problem?

    If there is just one recursive call, you don't need Dynamic Programming
    Here, for each method call, recursion is done multiple times (for loop of divider). So, you may be able to improve the performance by using Dynamic Programming.


*/
public class _1PaintersPartitionProblem {

    public static void main(String[] args) {

        {
            int[] A = {10, 20, 30, 40};
            int painters = 2;
            int min = partitionMaxSum_BruteForce(A, 0, A.length - 1, painters);
            System.out.println(min);//60


            int min2 = partitionMaxSum_BruteForce_Staring_From_Last_Element_And_Improvement_By_Memozing_Sum_of_Elements(A, 0, A.length - 1, painters);
            System.out.println(min2);//60
        }

        System.out.println();

        {
            int[] A = {10, 10, 10, 10};
            int painters = 2;
            int min = partitionMaxSum_BruteForce(A, 0, A.length - 1, painters);
            System.out.println(min);//20

            int min2 = partitionMaxSum_BruteForce_Staring_From_Last_Element_And_Improvement_By_Memozing_Sum_of_Elements(A, 0, A.length - 1, painters);
            System.out.println(min2);//20

        }

        System.out.println();

        {
            int[] A = {10, 20, 30, 40};
            int painters = 3;
            int min = partitionMaxSum_BruteForce(A, 0, A.length - 1, painters);
            System.out.println(min);//40

            int min2 = partitionMaxSum_BruteForce_Staring_From_Last_Element_And_Improvement_By_Memozing_Sum_of_Elements(A, 0, A.length - 1, painters);
            System.out.println(min2);//40

        }
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

        if(painters == 0) {
            return Integer.MAX_VALUE;
        }
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

    private static int partitionMaxSum_BruteForce_Staring_From_Last_Element(int[] A, int start, int end, int painters) {
        if (A == null || A.length == 0) return 0;

        if (end == start) return A[end];
        //if (end < start) return 0;

        if(painters == 0) {
            return Integer.MAX_VALUE;
        }

        if (painters == 1) {
            return sum(A, start, end);
        }

        int element = A[end];

        int min = Integer.MAX_VALUE;

        for (int divider = end - 1; divider >= start; divider--) {

            int sumOfTimeTakenByLastPainter = sum(A, end, A.length - 1);
            int timeTakenByRemainingPaintersToPaintRemainingBoards = partitionMaxSum_BruteForce_Staring_From_Last_Element(A, start, divider, painters - 1);

            int max = Math.max(sumOfTimeTakenByLastPainter, timeTakenByRemainingPaintersToPaintRemainingBoards);

            min = Math.min(min, max);
        }

        return min;
    }


    private static int partitionMaxSum_BruteForce_Staring_From_Last_Element_And_Improvement_By_Memozing_Sum_of_Elements(int[] A, int start, int end, int painters) {
        if (A == null || A.length == 0) return 0;

        if (end == start) return A[end];
        //if (end < start) return 0;

        if(painters == 0) {
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

            int timeTakenByRemainingPaintersToPaintRemainingBoards = partitionMaxSum_BruteForce_Staring_From_Last_Element_And_Improvement_By_Memozing_Sum_of_Elements(A, start, divider, painters - 1);

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

}
