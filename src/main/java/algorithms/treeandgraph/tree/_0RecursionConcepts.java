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

3) Value that is expected as a returned value from recursive method, don’t pass it as a method parameter to get away with the problem of hard coding return value of exit condition.

RecursionConcepts.java’s getMax algorithm

4) When you expect more than one values as output from left and right subtree processing. Think whether those values are shared between recusive calls. If yes, then pass them as parameter and return only required value from a method.

ValidateBST.java
PathsWithSum.java

5) Create separate output variables from root processing, left subtree processing and right subtree processing and then merge them as needed. Don’t take a chance of using the same output variable between these 3 processings.

PathsWithSum.java


 */
public class _0RecursionConcepts {

    public static void main(String[] args) {
        // find max from Binary Tree recursively
        {
            BST bst = BST.createBST();
            System.out.println("Max: " + getMax(bst.root, Integer.MIN_VALUE));

            System.out.println("Max: " + getMax_Improved(bst.root));
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

    So, I decided to use 'max' as method parameter and

    But, I made the code unnecessarily complex by passing the same parameter that I am returning from the method.
    When you see this, you can do better as shown in getMax_Improved
     */
    private static int getMax(TreeNode root, int max) {
        if(root == null) return max;
        if(root.data > max) max=root.data;

        int maxFromLeftSubTree = getMax(root.left, max);
        max = maxFromLeftSubTree > max ? maxFromLeftSubTree : max;

        int maxFromRightSubTree = getMax(root.right, max);
        max = maxFromRightSubTree > max ? maxFromRightSubTree : max;

        return max;
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
    private static Integer getMax_Improved(TreeNode root) {
        if(root == null) return null;

        Integer max = root.data;

        Integer maxFromLeft = getMax_Improved(root.left);
        if(max == null || (max != null && maxFromLeft != null && maxFromLeft > max)) max = maxFromLeft;


        Integer maxFromRight = getMax_Improved(root.right);
        if(max == null || (max != null && maxFromRight != null && maxFromRight > max)) max = maxFromRight;

        return max;
    }
}
