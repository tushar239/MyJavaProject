package algorithms.treeandgraph.tree;

import algorithms.treeandgraph.tree.baseclasses.BST;
import algorithms.treeandgraph.tree.baseclasses.TreeNode;


/*
This is very important algorithm that tells you a few thing about recursion.
- reducing the problem by 1
- when you should decide to pass extra parameter to a method
- how to write an exit condition


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

RecursionConcepts.java’s getMax and getMax_TailRecursion algorithms

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

 */
public class _0RecursionConcepts {

    public static void main(String[] args) {
        // find max from Binary Tree recursively
        {
            BST bst = BST.createBST();
            System.out.println("Max: " + getMax_TailRecursion(bst.root, Integer.MIN_VALUE));

            System.out.println("Max: " + getMax(bst.root));
        }
    }

    // find max from Binary Tree
    /*
    In this method, I started writing exit condition first

    if(root == null) return ???

    I was struggling to figure out what should be the return value.

    Idea that came to mind is can I get away from that problem by passing 'max' as method parameter?
    Answer was 'yes'.

    Next question that I asked myself was 'does method parameter 'max' need to be shared between recursive calls?'
    Answer was 'yes' because value of 'max' changes as we recurse.

    So, I decided to use 'max' as method parameter

    REMEMBER:
    When you pass a result of a method as a parameter, you are moving towards Tail-Recursion approach that is a bit complex.
    Try to solve the problem without Tail-Recursion first.
    If you are trying to pass a variable to a method that is not an output of the method, then it's ok. It's not a tail-recursion. e.g. PathsWithSum.java's countPathsWithSum(TreeNode root, int expectedSum, int currentSum) method.

    See getMax to see non-tail-recursive approach. I would do this first for simplicity of logic thinking.
     */
    private static int getMax_TailRecursion(TreeNode root, int max) {
        if(root == null) return max;
        if(root.data > max) {max=root.data;}

        getMax_TailRecursion(root.left, max);
        return getMax_TailRecursion(root.right, max);
    }

    /*
    In this method, I started thinking reduce the problem by 1 approach instead of thinking about
    which value needs to be shared between recursive calls and how to get away from returning hard coded value
    from exit condition.

    My thought process:
    I don't want to share the parameter 'max' between recursive calls because that's the return value of this method.
    left and right subtrees returns the max values (maxFromLeft and maxFromRight).
    How to use these values with root's data?
     */
    private static Integer getMax(TreeNode root) {
        if(root == null) return null;

        Integer maxOfRoot = root.data;

        Integer maxFromLeft = getMax(root.left);
        if(maxOfRoot == null || (maxOfRoot != null && maxFromLeft != null && maxFromLeft > maxOfRoot)) maxOfRoot = maxFromLeft;
        
        Integer maxFromRight = getMax(root.right);
        if(maxOfRoot == null || (maxOfRoot != null && maxFromRight != null && maxFromRight > maxOfRoot)) maxOfRoot = maxFromRight;

        return maxOfRoot;
    }
}
