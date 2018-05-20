package algorithms.crackingcodinginterviewbook._4tree_and_graph.tree;

import algorithms.crackingcodinginterviewbook._4tree_and_graph.tree.baseclasses.BST;
import algorithms.crackingcodinginterviewbook._4tree_and_graph.tree.baseclasses.TreeNode;

/**
 * @author Tushar Chokshi @ 11/6/15.
 */

// http://www.geeksforgeeks.org/find-maximum-path-sum-in-a-binary-tree/
public class FindMaximumSumInPathOfBinaryTree {
    public static void main(String[] args) {
        BST bst = BST.createBST();
        System.out.println(findMaxSumInPath(bst.root));
        System.out.println(findMaxSumInPathAnotherWay(bst.root, 0));

    }

    /*
    very simple concept of reducing the problem by 1 for recursion.
    First think of exit conditions
    - what if root is null
    - what if root doesn't have left and right sub trees
    Then call method recursively for left subtree and right subtree
    Then think how to add root with the result of left and right subtree recursions

    It looks like below example is using postOrder traversal because we are visiting root.data after left and right subtree traversals.
    It is easy to think compare to preOrder traversal.
     */
    private static int findMaxSumInPath(TreeNode root) {
        if(root == null) { // exit cond 1
            return 0;
        }
        if(!root.hasLeft() && !root.hasRight()) { // exit cond 2
            return root.data;
        }

        // reducing problem by 1 by finding max sumIteratively in path in left and right sub trees
        int leftTreeSum = findMaxSumInPath(root.left);
        int rightTreeSum = findMaxSumInPath(root.right);

        // adding root data to the result of left and right subtree results
        leftTreeSum+=root.data;
        rightTreeSum+=root.data;

        // return the final result by comparing leftTreeSum and rightTreeSum
        if(leftTreeSum >= rightTreeSum) {
            return leftTreeSum;
        } else {
            return rightTreeSum;
        }
    }

    // converting above post order traversal to pre order traversal
    // root.data is visited before left and right subtree traversals.
    private static int findMaxSumInPathAnotherWay(TreeNode root, int sum) {
        if(root == null) { // exit cond 1
            return sum;
        }
        if(!root.hasLeft() && !root.hasRight()) { // exit cond 2
            return sum + root.data;
        }

        sum = sum + root.data;
        // reducing problem by 1 by finding max sumIteratively in path in left and right sub trees
        int leftTreeSum = findMaxSumInPathAnotherWay(root.left, sum);
        int rightTreeSum = findMaxSumInPathAnotherWay(root.right, sum);


        // return the final result by comparing leftTreeSum and rightTreeSum
        if(leftTreeSum >= rightTreeSum) {
            return leftTreeSum;
        } else {
            return rightTreeSum;
        }
    }
}
