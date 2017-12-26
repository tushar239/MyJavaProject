package algorithms._4treeandgraph.tree;

/*
    This is very important algorithm that tells you a few thing about recursion.
    - reducing the problem by 1
    - when you should decide to pass extra parameter to a method
    - how to write an exit condition
    - Memoization to replace Brute Force disadvantages


    Recursion and Dynamic Programming Concepts
    ------------------------------------------

    1) How to think Reduce the problem by 1 or Half concept?

    Reducing the problem by 1:

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

     Reducing the problem by half:

        Sometimes it is obvious to reduce the problem by half like Binary Search.

        But sometimes, it is challenging to think about reducing the problem by half instead of 1.
        e.g. RecursiveMultiply.java
        This problem can be solved in both ways, but if you use 'reduce the problem by half' technique, then it is more efficient.


     Binary Tree problems are the combination of Reduce the problem by 1 and half.
     You reduce the problem by 1 by processing a root and reduce the problem by half by processing the remaining tree into to halves (root's left and root's right)

    2) When to decide whether to send some extra parameters to recursive method?
    When you start writing exit conditions or process the root/head, you may end up hard coding some values. At that time, you will not be sure whether this hard coding will work as expected for recursive calls.
    If these return value is not an actual return type, then think whether that value is shared between recursive calls.
    If answer is yes, then add a method parameter and use it instead of hard coded value.

        CreateLinkedListForEachLevelOfBinaryTree.java --- level is passed a parameter
        PathsWithSum.java   --- currentSum is passed a parameter

    3) When you need some output from a method, but to derive that you need more output parameters from left and/or right subtrees,
    then those extra output parameters can be passed as method parameters provided that they are shared by recursive calls.

        ValidateBST.java --- compare checkBST_Another_Harder_Approach vs checkBST methods

    4) When to pass and when to avoid whether passing return value of an algorithm method as its method parameter?

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

             e.g. GetMax.java --- in this case, solution 3 makes more sense, but it depends on the requirement.

        - If you need to modify root's, left subtree's, right subtree's output based on each other's output, then just go with normal approach (not passing result as parameter)


        - To generate left subtree's result, if you need root's result and to generate right subtree's result, if you need root's result and/or left subtree's result,
        then just go with normal approach (not passing result as parameter)

            FindSubsetsOfASet.java


    5) Create separate output variables from root processing, left subtree processing and right subtree processing and then merge them as needed.
    Don’t take a chance of using the same output variable between these 3 processings.
    You may not see any problem for a particular algorithm, if you use the same output variable, but it may cause the problem in some very niche conditions for other algorithms. So, better to stick to some principles.

        PathsWithSum.java --- 3 different variables totalPaths, totalPathsFromLeft, totalPathsFromRight are created and then they are merged finally instead of using totalPaths and changing it as need while left and right processings.
                              You may not see any problems in this algorithm, but you may see problems in some other complex algorithms.
                              Creating separate variables may force you to write some extra lines of code, but it is less error prone.

    6) For recursive algorithm, TRY your best to avoid root.left and/or root.right checks in exit conditions. If you cannot avoid it, then it's ok. Otherwise it makes the code complex and hard to debug.

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

    8) Top-Bottom Dynamic Programming(Memoization) and Bottom-Up Dynamic Programming
    As mentioned in 6), you can use memoization to get away from the disadvantages of Brute Force.
    You can also use it to convert your algorithm into Tail-Recursive algorithm.

        Fibonacci.java ------- Read this thoroughly to understand the difference between Top-Bottom Dynamic Programming(Memoization) and Bottom-Up Dynamic Programming.
        CreateMinimalBST.java
        PathWithSum.java

    9) time and space complexity
    Read 'Time and Space Complexities' section from 'README_Memorize_These_Points.docx'

    10) How to code TripleSteps.java kind of problem recursively?
    Watch 'Recurrence Relations Part2.mp4'

    11) How to start recursion method for array related algorithms?
    Watch ‘Recursion of Array.mp4’.

    binarySearchRecursive(array, 0, array.length - 1, elementToSearch)

    -	Always pass start and end element position in array to recursive method.
    -	One of the Exit condition will be if(start<end)…
    -	When you need to convert recursive method into iterative method, extra passed parameters to recursive method becomes local variables and after that that you need to add a while loop for reoccurring code.



*/
public class _0RecursionConcepts {

}
