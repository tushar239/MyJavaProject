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
How to know that you need to pass some extra parameters as input to the recursive method?

RecursionConcepts.java

2) First just reduce the problem by 1. Recurse left and right subtrees and think how to merge it with root processing.
If you need to hardcode any value other than actual return type of a method while processing root or while returning a value (other than null) from exit condition, think whether that value is shared between recursive method calls. If yes, then pass it as method parameter.

CreateLinkedListForEachLevelOfBinaryTree.java
PathsWithSum.java

3) When you expect more than one values as output from left and right subtree processing. Think whether those values are shared between recursive calls.
If yes, then pass them as parameter and return only required value from a method.

ValidateBST.java
PathsWithSum.java' countPathsWithSum(TreeNode root, int expectedSum, int currentSum) method

4) Value that is expected as a returned value from recursive method, don’t pass it as a method parameter to get away with the problem of hard coding return value of exit condition.
If you want to do it, you need to think of Tail-Recursion strategy that is a bit complex.

GetMax.java
CreateMinimalBST.java
PathWithSum.java

5) Create separate output variables from root processing, left subtree processing and right subtree processing and then merge them as needed.
Don’t take a chance of using the same output variable between these 3 processings.
You may not see any problem for a particular algorithm, if you use the same output variable, but it may cause the problem in some very niche conditions for other algorithms. So, better to stick to some principles.

PathsWithSum.java

6) Converting Brute Force approach to Memoization

To understand Brute Force algorithm, see 'Brute Force Sequential Search.mp4' and 'Brute Force String Matching.mp4' videos to understand how Brute Force works with array and string.
Similar concept is there for Tree also.

In Brute Force approach, you do the same operation for every single node in a tree. This makes the algorithm visit the same node multiple times.
Sometimes, this can make Runtime complexity O(n^2).

Ideally, you don't want to visit the node more than once to achieve O(n) runtime complexity.
To achieve this, you either need to think of a better logic or use Memoization.
It might be sometimes harder to come up with better logic, but converting any Brute Force algorithm to Memoized algorithm is super easy.
Memoization will use some extra memory to store intermittent values in a map, but runtime complexity will be improved a lot.


FindWhetherTreeIsBalanced.java
PathWithSum.java

7) Top-Bottom Dynamic Programming(Memoization) and Bottom-Up Dynamic Programming
As mentioned in 6), you can use memoization to get away from the disadvantages of Brute Force.
You can use it convert your algorithm into Tail-Recursive algorithm also.

Fibonacci.java ------- Read this thoroughly to understand the difference between Top-Bottom Dynamic Programming(Memoization) and Bottom-Up Dynamic Programming.
CreateMinimalBST.java
PathWithSum.java
*/
public class _0RecursionConcepts {

}
