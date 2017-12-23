package algorithms._4treeandgraph.tree;

/*
    This is very important algorithm that tells you a few thing about recursion.
    - reducing the problem by 1
    - when you should decide to pass extra parameter to a method
    - how to write an exit condition
    - Memoization to replace Brute Force disadvantages


    Recursion and Dynamic Programming Concepts
    ------------------------------------------

    1) How to think Reduce the problem by 1 concept?

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

    2) When to decide whether to send some extra parameters to recursive method?
    When you start writing exit conditions or process the root/head, you may end up hard coding some values. At that time, you will not be sure whether this hard coding will work as expected for recursive calls.
    If these return value is not an actual return type, then think whether that value is shared between recursive calls.
    If answer is yes, then add a method parameter and use it instead of hard coded value.

        CreateLinkedListForEachLevelOfBinaryTree.java --- level is passed a parameter
        PathsWithSum.java   --- currentSum is passed a parameter

    3) When you expect more than one values as output from left and right subtree processing, what output is expected by the algorithm and whether other output parameters needed by left and right subtrees are shared between recursive calls.
    If yes, then pass them as parameter and return only required value from the algorithm(actual method).

        ValidateBST.java --- compare checkBST_Another_Harder_Approach vs checkBST methods

    Here, you don't have wrap min and max under some wrapper class because min and max are being used to check some conditions and they are not a part of final result.
    If they would have been a part of final result, then they have to be wrapped by some Result object and this Result object should be modified in the method code as needed.

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

    6) Try your best to avoid root.left and/or root.right checks, if it is a recursive algorithm.
       For iterative algorithm, it is fine to do that.

    Instead, try to get outputs by traversing root.left and root.right and based on their outputs you make some decision for a root.

        FindLowestCommonAncestorInBinaryTree.java


    For iterative algorithm, it's fine to check root.left and root.right.

        FindInOrderSuccessor.java

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
    Read 'Recursive Runtimes and Space Complexities' section from 'README_Memorize_These_Points.docx'

*/
public class _0RecursionConcepts {

}
