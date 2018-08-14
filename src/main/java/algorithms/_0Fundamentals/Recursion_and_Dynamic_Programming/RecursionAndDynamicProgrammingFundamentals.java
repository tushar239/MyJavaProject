package algorithms._0Fundamentals.Recursion_and_Dynamic_Programming;

/*
    This is very important algorithm that tells you a few thing about recursion.
    - reducing the problem by 1
    - when you should decide to pass extra parameter to a method
    - how to write an exit condition
    - Memoization to replace Brute Force disadvantages

    Recursion and Dynamic Programming Concepts
    ------------------------------------------

    1) How to think Reduce the problem by 1 or Half concept?

        Recursion is a concept of solving the entire problem by solving a part of it.
        One or both of the following approaches used based on the problem.

        - Reducing the problem by 1:

            In case of array, you normally pass an array, start and end

                PrintAllSubSequencesAndSubArrays.java

            In case of tree, you normally pass root to a method and recurse a method with root.left and/or root.right and then you think how to merge the results of left and/or right with that of root.
            In case of LinkedList, you do the same. You normally pass head to a method and recurse a method with head.next and then you think how to merge the results of head.next with that of head.

                ReverseLinkedList.java
                CreateMinimalBST.java
                FindWhetherTreeIsBalanced.java's getHeight method

            In case of matrix, you normally pass startRow and startCol to a method. If startRow and startCol satisfies the required conditions, then you recurse the method with (startRow+1, startCol) and/or (startRow, startCol+1)
            This way is good, if you need to traverse a matrix based on certain conditions

                RobotInGrid.java

            If you need place items in a matrix based on certain conditions, then sometimes you need to fully process the first item and place it in the matrix outside recursive method (in caller method of recursive method)
            and then use its results to place rest of the items in the matrix.

                EightQueens.java

        - Reducing the problem by half:

            Sometimes it is obvious to reduce the problem by half like Binary Search.

            But sometimes, it is challenging to think about reducing the problem by half instead of 1.
            e.g.
                 RecursiveMultiply.java
                    This problem can be solved in both ways, but if you use 'reduce the problem by half' technique, then it is more efficient.
                 Merge Sort is another example.

        - Using both of the above approaches

             Binary Tree problems are the combination of Reduce the problem by 1 and half.
             You reduce the problem by 1 by processing a root and reduce the problem by half by processing the remaining tree into to halves (root's left and root's right)

    2) How to convert Iterative code to Recursive code?

        See FindTripletWithMaxSum.java

    3) How to avoid using any other data structure and using recursion instead

        e.g. DeleteMiddleElementOfAStackWithoutUsingAnyAdditionalDataStructure.java

    3) Recursive method that doesn't return anything may result in infinite-loop, if you don't wrap every recursive call with some condition.

       void method(int n){
            if(n > 1)
                method(n-1);

            method(n-2);
       }

       This will result in infinite loop because even though if(n>1) condition is satisfied, method(n-2) will still be called.

       To avoid a problem,

       void method(int n){
            if(n > 1)
                method(n-1);
            else // wrap every single recursive call with some condition, if recursive method doesn't return anything.
                method(n-2);

            // return; // implicit
       }

       e.g. FindTheOnlyRepeatingElementInSortedArrayOfSizeN.java

    4) How to decide exit conditions for recursive method?

        find(A,start,end,expectedSum) {
            ...

            find(A,start,end-1,expectedSum)
            ...
            find(A,start,end,expectedSum-element)
        }

        As you see, two parameters are changing in recursive calls (endIndex and expectedSum).
        So, you need exit conditions for both these parameters.
        Each parameter is changing just once (endIndex is reduced by 1  and  expectedSum is reduced by element value), so you need one or more exit condition(s) for endIndex and one or more exit conditions for expectedSum.

        In recursive calls, if you see endIndex is changing multiple times, then you need minimum two exit conditions for endIndex (start==end, end<start)
        e.g. find(A,start,end-1,expectedSum) || find(A,start,end-2,expectedSum)

        If you see just
        e.g. find(A,start,end-2,expectedSum)
        then also you need end==start and end<start conditions because end-2 can go lesser than start also.


        e.g.
           - FindIfASubsetWithGivenSumExistsInGivenArrayWithAConstraintOfContinuousElements.java
           - FindIfASubsetWithGivenSumExistsInGivenArray.java
           - PaintersPartitionProblem.java

    6) Recursive method's time-complexity

        See README_Memorize_These_Points.docx

    5) When to decide whether to send some extra parameters to recursive method?

    When you start writing exit conditions or process the root/head, you may end up root_to_leaf_problems_hard coding some values. At that time, you will not be sure whether this root_to_leaf_problems_hard coding will work as expected for recursive calls.
    If these return value is not an actual return type, then think whether that value is shared between recursive calls.
    If answer is yes, then add a method parameter and use it instead of root_to_leaf_problems_hard coded value.

        CreateLinkedListForEachLevelOfBinaryTree.java --- level is passed a parameter
        PathsWithSum.java   --- currentSum is passed a parameter

    6) When you need some output from a method, but to derive that you need more output parameters from left and/or right subtrees,
    then those extra output parameters can be passed as method parameters provided that they are shared by recursive calls.

        ValidateBST.java --- compare checkBST_Another_Harder_Approach vs checkBST methods

    7) When to pass and when to avoid whether passing return value of an algorithm method as its method parameter?

        When to pass return value as a method parameter?

            - I would say that start with non-tail-recursion(not passing result as an argument).
              But if you find that you will end up with merging the results like CreateLinkedListForEachLevelOfBinaryTree.java and RobotInGrid.java, then pass result container as method parameter.

                CreateLinkedListForEachLevelOfBinaryTree.java
                RobotInGrid.java
                GetMax.java
                CreateMinimalBST.java
                PathWithSum.java

        When not to pass return value as a method parameter?

            - If your return value is a literal (Integer, Long, String etc). If you pass it as a method parameter, then caller of the method will not see the final result. It will see the same value that it passed as method parameter.
            e.g. ConvertBinaryTreeToBstWithoutChangingItsSpatialStructure.java
                 GetMax.java --- in this case, solution 3 makes more sense, but it depends on the requirement.

                    int max = 1;
                    method(max);
                    System.out.println(max); --- O/P:1

                    void method(int max) { max = 5; }

                    To avoid this,

                    Solution 1:

                        int max = 1;
                        max = method(max);
                        System.out.println(max); --- O/P:5

                        int method(int max) { max = 5; return max;}

                    Solution 2:

                        Result result = new Result(); result.setMax(1);
                        method(result);
                        System.out.println(result.getMax()); --- O/P:5

                        void method(Result result) { result.setMax(5);}

                    Solution 3:

                        int max = method(max);
                        System.out.println(max); --- O/P:5

                        int method() { max = 5; return max;}

            - If you need to modify root's, left subtree's, right subtree's output based on each other's output, then just go with normal approach (not passing result as parameter)


            - To generate left subtree's result, if you need root's result and to generate right subtree's result, if you need root's result and/or left subtree's result,
            then just go with normal approach (not passing result as parameter)

                FindSubsetsOfASet.java

    8) CAUTION to avoid infinite loops in recursion:

        - MatrixChainMultiplication.java

         private static int method(int[] A, int start, int end) {
            .....
            for (int divider = start + 1; divider < end; divider++) {// CAUTION: do not use 'divider <= end'

                int result =
                        // CAUTION: if you say divider <= end, this recursive call will result in infinite recursion because when 'divider' reaches to 'end', it will be like a brand new call with (A, start, divider=end). This is where you started this algorithm from and it will recurse it again from the beginning.
                        method(A, start, divider) +
                        method(A, divider , end) +
                        +(A[start] * A[divider] * A[end]);

                ....
            }
            ....
          }

       - Divide and Conquer (FindPeakInGivenArray.java)

            Always recurse with

                findPeak(A, start, mid - 1 and/or mid+1);

            Do not recurse with findPeak(A, start, mid). It will result in infinite recursions. Because at some point mid=1 will come. At this point, when you do findPeak(A,start=0,mid=1), in this call again mid=(0+1)/2=1. So, you will infinitely end up calling findPeak(A,0,1).


    9) Create separate output variables from root processing, left subtree processing and right subtree processing and then merge them as needed.
    Don’t reserve a chance of using the same output variable between these 3 processings.
    You may not see any problem for a particular algorithm, if you use the same output variable, but it may cause the problem in some very niche conditions for other algorithms. So, better to stick to some principles.

        PathsWithSum.java --- 3 different variables totalPaths, totalPathsFromLeft, totalPathsFromRight are created and then they are merged finally instead of using totalPaths and changing it as need while left and right processings.
                              You may not see any problems in this algorithm, but you may see problems in some other complex algorithms.
                              Creating separate variables may force you to write some extra lines of code, but it is less error prone.

    10) For recursive algorithm, TRY your best to avoid root.left and/or root.right checks in exit conditions. If you cannot avoid it, then it's ok. Otherwise it makes the code complex and root_to_leaf_problems_hard to debug.

        Instead, try to get outputs by traversing root.left and root.right and based on their outputs you make some decision for a root.

            FindLowestCommonAncestorInBinaryTree.java
            ValidateBST.java

        For recursive algorithm, if you can't avoid it, then do it

            if ValidateBST.java is expected to return isBst,min,max as a result instead of just isBst, then it would have been impossible to avoid checking root.left and root.right in exit conditions.

        For iterative algorithm, it's fine to check root.left and root.right.

            FindInOrderSuccessor.java

        NOTE:
        As I mentioned, if you cannot think of any alternative of checking root.left and root.right in exit conditions, then it's ok. Do not panic.

        7) Converting Brute Force approach to Top-Bottom Dynamic Programming (Memoization)

        To understand Brute Force algorithm, see 'Brute Force Sequential Search.mp4' and 'Brute Force String Matching.mp4' videos to understand how Brute Force works with array and string.
        Similar concept is there for Tree also.

        In Brute Force approach, you do the same operation for every single node in a tree. This makes the algorithm visit the same node multiple times.
        Sometimes, this can make Runtime complexity O(n^2) or O(2^n).

        Ideally, you don't want to visit the node more than once to achieve O(n) runtime complexity.
        To achieve this, you either need to think of a better logic or use Memoization.
        It might be sometimes harder to come up with better logic, but converting any Brute Force algorithm to Memoized algorithm is super easy.
        Memoization will use some extra memory to store intermittent values in a map, but runtime complexity will be improved a lot.


            Fibonacci.java
            FindWhetherTreeIsBalanced.java
            PathWithSum.java


    11) time and space complexity
        Read 'Time and Space Complexities' section from 'README_Memorize_These_Points.docx'

    12) How to code TripleSteps.java kind of problem recursively?
        Watch 'Recurrence Relations Part2.mp4'

    13) How to start recursion method for array related algorithms?
        Watch ‘Recursion of Array.mp4’.

        binarySearchRecursive(array, 0, array.length - 1, elementToSearch)

        -	Always pass start and end element position in array to recursive method.
        -	One of the Exit condition will be if(start<end)…
        -	When you need to convert recursive method into iterative method, extra passed parameters to recursive method becomes local variables and after that that you need to add a while loop for reoccurring code.

    14) Two recursive calls in a method

        You know that it is common to have two recursive calls in a tree related algorithm. One with root.left and another one with root.right.
        There can be two recursive calls in 1-D and 2-D array(matrix) related algorithms also.

        e.g. BoxStacking.java
             EightQueens.java

    15) Dynamic Programming
        -------------------

        You can use either Greedy Programming or Dynamic Programming to solve NP-Complete problem.

        What is NP-Complete problem? (read from grokking algorithm self created document)

        e.g. travel sales problem
             set covering problem

             All these problems need all possible combinations of the available elements.
             So, it basically takes n! time or 2^n (exponential) time

             Above two examples take n! time

             Another good example in array related problem that takes 2^n time

             FindIfASubsetWithGivenSumExistsInGivenArray.java --- It uses Dynamic Programming to solve this problem.

             NP-complete (Non-Deterministic Polynomial) algorithm either takes O(n!) or O(2^n)

             e.g. {1,2,3,4}

                 O(n!) situation    (include duplicates)

                    {1}
                    {1,2} {1,3} {1,4}
                    {1,2,3} {1,3,4}
                    {1,2,3,4}

                    {2}
                    {1,2} {2,3} {2,4}
                    {1,2,3} {2,3,4} {1,2,4}
                    {1,2,3,4}

                    {3}
                    {2,3} {3,4}
                    {1,2,3} {2,3,4} {1,3,4}
                    {1,2,3,4}

                    {4}
                    {1,4} {2,4} {3,4}
                    {1,2,4} {2,3,4} {1,3,4}
                    {1,2,3,4}

                O(2^n) situation   (do not include duplicate sets)

                    -
                        {1}
                        {1,2}   {1,3}   {1,4}
                        {1,2,3} {1,3,4}
                        {1,2,3,4}

                        {2}
                        {2,3} {2,4}
                        {2,3,4}

                        {3}
                        {3,4}

                        {4}

                    If you see to create a set of {1,2,3}, you need {1,2} and then you can add 3 to it. So, you can use previously computed results for new result for better optimization.
                    This can be achieved using Dynamic Programming.


                    - Another O(2^n) situation

                    doubling continuous sequence

                        {1}
                        {1,2}
                        {1,2,3}
                        {1,2,3,4}

                        {2}
                        {2,3}
                        {2,3,4}

                        {3}
                        {3,4}

                        {4}

                    Double this number. It will be close to 2^n.

                                            (1,2,3,4)
                              (1,2,3)                   (1,2,3)
                          (1,2)   (1,2)               (1,2) (1,2)
                        (1) (1) (1) (1)              (1) (1)(1) (1)

                   2^n nodes. This is the situation for FindIfASubsetWithGivenSumExistsInGiveArray.java algorithm.

                   - Fibonacci Series situation

                                            (1,2,3,4)
                              (1,2,3)                   (1,2)
                          (1,2)   (1)               (1)     (1)
                        (1)                       (1)

                    This is a similar situation as fibonacci series.

                                    fib(n)
                            fib(n-1)    fib(n-2)

                    It will also have ~ 2^n nodes

                    -   Longest Increasing SubSequence situation

                                            (1,2,3,4,5)
                                    (1,2,3,4)         (1,2,3)           (1,2)       (1)
                                (1,2,3) (1,2) (1)    (1,2) (1)          (1)
                               (1,2) (1) (1)        (1)
                               (1)

                    It will also have ~ 2^n nodes. LongestIncreasingSubSequenceInArray.java



                 n!
               -------  situation (do not include duplicate sets of specific k number of elements) (This is not occur for NP-Complete problem)
               k!(n-k)!

                   When you need to find combinations of k=3 elements in such a way that there are no duplicate combinations, then use above formula.
                   e.g. FindTripletWithMaxSum.java


        When can you use dynamic programming?

            'Optimal Substructure and Overlapping Problem - Prerequisite for Dynamic Programming.mp4'

            when you have
            - optimal substructure
             if problem can be divided into subproblems and the solutions of subproblems can be used to construct the solution of a bigger problem (basically you can use recursion)
                and
            - overlapping problems (like fibonacci.java, LongestIncreasingSubSequenceInArray.java)
            function with same input is called multiple times or function’s output depends on previously calculated output of the function with previous parameters.

            When you see O(2^n), O(n * 2^n), O(n!) problems, there is a possibility of using Dynamic Programming provided that
            - there are at least two varying variables in recursive method calls (using which you can create a key in memoization table(draw a 2-D matrix))
                or
              if there is just one varying variable, then recursion is happening like LIS (LongestIncreasingSubSequenceInArray.java) algorithm.

            - there are at least two or more recursive calls for each method call.

            e.g. FindIfASubsetWithGivenSumExistsInGivenArray.java --- there are two varying variables (index and sum) using which you can create a key for memoization table.
                 LongestIncreasingSubSequenceInArray.java --- there is just one varying variable, but recursion is happens multiple times
                 BalancedExpressionWithReplacement.java --- even though there are two varying variables (index and stack), you cannot use Dynamic Programming because it is not possible to create a key from stack variable. To do that, you may need to iterate through entire stack when you want to generate a key.


        When can you use Greedy programming?

            When you have
            - optimal substructure
            but NOT
            - overlapping problem


        Brute-Force and Dynamic Algorithms give most optimal solution, whereas Greedy Algorithms give close to optimal solution.

        All problems that can be solved using Dynamic Programming can be solved using Greedy Programming also provided that you are ok with close to optimal solution and not the most optimal solution.
        Vice-a-versa is not true.
        e.g. KnapSack.java uses Dynamic Programming


        How to figure out from your code whether you can use Dynamic Programming to improve the performance?

            Read
            - FindIfASubsetWithGivenSumExistsInGivenArrayWithAConstraintOfContinuousElements.java
            - FindIfASubsetWithGivenSumExistsInGivenArray.java
            - PaintersPartitionProblem.java

        Two approaches of Dynamic Programming:

            - Top-Down Dynamic Programming    (Memoization For recursive method results)

                Normally, you write Brute-Force code first and then if you see Overlapping Problem(function with same parameters is being called more than once in recursive tree),
                then you can easily convert Brute-Force into Top-Down Dynamic programming approach by memoizing the return values of the function for different parameters.

                How to decide what should be the key for memoization table(array/map) for Top-Down Dynamic Approach?

                    In memoization array/map, you need to have index/key as parameters that are changing in recursive call.
                    e.g.
                    method(int[] a, int startIndex, int endIndex) {
                        ...
                        method(a, startIndex+1, endIndex);
                        ...
                    }
                    As you see on recursive call, startIndex is changing. So, if you need to memoize the result of method with different parameters,
                    then you can use memoization array/map that has index/key as array indices.

                    There can be more than one parameters also that changes on recursive calls.
                    You need to concatenate those parameters and store as a key in Map<String,...>

                Top-Down Dynamic Programming Approach examples:

                    Array
                        Fibonacci.java ------- Read this thoroughly to understand the difference between Top-Bottom Dynamic Programming(Memoization) and Bottom-Up Dynamic Programming.
                        FindIfASubsetWithGivenSumExistsInGivenArray.java --- Read this thoroughly
                        PaintersPartitionProblem.java --- Important concept to remember when more than one parameters in recursive method are getting reduces simultaneously and there is no other call(s) to recursive method that reduces them one by one.
                        RodCuttingProblem.java --- Important concept to remember when you can use more than one quantities of the same element to get the result
                    Tree
                        CreateMinimalBST.java
                        PathWithSum.java


            - Bottom-Up Dynamic Programming (Memoization + avoids using recursion)

                CAUTION:
                Try to avoid this approach because it acquires a lot of memory to store 2-D memoization array for large input.
                Practice Top-Down approach.

                For Bottom-Up Dynamic Programming, you need to draw 2-D matrix and fill up the values in the cells.
                Value in the cell will be computed based on prev cell(s) value(s).

                When you are filling up the values in the cells based on previous and current values, you will come up with formulas for your code.
                Sometimes, this becomes a tricky part.

                Important thing to remember is that
                - you need to have both dimensions in sorted form. (NOT NECESSARY. It is proven by running the examples with unsorted values)
                e.g. in CoinChange.java problem, if coins need to be sorted first, if they are not.
                - you need to have one additional row and col (0th row and 0th col).
                It is easy to fill up 0th row most of the time, but to fill up 0th col (which will be the base condition of the code), you sometimes need to wait till you reach to some point in filling up the matrix.

                Read
                - FindIfASubsetWithGivenSumExistsInGivenArray.java
                - CoinChange.java



                1-D and 2-D problems for Bottom-Up approach:

                    In Bottom-Up approach, you can use either 1-D array or 2-D array for memoization.

                    Examples of 1-D array
                        Fibonacci.java
                        TripleSteps.java

                    Examples of 2-D array
                        LongestCommonSubSequence.java ------ Understand this thoroughly
                        CoinsChange.java ------ Understand this thoroughly to understand how start thinking directly with Bottom-Up Dynamic Programming.
                        CoinsChangeMin.java

                    Important:

                        2-D problems are a bit tricky. You need to think recursion in a bit different way.
                        You start reducing the problem by 1 to start with last element instead of first. This makes to think of 2-D problem solution easy.
                        Read CoinsChange.java thoroughly.
                        I tried EightQueens.java also starting from last element.

                Standard code template for Bottom-Up Dynamic programming that uses 2-D matrix

                - pre-initialize first row of a 2-D matrix
                - pre-initialize first column of 2-D a matrix
                - two for looks (one inside another). one for loop iterates rows and another one iterates cols.

                    // start iterating from second row
                    for (int row = 1; row < memo.length; row++) {

                        // start iterating from second col
                        for (int col = 1; col < memo[row].length; col++) {

                            if (element == col) { // base condition 3
                                memo[row][col] = .....;
                            } else if (element > sum) { // base condition 4
                                memo[row][col] = .....;
                            } else {
                                .. fill up the matrix based on some formula ...
                                // you have to make sure that your formula doesn't throw ArrayIndexOutOfBoundException. See below algorithm.
                            }
                        }
                    }


        IMPORTANT:
            It is advisable that you write your Brute-Force recursion by considering the last elements for both dimensions to reduce the problem by one.
            This concept is applicable not only for 2-D problems, but also for matrix traversal problems where you are given a matrix and you need to place certain items in it or traverse the matrix in certain fashion (e.g. EightQueens.java, RobotInGrid.java).
            For trees and arrays (1-D) related algorithms, to reduce the problem by 1, we consider root element or 1st element of an array.



*/
public class RecursionAndDynamicProgrammingFundamentals {

}
